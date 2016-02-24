package com.sample.androidtv.background.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.sample.androidtv.background.tasks.listeners.ImageTaskListener;

import java.util.concurrent.ExecutionException;

/**
 * Created by pankaj on 24/2/16.
 */
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private int height, width;
    private String imageURL;
    private ImageTaskListener listener;

    public ImageLoadTask(Context context, String imageURL, int height, int width, ImageTaskListener listener) {
        this.context = context;
        this.height = height;
        this.width = width;
        this.imageURL = imageURL;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onImageLoadingStart(imageURL);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap poster = null;

        try {
            poster = Glide.with(context)
                    .load(imageURL)
                    .asBitmap()
                    .into(width, height)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return poster;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        listener.onImageLoadingComplete(bitmap);
    }
}
