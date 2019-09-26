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
import com.andrew.moviecatalogue.model.favorite.TvShowFavorite;
import com.andrew.moviecatalogue.model.tvshow.TvShowItems;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.andrew.moviecatalogue.widget.tvshowfavorite.TvShowFavoriteWidget;
import com.bumptech.glide.Glide;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";

    private int tvShowId;
    private TvShowItems tvShowItems;
    private TvShowFavorite myTvShowFavorite;
    private ImageView imgCover, imgPoster;
    private TextView tvTitle, tvReleaseDate, tvRating, tvOverview, tvPopularity;
    private FloatingActionButton btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        imgCover = findViewById(R.id.tvShow_cover);
        imgPoster = findViewById(R.id.tvShow_poster);
        tvTitle = findViewById(R.id.tvShow_title);
        tvReleaseDate = findViewById(R.id.tvShow_release_date);
        tvOverview = findViewById(R.id.tvShow_overview);
        tvRating = findViewById(R.id.tvShow_rating);
        tvPopularity = findViewById(R.id.tvShow_popularity);
        btnFavorite = findViewById(R.id.btn_favorite);

        if (getIntent().getExtras() != null) {
            tvShowId = getIntent().getExtras().getInt("tvShowId");
            tvShowItems = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        }
        myTvShowFavorite = MyApp.getDbInstance(getApplicationContext()).tvShowFavoriteDao().getTvShowFavoriteById(tvShowId);

        if (tvShowItems == null) {
            tvShowItems = new TvShowItems();
            tvShowItems.setId(myTvShowFavorite.getTmdbId());
            tvShowItems.setTitle(myTvShowFavorite.getTitle());
            tvShowItems.setBackdropPath(myTvShowFavorite.getBackdropPath());
            tvShowItems.setPosterPath(myTvShowFavorite.getPosterPath());
            tvShowItems.setReleaseDate(myTvShowFavorite.getReleaseDate());
            tvShowItems.setVoteAverage(myTvShowFavorite.getVoteAverage());
            tvShowItems.setOverview(myTvShowFavorite.getOverview());
            tvShowItems.setPopularity(myTvShowFavorite.getPopularity());
        }

        if (myTvShowFavorite != null) {
            btnFavorite.setImageResource(R.drawable.ic_favorite_white_24dp);
            setTvShowFavoriteValue(myTvShowFavorite);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(myTvShowFavorite.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else {
            setTvShowValue(tvShowItems);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(tvShowItems.getTitle());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTvShowFavorite = MyApp.getDbInstance(getApplicationContext()).tvShowFavoriteDao().getTvShowFavoriteById(tvShowId);
                if  (myTvShowFavorite == null) {
                    Toast.makeText(getApplicationContext(), R.string.favorite_added, Toast.LENGTH_SHORT).show();
                    TvShowFavorite myTvShowFavorite = new TvShowFavorite();
                    myTvShowFavorite.setTmdbId(tvShowId);
                    myTvShowFavorite.setTitle(tvShowItems.getTitle());
                    myTvShowFavorite.setBackdropPath(tvShowItems.getBackdropPath());
                    myTvShowFavorite.setPosterPath(tvShowItems.getPosterPath());
                    myTvShowFavorite.setReleaseDate(tvShowItems.getReleaseDate());
                    myTvShowFavorite.setVoteAverage(tvShowItems.getVoteAverage());
                    myTvShowFavorite.setOverview(tvShowItems.getOverview());
                    myTvShowFavorite.setPopularity(tvShowItems.getPopularity());
                    MyApp.getDbInstance(getApplicationContext()).tvShowFavoriteDao().insert(myTvShowFavorite);

                    btnFavorite.setImageResource(R.drawable.ic_favorite_white_24dp);
                    updateWidget();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.favorite_deleted, Toast.LENGTH_SHORT).show();
                    MyApp.getDbInstance(getApplicationContext()).tvShowFavoriteDao().delete(myTvShowFavorite);
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    updateWidget();
                }
            }
        });
    }

    private void setTvShowValue(TvShowItems items) {
        Glide.with(this).load(ApiConfiguration.POSTER_PATH + items.getBackdropPath()).into(imgCover);
        Glide.with(this).load(ApiConfiguration.POSTER_PATH + items.getPosterPath()).into(imgPoster);
        tvTitle.setText(items.getTitle());
        tvReleaseDate.setText(items.getReleaseDate());
        tvRating.setText(items.getVoteAverage());
        tvOverview.setText(items.getOverview());
        tvPopularity.setText(items.getPopularity());
    }

    private void setTvShowFavoriteValue(TvShowFavorite tvShowFavorite) {
        Glide.with(this).load(ApiConfiguration.POSTER_PATH + tvShowFavorite.getBackdropPath()).into(imgCover);
        Glide.with(this).load(ApiConfiguration.POSTER_PATH + tvShowFavorite.getPosterPath()).into(imgPoster);
        tvTitle.setText(tvShowFavorite.getTitle());
        tvReleaseDate.setText(tvShowFavorite.getReleaseDate());
        tvRating.setText(tvShowFavorite.getVoteAverage());
        tvOverview.setText(tvShowFavorite.getOverview());
        tvPopularity.setText(tvShowFavorite.getPopularity());
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName mWidget = new ComponentName(this, TvShowFavoriteWidget.class);
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
