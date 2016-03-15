package com.sample.androidtv.fragments;

import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sample.androidtv.R;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.model.SearchResult;
import com.sample.androidtv.network.OMDBClient;
import com.sample.androidtv.presenters.MovieCardPresenter;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pankaj on 15/3/16.
 */
public class VerticalGridFragment extends android.support.v17.leanback.app.VerticalGridFragment {

    private static final String TAG = VerticalGridFragment.class.getSimpleName();

    private HashMap<String, SearchResult> searchList;

    private ArrayObjectAdapter mAdapter;

    private SpinnerFragment spinnerFragment;

    private int totalPages;

    private String screen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragment();
        screen = "x-men";
        loadMultiRowsData(screen);
    }

    private void setupFragment() {
        setTitle(screen);
        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(5);
        setGridPresenter(gridPresenter);
    }

    private void loadMultiRowsData(final String movieName) {
        final RequestParams searchParams = new RequestParams();
        searchParams.put("s", movieName);
        searchParams.put("type", "movie");
        spinnerFragment = new SpinnerFragment();
        getFragmentManager().beginTransaction().add(R.id.vertical_grid_fragment, spinnerFragment).commit();
        OMDBClient.get("", searchParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final Gson gson = new Gson();
                SearchResult result = gson.fromJson(new String(responseBody), SearchResult.class);
                if (result.getTotalResults() != null)
                    totalPages = Integer.parseInt(result.getTotalResults()) / 10 + 1;
                searchList = new HashMap<String, SearchResult>();
                for (int i = 1; i <= totalPages; i++) {
                    RequestParams params = new RequestParams();
                    params.put("s", movieName);
                    params.put("type", "movie");
                    params.put("page", i);
                    final int finalI = i;
                    OMDBClient.get("", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            SearchResult movieList = gson.fromJson(new String(responseBody), SearchResult.class);
                            searchList.put(String.valueOf(finalI), movieList);
                            if (finalI == totalPages) {
                                getFragmentManager().beginTransaction().remove(spinnerFragment).commit();

                                if (searchList.size() > 0) {
                                    mAdapter = new ArrayObjectAdapter(new MovieCardPresenter());

//                                    for (int i = 1; i <= totalPages; i++) { // This loop is to for increasing the number of contents. not necessary.
                                    for (Map.Entry<String, SearchResult> entry : searchList.entrySet()) {
                                        // String categoryName = entry.getKey();
                                        SearchResult entryValue = entry.getValue();
                                        if (entryValue.getSearch() != null) {
                                            for (int i = 0; i < entryValue.getSearch().length; i++) {
                                                Search movie = entryValue.getSearch()[i];
                                                if (!movie.getPoster().equalsIgnoreCase("N/A"))
                                                    mAdapter.add(movie);
                                            }
                                            Log.i(TAG, entryValue.getSearch().length + "");
                                        }
//                    for (int j = 0; j < list.size(); j++) {
//                        Movie movie = list.get(j);
//                        mAdapter.add(movie);
                                    }
                                    //                                   }
                                    setAdapter(mAdapter);
                                }

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            getFragmentManager().beginTransaction().remove(spinnerFragment).commit();
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                getFragmentManager().beginTransaction().remove(spinnerFragment).commit();
            }
        });


    }
}

