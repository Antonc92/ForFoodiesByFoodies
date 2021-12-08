package com.example.forfoodiesbyfoodies.activity;

public class User {

    public String first, last, username, email, access;

    public User(){

    }

    public User(String first, String last, String username, String email, String access){
        this.first = first;
        this.last = last;
        this.username = username;
        this.email = email;
        this.access = access;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {

        this.first = first;
    }

    public String getLast() {

        return last;
    }

    public void setLast(String last) {

        this.last = last;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }



    public void setEmail(String email) {

        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getAccess() {

        return access;
    }
    public void setAccess(String access) {

        this.access = access;
    }




}
