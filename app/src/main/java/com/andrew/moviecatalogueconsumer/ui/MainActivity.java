package com.andrew.moviecatalogueconsumer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.andrew.moviecatalogueconsumer.R;
import com.andrew.moviecatalogueconsumer.ui.movie.MovieFragment;
import com.andrew.moviecatalogueconsumer.ui.tvshow.TvShowFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabs(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void tabs(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MovieFragment(), getString(R.string.movie));
        viewPagerAdapter.addFragment(new TvShowFragment(), getString(R.string.tv_show));
        viewPager.setAdapter(viewPagerAdapter);
    }
}
