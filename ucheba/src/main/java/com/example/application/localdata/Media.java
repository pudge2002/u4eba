package com.example.application.localdata;

import java.time.LocalDateTime;

public class Media {
    private int id;
    private int postId;
    private String mediaType;
    private String mediaData;
    private LocalDateTime createdAt;

    public Media() {
    }

    public Media(int id, int postId, String mediaType, String mediaData, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.mediaType = mediaType;
        this.mediaData = mediaData;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMediaData() {
        return mediaData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setMediaData(String mediaData) {
        this.mediaData = mediaData;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
