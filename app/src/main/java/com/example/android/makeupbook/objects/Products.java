package com.example.android.makeupbook.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Products{
    @SerializedName("id")
    private int id;
    @SerializedName("brand")
    private String brand;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("image_link")
    private String image_link;

    public Products(){

    }

//    public Products(int id,String name,String brand, String image_link, String price_sign, String price){
//        this.id = id;
//        this.name = name;
//        this.brand = brand;
//        this.image_link = image_link;
//        this.price_sign = price_sign;
//        this.price = price;
//    }

    public int getProductId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getImageUrl() {
        return image_link;
    }

    public String getPrice() {
        return price;
    }

}
