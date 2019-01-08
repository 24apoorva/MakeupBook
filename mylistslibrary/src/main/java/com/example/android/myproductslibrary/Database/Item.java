package com.example.android.myproductslibrary.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "myList_Products")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int product_id;
    private String brand;
    private String name;
    private String price;
    private String image_link;
    private String productType;
    private String colorName;
    private String colorValue;
    private boolean inHaveList;
    private boolean inWantList;

    public Item(int product_id, String brand, String name, String price, String image_link, String productType, String colorName, String colorValue, boolean inHaveList, boolean inWantList) {
        this.product_id = product_id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.image_link = image_link;
        this.productType = productType;
        this.colorName = colorName;
        this.colorValue = colorValue;
        this.inHaveList = inHaveList;
        this.inWantList = inWantList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage_link() {
        return image_link;
    }

    public String getProductType() {
        return productType;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorValue() {
        return colorValue;
    }

    public boolean isInHaveList() {
        return inHaveList;
    }

    public boolean isInWantList() {
        return inWantList;
    }
}
