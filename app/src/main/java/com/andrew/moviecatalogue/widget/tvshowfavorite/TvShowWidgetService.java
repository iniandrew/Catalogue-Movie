package com.andrew.moviecatalogue.widget.tvshowfavorite;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class TvShowWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TvShowRemoteViewFactory(this.getApplicationContext());
    }
}
