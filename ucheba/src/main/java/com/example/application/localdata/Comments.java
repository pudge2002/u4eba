package com.example.application.localdata;

import java.sql.Date;
import java.time.LocalDateTime;

public class Comments {
    private int id;
    private int post_id;
    private int user_id;

    public Comments(int id, int post_id, int user_id, String content, LocalDateTime created_at) {
        this.id = id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.created_at = created_at;
        this.content = content;
    }

    private LocalDateTime created_at;
    private String content;
}
