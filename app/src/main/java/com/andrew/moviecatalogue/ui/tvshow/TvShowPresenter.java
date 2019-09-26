package com.andrew.moviecatalogue.ui.tvshow;

import android.util.Log;

import com.andrew.moviecatalogue.model.tvshow.TvShowResponse;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class TvShowPresenter {
    private TvShowFragmentView view;

    public TvShowPresenter(TvShowFragmentView view) {
        this.view = view;
    }

    public void getListTvShows() {
        view.showProgressBar();
        AndroidNetworking.get(ApiConfiguration.TMDB_TVSHOW)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(TvShowResponse.class, new ParsedRequestListener<TvShowResponse>() {
                    @Override
                    public void onResponse(TvShowResponse response) {
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

    public void getListMoviesByQuery(String tvTitle) {
        view.showProgressBar();
        AndroidNetworking.get(ApiConfiguration.SEARCH_TVSHOW)
                .addPathParameter("query", tvTitle)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(TvShowResponse.class, new ParsedRequestListener<TvShowResponse>() {
                    @Override
                    public void onResponse(TvShowResponse response) {
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

