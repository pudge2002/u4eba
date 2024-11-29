package com.example.application.localdata;
import java.sql.Date;
import java.time.LocalDateTime;

public class Reaction {
    private int id;
    private int post_id;

    private int user_id;
    private LocalDateTime created_at;
    public Reaction(int id, int post_id, int user_id, LocalDateTime created_at) {
        this.id = id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.created_at = created_at;
    }
    public Reaction(int post_id, int user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.created_at = LocalDateTime.now();
    }



    public int getUser_id() {
        return user_id;
    }
    public int getPost_id() {
        return post_id;
    }


}
