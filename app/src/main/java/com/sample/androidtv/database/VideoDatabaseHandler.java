package com.sample.androidtv.database;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.model.SearchResult;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pankaj on 17/5/16.
 */
public class VideoDatabaseHandler {
    public static final String KEY_NAME =
            SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String KEY_DATA_TYPE =
            SearchManager.SUGGEST_COLUMN_CONTENT_TYPE;
    public static final String KEY_PRODUCTION_YEAR =
            SearchManager.SUGGEST_COLUMN_PRODUCTION_YEAR;
    public static final String KEY_COLUMN_DURATION =
            SearchManager.SUGGEST_COLUMN_DURATION;
    public static final String KEY_ACTION =
            SearchManager.SUGGEST_COLUMN_INTENT_ACTION;
    public static final String KEY_ICON =
            SearchManager.SUGGEST_COLUMN_RESULT_CARD_IMAGE;
    private static final String DATABASE_NAME = "video_database_leanback";
    private static final int DATABASE_VERSION = 1;
    private static final String FTS_VIRTUAL_TABLE = "Leanback_table";
    private static final HashMap<String, String> COLUMN_MAP =
            buildColumnMap();
    private final VideoDatabaseOpenHelper mDatabaseOpenHelper;

    public VideoDatabaseHandler(Context context) {
        mDatabaseOpenHelper = new VideoDatabaseOpenHelper(context);
    }

    private static HashMap<String, String> buildColumnMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_NAME, KEY_NAME);
        map.put(KEY_ICON, KEY_ICON);
        map.put(KEY_DATA_TYPE, KEY_DATA_TYPE);
        map.put(KEY_PRODUCTION_YEAR, KEY_PRODUCTION_YEAR);
        map.put(KEY_COLUMN_DURATION, KEY_COLUMN_DURATION);
        map.put(KEY_ACTION, KEY_ACTION);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid AS " +
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid AS " +
                SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);

        return map;
    }

    public Cursor getWordMatch(String query, String[] columns) {
        String selection = KEY_NAME + " MATCH ?";
        String[] selectionArgs = new String[]{query + "*"};

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[]
            columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);
        builder.setProjectionMap(COLUMN_MAP);

        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    private static class VideoDatabaseOpenHelper extends SQLiteOpenHelper {
        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " USING fts3 (" +
                        KEY_NAME + ", " +
                        KEY_DATA_TYPE + "," +
                        KEY_ACTION + "," +
                        KEY_PRODUCTION_YEAR + "," +
                        KEY_ICON + "," +
                        KEY_COLUMN_DURATION + ");";
        private final WeakReference<Context> mHelperContext;
        private SQLiteDatabase mDatabase;

        public VideoDatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = new WeakReference<Context>(context);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(FTS_TABLE_CREATE);
            loadDatabase();

        }

        private void loadDatabase() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        loadVideos();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void loadVideos() throws IOException {
            Search[] videos;

            String json = "{\"Search\":[{\"Title\":\"Batman Begins\",\"Year\":\"2005\",\"imdbID\":\"tt0372784\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg\"},{\"Title\":\"Batman\",\"Year\":\"1989\",\"imdbID\":\"tt0096895\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg\"},{\"Title\":\"Batman v Superman: Dawn of Justice\",\"Year\":\"2016\",\"imdbID\":\"tt2975590\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTE5NzU3MTYzOF5BMl5BanBnXkFtZTgwNTM5NjQxODE@._V1_SX300.jpg\"},{\"Title\":\"Batman Returns\",\"Year\":\"1992\",\"imdbID\":\"tt0103776\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BODM2OTc0Njg2OF5BMl5BanBnXkFtZTgwMDA4NjQxMTE@._V1_SX300.jpg\"},{\"Title\":\"Batman Forever\",\"Year\":\"1995\",\"imdbID\":\"tt0112462\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMjAwOTEyNjg0M15BMl5BanBnXkFtZTYwODQyMTI5._V1_SX300.jpg\"},{\"Title\":\"Batman & Robin\",\"Year\":\"1997\",\"imdbID\":\"tt0118688\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTM1NTIyNjkwM15BMl5BanBnXkFtZTcwODkxOTQxMQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Animated Series\",\"Year\":\"1992–1995\",\"imdbID\":\"tt0103359\",\"Type\":\"series\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTU3MjcwNzY3NF5BMl5BanBnXkFtZTYwNzA2MTI5._V1_SX300.jpg\"},{\"Title\":\"Batman: The Dark Knight Returns, Part 1\",\"Year\":\"2012\",\"imdbID\":\"tt2313197\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMzIxMDkxNDM2M15BMl5BanBnXkFtZTcwMDA5ODY1OQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: Under the Red Hood\",\"Year\":\"2010\",\"imdbID\":\"tt1569923\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMwNDEyMjExOF5BMl5BanBnXkFtZTcwMzU4MDU0Mw@@._V1_SX300.jpg\"},{\"Title\":\"Batman: Mask of the Phantasm\",\"Year\":\"1993\",\"imdbID\":\"tt0106364\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMzODU0NTYxN15BMl5BanBnXkFtZTcwNDUxNzUyMQ@@._V1_SX300.jpg\"}],\"totalResults\":\"298\",\"Response\":\"True\"}";
            Type collection = new TypeToken<ArrayList<Search>>() {
            }.getType();

            Gson gson = new Gson();
            SearchResult sampleResult = gson.fromJson(json, SearchResult.class);
            videos = sampleResult.getSearch();

            for (Search video : videos) {
                addVideoForDeepLink(video);
            }
        }

        public void addVideoForDeepLink(Search video) {
            ContentValues initialValues = new ContentValues();
            if (video.getPoster() != null && !video.getPoster().equalsIgnoreCase("N/A")) {
                initialValues.put(KEY_NAME, video.getTitle());
                initialValues.put(KEY_ICON, video.getPoster());
                initialValues.put(KEY_DATA_TYPE, "video/mp4");
                initialValues.put(KEY_PRODUCTION_YEAR, "2015");
                initialValues.put(KEY_COLUMN_DURATION, 6400000);

                mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
