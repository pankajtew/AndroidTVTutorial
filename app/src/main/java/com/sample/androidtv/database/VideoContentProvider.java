package com.sample.androidtv.database;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pankaj on 17/5/16.
 */
public class VideoContentProvider extends ContentProvider {

    private VideoDatabaseHandler mVideoDatabase;

    @Override
    public boolean onCreate() {
        mVideoDatabase = new VideoDatabaseHandler(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {


        String query = selectionArgs[0];

        query = query.toLowerCase();
        String[] columns = new String[]{
                BaseColumns._ID,
                VideoDatabaseHandler.KEY_NAME,
                VideoDatabaseHandler.KEY_ICON,
                VideoDatabaseHandler.KEY_DATA_TYPE,
                VideoDatabaseHandler.KEY_PRODUCTION_YEAR,
                VideoDatabaseHandler.KEY_COLUMN_DURATION,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
        };
        return mVideoDatabase.getWordMatch(query, columns);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
