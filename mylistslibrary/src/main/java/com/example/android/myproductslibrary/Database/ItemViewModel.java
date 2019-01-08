package com.example.android.myproductslibrary.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;
    private LiveData<List<Item>> iHaveItems;
    private LiveData<List<Item>> iWantItems;
    private LiveData<Integer> haveProductsCount;
    private LiveData<Integer> wantProductCount;
    private String productType;
    private LiveData<Integer> haveCount;
    private LiveData<Integer> wantCount;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        iHaveItems = itemRepository.loadHaveList();
        iWantItems = itemRepository.loadWantList();
        haveProductsCount = itemRepository.getHaveProductsCount(productType);
        wantProductCount = itemRepository.getWantProductsCount(productType);
        haveCount = itemRepository.getHaveCount();
        wantCount = itemRepository.getWantCount();
    }

    public void insertItem(Item item){
        itemRepository.insert(item);
    }

    public LiveData<Integer> getHaveProductsCount(String productType) {
        return haveProductsCount;
    }
    public LiveData<Integer> getWantProductCount(String productType){
        return wantProductCount;
    }

    public LiveData<Integer> getWantCount() {
        return wantCount;
    }

    public LiveData<Integer> getHaveCount() {
        return haveCount;
    }

    public void removeItem(Item item){
        itemRepository.delete(item);
    }

    public void removeAllWantedItems(){
        itemRepository.deleteWantList();
    }

    public void removeAllHaveItems(){
        itemRepository.deleteHaveList();
    }

    public LiveData<List<Item>> loadHaveList(){
        return iHaveItems;
    }

    public LiveData<List<Item>> loadWantList(){
        return iWantItems;
    }
}
