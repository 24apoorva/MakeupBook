package com.example.android.makeupbook.objects;

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

    public int getCount() {
        return count;
    }

}
