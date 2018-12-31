package com.example.android.makeupbook.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Products implements Parcelable {
    private int productId;
    private String name;
    private String brand;
    private String image_link;
    private String price_sign;
    private String price;
    private String category;
    private String product_type;

    public Products(){

    }

    public Products(int productId,String name,String brand, String image_link, String price_sign, String price,
            String category,String product_type ){
        this.productId= productId;
        this.name = name;
        this.brand = brand;
        this.image_link = image_link;
        this.price_sign = price_sign;
        this.price = price;
        this.category = category;
        this.product_type = product_type;
    }

    protected Products(Parcel in) {
        productId = in.readInt();
        name = in.readString();
        brand = in.readString();
        image_link = in.readString();
        price_sign = in.readString();
        price = in.readString();
        category = in.readString();
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

    public int getProductId() {
        return productId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(name);
        dest.writeString(brand);
        dest.writeString(image_link);
        dest.writeString(price_sign);
        dest.writeString(price);
        dest.writeString(category);
        dest.writeString(product_type);
    }
}
