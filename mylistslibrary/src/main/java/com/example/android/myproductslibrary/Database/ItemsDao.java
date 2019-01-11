package com.example.android.myproductslibrary.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.myproductslibrary.ProductCount;

import java.util.List;

@Dao
public interface ItemsDao {

    @Insert
    void insertItem(Item item);

    @Delete
    void removeItem(Item item);

    @Query("DELETE FROM myList_Products WHERE inHaveList = 1 ")
    void deleteAllHaveList();

    @Query("DELETE FROM myList_Products WHERE inWantList = 1 ")
    void deleteAllWantList();

    @Query("SELECT * FROM myList_Products WHERE inHaveList = 1")
    LiveData<List<Item>> loadHaveList();

    @Query("SELECT * FROM myList_Products WHERE inWantList = 1")
    LiveData<List<Item>> loadWantList();

    @Query("SELECT COUNT(*) FROM myList_Products WHERE inHaveList = 1")
    LiveData<Integer> getHaveListCount();

    @Query("SELECT COUNT(*) FROM myList_Products WHERE inWantList = 1")
    LiveData<Integer> getWantListCount();

    @Query("SELECT DISTINCT productType,COUNT(productType) as count FROM myList_Products WHERE inWantList=1 GROUP BY productType")
    LiveData<List<ProductCount>> getWantProductCountList();

    @Query("SELECT DISTINCT productType,COUNT(productType) as count FROM myList_Products WHERE inHaveList=1 GROUP BY productType")
    LiveData<List<ProductCount>> getHaveProductCountList();

}
