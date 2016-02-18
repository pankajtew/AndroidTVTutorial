package com.sample.androidtv.background.managers;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;

import com.sample.androidtv.R;

/**
 * Created by pankaj on 18/2/16.
 */
public class SimpleBackgroundManager {

    private static final String TAG = SimpleBackgroundManager.class.getSimpleName();
    private static Drawable mDefaultBackground;
    private final int DEFAULT_BACKGROUND_RES_ID = R.drawable.lb_background;
    private Activity mActivity;
    private BackgroundManager mBackgroundManager;

    public SimpleBackgroundManager(Activity activity) {
        mActivity = activity;
        mDefaultBackground = activity.getDrawable(DEFAULT_BACKGROUND_RES_ID);
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        activity.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
    }

    public void updateBackground(Drawable drawable) {
        mBackgroundManager.setDrawable(drawable);
    }

    public void clearBackground() {
        mBackgroundManager.setDrawable(mDefaultBackground);
    }
}
