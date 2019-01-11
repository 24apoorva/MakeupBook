package com.example.android.myproductslibrary.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Item.class, version = 1, exportSchema = false)
public abstract class ItemsDatabase extends RoomDatabase {
    private static final String DATABASE_NAME ="items_list";
    private static final Object LOCK = new Object();
    private static ItemsDatabase itemsDatabaseInstance;

    public abstract ItemsDao itemsDao();

    public static synchronized ItemsDatabase getItemsDatabaseInstance(Context context){
        if(itemsDatabaseInstance == null){
            synchronized (LOCK) {
                itemsDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), ItemsDatabase.class, ItemsDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return itemsDatabaseInstance;
    }
}
