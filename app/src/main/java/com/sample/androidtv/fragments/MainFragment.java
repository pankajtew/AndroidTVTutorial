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
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sample.androidtv.R;
import com.sample.androidtv.activities.ErrorActivity;
import com.sample.androidtv.activities.GuidedStepActivity;
import com.sample.androidtv.activities.SearchActivity;
import com.sample.androidtv.activities.VerticalGridActivity;
import com.sample.androidtv.activities.VideoDetailsActivity;
import com.sample.androidtv.background.managers.GlideBackgroundManager;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.model.SearchResult;
import com.sample.androidtv.network.OMDBClient;
import com.sample.androidtv.presenters.CustomHeaderItemPresenter;
import com.sample.androidtv.presenters.CustomListRowPresenter;
import com.sample.androidtv.presenters.GridItemImagePresenter;
import com.sample.androidtv.presenters.GridTextItemPresenter;
import com.sample.androidtv.presenters.MovieCardPresenter;
import com.sample.androidtv.recommendation.RecommendationFactory;
import com.sample.androidtv.view.model.CustomHeaderItem;
import com.sample.androidtv.view.model.IMenuItems;
import com.sample.androidtv.view.rows.CustomListRow;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BrowseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private ArrayObjectAdapter mRowsAdapter;
    private ArrayList<Search> mItems = new ArrayList<>();
    private GlideBackgroundManager mBackgroundManager;
    private static int recommendationCounter = 0;
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
        //loadMultipleRows();
        loadCustomHeaderRow();
        //loadMultipleRows();
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
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadCustomHeaderRow() {
        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return new CustomHeaderItemPresenter();
            }
        });
        mRowsAdapter = new ArrayObjectAdapter(new CustomListRowPresenter());
        CustomHeaderItem gridItemTextPresenterCustomHeaderRow0 = new CustomHeaderItem(0, "With Drawable", android.R.drawable.ic_dialog_email);
        CustomHeaderItem gridItemTextPresenterCustomHeaderRow1 = new CustomHeaderItem(1, "With URL", "http://icons.iconarchive.com/icons/designbolts/free-multimedia/1024/iMac-icon.png", IMenuItems.MENU_ITEM_WITH_ICON);
        CustomHeaderItem gridItemTextPresenterCustomHeaderRow2 = new CustomHeaderItem(2, "Divider Non Focus", android.R.drawable.ic_menu_report_image, IMenuItems.MENU_DIVIDER_ITEM_WITH_ICON, false);
        CustomHeaderItem gridItemTextPresenterCustomHeaderRow3 = new CustomHeaderItem(3, "Divider Focus", "http://dl.hiapphere.com/data/icon/201409/HiAppHere_com_kov.theme.lumos.png", IMenuItems.MENU_DIVIDER_ITEM_WITH_ICON);
        CustomHeaderItem gridItemTextPresenterCustomHeaderRow4 = new CustomHeaderItem(3, "Multi Rows ", "https://cdn2.iconfinder.com/data/icons/budicon-interface-layout-2/16/131-interface_-_list_row_layout-128.png", IMenuItems.MENU_ITEM_WITH_ICON);
        GridTextItemPresenter gridTextItemPresenter = new GridTextItemPresenter();
        ArrayObjectAdapter gridTextRowAdapter = new ArrayObjectAdapter(gridTextItemPresenter);
        gridTextRowAdapter.add("Item ---> 1");
        gridTextRowAdapter.add("Item ---> 2");
        gridTextRowAdapter.add("Item ---> 3");
        gridTextRowAdapter.add("Item ---> 4");
        gridTextRowAdapter.add("GuidedStepFragment");

        ArrayObjectAdapter gridTextObjectAdapter = new ArrayObjectAdapter(gridTextItemPresenter);
        gridTextObjectAdapter.add("ITEM-CUSTOM-ROW-1");
        gridTextObjectAdapter.add("ITEM-CUSTOM-ROW-2");
        gridTextObjectAdapter.add("ITEM-CUSTOM-ROW-3");
        gridTextObjectAdapter.add("ITEM-CUSTOM-ROW-4");
        CustomListRow customListRow = new CustomListRow(gridItemTextPresenterCustomHeaderRow4, gridTextObjectAdapter);
        customListRow.setNumRows(4);
