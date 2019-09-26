package com.andrew.moviecatalogue.ui.movie;

import android.util.Log;

import com.andrew.moviecatalogue.model.movie.MovieResponse;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class MoviePresenter {
    private MovieFragmentView view;

    public MoviePresenter(MovieFragmentView view) {
        this.view = view;
    }

    public void getListMovies() {
        view.showProgressBar();
        AndroidNetworking.get(ApiConfiguration.TMDB_MOVIE)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieResponse.class, new ParsedRequestListener<MovieResponse>() {
                    @Override
                    public void onResponse(MovieResponse response) {
                        view.onSuccess(response);
                        view.hideProgressBar();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError: ", anError);
                        view.onFailed("Gagal Mengambil data dari Server!");
                    }
                });
    }

    public void getListMoviesByQuery(String movieTitle) {
        view.showProgressBar();
        AndroidNetworking.get(ApiConfiguration.SEARCH_MOVIE)
                .addPathParameter("query", movieTitle)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieResponse.class, new ParsedRequestListener<MovieResponse>() {
                    @Override
                    public void onResponse(MovieResponse response) {
                        if (response.getResults().size() == 0) {
                            view.onFailed("Data tidak di Temukan!");
                            view.hideProgressBar();
                        } else {
                            view.onSuccess(response);
                            view.hideProgressBar();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.i("ERROR", "onError: ", anError);
                        view.onFailed("Gagal Mengambil data dari Server!");
                    }
                });
    }
}
