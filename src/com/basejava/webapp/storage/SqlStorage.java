package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Contact;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ExceptionUtil;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
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
            if (modifyResume(conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid=?"), resume) == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            modifyContact(conn.prepareStatement("UPDATE contact SET values =? WHERE type=? and resume_uuid = ?"), resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            modifyResume(conn.prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?, ?)"), resume);
            modifyContact(conn.prepareStatement("INSERT INTO contact (values, type, resume_uuid) VALUES (?, ?, ?)"), resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                "SELECT * " +
                "FROM resume r  LEFT JOIN  contact c on r.uuid = c.resume_uuid " +
                "WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                resume.addContact(Contact.valueOf(rs.getString("type")), rs.getString("values"));
            } while (rs.next());
            return resume;
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
        return sqlHelper.execute(
                "SELECT * FROM resume r ORDER BY  full_name, uuid", ps -> {
                    List<Resume> resumes = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        resumes.add(get(rs.getString("uuid")));
                    }
                    return resumes;
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

    private static int modifyResume(PreparedStatement ps, Resume resume) {
        try (ps) {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    private static void modifyContact(PreparedStatement ps, Resume resume) {
        try (ps) {
            for (Map.Entry<Contact, String> e : resume.getContactSection().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, e.getKey().name());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
}