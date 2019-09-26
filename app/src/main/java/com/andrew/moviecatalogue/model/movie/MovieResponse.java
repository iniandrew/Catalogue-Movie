package com.andrew.moviecatalogue.model.movie;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {

    @SerializedName("results")
    private ArrayList<MovieItems> results;

    public ArrayList<MovieItems> getResults() {
        return results;
    }
}
