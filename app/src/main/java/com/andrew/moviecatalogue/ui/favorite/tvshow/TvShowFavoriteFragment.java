package com.andrew.moviecatalogue.ui.favorite.tvshow;


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
import com.andrew.moviecatalogue.model.favorite.TvShowFavorite;
import com.andrew.moviecatalogue.ui.details.TvShowDetailActivity;
import com.andrew.moviecatalogue.ui.favorite.adapter.TvShowFavoriteAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment {

    private TvShowFavoriteAdapter adapter;

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TvShowFavoriteAdapter();
        LiveData<List<TvShowFavorite>> liveData = MyApp.getDbInstance(getContext()).tvShowFavoriteDao().getAllTvShowFavorite();
        liveData.observe(this, new Observer<List<TvShowFavorite>>() {
            @Override
            public void onChanged(@Nullable List<TvShowFavorite> tvShowFavorites) {
                adapter.setItems(tvShowFavorites);
                adapter.notifyDataSetChanged();
            }
        });

        setupRecyclerView(view);

        adapter.setOnItemClickCallback(new TvShowFavoriteAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowFavorite data) {
                Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
                intent.putExtra("tvShowId", data.getTmdbId());
                startActivity(intent);
            }
        });

    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_tvshow_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

}
