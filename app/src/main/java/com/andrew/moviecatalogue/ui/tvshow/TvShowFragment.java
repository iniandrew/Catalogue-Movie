package com.andrew.moviecatalogue.ui.tvshow;


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
import com.andrew.moviecatalogue.model.tvshow.TvShowItems;
import com.andrew.moviecatalogue.model.tvshow.TvShowResponse;
import com.andrew.moviecatalogue.ui.details.TvShowDetailActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements TvShowFragmentView {
    public static final String KEY_TV_SHOW = "tv_show";

    private ArrayList<TvShowItems> tvShows = new ArrayList<>();
    private TvShowAdapter adapter;
    private ProgressBar progressBar;
    TvShowPresenter presenter;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        presenter = new TvShowPresenter(this);
        adapter = new TvShowAdapter(tvShows);

        setupRecyclerView(view);

        if (savedInstanceState == null) {
            presenter.getListTvShows();
        } else {
            tvShows = savedInstanceState.getParcelableArrayList(KEY_TV_SHOW);
            adapter.setData(tvShows);
            hideProgressBar();
        }

        adapter.setOnItemClickCallback(new TvShowAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowItems data) {
                Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
                intent.putExtra("tvShowId", data.getId());
                intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, data);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvTvShow = view.findViewById(R.id.rv_tvshow);
        rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShow.setHasFixedSize(true);
        rvTvShow.setAdapter(adapter);
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
                presenter.getListTvShows();
                return true;
            }
        });
    }

    private void getDataByQuery(String tvTitle) {
        presenter.getListMoviesByQuery(tvTitle);
    }

    @Override
    public void onSuccess(TvShowResponse tvShowResponse) {
        tvShows = tvShowResponse.getResults();
        adapter.setData(tvShows);
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
        outState.putParcelableArrayList(KEY_TV_SHOW, tvShows);
        super.onSaveInstanceState(outState);
    }
}
