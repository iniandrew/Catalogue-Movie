package com.andrew.moviecatalogue.database;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

public class MyApp extends Application {
    private static AppRoomDatabase database;

    public static synchronized AppRoomDatabase getDbInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context,
                    AppRoomDatabase.class, "favorite.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
