package com.example.android.makeupbook.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.annotations.NotNull;

@Entity(tableName = "myList_Products")
public class Item implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int id;
    private final int product_id;
    private final String brand;
    private final String name;
    private final String price;
    private final String image_link;
    private final String productType;
    private final String colorName;
    private final String colorValue;
    private final String description;
    private final float rating;
    private final String category;
    private final String product_link;
    private final boolean inHaveList;
    private final boolean inWantList;


    public Item(int product_id, String brand, String name, String price, String image_link, String productType, String colorName, String colorValue,
                String description, float rating, String category,String product_link,boolean inHaveList, boolean inWantList) {
        this.product_id = product_id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.image_link = image_link;
        this.productType = productType;
        this.colorName = colorName;
        this.colorValue = colorValue;
        this.description = description;
        this.rating = rating;
        this.category = category;
        this.product_link = product_link;
        this.inHaveList = inHaveList;
        this.inWantList = inWantList;
    }

    Item(Parcel in) {
        id = in.readInt();
        product_id = in.readInt();
        brand = in.readString();
        name = in.readString();
        price = in.readString();
        image_link = in.readString();
        productType = in.readString();
        colorName = in.readString();
        colorValue = in.readString();
        description = in.readString();
        rating = in.readFloat();
        category = in.readString();
        product_link = in.readString();
        inHaveList = in.readByte() != 0;
        inWantList = in.readByte() != 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public String getCategory() {
        return category;
    }

    public String getProduct_link() {
        return product_link;
    }

    public boolean isInHaveList() {
        return inHaveList;
    }

    public boolean isInWantList() {
        return inWantList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(product_id);
        dest.writeString(brand);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(image_link);
        dest.writeString(productType);
        dest.writeString(colorName);
        dest.writeString(colorValue);
        dest.writeString(description);
        dest.writeFloat(rating);
        dest.writeString(category);
        dest.writeString(product_link);
        dest.writeByte((byte) (inHaveList ? 1 : 0));
        dest.writeByte((byte) (inWantList ? 1 : 0));
    }
}
