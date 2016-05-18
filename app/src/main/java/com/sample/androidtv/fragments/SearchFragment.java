package com.sample.androidtv.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.util.Log;

import com.google.gson.Gson;
import com.sample.androidtv.R;
import com.sample.androidtv.Utils.Utils;
import com.sample.androidtv.adapters.SearchAdapter;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.model.SearchResult;
import com.sample.androidtv.presenters.MovieCardPresenter;
import com.sample.androidtv.presenters.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pankaj on 6/5/16.
 */
public class SearchFragment extends android.support.v17.leanback.app.SearchFragment implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider {

    private static final String TAG = SearchFragment.class.getSimpleName();

    private static final int REQUEST_SPEECH = 0x00000010;

    private ArrayObjectAdapter mRowsAdapter;

    private SearchAdapter mSearchAdapter;

    private String mSearchQuery;

    private SpinnerFragment mSpinnerFragment;

    private String sampleResult = "{\"Search\":[{\"Title\":\"Batman Begins\",\"Year\":\"2005\",\"imdbID\":\"tt0372784\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg\"},{\"Title\":\"Batman\",\"Year\":\"1989\",\"imdbID\":\"tt0096895\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg\"},{\"Title\":\"Batman v Superman: Dawn of Justice\",\"Year\":\"2016\",\"imdbID\":\"tt2975590\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTE5NzU3MTYzOF5BMl5BanBnXkFtZTgwNTM5NjQxODE@._V1_SX300.jpg\"},{\"Title\":\"Batman Returns\",\"Year\":\"1992\",\"imdbID\":\"tt0103776\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BODM2OTc0Njg2OF5BMl5BanBnXkFtZTgwMDA4NjQxMTE@._V1_SX300.jpg\"},{\"Title\":\"Batman Forever\",\"Year\":\"1995\",\"imdbID\":\"tt0112462\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMjAwOTEyNjg0M15BMl5BanBnXkFtZTYwODQyMTI5._V1_SX300.jpg\"},{\"Title\":\"Batman & Robin\",\"Year\":\"1997\",\"imdbID\":\"tt0118688\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTM1NTIyNjkwM15BMl5BanBnXkFtZTcwODkxOTQxMQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Animated Series\",\"Year\":\"1992–1995\",\"imdbID\":\"tt0103359\",\"Type\":\"series\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTU3MjcwNzY3NF5BMl5BanBnXkFtZTYwNzA2MTI5._V1_SX300.jpg\"},{\"Title\":\"Batman: The Dark Knight Returns, Part 1\",\"Year\":\"2012\",\"imdbID\":\"tt2313197\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMzIxMDkxNDM2M15BMl5BanBnXkFtZTcwMDA5ODY1OQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: Under the Red Hood\",\"Year\":\"2010\",\"imdbID\":\"tt1569923\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMwNDEyMjExOF5BMl5BanBnXkFtZTcwMzU4MDU0Mw@@._V1_SX300.jpg\"},{\"Title\":\"Batman: Mask of the Phantasm\",\"Year\":\"1993\",\"imdbID\":\"tt0106364\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMzODU0NTYxN15BMl5BanBnXkFtZTcwNDUxNzUyMQ@@._V1_SX300.jpg\"}],\"totalResults\":\"298\",\"Response\":\"True\"}";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        mSearchAdapter = new SearchAdapter(new SearchPresenter());
        setSearchResultProvider(this);
        if (!Utils.hasPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) {
            // SpeechRecognitionCallback is not required and if not provided recognition will be handled
            // using internal speech recognizer, in which case you must have RECORD_AUDIO permission
            setSpeechRecognitionCallback(new SpeechRecognitionCallback() {
                @Override
                public void recognizeSpeech() {
                    Log.v(TAG, "recognizeSpeech");
                    try {
                        startActivityForResult(getRecognizerIntent(), REQUEST_SPEECH);
                    } catch (ActivityNotFoundException e) {
                        Log.e(TAG, "Cannot find activity for speech recognizer", e);
                    }
                }
            });
        }


    }

    public boolean hasResults() {
        return mRowsAdapter.size() > 0;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "onActivityResult requestCode=" + requestCode +
                " resultCode=" + resultCode +
                " data=" + data);

        switch (requestCode) {
            case REQUEST_SPEECH:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        setSearchQuery(data, true);
                        break;
                }
        }
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        Log.d(TAG, "getResultsAdapter");
        // Delete previously implemented mock code.
        // mRowsAdapter (Search result) is already prepared in loadRows method
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        Log.i(TAG, String.format("Search Query Text Change %s", newQuery));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i(TAG, String.format("Search Query Text Submit %s", query));
        mSearchQuery = query;
        mSpinnerFragment = new SpinnerFragment();
        getFragmentManager().beginTransaction().add(R.id.search_fragment, mSpinnerFragment).commit();
        loadRows();
        return true;
    }

    private void loadRows() {
        // offload processing from the UI thread
        new AsyncTask<String, Void, ListRow>() {
            private final String query = mSearchQuery;

            @Override
            protected void onPreExecute() {
                mRowsAdapter.clear();

            }

            @Override
            protected ListRow doInBackground(String... params) {
                final List<Search> result = new ArrayList<>();
                Gson gson = new Gson();
                HeaderItem cardHeaderItem = new HeaderItem(0, "batman".toUpperCase() + " Movies");
                MovieCardPresenter movieCardPresenter = new MovieCardPresenter();
                SearchPresenter searchPresenter = new SearchPresenter();
                SearchResult searchResult = gson.fromJson(sampleResult, SearchResult.class);
                ArrayObjectAdapter movieCardRowAdapter = new ArrayObjectAdapter(movieCardPresenter);
                SearchAdapter searchAdapter = new SearchAdapter(searchPresenter);
                for (int i = 0; i < searchResult.getSearch().length; i++) {
                    Search movie = searchResult.getSearch()[i];
                    if (!movie.getPoster().equalsIgnoreCase("N/A"))
                        //movieCardRowAdapter.add(movie);
                        mSearchAdapter.add(movie);
                }
                HeaderItem header = new HeaderItem("Search Results");


                //return new ListRow(header, movieCardRowAdapter);
                return new ListRow(header, mSearchAdapter);
            }

            @Override
            protected void onPostExecute(ListRow listRow) {
                mRowsAdapter.add(listRow);
                getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
