package com.loginapp;

public class User {

    // Class variables:
    private String username, password;

    // Constructors:
    public User(String name, String pass) {
        username = name;
        password = pass;
    }


    // Public functions (Methods):
    public String getUsername() {
        return username;
    }

    public boolean isPasswordValid(String pass) {
        return password.equals(pass);
    }
}