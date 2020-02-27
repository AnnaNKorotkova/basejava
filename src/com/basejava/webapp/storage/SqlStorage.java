package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Contact;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            String fullName;
            Map<Contact, String> contactSection = new EnumMap<>(Contact.class);
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume where uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                } else {
                    fullName = rs.getString("full_name");
                    try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM contact where resume_uuid = ?")) {
                        ps1.setString(1, uuid);
                        ResultSet rs1 = ps1.executeQuery();
                        while (rs1.next()) {
                            contactSection.put(Contact.valueOf(rs1.getString("type")), rs1.getString("values"));
                        }
                    }
                }
            }
            return new Resume(uuid, fullName, contactSection);
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                "SELECT * " +
                "FROM resume r LEFT JOIN contact c on r.uuid = c.resume_uuid " +
                "ORDER BY r.full_name, r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();

            if (rs.next()) {
                String currenUuid;
                do {
                    currenUuid = rs.getString("uuid");
                    Resume resume = new Resume(currenUuid, rs.getString("full_name"));
                    do {
                        resume.addContact(Contact.valueOf(rs.getString("type")), rs.getString("values"));
                    } while (rs.next() && currenUuid.equals(rs.getString("uuid")));
                    resumes.add(resume);
                } while (!rs.isAfterLast());
            }
            return resumes;
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
            addContacts(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            modifyResume(conn, "INSERT INTO resume (full_name, uuid) VALUES (?, ?)", resume);
            addContacts(conn, resume);
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
                if (value != null) {
                    ps.setString(1, value);
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, resume.getUuid());
                    ps.addBatch();
                }
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
}