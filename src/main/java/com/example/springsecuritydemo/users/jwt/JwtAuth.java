package com.example.springsecuritydemo.users.jwt;

public class JwtAuth {
    private String username;
    private String password;

    public JwtAuth() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
