package com.example.ustart.models;

public class User {
    private int id;
    private String name,email,profileImg;

    public User(int id, String name, String email, String profileImg) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImg() {
        return profileImg;
    }
}
