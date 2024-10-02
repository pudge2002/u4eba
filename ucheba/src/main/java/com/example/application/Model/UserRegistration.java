package com.example.application.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistration {
    private Connect_to_DataBase dbConnection;

    public UserRegistration() {
        try {
            dbConnection = Connect_to_DataBase.getInstance();
        } catch (SQLException e) {
            System.err.println("Ошибка при подключении к базе данных: " + e.getMessage());
        }
    }

    public boolean register(String username, String password, String email) {
        String query = "INSERT INTO users (username, password_hash, email) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при регистрации пользователя: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        UserRegistration registration = new UserRegistration();
        String username = "newuser23";
        String password = "newpassword";
        String email = "olyxandrey@";

        if (registration.register(username, password, email)) {
            System.out.println("Регистрация успешна!");
        } else {
            System.out.println("Регистрация не удалась.");
        }
    }
}
