package com.andrew.moviecatalogue.widget.tvshowfavorite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.database.MyApp;
import com.andrew.moviecatalogue.database.TvShowFavoriteDao;
import com.andrew.moviecatalogue.model.favorite.TvShowFavorite;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TvShowRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<TvShowFavorite> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    TvShowRemoteViewFactory(Context context) {
        this.mContext = context;
    }

    private void getTvShowFavorite() {
        TvShowFavoriteDao tvshowDb = MyApp.getDbInstance(mContext).tvShowFavoriteDao();
        mWidgetItems.clear();
        mWidgetItems.addAll(tvshowDb.getTvShowFavorite());

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        getTvShowFavorite();
    }

    @Override
    public void onDestroy() {
        this.mWidgetItems.clear();
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        TvShowFavorite tvShowFavorite = mWidgetItems.get(i);

        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(ApiConfiguration.POSTER_PATH + tvShowFavorite.getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(TvShowFavoriteWidget.TVSHOW_EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
