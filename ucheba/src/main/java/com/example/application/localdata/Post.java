package com.example.application.localdata;
import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private int id; // id поста
    private int userId; // id автора
    private String author; // имя автора
    private String heading;
    private String content;
    private LocalDateTime createdAt;
    private String avatar;

    private List<Reaction> reaction;
    private List<Comments> comments;

    public Post( int userId, String author, String avatar, String heading, String content, List<Reaction> reaction, List<Comments> comments, LocalDateTime createdAt) {

        this.userId = userId;
        this.author = author;
        this.avatar = avatar;
        this.heading = heading;
        this.content = content;
        this.reaction = reaction;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public Post(int id, int userId, String author, String avatar, String heading, String content, List<Reaction> reaction, List<Comments> comments, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.author = author;
        this.avatar = avatar;
        this.heading = heading;
        this.content = content;
        this.reaction = reaction;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public Post() { // какая то куча если честно....
        id = 0;
        userId = 0;
        content = "null";
        createdAt = LocalDateTime.now();
        heading = "null";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
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

    public List<Reaction> getReaction() {
        return reaction;
    }

    public void setReaction(List<Reaction> reaction) {
        this.reaction = reaction;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", author='" + author + '\'' +
                ", heading='" + heading + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", reaction=" + reaction +
                ", comments=" + comments +
                '}';
    }

    //    public int getId() {
//        return id;
//    }
//
//    public String getHeading() {
//        return heading;
//    }
//
//    public void setHeading(String heading) {
//        this.heading = heading;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public String getUserName(){
//        return author;
//    }
}
