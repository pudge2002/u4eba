package com.example.application.Model;

import com.example.application.localdata.Media;
import com.example.application.localdata.Post;
import com.example.application.localdata.UserData;
import com.vaadin.flow.server.VaadinSession;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Connect_to_DataBase dbConnection;

    public Controller() throws SQLException {
        dbConnection = Connect_to_DataBase.getInstance();
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
        Connection connection = dbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    String userEmail = resultSet.getString("email");
                    String userName = resultSet.getString("username");
                    String userDescription = resultSet.getString("description");
                    String userAvatar = resultSet.getString("avatar");
                    UserData userData = new UserData(userName, userEmail, userDescription, userAvatar);

                    VaadinSession.getCurrent().setAttribute(UserData.class, userData);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return false;
        }
    }

    public boolean registerUser(String username, String password, String email) {
        String query = "INSERT INTO users (username, password_hash, email) VALUES (?, ?, ?)";
        Connection connection = dbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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

    public boolean saveUser() {
        UserData userData = VaadinSession.getCurrent().getAttribute(UserData.class);
        String query = "INSERT INTO users (username, email, avatar, description) VALUES (?, ?, ?, ?)";
        Connection connection = dbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userData.getUsername());
            preparedStatement.setString(2, userData.getEmail());
            preparedStatement.setString(4, userData.getAvatar());
            preparedStatement.setString(6, userData.getDescription());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return false;
        }
    }

    public List<Post> getAllPosts(){
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts";
        Connection conn;
        PreparedStatement stmt;
        ResultSet rs;

        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String content = rs.getString("content");
                String heading = rs.getString("heading");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                posts.add(new Post(userId, content, heading, createdAt));
            }

        } catch (Exception e) {
            posts.add(new Post());
            System.err.println("Ошибка при загрузке постов: " + e.getMessage());
        }
        return posts;
    }

    public String getUserameById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        Connection connection = dbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String userName = resultSet.getString("username");
                    return userName;
                } else {
                    return null; // Или можно вернуть пустую строку, если пользователь не найден
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return null; // Или можно вернуть пустую строку в случае ошибки
        }
    }
    public void savePost(Post post, List<Media> mediaList) {
        String sql = "INSERT INTO posts (user_id, content, heading) VALUES (?, ?, ?)";
        Connection conn;
        PreparedStatement stmt;
        ResultSet generatedKeys;

        try {
            conn = dbConnection.getConnection();
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getContent());
            stmt.setString(3, post.getHeading());
            stmt.executeUpdate();

            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int postId = generatedKeys.getInt(1);
                if (mediaList != null && !mediaList.isEmpty()) {
                    saveMedia(postId, mediaList);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении постов: " + e.getMessage());
        }
    }

    private void saveMedia(int postId, List<Media> mediaList){
        String sql = "INSERT INTO media (post_id, media_type, media_data, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Media media : mediaList) {
                stmt.setInt(1, postId);
                stmt.setString(2, media.getMediaType());
                stmt.setString(3, media.getMediaData());
                stmt.setTimestamp(4, Timestamp.valueOf(media.getCreatedAt()));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
        catch (Exception e) {
            System.err.println("Ошибка при сохранении медиа: " + e.getMessage());
        }
    }

    public List<Media> getMediaByPostId(int postId) throws SQLException {
        List<Media> mediaList = new ArrayList<>();
        String sql = "SELECT * FROM media WHERE post_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, postId);
             try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String mediaType = rs.getString("media_type");
                    String mediaData = rs.getString("media_data");
                    LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                    mediaList.add(new Media(id, postId, mediaType, mediaData, createdAt));
                }
             }
        }
        return mediaList;
    }

}

