package com.andrew.moviecatalogue.model.tvshow;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowResponse {

    @SerializedName("results")
    private ArrayList<TvShowItems> results;

    public ArrayList<TvShowItems> getResults() {
        return results;
    }
}
