package com.sample.androidtv.background.tasks.listeners;

import android.graphics.Bitmap;

/**
 * Created by pankaj on 24/2/16.
 */
public interface ImageTaskListener {

    public void onImageLoadingStart(String url);

    public void onImageLoadingComplete(Bitmap bitmap);
}
