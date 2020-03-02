package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume where uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    resume.addContact(Contact.valueOf(rs.getString("type")), rs.getString("values"));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    forGet(rs, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume order by full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    map.put(uuid, new Resume(uuid, fullName));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * from contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resumeUuid = rs.getString("resume_uuid");
                    map.get(resumeUuid).addContact(Contact.valueOf(rs.getString("type")), rs.getString("values"));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resumeUuid = rs.getString("resume_uuid");
                    forGet(rs, map.get(resumeUuid));
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume where uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(uuid) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            if (modifyResume(conn, "UPDATE resume SET full_name =? WHERE uuid=?", resume) == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            deleteContacts(conn, resume);
            deleteSections(conn, resume);
            addContacts(conn, resume);
            addSection(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            modifyResume(conn, "INSERT INTO resume (full_name, uuid) VALUES (?, ?)", resume);
            addContacts(conn, resume);
            addSection(conn, resume);
            return null;
        });
    }

    private int modifyResume(Connection conn, String sql, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            return ps.executeUpdate();
        }
    }

    private void addContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (values, type, resume_uuid) VALUES (?, ?, ?)")) {
            for (Map.Entry<Contact, String> e : resume.getContactSection().entrySet()) {
                String value = e.getValue();
                dataForResume(ps, value, e.getKey().name(), resume);
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact where resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void addSection(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (text_section, type, resume_uuid) VALUES (?, ?, ?)")) {
            for (Map.Entry<TypeSection, AbstractSection> e : resume.getResumeSection().entrySet()) {
                switch (e.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        String textSection = ((TextSection) e.getValue()).getTextContainer();
                        if (textSection != null) {
                            dataForResume(ps, textSection, e.getKey().name(), resume);
                            break;
                        }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = ((ListSection) e.getValue()).getTextList();
                        if (list.size() != 0) {
                            dataForResume(ps, String.join("\n", list), e.getKey().name(), resume);
                        }
                        break;
                }
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section where resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void dataForResume(PreparedStatement ps, String text, String name, Resume resume) throws SQLException {
        ps.setString(1, text);
        ps.setString(2, name);
        ps.setString(3, resume.getUuid());
        ps.addBatch();
    }

    private void forGet(ResultSet rs, Resume r) throws SQLException {
        TypeSection type = TypeSection.valueOf(rs.getString("type"));
        String textSection = rs.getString("text_section");
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection ts = new TextSection(textSection);
                r.addSection(type, ts);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection ls = new ListSection(Arrays.asList(textSection.split("\\n")));
                r.addSection(type, ls);
                break;
        }
    }
}