//        mRowsAdapter.add(new CustomHeaderRow(gridItemTextPresenterCustomHeaderRow0, gridItemTextPresenterCustomHeaderRow0.getHeaderItem(), gridTextRowAdapter));
//        mRowsAdapter.add(new CustomHeaderRow(gridItemTextPresenterCustomHeaderRow1, gridItemTextPresenterCustomHeaderRow1.getHeaderItem(), gridTextRowAdapter));
//        mRowsAdapter.add(new CustomHeaderRow(gridItemTextPresenterCustomHeaderRow2, gridItemTextPresenterCustomHeaderRow2.getHeaderItem(), gridTextRowAdapter));
//        mRowsAdapter.add(new CustomHeaderRow(gridItemTextPresenterCustomHeaderRow3, gridItemTextPresenterCustomHeaderRow3.getHeaderItem(), gridTextRowAdapter));
        mRowsAdapter.add(customListRow);
        mRowsAdapter.add(new CustomListRow(gridItemTextPresenterCustomHeaderRow0, gridTextRowAdapter));
        mRowsAdapter.add(new CustomListRow(gridItemTextPresenterCustomHeaderRow1,loadSampleSearchData()));
        setAdapter(mRowsAdapter);

    }

    private ArrayObjectAdapter loadSampleSearchData(){
        String json = "{\"Search\":[{\"Title\":\"Batman Begins\",\"Year\":\"2005\",\"imdbID\":\"tt0372784\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg\"},{\"Title\":\"Batman\",\"Year\":\"1989\",\"imdbID\":\"tt0096895\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg\"},{\"Title\":\"Batman v Superman: Dawn of Justice\",\"Year\":\"2016\",\"imdbID\":\"tt2975590\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTE5NzU3MTYzOF5BMl5BanBnXkFtZTgwNTM5NjQxODE@._V1_SX300.jpg\"},{\"Title\":\"Batman Returns\",\"Year\":\"1992\",\"imdbID\":\"tt0103776\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BODM2OTc0Njg2OF5BMl5BanBnXkFtZTgwMDA4NjQxMTE@._V1_SX300.jpg\"},{\"Title\":\"Batman Forever\",\"Year\":\"1995\",\"imdbID\":\"tt0112462\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMjAwOTEyNjg0M15BMl5BanBnXkFtZTYwODQyMTI5._V1_SX300.jpg\"},{\"Title\":\"Batman & Robin\",\"Year\":\"1997\",\"imdbID\":\"tt0118688\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BNTM1NTIyNjkwM15BMl5BanBnXkFtZTcwODkxOTQxMQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Animated Series\",\"Year\":\"1992–1995\",\"imdbID\":\"tt0103359\",\"Type\":\"series\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTU3MjcwNzY3NF5BMl5BanBnXkFtZTYwNzA2MTI5._V1_SX300.jpg\"},{\"Title\":\"Batman: The Dark Knight Returns, Part 1\",\"Year\":\"2012\",\"imdbID\":\"tt2313197\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMzIxMDkxNDM2M15BMl5BanBnXkFtZTcwMDA5ODY1OQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: Under the Red Hood\",\"Year\":\"2010\",\"imdbID\":\"tt1569923\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMwNDEyMjExOF5BMl5BanBnXkFtZTcwMzU4MDU0Mw@@._V1_SX300.jpg\"},{\"Title\":\"Batman: Mask of the Phantasm\",\"Year\":\"1993\",\"imdbID\":\"tt0106364\",\"Type\":\"movie\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BMTMzODU0NTYxN15BMl5BanBnXkFtZTcwNDUxNzUyMQ@@._V1_SX300.jpg\"}],\"totalResults\":\"298\",\"Response\":\"True\"}";
        MovieCardPresenter movieCardPresenter = new MovieCardPresenter();
        ArrayObjectAdapter movieCardRowAdapter = new ArrayObjectAdapter(movieCardPresenter);
        Gson gson = new Gson();
        SearchResult result = gson.fromJson(new String(json), SearchResult.class);
        Log.i(TAG, result.toString());

        for (int i = 0; i < result.getSearch().length; i++) {
            Search movie = result.getSearch()[i];
            if (!movie.getPoster().equalsIgnoreCase("N/A")) {
                movieCardRowAdapter.add(movie);
                mItems.add(movie);
            }
        }

        return movieCardRowAdapter;
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
        CustomHeaderItem gridItemTextPresenterCustomHeader = new CustomHeaderItem(2, "Custom Header", android.R.drawable.ic_dialog_email);

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


    private ArrayObjectAdapter loadListDataFromApi(final int pos, final String movieName) {
        RequestParams searchParams = new RequestParams();
        searchParams.put("s", movieName);
        searchParams.put("type", "movie");
        final ArrayObjectAdapter[] arrayObjectAdapter = new ArrayObjectAdapter[1];
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
                arrayObjectAdapter[0] = movieCardRowAdapter;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG + "Error", new String(error.toString()));
            }
        });

        return arrayObjectAdapter[0];
    }

    private void loadListDataFromApiCustomHeader(final int pos, final String movieName) {
        RequestParams searchParams = new RequestParams();
        searchParams.put("s", movieName);
        searchParams.put("type", "movie");
        OMDBClient.get("", searchParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i(TAG, new String(responseBody));
                Gson gson = new Gson();
                SearchResult result = gson.fromJson(new String(responseBody), SearchResult.class);
                CustomHeaderItem cardHeaderItem = new CustomHeaderItem(pos, movieName.toUpperCase() + " Movies", "http://www.clipartbest.com/cliparts/ace/XLK/aceXLKdc4.gif", IMenuItems.MENU_ITEM_WITH_ICON);
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

                    if (((String) item).equalsIgnoreCase("Item ---> 1")) {
                        Intent intent = new Intent(getActivity(), VerticalGridActivity.class);
                        startActivity(intent);
                    } else if (((String) item).equalsIgnoreCase("GuidedStepFragment")) {
                        Intent intent = new Intent(getActivity(), GuidedStepActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), ErrorActivity.class);
                        startActivity(intent);
                    }

                } else if (item instanceof Search) {              // CardPresenter
                    Search movie = (Search) item;
                    Log.d(TAG, "Item: " + item.toString());
                    RecommendationFactory recommendationFactory = new RecommendationFactory(getActivity().getApplicationContext());
                    Search searchMovie = mItems.get(recommendationCounter % mItems.size());
                    recommendationFactory.recommend(recommendationCounter, movie, NotificationCompat.PRIORITY_HIGH);
                    Toast.makeText(getActivity(), "Recommendation sent (item " + recommendationCounter + ")", Toast.LENGTH_SHORT).show();
                    recommendationCounter++;
                    /*Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                    intent.putExtra("IMDB_MOVIE", movie);

                    getActivity().startActivity(intent);*/
                }
            }
        });

    }

}
