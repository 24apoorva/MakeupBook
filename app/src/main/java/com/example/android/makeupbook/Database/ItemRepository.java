package com.example.android.makeupbook.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.example.android.makeupbook.objects.ProductCount;

import java.util.List;

class ItemRepository {
    private final ItemsDao itemsDao;
    private final LiveData<List<Item>> iHaveItems;
    private final LiveData<List<Item>> iWantItem;
    private final LiveData<Integer> wantCount;
    private final LiveData<Integer> haveCount;
    private final LiveData<List<ProductCount>> wantCountProductsList;
    private final LiveData<List<ProductCount>> haveCountProductsList;




    public ItemRepository(Application application){
        ItemsDatabase database = ItemsDatabase.getItemsDatabaseInstance(application);
        itemsDao = database.itemsDao();
        iHaveItems = itemsDao.loadHaveList();
        iWantItem = itemsDao.loadWantList();
        haveCount = itemsDao.getHaveListCount();
        wantCount = itemsDao.getWantListCount();
        wantCountProductsList = itemsDao.getWantProductCountList();
        haveCountProductsList = itemsDao.getHaveProductCountList();

    }

    public LiveData<List<ProductCount>> getHaveCountProductsList() {
        return haveCountProductsList;
    }

    public LiveData<List<ProductCount>> getWantCountProductsList() {
        return wantCountProductsList;
    }

    public LiveData<Integer> getHaveCount() {
        return haveCount;
    }

    public LiveData<Integer> getWantCount() {
        return wantCount;
    }

    public void insert(Item item){
        new InsertItemAsynTask(itemsDao).execute(item);
    }

    public void delete(Item item){
        new DeleteItemAsynTask(itemsDao).execute(item);
    }

    public void deleteHaveList(){
        new DeleteHaveItemsAsynTask(itemsDao).execute();
    }

    public void deleteWantList() {
        new DeleteWantItemsAsynTask(itemsDao).execute();
    }

    public LiveData<List<Item>> loadHaveList(){
        return iHaveItems;
    }

    public LiveData<List<Item>> loadWantList(){
        return iWantItem;
    }

    private static class InsertItemAsynTask extends AsyncTask<Item,Void,Void>{

        private final ItemsDao itemsDao;

        private InsertItemAsynTask(ItemsDao itemsDao){

            this.itemsDao = itemsDao;
        }
        @Override
        protected Void doInBackground(Item... items) {
            itemsDao.insertItem(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsynTask extends AsyncTask<Item,Void,Void>{

        private final ItemsDao itemsDao;

        private DeleteItemAsynTask(ItemsDao itemsDao){
            this.itemsDao = itemsDao;
        }
        @Override
        protected Void doInBackground(Item... items) {
            itemsDao.removeItem(items[0]);
            return null;
        }
    }

    private static class DeleteHaveItemsAsynTask extends AsyncTask<Void,Void,Void>{

        private final ItemsDao itemsDao;

        private DeleteHaveItemsAsynTask(ItemsDao itemsDao){
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemsDao.deleteAllHaveList();
            return null;
        }
    }

    private static class DeleteWantItemsAsynTask extends AsyncTask<Void,Void,Void>{

        private final ItemsDao itemsDao;

        private DeleteWantItemsAsynTask(ItemsDao itemsDao){
            this.itemsDao = itemsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemsDao.deleteAllWantList();
            return null;
        }
    }
}
