package com.andrew.moviecatalogue.model.favorite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

import java.io.Serializable;

@Entity(tableName = TvShowFavorite.TABLE_NAME)
public class TvShowFavorite implements Serializable {

    public static final String TABLE_NAME = "tvShowFavorite";

    private static final String COLUMN_TMDB_ID = "tmdbId";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_POSTER_PATH = "poster_path";
    private static final String COLUMN_BACKDROP_PATH = "backdrop_path";
    private static final String COLUMN_RELEASE_DATE = "release_date";
    private static final String COLUMN_VOTE_AVERAGE = "vote_average";
    private static final String COLUMN_POPULARITY = "popularity";
    private static final String COLUMN_OVERVIEW = "overview";

    @PrimaryKey
    @ColumnInfo(name = COLUMN_TMDB_ID)
    private int tmdbId;

    @ColumnInfo(name = COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = COLUMN_POSTER_PATH)
    private String posterPath;

    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    private String backdropPath;

    @ColumnInfo(name = COLUMN_RELEASE_DATE)
    private String releaseDate;

    @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
    private String voteAverage;

    @ColumnInfo(name = COLUMN_POPULARITY)
    private String popularity;

    @ColumnInfo(name = COLUMN_OVERVIEW)
    private String overview;

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
