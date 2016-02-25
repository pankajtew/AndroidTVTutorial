package com.sample.androidtv.activities;

import android.app.Activity;
import android.os.Bundle;

import com.sample.androidtv.R;

public class ErrorActivity extends Activity {

    private com.sample.androidtv.fragments.ErrorFragment errorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testError();
    }

    private void testError() {
        errorFragment = new com.sample.androidtv.fragments.ErrorFragment();
        getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, errorFragment).commit();
    }
}
