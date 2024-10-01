package com.example.application.Model;

import com.example.application.Model.Connect_to_DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {
    private Connect_to_DataBase dbConnection;

    public UserAuthentication() throws SQLException {
        dbConnection = Connect_to_DataBase.getInstance();
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
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            UserAuthentication auth = new UserAuthentication();
            String username = "newuser";
            String password = "hashed_password";

            if (auth.authenticate(username, password)) {
                System.out.println("Аутентификация успешна!");
            } else {
                System.out.println("Аутентификация не удалась.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
