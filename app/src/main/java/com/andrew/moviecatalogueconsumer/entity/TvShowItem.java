package com.andrew.moviecatalogueconsumer.entity;

public class TvShowItem {

    private int id;
    private String title, posterPath, overview;

    public static final String TABLE_NAME = "tvShowFavorite";
    public static final String COLUMN_TMDB_ID = "tmdbId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_OVERVIEW = "overview";

    public TvShowItem(int id, String title, String posterPath, String overview) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

}
