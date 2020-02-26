package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Contact;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            if (modifyResume("UPDATE resume SET full_name =? WHERE uuid=?", resume) == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            deleteContacts(resume);
            addContacts(resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            modifyResume("INSERT INTO resume (full_name, uuid) VALUES (?, ?)", resume);
            addContacts(resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            String fullName;
            Map<Contact, String> contactSection = new EnumMap<>(Contact.class);
            boolean isExist;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume where uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                isExist = rs.next();
                if (!isExist) {
                    throw new NotExistStorageException(uuid);
                }
                fullName = rs.getString("full_name");
            }
            if (isExist) {
                try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact where resume_uuid = ?")) {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        contactSection.put(Contact.valueOf(rs.getString("type")), rs.getString("values"));
                    }
                }
            }
            return new Resume(uuid, fullName, contactSection);
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
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY  full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    for (Resume r : list) {
                        r.addContact(Contact.valueOf(rs.getString("type")), rs.getString("values"));
                    }
                }
            }
            return null;
        });
        return list;
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

    private int modifyResume(String sql, Resume resume) {
        return sqlHelper.execute(sql, ps -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                    return ps.executeUpdate();
                }
        );
    }

    private void addContacts(Resume resume) {
        sqlHelper.execute("INSERT INTO contact (values, type, resume_uuid) VALUES (?, ?, ?)", ps -> {
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
            return null;
        });
    }

    private void deleteContacts(Resume resume) {
        sqlHelper.execute("DELETE FROM contact where resume_uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }
}