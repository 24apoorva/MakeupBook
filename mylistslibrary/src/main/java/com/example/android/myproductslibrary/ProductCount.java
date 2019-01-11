package com.example.android.myproductslibrary;

import android.arch.persistence.room.ColumnInfo;

public class ProductCount {
    @ColumnInfo(name = "productType")
    private String type;
    private int count;

    public ProductCount(String type, int count) {
        this.type = type;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
