package com.example.android.myproductslibrary.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.myproductslibrary.ProductCount;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;
    private LiveData<List<Item>> iHaveItems;
    private LiveData<List<Item>> iWantItems;
    private LiveData<Integer> haveCount;
    private LiveData<Integer> wantCount;
    private LiveData<List<ProductCount>> wantCountProductsList;
    private LiveData<List<ProductCount>> haveCountProductsList;


    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
        iHaveItems = itemRepository.loadHaveList();
        iWantItems = itemRepository.loadWantList();
        haveCount = itemRepository.getHaveCount();
        wantCount = itemRepository.getWantCount();
        wantCountProductsList = itemRepository.getWantCountProductsList();
        haveCountProductsList = itemRepository.getHaveCountProductsList();
    }

    public LiveData<List<ProductCount>> getHaveCountProductsList() {
        return haveCountProductsList;
    }

    public LiveData<List<ProductCount>> getWantCountProductsList() {
        return wantCountProductsList;
    }

    public void insertItem(Item item){
        itemRepository.insert(item);
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
