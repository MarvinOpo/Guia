package com.example.jadjaluddin.guia.Model;

/**
 * Created by Stephanie Lyn on 11/9/2015.
 */
public class Course {
    public User user;
    public String location, description;

    public Course(User user, String location, String description) {
        this.user = user;
        this.location = location;
        this.description = description;
    }
}
