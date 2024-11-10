package com.example.application.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect_to_DataBase {
        private static Connect_to_DataBase instance;
        private Connection connection;
        private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
        private static final String USER = "readonlyuser";
        private static final String PASSWORD = "password";

        private Connect_to_DataBase() throws SQLException {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Ошибка при подключении к базе данных: " + e.getMessage());
            }
        }

        public static synchronized Connect_to_DataBase getInstance() throws SQLException {
            if (instance == null) {
                instance = new Connect_to_DataBase();
            }
            return instance;
        }

        public Connection getConnection() {
            return connection;
        }

        public void closeConnection() throws SQLException {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

        public boolean isConnectionSuccessful() {
            if (connection == null) {
                return false;
            }
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT 1")) {
                return resultSet.next();
            } catch (SQLException e) {
                System.err.println("Ошибка при проверке подключения: " + e.getMessage());
                return false;
            }
        }
}



