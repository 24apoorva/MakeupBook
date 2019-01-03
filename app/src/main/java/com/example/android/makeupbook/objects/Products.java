package com.example.android.makeupbook.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Products{
    private int id;
    private String name;
    private String brand;
    private String image_link;
    private String price_sign;
    private String price;
    private String category;
    private String product_type;

    public Products(){

    }

    public Products(int id,String name,String brand, String image_link, String price_sign, String price,
            String category,String product_type){
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.image_link = image_link;
        this.price_sign = price_sign;
        this.price = price;
        this.category = category;
        this.product_type = product_type;
    }

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

    public String getPriceSign() {
        return price_sign;
    }

}
