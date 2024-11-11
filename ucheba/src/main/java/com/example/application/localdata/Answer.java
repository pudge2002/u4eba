package com.example.application.localdata;

import java.time.LocalDateTime;

public class Answer {
    private int id;
    private int questId;
    private int userId;
    private String content;
    private LocalDateTime createdAt;

    public Answer(int id, int questId, int userId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.questId = questId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Answer(int questId, int userId, String content) {
        this.questId = questId;
        this.userId = userId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
