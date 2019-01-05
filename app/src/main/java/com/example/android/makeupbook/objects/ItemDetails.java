package com.example.android.makeupbook.objects;

import com.google.gson.annotations.SerializedName;

public class ItemDetails {
    @SerializedName("brand")
    private String brand;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("image_link")
    private String image_link;
    @SerializedName("image_link")
    private String product_link;
    @SerializedName("image_link")
    private String description;
    @SerializedName("image_link")
    private double rating;
    @SerializedName("image_link")
    private String category;
    @SerializedName("image_link")
    private Colors product_colors;

    public ItemDetails(){

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getProduct_link() {
        return product_link;
    }

    public void setProduct_link(String product_link) {
        this.product_link = product_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Colors getProduct_colors() {
        return product_colors;
    }

    public void setProduct_colors(Colors product_colors) {
        this.product_colors = product_colors;
    }
}
