package com.andrew.moviecatalogue.ui.tvshow;

import com.andrew.moviecatalogue.model.tvshow.TvShowResponse;

public interface TvShowFragmentView {
    void onSuccess(TvShowResponse movieResponse);
    void onFailed(String error);
    void showProgressBar();
    void hideProgressBar();
}
