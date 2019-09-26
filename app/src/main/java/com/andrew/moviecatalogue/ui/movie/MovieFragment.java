package com.andrew.moviecatalogue.ui.movie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.model.movie.MovieItems;
import com.andrew.moviecatalogue.model.movie.MovieResponse;
import com.andrew.moviecatalogue.ui.details.MovieDetailActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieFragmentView {
    public static final String KEY_MOVIES = "movies";

    private ArrayList<MovieItems> movies = new ArrayList<>();
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    MoviePresenter presenter;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progress_bar);
        presenter = new MoviePresenter(this);
        adapter = new MovieAdapter(movies);

        setupRecyclerView();

        if (savedInstanceState == null){
            presenter.getListMovies();
        } else {
            movies = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            adapter.setData(movies);
            hideProgressBar();
        }

        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItems data) {
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, data);
                intent.putExtra("movieId", data.getId());
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView() {
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        SearchManager searchManager;
        Context context = getContext();

        if (context != null) {
            searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        } else {
            searchManager = null;
        }

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    getDataByQuery(s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                presenter.getListMovies();
                return true;
            }
        });
    }

    private void getDataByQuery(String movieTitle) {
        presenter.getListMoviesByQuery(movieTitle);
    }

    @Override
    public void onSuccess(MovieResponse movieResponse) {
        movies = movieResponse.getResults();
        adapter.setData(movies);
        hideProgressBar();
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getContext(), "Error! " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIES, movies);
        super.onSaveInstanceState(outState);
    }
}
