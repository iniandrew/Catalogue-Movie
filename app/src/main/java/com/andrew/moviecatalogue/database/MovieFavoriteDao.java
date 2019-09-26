package com.andrew.moviecatalogue.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.andrew.moviecatalogue.model.favorite.MovieFavorite;

import java.util.List;

@Dao
public interface MovieFavoriteDao {
    @Query("SELECT * FROM " + MovieFavorite.TABLE_NAME)
    LiveData<List<MovieFavorite>> getAllMovieFavorite();

    @Query("SELECT * FROM " + MovieFavorite.TABLE_NAME)
    List<MovieFavorite> getMovieFavorite();

    @Query("SELECT * FROM " + MovieFavorite.TABLE_NAME)
    Cursor getMovieFavoritesByCursor();

    @Query("SELECT * FROM " + MovieFavorite.TABLE_NAME + " WHERE " + MovieFavorite.COLUMN_TMDB_ID + " = :tmdbId")
    MovieFavorite getMovieFavoriteById(int tmdbId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieFavorite movieFavorite);

    @Delete
    void delete(MovieFavorite movieFavorite);
}
