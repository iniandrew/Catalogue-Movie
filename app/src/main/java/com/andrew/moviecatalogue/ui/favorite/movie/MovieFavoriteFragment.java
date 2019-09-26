package com.andrew.moviecatalogue.ui.favorite.movie;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.database.MyApp;
import com.andrew.moviecatalogue.model.favorite.MovieFavorite;
import com.andrew.moviecatalogue.ui.details.MovieDetailActivity;
import com.andrew.moviecatalogue.ui.favorite.adapter.MovieFavoriteAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {

    private MovieFavoriteAdapter adapter;

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MovieFavoriteAdapter();
        LiveData<List<MovieFavorite>> moviesFavorite = MyApp.getDbInstance(getContext()).movieFavoriteDao().getAllMovieFavorite();
        moviesFavorite.observe(this, new Observer<List<MovieFavorite>>() {
            @Override
            public void onChanged(@Nullable List<MovieFavorite> movieFavorites) {
                adapter.setItems(movieFavorites);
                adapter.notifyDataSetChanged();
            }
        });

        setupRecyclerView(view);

        adapter.setOnItemClickCallback(new MovieFavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieFavorite data) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra("movieId", data.getTmdbId());
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_movie_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

}
