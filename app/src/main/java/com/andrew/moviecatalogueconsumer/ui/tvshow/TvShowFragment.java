package com.andrew.moviecatalogueconsumer.ui.tvshow;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrew.moviecatalogueconsumer.R;
import com.andrew.moviecatalogueconsumer.entity.TvShowItem;

import java.util.ArrayList;

import static com.andrew.moviecatalogueconsumer.Utils.getContentUri;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private static final int LOADER_TVSHOW = 2;
    private TvShowAdapter adapter;
    private ArrayList<TvShowItem> listTvShows = new ArrayList<>();

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TvShowAdapter(listTvShows);

        RecyclerView rvTvShow = view.findViewById(R.id.rv_tvshow);
        rvTvShow.setHasFixedSize(true);
        rvTvShow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvShow.setAdapter(adapter);

        getLoaderManager().initLoader(LOADER_TVSHOW, null, mLoaderCallbacks);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            Context context = getContext();

            if (context == null) {
                throw new NullPointerException();
            }

            return new CursorLoader(
                    context,
                    getContentUri(TvShowItem.TABLE_NAME),
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            listTvShows = mapCursorToArrayList(data);
            if (listTvShows.size() > 0) {
                adapter.setData(listTvShows);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    };


    private static ArrayList<TvShowItem> mapCursorToArrayList(Cursor tvShowCursor) {
        ArrayList<TvShowItem> tvShowsList = new ArrayList<>();

        while (tvShowCursor.moveToNext()) {
            int tmdbId = tvShowCursor.getInt(tvShowCursor.getColumnIndexOrThrow(TvShowItem.COLUMN_TMDB_ID));
            String title = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowItem.COLUMN_TITLE));
            String posterPath = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowItem.COLUMN_POSTER_PATH));
            String overView = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(TvShowItem.COLUMN_OVERVIEW));
            tvShowsList.add(new TvShowItem(tmdbId, title, posterPath, overView));
        }

        return tvShowsList;
    }
}
