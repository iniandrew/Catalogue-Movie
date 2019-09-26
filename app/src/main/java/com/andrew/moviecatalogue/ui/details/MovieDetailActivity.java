package com.andrew.moviecatalogue.ui.details;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.database.MyApp;
import com.andrew.moviecatalogue.model.favorite.MovieFavorite;
import com.andrew.moviecatalogue.model.movie.MovieItems;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.andrew.moviecatalogue.widget.moviefavorite.MovieFavoriteWidget;
import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    private int movieId;
    private MovieItems movieItems;
    private MovieFavorite myMovieFavorite;
    private ImageView imgCover, imgPoster;
    private TextView tvTitle, tvReleaseDate, tvRating, tvOverview, tvPopularity;
    private FloatingActionButton btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgCover = findViewById(R.id.image_cover);
        imgPoster = findViewById(R.id.movie_poster);
        tvTitle = findViewById(R.id.movie_title);
        tvReleaseDate = findViewById(R.id.movie_release_date);
        tvOverview = findViewById(R.id.movie_overview);
        tvRating = findViewById(R.id.movie_rating);
        tvPopularity = findViewById(R.id.movie_popularity);
        btnFavorite = findViewById(R.id.btn_favorite);

        if (getIntent().getExtras() != null) {
            movieId = getIntent().getExtras().getInt("movieId");
            movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
        }

        myMovieFavorite = MyApp.getDbInstance(getApplicationContext()).movieFavoriteDao().getMovieFavoriteById(movieId);

        if (movieItems == null) {
            movieItems = new MovieItems();
            movieItems.setId(myMovieFavorite.getTmdbId());
            movieItems.setTitle(myMovieFavorite.getTitle());
            movieItems.setBackdropPath(myMovieFavorite.getBackdropPath());
            movieItems.setPosterPath(myMovieFavorite.getPosterPath());
            movieItems.setReleaseDate(myMovieFavorite.getReleaseDate());
            movieItems.setVoteAverage(myMovieFavorite.getVoteAverage());
            movieItems.setOverview(myMovieFavorite.getOverview());
            movieItems.setPopularity(myMovieFavorite.getPopularity());
        }

        if (myMovieFavorite != null) {
            btnFavorite.setImageResource(R.drawable.ic_favorite_white_24dp);
            setMovieFavoriteValue(myMovieFavorite);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(myMovieFavorite.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else {
            setMovieValue(movieItems);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(movieItems.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMovieFavorite = MyApp.getDbInstance(getApplicationContext()).movieFavoriteDao().getMovieFavoriteById(movieId);
                if  (myMovieFavorite == null) {
                    Toast.makeText(getApplicationContext(), R.string.favorite_added, Toast.LENGTH_SHORT).show();
                    myMovieFavorite = new MovieFavorite();
                    myMovieFavorite.setTmdbId(movieId);
                    myMovieFavorite.setTitle(movieItems.getTitle());
                    myMovieFavorite.setBackdropPath(movieItems.getBackdropPath());
                    myMovieFavorite.setPosterPath(movieItems.getPosterPath());
                    myMovieFavorite.setReleaseDate(movieItems.getReleaseDate());
                    myMovieFavorite.setVoteAverage(movieItems.getVoteAverage());
                    myMovieFavorite.setOverview(movieItems.getOverview());
                    myMovieFavorite.setPopularity(movieItems.getPopularity());
                    MyApp.getDbInstance(getApplicationContext()).movieFavoriteDao().insert(myMovieFavorite);

                    btnFavorite.setImageResource(R.drawable.ic_favorite_white_24dp);
                    updateWidget();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.favorite_deleted, Toast.LENGTH_SHORT).show();
                    MyApp.getDbInstance(getApplicationContext()).movieFavoriteDao().delete(myMovieFavorite);
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    updateWidget();
                }
            }
        });
    }

    private void setMovieValue(MovieItems items) {
        Glide.with(this).load(ApiConfiguration.POSTER_PATH + items.getBackdropPath()).into(imgCover);
        Glide.with(this).load(ApiConfiguration.POSTER_PATH + items.getPosterPath()).into(imgPoster);
        tvTitle.setText(items.getTitle());
        tvReleaseDate.setText(items.getReleaseDate());
        tvRating.setText(items.getVoteAverage());
        tvOverview.setText(items.getOverview());
        tvPopularity.setText(items.getPopularity());
    }

    private void setMovieFavoriteValue(MovieFavorite movieFavorite) {
        Glide.with(this).load(ApiConfiguration.POSTER_PATH + movieFavorite.getBackdropPath()).into(imgCover);
        Glide.with(this).load(ApiConfiguration.POSTER_PATH + movieFavorite.getPosterPath()).into(imgPoster);
        tvTitle.setText(movieFavorite.getTitle());
        tvReleaseDate.setText(movieFavorite.getReleaseDate());
        tvRating.setText(movieFavorite.getVoteAverage());
        tvOverview.setText(movieFavorite.getOverview());
        tvPopularity.setText(movieFavorite.getPopularity());
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName mWidget = new ComponentName(this, MovieFavoriteWidget.class);
        int[] ids = appWidgetManager.getAppWidgetIds(mWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.stack_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
