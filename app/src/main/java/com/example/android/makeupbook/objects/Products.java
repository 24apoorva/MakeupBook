package com.example.android.makeupbook.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Products implements Parcelable{
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
    @SerializedName("product_type")
    private String product_type;
    @SerializedName("product_colors")
    private ArrayList<Colors> product_colors;

    public Products(){

    }

    public Products(int id, String brand, String name, String price, String image_link, String product_type, ArrayList<Colors> product_colors) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.image_link = image_link;
        this.product_type = product_type;
        this.product_colors = product_colors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public ArrayList<Colors> getProduct_colors() {
        return product_colors;
    }

    public void setProduct_colors(ArrayList<Colors> product_colors) {
        this.product_colors = product_colors;
    }

    public static Creator<Products> getCREATOR() {
        return CREATOR;
    }

    protected Products(Parcel in) {
        id = in.readInt();
        brand = in.readString();
        name = in.readString();
        price = in.readString();
        image_link = in.readString();
        product_type = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(brand);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(image_link);
        dest.writeString(product_type);
    }
}
