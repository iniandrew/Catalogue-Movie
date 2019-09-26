package com.andrew.moviecatalogue.network;

import com.andrew.moviecatalogue.BuildConfig;

public class ApiConfiguration {
    public static final String POSTER_PATH = "https://image.tmdb.org/t/p/w185";
    public static final String TMDB_MOVIE = BuildConfig.TMBD_BASE_URL + "discover/movie?api_key=" + BuildConfig.TMBD_API_KEY + "&language=en-US";
    public static final String TMDB_TVSHOW = BuildConfig.TMBD_BASE_URL + "discover/tv?api_key=" + BuildConfig.TMBD_API_KEY + "&language=en-US";
    public static final String SEARCH_MOVIE = BuildConfig.TMBD_BASE_URL + "search/movie?api_key=" + BuildConfig.TMBD_API_KEY + "&language=en-US&query={query}";
    public static final String SEARCH_TVSHOW = BuildConfig.TMBD_BASE_URL + "search/tv?api_key=" + BuildConfig.TMBD_API_KEY + "&language=en-US&query={query}";
    public static final String TMDB_RELEASE = BuildConfig.TMBD_BASE_URL + "discover/movie?api_key=" + BuildConfig.TMBD_API_KEY + "&primary_release_date.gte={today_date}&primary_release_date.lte={today_date}";
}
