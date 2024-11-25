package org.project.mindpulse.CoreModules;

import org.project.mindpulse.SystemManagement.UserHandler;

import java.util.List;

public class User {

    private int userId;
    private String name;
    private String email;
    private String username;
    private String password;

    private List<Category> favouriteCategories;

    public User(int userId, String name, String email, String username, String password, List<Category> favouriteCategories) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.favouriteCategories = favouriteCategories;
    }

    public User(int userid, String name, String email, String username, String password) {
        this.userId = userid;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


    public List<Category> getFavouriteCategories() {
        return favouriteCategories;
    }

    public void setFavouriteCategories(List<Category> favouriteCategories) {
        this.favouriteCategories = favouriteCategories;
    }

    public void addFavouriteCategory(Category category) {
        this.favouriteCategories.add(category);
    }

}

