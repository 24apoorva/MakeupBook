package com.example.android.makeupbook.accounts;

public class User {
    private String userName;
    public String email;

    public User(){

    }

    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

}
