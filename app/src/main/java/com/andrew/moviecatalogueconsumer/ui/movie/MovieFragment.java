package com.andrew.moviecatalogueconsumer.ui.movie;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrew.moviecatalogueconsumer.R;
import com.andrew.moviecatalogueconsumer.entity.MovieItem;

import java.util.ArrayList;

import static com.andrew.moviecatalogueconsumer.Utils.getContentUri;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private static final int LOADER_MOVIE = 1;
    private MovieAdapter adapter;
    private ArrayList<MovieItem> listMovies = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MovieAdapter(listMovies);

        RecyclerView rvMovies = view.findViewById(R.id.rv_movie);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(adapter);

        getLoaderManager().initLoader(LOADER_MOVIE, null, mLoaderCallbacks);
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
                    getContentUri(MovieItem.TABLE_NAME),
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            listMovies = mapCursorToArrayList(data);
            if (listMovies.size() > 0) {
                adapter.setData(listMovies);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    };


    private static ArrayList<MovieItem> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<MovieItem> movieList = new ArrayList<>();

        while (movieCursor.moveToNext()) {
            int tmdbId = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(MovieItem.COLUMN_TMDB_ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MovieItem.COLUMN_TITLE));
            String posterPath = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MovieItem.COLUMN_POSTER_PATH));
            String overView = movieCursor.getString(movieCursor.getColumnIndexOrThrow(MovieItem.COLUMN_OVERVIEW));
            movieList.add(new MovieItem(tmdbId, title, posterPath, overView));
        }

        return movieList;
    }
}
