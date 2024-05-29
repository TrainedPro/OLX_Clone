package com.example.olx;

import java.util.ArrayList;

public class ModelAd {

    int id;
    int uid;
    String title;
    String category;
    String description;
    String area;
    float price;

    public ModelAd() {
    }

    public ModelAd(int id, int uid, String title, String category, String description, String area, float price) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.category = category;
        this.description = description;
        this.area = area;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isFavourite(int userId) {
        ArrayList<Integer> favorites = Utils.userFavorites.get(userId);
        return favorites != null && favorites.contains(this.id);
    }

    public void setFavourite(int userId, boolean favourite) {
        if (favourite) {
            Utils.addToFavourites(null, userId, this.id); // null for context if you don’t want to show a toast message
        } else {
            Utils.removeFromFavourites(null, userId, this.id); // null for context if you don’t want to show a toast message
        }
    }
}
