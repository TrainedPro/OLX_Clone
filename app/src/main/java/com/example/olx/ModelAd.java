package com.example.olx;

public class ModelAd {

    int id;
    int uid;

    String title;
    String category;
    String description;
    String area;
    float price;
    boolean favourite;

    public ModelAd() {
    }

    public ModelAd(int id, int uid, String title, String category, String description, String area, float price, boolean favourite) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.category = category;
        this.description = description;
        this.area = area;
        this.price = price;
        this.favourite = favourite;
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

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
