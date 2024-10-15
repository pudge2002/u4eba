package com.example.application.views.posts;

public class Person {

    private String id = "";
    private String image= "";
    private String name= "";
    private String date= "";
    private String post= "";
    private String likes= "";
    private String comments= "";
    private String shares= "";

    public Person() {
    }

    public Person(String image, String name, String date, String post, String likes, String comments, String shares) {
        this.id = "1";
        this.image = image;
        this.date = date;
        this.name = name;
        this.post = post;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
}
