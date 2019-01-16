package com.example.android.makeupbook.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Colors implements Parcelable {

    private String hex_value;
    private String colour_name;


    public Colors(){

    }

    public  Colors(String colour_name,String hex_value){
        this.colour_name = colour_name;
        this.hex_value = hex_value;
    }

    private Colors(Parcel in) {
        hex_value = in.readString();
        colour_name = in.readString();
    }

    public static final Creator<Colors> CREATOR = new Creator<Colors>() {
        @Override
        public Colors createFromParcel(Parcel in) {
            return new Colors(in);
        }

        @Override
        public Colors[] newArray(int size) {
            return new Colors[size];
        }
    };

    public String getColour_name() {
        return colour_name;
    }

    public void setColour_name(String colour_name) {
        this.colour_name = colour_name;
    }

    public String getHex_value() {
        return hex_value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hex_value);
        dest.writeString(colour_name);
    }
}
