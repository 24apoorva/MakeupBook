package com.example.android.makeupbook.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Products implements Parcelable{
    private int product_id;
    private String brand;
    private String name;
    private String price;
    private String image_link;
    private String product_link;
    private String product_type;
    private String description;
    private float rating;
    private String category;
    private ArrayList<Colors> product_colors;
    private ArrayList<String> tag_list;

    public Products(int product_id, String brand, String name, String price, String image_link, String product_link, String product_type, String description, float rating, String category, ArrayList<Colors> product_colors, ArrayList<String> tag_list) {
        this.product_id = product_id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.image_link = image_link;
        this.product_link = product_link;
        this.product_type = product_type;
        this.description = description;
        this.rating = rating;
        this.category = category;
        this.product_colors = product_colors;
        this.tag_list = tag_list;
    }

    private Products(Parcel in) {
        product_id = in.readInt();
        brand = in.readString();
        name = in.readString();
        price = in.readString();
        image_link = in.readString();
        product_link = in.readString();
        product_type = in.readString();
        description = in.readString();
        rating = in.readFloat();
        category = in.readString();
        product_colors = in.createTypedArrayList(Colors.CREATOR);
        tag_list = in.createStringArrayList();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

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

    public String getProduct_link() {
        return product_link;
    }

    public String getProduct_type() {
        return product_type;
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

    public ArrayList<Colors> getProduct_colors() {
        return product_colors;
    }

    public ArrayList<String> getTag_list() {
        return tag_list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(product_id);
        dest.writeString(brand);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(image_link);
        dest.writeString(product_link);
        dest.writeString(product_type);
        dest.writeString(description);
        dest.writeFloat(rating);
        dest.writeString(category);
        dest.writeTypedList(product_colors);
        dest.writeStringList(tag_list);
    }
}
