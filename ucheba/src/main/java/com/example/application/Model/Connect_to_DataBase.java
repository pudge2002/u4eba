package com.example.application.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connect_to_DataBase {

    private static Connect_to_DataBase instance;
    private Connection connection;
    private static final String URL = "jdbc:postgresql://26.133.59.159:5432/postgres";
    private static final String USER = "readonlyuser";
    private static final String PASSWORD = "password";
    // Private constructor to prevent instantiation
    private Connect_to_DataBase() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Public method to get the single instance of the class
    public static synchronized Connect_to_DataBase getInstance() throws SQLException {
        if (instance == null) {
            instance = new Connect_to_DataBase();
        }
        return instance;
    }

    // Method to get the connection
    public Connection getConnection() {
        return connection;
    }

    // Method to close the connection
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // проверка подключения
    public boolean isConnectionSuccessful() {
        if (connection == null) {
            return false;
        }
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            // Получение единственного экземпляра подключения к базе данных
            Connect_to_DataBase dbConnection = Connect_to_DataBase.getInstance();
            Connection connection = dbConnection.getConnection();

            // Проверка успешности подключения
            if (dbConnection.isConnectionSuccessful()) {
                System.out.println("Подключение к базе данных успешно!");
            } else {
                System.out.println("Подключение к базе данных не удалось.");
            }

            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

