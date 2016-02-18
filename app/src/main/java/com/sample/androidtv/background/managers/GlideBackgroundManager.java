package com.sample.androidtv.background.managers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.sample.androidtv.R;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pankaj on 7/1/16.
 */
public class GlideBackgroundManager {
    private static final String TAG = GlideBackgroundManager.class.getSimpleName();

    private static int BACKGROUND_UPDATE_DELAY = 500;
    private static Drawable mDefaultBackground;
    private final int DEFAULT_BACKGROUND_RES_ID = R.drawable.lb_ic_thumb_up;
    // Handler attached with main thread
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    Timer mBackgroundTimer; // null when no UpdateBackgroundTask is running.
    private Activity mActivity;
    private BackgroundManager mBackgroundManager = null;
    private DisplayMetrics mMetrics;
    private URI mBackgroundURI;
    private GlideBackGroundManagerTarget mBackgroundTarget;

    public GlideBackgroundManager(Activity activity) {
        mActivity = activity;
        mDefaultBackground = activity.getDrawable(DEFAULT_BACKGROUND_RES_ID);
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        mBackgroundTarget = new GlideBackGroundManagerTarget(mBackgroundManager);
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

    }

    /**
     * if UpdateBackgroundTask is already running, cancel this task and start new task.
     */
    private void startBackgroundTimer() {
        if (mBackgroundTimer != null) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        /* set delay time to reduce too much background image loading process */
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    public void updateBackgroundWithDelay(String url) {
        try {
            URI uri = new URI(url);
            updateBackgroundWithDelay(uri);
        } catch (URISyntaxException e) {
            /* skip updating background */
            Log.e(TAG, e.toString());
        }
    }

    /**
     * updateBackground with delay
     * delay time is measured in other Timer task thread.
     *
     * @param uri
     */
    public void updateBackgroundWithDelay(URI uri) {
        mBackgroundURI = uri;
        startBackgroundTimer();
    }

    private void updateBackground(URI uri) {
        try {
            Glide.with(mActivity)
                    .load(uri.toString())
                    .asBitmap()
                    .centerCrop()
                    .override(mMetrics.widthPixels, mMetrics.heightPixels)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .error(mDefaultBackground)
                    .into(mBackgroundTarget);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            /* Here is TimerTask thread, not UI thread */
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                     /* Here is main (UI) thread */
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI);
                    }
                }
            });
        }
    }

    public class GlideBackGroundManagerTarget implements Target<Bitmap> {
        BackgroundManager mBackgroundManager;

        public GlideBackGroundManagerTarget(BackgroundManager mBackgroundManager) {
            this.mBackgroundManager = mBackgroundManager;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            GlideBackGroundManagerTarget that = (GlideBackGroundManagerTarget) o;

            if (!mBackgroundManager.equals(that.mBackgroundManager))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return mBackgroundManager.hashCode();
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {

        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            this.mBackgroundManager.setDrawable(errorDrawable);
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            this.mBackgroundManager.setBitmap(resource);
        }

        @Override
        public void onLoadCleared(Drawable placeholder) {

        }

        @Override
        public void getSize(SizeReadyCallback cb) {

        }

        @Override
        public Request getRequest() {
            return null;
        }

        @Override
        public void setRequest(Request request) {

        }

        @Override
        public void onStart() {
            mBackgroundManager.attach(mActivity.getWindow());
        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }
    }
}
