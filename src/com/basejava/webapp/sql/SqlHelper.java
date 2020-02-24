package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Contact;
import com.basejava.webapp.model.Resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, SqlQuery<T> sqlQuery) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return sqlQuery.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

//    public void modifyContact(PreparedStatement ps, Resume resume) {
//        try (ps) {
//            for (Map.Entry<Contact, String> e : resume.getContactSection().entrySet()) {
//                ps.setString(1, e.getValue());
//                ps.setString(2, e.getKey().name());
//                ps.setString(3, resume.getUuid());
//                ps.addBatch();
//            }
//        ps.executeBatch();
//        } catch (SQLException e) {
//            throw ExceptionUtil.convertException(e);
//        }
//    }
}