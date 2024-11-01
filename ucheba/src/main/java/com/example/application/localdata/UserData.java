package com.example.application.localdata;

public class UserData {
    private String username;
    private String email;
    private String description;
    private String avatar;

    public UserData(String username, String email, String description, String avatar) {
        this.username = username;
        this.email = email;
        this.description = description;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
