package com.andrew.moviecatalogue.ui.movie;


import com.andrew.moviecatalogue.model.movie.MovieResponse;

public interface MovieFragmentView {
    void onSuccess(MovieResponse movieResponse);
    void onFailed(String error);
    void showProgressBar();
    void hideProgressBar();
}
