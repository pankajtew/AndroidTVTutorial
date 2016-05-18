package com.sample.androidtv.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.app.GuidedStepFragment;

import com.sample.androidtv.fragments.FirstStepFragment;

/**
 * Created by pankaj on 5/4/16.
 */
public class GuidedStepActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            GuidedStepFragment.add(getFragmentManager(), new FirstStepFragment());
        }
    }
}
