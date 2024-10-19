package com.example.application.Model;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private int userId;
    private String content;
    private LocalDateTime createdAt;
    private String heading;

    public Post(int userId, String content, String heading) {
        this.userId = userId;
        this.content = content;
        this.heading = heading;
    }

    public Post(int userId, String content, String heading, LocalDateTime createdAt) {
        this.userId = userId;
        this.heading = heading;
        this.createdAt = createdAt;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", heading=" + heading +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public Post() {
        // Пустой конструктор для формы
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
