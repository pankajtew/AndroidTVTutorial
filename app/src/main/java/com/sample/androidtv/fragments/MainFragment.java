package com.sample.androidtv.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sample.androidtv.R;
import com.sample.androidtv.activities.ErrorActivity;
import com.sample.androidtv.activities.VideoDetailsActivity;
import com.sample.androidtv.background.managers.GlideBackgroundManager;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.model.SearchResult;
import com.sample.androidtv.network.OMDBClient;
import com.sample.androidtv.presenters.GridItemImagePresenter;
import com.sample.androidtv.presenters.GridTextItemPresenter;
import com.sample.androidtv.presenters.MovieCardPresenter;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BrowseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private ArrayObjectAdapter mRowsAdapter;
    private GlideBackgroundManager mBackgroundManager;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "on Activity Created");
        super.onActivityCreated(savedInstanceState);
        initUI();
        //loadTextRows();
        //loadMediaIconRows();
        loadMultipleRows();
        mBackgroundManager = new GlideBackgroundManager(getActivity());
        setupEventListener();
    }

    private void initUI(){
        setTitle(getResources().getString(R.string.app_text));
        setBadgeDrawable(getResources().getDrawable(R.drawable.ic_app_header));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
        //this.getView().setBackgroundColor(Color.LTGRAY);
        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadTextRows(){
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        /* GridTextItemPresenter */
        HeaderItem gridItemPresenterHeader = new HeaderItem(0, "GridItem Text Presenter");

        GridTextItemPresenter mGridPresenter = new GridTextItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add("ITEM\n1");
        gridRowAdapter.add("ITEM\n2");
        gridRowAdapter.add("ITEM\n3");
        mRowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));

        /* set */
        setAdapter(mRowsAdapter);
    }

    private void loadMediaIconRows(){

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        /* GridImageItemPresenter */
        HeaderItem gridItemPresenterHeader = new HeaderItem(0, "GridItem Image Presenter");

        GridItemImagePresenter mGridPresenter = new GridItemImagePresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add("play");
        gridRowAdapter.add("rewind");
        gridRowAdapter.add("next");
        mRowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));

        /* set */
        setAdapter(mRowsAdapter);
    }

    private void loadMultipleRows(){

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        HeaderItem gridItemTextPresenterHeader = new HeaderItem(0, "GridItem Text Presenter");
        HeaderItem gridItemImagePresenterHeader = new HeaderItem(1, "GridItem Image Presenter");

        GridItemImagePresenter gridItemImagePresenter = new GridItemImagePresenter();
        GridTextItemPresenter gridTextItemPresenter = new GridTextItemPresenter();

        ArrayObjectAdapter gridTextRowAdapter = new ArrayObjectAdapter(gridTextItemPresenter);
        gridTextRowAdapter.add("Item ---> 1");
        gridTextRowAdapter.add("Item ---> 2");
        gridTextRowAdapter.add("Item ---> 3");
        gridTextRowAdapter.add("Item ---> 4");

        ArrayObjectAdapter gridImageRowAdapter = new ArrayObjectAdapter(gridItemImagePresenter);
        gridImageRowAdapter.add("play");
        gridImageRowAdapter.add("previous");
        gridImageRowAdapter.add("next");
        gridImageRowAdapter.add("fastforward");
        gridImageRowAdapter.add("rewind");
        gridImageRowAdapter.add("pause");

        mRowsAdapter.add(new ListRow(gridItemTextPresenterHeader,gridTextRowAdapter));
        mRowsAdapter.add(new ListRow(gridItemImagePresenterHeader, gridImageRowAdapter));


        loadDataFromApi(2, "batman");
        loadDataFromApi(3, "terminator");
        setAdapter(mRowsAdapter);
    }

    private void loadDataFromApi(final int pos, final String movieName) {
        RequestParams searchParams = new RequestParams();
        searchParams.put("s", movieName);
        searchParams.put("type", "movie");
        OMDBClient.get("", searchParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, new String(responseBody));
                Gson gson = new Gson();
                SearchResult result = gson.fromJson(new String(responseBody), SearchResult.class);
                HeaderItem cardHeaderItem = new HeaderItem(pos, movieName.toUpperCase() + " Movies");
                MovieCardPresenter movieCardPresenter = new MovieCardPresenter();
                ArrayObjectAdapter movieCardRowAdapter = new ArrayObjectAdapter(movieCardPresenter);
                Log.i(TAG, result.toString());

                for (int i = 0; i < result.getSearch().length; i++) {
                    Search movie = result.getSearch()[i];
                    if (!movie.getPoster().equalsIgnoreCase("N/A"))
                        movieCardRowAdapter.add(movie);
                }
                mRowsAdapter.add(new ListRow(cardHeaderItem, movieCardRowAdapter));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG + "Error", new String(error.toString()));
            }
        });
    }

    private void setupEventListener() {
        setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof String) {                    // GridItemPresenter
                    mBackgroundManager.updateBackgroundWithDelay("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/10/RIMG0656.jpg");
                } else if (item instanceof Search) {              // CardPresenter
                    mBackgroundManager.updateBackgroundWithDelay(((Search) item).getPoster());
                }
            }
        });

        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof String) {                    // GridItemPresenter

                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);

                } else if (item instanceof Search) {              // CardPresenter
                    Search movie = (Search) item;
                    Log.d(TAG, "Item: " + item.toString());
                    Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                    intent.putExtra("IMDB_MOVIE", movie);

                    getActivity().startActivity(intent);
                }
            }
        });

    }

}
