package com.example.application.Model;

import com.example.application.localdata.*;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.http.Cookie;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Connect_to_DataBase dbConnection;

    public Controller() throws SQLException {
        dbConnection = Connect_to_DataBase.getInstance();
    }

    public boolean authenticateUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
        Connection connection = dbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userid = resultSet.getInt("id");
                    String userEmail = resultSet.getString("email");
                    String userName = resultSet.getString("username");
                    String userDescription = resultSet.getString("description");
                    String userAvatar = resultSet.getString("avatar");
                    UserData userData = new UserData(userid, userName, userEmail, userDescription, userAvatar);

                    VaadinSession.getCurrent().setAttribute(UserData.class, userData);
                    // Сохранение данных в куки
                    saveUserDataToCookies(userData);
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
        String query = "UPDATE users SET username = ?, avatar = ?, description = ? WHERE email = ?;";
        Connection connection = dbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userData.getUsername());
            preparedStatement.setString(2, userData.getAvatar());
            preparedStatement.setString(3, userData.getDescription());
            preparedStatement.setString(4, userData.getEmail());
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
        String sqlCom = "Select * from comments where id = ";
        String sqlReact = "Select * from reaction where id = ";
        String sqlAuthor = "Select * from users where id = ";

        Connection conn;

        PreparedStatement stmt;
        PreparedStatement stmt_author;
        PreparedStatement stmt_com;
        PreparedStatement stmt_react;

        ResultSet rs;
        ResultSet rs_author;
        ResultSet rs_com;
        ResultSet rs_react;

        List<Reaction> reactions = null;
        List<Comments> comments = null;


        try {
            conn = dbConnection.getConnection();

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");

                stmt_author = conn.prepareStatement(sqlAuthor + String.valueOf(userId));
                rs_author = stmt_author.executeQuery();
                String author = null;
                String avatar = null;
                rs_author.next();
                author = rs_author.getString("username");
                avatar = rs_author.getString("avatar");

                stmt_react = conn.prepareStatement(sqlReact + String.valueOf(id));
                rs_react = stmt_react.executeQuery();
                if (rs_react != null) {
                    while(rs_react.next()){
                        reactions.add(new Reaction(rs_react.getInt("id"), rs_react.getInt("post_id"), rs_react.getInt("user_id"), rs_react.getTimestamp("created_at").toLocalDateTime()));
                    }
                }

                stmt_com = conn.prepareStatement(sqlCom + String.valueOf(id));
                rs_com = stmt_com.executeQuery();
                if (rs_com != null) {
                    while(rs_com.next()){
                        comments.add(new Comments(rs_com.getInt("id"), rs_com.getInt("post_id"), rs_com.getInt("user_id"), rs_com.getString("content"), rs_com.getTimestamp("created_at").toLocalDateTime()));
                    }
                }

                String content = rs.getString("content");
                String heading = rs.getString("heading");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                posts.add(new Post(id, userId, author, avatar, heading, content, reactions, comments, createdAt));
            }
        } catch (Exception e) {
            posts.add(new Post());
            System.err.println("Ошибка при загрузке постов: " + e.getMessage());
        }
        return posts;
    }
    public List<Post> getUserPosts(int userId){
        List<Post> posts = new ArrayList<>();

        String sql = "SELECT * FROM posts where id = ?";
        String sqlCom = "Select * from comments where id = ";
        String sqlReact = "Select * from reaction where id = ";
        String sqlAuthor = "Select * from users where id = ";

        Connection conn;

        PreparedStatement stmt;
        PreparedStatement stmt_author;
        PreparedStatement stmt_com;
        PreparedStatement stmt_react;

        ResultSet rs;
        ResultSet rs_author;
        ResultSet rs_com;
        ResultSet rs_react;

        List<Reaction> reactions = null;
        List<Comments> comments = null;


        try {
            conn = dbConnection.getConnection();

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                stmt_author = conn.prepareStatement(sqlAuthor + String.valueOf(userId));
                rs_author = stmt_author.executeQuery();
                String author = null;
                String avatar = null;
                rs_author.next();
                author = rs_author.getString("username");
                avatar = rs_author.getString("avatar");

                stmt_react = conn.prepareStatement(sqlReact + String.valueOf(id));
                rs_react = stmt_react.executeQuery();
                if (rs_react != null) {
                    while(rs_react.next()){
                        reactions.add(new Reaction(rs_react.getInt("id"), rs_react.getInt("post_id"), rs_react.getInt("user_id"), rs_react.getTimestamp("created_at").toLocalDateTime()));
                    }
                }

                stmt_com = conn.prepareStatement(sqlCom + String.valueOf(id));
                rs_com = stmt_com.executeQuery();
                if (rs_com != null) {
                    while(rs_com.next()){
                        comments.add(new Comments(rs_com.getInt("id"), rs_com.getInt("post_id"), rs_com.getInt("user_id"), rs_com.getString("content"), rs_com.getTimestamp("created_at").toLocalDateTime()));
                    }
                }

                String content = rs.getString("content");
                String heading = rs.getString("heading");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                posts.add(new Post(id, userId, author, avatar, heading, content, reactions, comments, createdAt));
            }
        } catch (Exception e) {
            posts.add(new Post());
            System.err.println("Ошибка при загрузке постов: " + e.getMessage());
        }
        return posts;
    }
    public UserData getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        Connection connection = dbConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String description = resultSet.getString("description");
                    String avatar = resultSet.getString("avatar");

                    return new UserData(userId, username, email, description, avatar);
                } else {
                    return null; // Или можно вернуть пустой объект UserData, если пользователь не найден
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return null; // Или можно вернуть пустой объект UserData в случае ошибки
        }
    }
    public void updatePost(Post post, List<Media> mediaList, Connection conn) {
        String sql = "UPDATE posts SET content = ?, heading = ? WHERE id = ?";
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, post.getContent());
            stmt.setString(2, post.getHeading());
            stmt.setInt(3, post.getId());
            stmt.executeUpdate();

            // Обновление медиафайлов
            if (mediaList != null && !mediaList.isEmpty()) {
                updateMedia(post.getId(), mediaList, conn);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении поста: " + e.getMessage());
        }
    }

    private void updateMedia(int postId, List<Media> mediaList, Connection conn) {
        String deleteSql = "DELETE FROM media WHERE post_id = ?";
        String insertSql = "INSERT INTO media (post_id, media_type, media_data, created_at) VALUES (?, ?, ?, ?)";
        PreparedStatement deleteStmt;
        PreparedStatement insertStmt;

        try {
            // Удаление старых медиафайлов
            deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, postId);
            deleteStmt.executeUpdate();

            // Добавление новых медиафайлов
            insertStmt = conn.prepareStatement(insertSql);
            for (Media media : mediaList) {
                insertStmt.setInt(1, postId);
                insertStmt.setString(2, media.getMediaType());
                insertStmt.setString(3, media.getMediaData());
                insertStmt.setTimestamp(4, Timestamp.valueOf(media.getCreatedAt()));
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении медиафайлов: " + e.getMessage());
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
    public void saveQuestion(Question question, Connection conn) {
        String sql = "INSERT INTO question (user_id, content, created_at) VALUES (?, ?, ?)";
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, question.getUserId());
            stmt.setString(2, question.getContent());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                question.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении вопроса: " + e.getMessage());
        }
    }

    public void updateQuestion(Question question, Connection conn) {
        String sql = "UPDATE question SET content = ? WHERE id = ?";
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, question.getContent());
            stmt.setInt(2, question.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении вопроса: " + e.getMessage());
        }
    }

    public void saveAnswer(Answer answer, Connection conn) {
        String sql = "INSERT INTO answer (quest_id, user_id, content, created_at) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, answer.getQuestId());
            stmt.setInt(2, answer.getUserId());
            stmt.setString(3, answer.getContent());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                answer.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении ответа: " + e.getMessage());
        }
    }

    public void updateAnswer(Answer answer, Connection conn) {
        String sql = "UPDATE answer SET content = ? WHERE id = ?";
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, answer.getContent());
            stmt.setInt(2, answer.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении ответа: " + e.getMessage());
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

    private void saveUserDataToCookies(UserData userData) {
        VaadinResponse response = VaadinService.getCurrentResponse();
        addCookie(response, "username", userData.getUsername());
        addCookie(response, "email", userData.getEmail());
        addCookie(response, "avatar", userData.getAvatar());
        addCookie(response, "description", userData.getDescription());
    }

    private void addCookie(VaadinResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(3600); // Время жизни куки в секундах
        response.addCookie(cookie);
    }
}

