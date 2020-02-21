package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }


    @Override
    public void update(Resume resume) {
        get(resume.getUuid());
        PreparedStatement ps = sqlHelper.execute(conn -> conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid=?"));
        try {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ps.execute();
        } catch (SQLException e) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        PreparedStatement ps = sqlHelper.execute(conn -> conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)"));
        try {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        PreparedStatement ps = sqlHelper.execute(conn -> conn.prepareStatement("SELECT * FROM resume where uuid=?"));
        try {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        get(uuid);
        PreparedStatement ps = sqlHelper.execute(conn -> conn.prepareStatement("DELETE FROM resume where uuid=?"));
        try {
            ps.setString(1, uuid);
            ps.execute();
        } catch (SQLException e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        PreparedStatement ps = sqlHelper.execute(conn -> conn.prepareStatement("SELECT * FROM resume order by full_name"));
        try {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").substring(0, 5), rs.getString("full_name")));
            }
            return resumes;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        PreparedStatement ps = sqlHelper.execute(conn -> conn.prepareStatement("SELECT count(uuid) FROM resume"));
        try {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void clear() {
        PreparedStatement ps = sqlHelper.execute(conn -> conn.prepareStatement("DELETE FROM resume"));
        try {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

interface SqlQuery {
    PreparedStatement execute(Connection conn) throws SQLException;
}

class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        };
    }

    public PreparedStatement execute(SqlQuery sqlQuery) {
        try {
            Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = sqlQuery.execute(conn);
            return ps;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}