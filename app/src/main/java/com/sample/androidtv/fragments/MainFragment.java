package com.sample.androidtv.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.androidtv.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BrowseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "on Activity Created");
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    private void initUI(){
        setTitle(getResources().getString(R.string.app_text));
        setBadgeDrawable(getResources().getDrawable(R.drawable.ic_app_header));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
        this.getView().setBackgroundColor(Color.LTGRAY);
    }
}
