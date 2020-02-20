package com.basejava.webapp.sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class  SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper (String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute (GetConnectionResume connectionResume, String sqlQuery, Exception ex){
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            connectionResume.execute();
        } catch (SQLException e) {
             ex.printStackTrace();
        }
    }
}

interface GetConnectionResume {
    void execute();
}

