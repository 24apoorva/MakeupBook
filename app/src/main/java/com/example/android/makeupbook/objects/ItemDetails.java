package com.example.android.makeupbook.objects;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemDetails {
    @SerializedName("brand")
    private String brand;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("image_link")
    private String image_link;
    @SerializedName("product_link")
    private String product_link;
    @SerializedName("description")
    private String description;
    @SerializedName("rating")
    private float rating;
    @SerializedName("category")
    private String category;
    @SerializedName("product_colors")
    private ArrayList<Colors> product_colors;
    @SerializedName("tag_list")
    private ArrayList<String> tag_list;


    public ItemDetails(){

    }

    public ItemDetails(String brand, String name, String price, String image_link, String product_link, String description,
                       float rating, String category, ArrayList<Colors> product_colors, ArrayList<String> tag_list) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.image_link = image_link;
        this.product_link = product_link;
        this.description = description;
        this.rating = rating;
        this.category = category;
        this.product_colors = product_colors;
        this.tag_list = tag_list;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Colors> getProduct_colors() {
        return product_colors;
    }

    public void setProduct_colors(ArrayList<Colors> product_colors) {
        this.product_colors = product_colors;
    }

    public ArrayList<String> getTag_list() {
        return tag_list;
    }

    public void setTag_list(ArrayList<String> tag_list) {
        this.tag_list = tag_list;
    }
}
