package com.example.application.Model;

public class UserController {
    private UserAuthentication auth;
    private UserRegistration registration;

    public UserController() {
        auth = new UserAuthentication();
        registration = new UserRegistration();
    }

    public boolean authenticateUser(String username, String password) {
        return auth.authenticate(username, password);
    }

    public boolean registerUser(String username, String password, String email) {
        return registration.register(username, password, email);
    }
}

