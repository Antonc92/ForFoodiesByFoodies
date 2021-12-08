package com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants;

import java.io.Serializable;

public class Restaurants implements Serializable {
    public String name, description, street, photo;


    public Restaurants(){

    }

    public Restaurants(String name, String description, String street, String photo) {
        this.name = name;
        this.description = description;
        this.street = street;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
