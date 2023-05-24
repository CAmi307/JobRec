package com.jobrec.domain;

public class CurrentUser {
    private String email;

    public CurrentUser(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
