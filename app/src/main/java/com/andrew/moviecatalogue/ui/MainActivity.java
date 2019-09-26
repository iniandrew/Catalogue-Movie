package com.andrew.moviecatalogue.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.ui.favorite.FavoriteFragment;
import com.andrew.moviecatalogue.ui.movie.MovieFragment;
import com.andrew.moviecatalogue.ui.tvshow.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_movie:
                    getFragmentMovie();
                    return true;
                case R.id.navigation_tv_show:
                    getFragmentTvShow();
                    return true;
                case R.id.navigation_favorite:
                    getFragmentFavorite();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_movie);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    void getFragmentMovie() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new MovieFragment(), MovieFragment.class.getSimpleName())
                .commit();
    }

    void getFragmentTvShow() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new TvShowFragment(), TvShowFragment.class.getSimpleName())
                .commit();
    }

    void getFragmentFavorite() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new FavoriteFragment(), FavoriteFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
