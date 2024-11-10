package com.example.application.Model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void getAllPosts() {
        try {
            Controller db = new Controller();
            System.out.println(db.getAllPosts().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}