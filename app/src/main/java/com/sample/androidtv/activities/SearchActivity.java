package com.sample.androidtv.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sample.androidtv.R;
import com.sample.androidtv.fragments.SearchFragment;

/**
 * Created by pankaj on 6/5/16.
 */
public class SearchActivity extends Activity {

    SearchFragment mSearchFragment;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchFragment = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_fragment);
    }

}
