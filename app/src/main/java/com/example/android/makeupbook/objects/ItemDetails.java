package com.example.android.makeupbook.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemDetails implements Parcelable {
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


    public ItemDetails(){

    }

    public ItemDetails(int product_id,String brand, String name, String price, String image_link, String product_link,String product_type, String description,
                       float rating, String category, ArrayList<Colors> product_colors, ArrayList<String> tag_list) {
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

    protected ItemDetails(Parcel in) {
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

    public static final Creator<ItemDetails> CREATOR = new Creator<ItemDetails>() {
        @Override
        public ItemDetails createFromParcel(Parcel in) {
            return new ItemDetails(in);
        }

        @Override
        public ItemDetails[] newArray(int size) {
            return new ItemDetails[size];
        }
    };

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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
