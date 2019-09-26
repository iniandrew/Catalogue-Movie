package com.andrew.moviecatalogue.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.andrew.moviecatalogue.model.favorite.MovieFavorite;
import com.andrew.moviecatalogue.model.favorite.TvShowFavorite;

@Database(entities = {MovieFavorite.class, TvShowFavorite.class}, version = 2, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract MovieFavoriteDao movieFavoriteDao();
    public abstract TvShowFavoriteDao tvShowFavoriteDao();
}
