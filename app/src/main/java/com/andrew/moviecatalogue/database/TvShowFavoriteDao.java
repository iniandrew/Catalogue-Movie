package com.andrew.moviecatalogue.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.andrew.moviecatalogue.model.favorite.MovieFavorite;
import com.andrew.moviecatalogue.model.favorite.TvShowFavorite;

import java.util.List;

@Dao
public interface TvShowFavoriteDao {
    @Query("SELECT * FROM " + TvShowFavorite.TABLE_NAME)
    LiveData<List<TvShowFavorite>> getAllTvShowFavorite();

    @Query("SELECT * FROM " + TvShowFavorite.TABLE_NAME)
    List<TvShowFavorite> getTvShowFavorite();

    @Query("SELECT * FROM " + TvShowFavorite.TABLE_NAME)
    Cursor getTvShowsByCursor();

    @Query("SELECT * FROM " + TvShowFavorite.TABLE_NAME + " WHERE " + MovieFavorite.COLUMN_TMDB_ID + " = :tmdbId")
    TvShowFavorite getTvShowFavoriteById(int tmdbId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TvShowFavorite tvShowFavorite);

    @Delete
    void delete(TvShowFavorite tvShowFavorite);
}
