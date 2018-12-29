package com.example.android.makeupbook.accounts;

public class User {
    public String userName, email;

    public User(){}

    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }
}
