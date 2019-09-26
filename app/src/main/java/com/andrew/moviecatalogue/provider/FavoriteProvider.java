package com.andrew.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.andrew.moviecatalogue.database.MovieFavoriteDao;
import com.andrew.moviecatalogue.database.MyApp;
import com.andrew.moviecatalogue.database.TvShowFavoriteDao;
import com.andrew.moviecatalogue.model.favorite.MovieFavorite;
import com.andrew.moviecatalogue.model.favorite.TvShowFavorite;

public class FavoriteProvider extends ContentProvider {

    private static final String AUTHORITY = "com.andrew.moviecatalogue";
    private static final int MOVIE = 1;
    private static final int TV_SHOW = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, MovieFavorite.TABLE_NAME, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TvShowFavorite.TABLE_NAME, TV_SHOW);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        MovieFavoriteDao movieFavoriteDao = MyApp.getDbInstance(getContext()).movieFavoriteDao();
        TvShowFavoriteDao tvShowFavoriteDao = MyApp.getDbInstance(getContext()).tvShowFavoriteDao();

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieFavoriteDao.getMovieFavoritesByCursor();
                break;
            case TV_SHOW:
                cursor = tvShowFavoriteDao.getTvShowsByCursor();
                break;
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
