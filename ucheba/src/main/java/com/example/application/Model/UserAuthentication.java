package com.example.application.Model;

import com.example.application.Model.Connect_to_DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {
    private Connect_to_DataBase dbConnection;

    public UserAuthentication() {
        try {
            dbConnection = Connect_to_DataBase.getInstance();
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к базе данных: " + e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        UserAuthentication auth = new UserAuthentication();
        String username = "newuser";
        String password = "hashed_password";
        if (auth.authenticate(username, password)) {
            System.out.println("Аутентификация успешна!");
        } else {
            System.out.println("Аутентификация не удалась.");
        }
    }
}
