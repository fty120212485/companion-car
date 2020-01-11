package com.companioncar.backstage.model;

import com.companioncar.backstage.model.User;

public class JsonWebToken {

    public User user;

    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
