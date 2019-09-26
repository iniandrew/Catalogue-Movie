package com.andrew.moviecatalogue.ui.favorite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.ui.favorite.adapter.ViewPagerAdapter;
import com.andrew.moviecatalogue.ui.favorite.movie.MovieFavoriteFragment;
import com.andrew.moviecatalogue.ui.favorite.tvshow.TvShowFavoriteFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
     private ViewPager viewPager;
     private TabLayout tabLayout;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new MovieFavoriteFragment(), getString(R.string.movie));
        viewPagerAdapter.addFragment(new TvShowFavoriteFragment(), getString(R.string.tv_show));
        viewPager.setAdapter(viewPagerAdapter);
    }
}
