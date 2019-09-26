package com.andrew.moviecatalogueconsumer;

import android.net.Uri;

public class Utils {
    private static final String AUTHORITY = "com.andrew.moviecatalogue";
    private static final String SCHEME = "content";

    public static Uri getContentUri(String tableName) {
        return new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(tableName)
                .build();
    }
}
