package com.sample.androidtv.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.sample.androidtv.R;
import com.sample.androidtv.presenters.GridItemImagePresenter;
import com.sample.androidtv.presenters.GridTextItemPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BrowseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private ArrayObjectAdapter mRowsAdapter;
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
    }

    private void initUI(){
        setTitle(getResources().getString(R.string.app_text));
        setBadgeDrawable(getResources().getDrawable(R.drawable.ic_app_header));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
        this.getView().setBackgroundColor(Color.LTGRAY);
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
        HeaderItem gridItemImagePresenterHeader = new HeaderItem(0, "GridItem Image Presenter");

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
        mRowsAdapter.add(new ListRow(gridItemImagePresenterHeader,gridImageRowAdapter));

        setAdapter(mRowsAdapter);
    }

}
