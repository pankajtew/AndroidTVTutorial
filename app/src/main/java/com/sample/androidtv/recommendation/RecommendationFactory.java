package com.sample.androidtv.recommendation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.sample.androidtv.R;
import com.sample.androidtv.activities.VideoDetailsActivity;
import com.sample.androidtv.model.Search;

import java.net.URI;

/**
 * Created by pankaj on 19/5/16.
 */
public class RecommendationFactory {

    private static final String TAG = RecommendationFactory.class.getSimpleName();
    private static final int CARD_WIDTH = 500;
    private static final int CARD_HEIGHT = 500;
    private static final int BACKGROUND_WIDTH = 1920;
    private static final int BACKGROUND_HEIGHT = 1080;

    private Context mContext;
    private NotificationManager mNotificationManager;

    public RecommendationFactory(Context context) {
        mContext = context;
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public void recommend(int id, Search movie) {
        recommend(id, movie, NotificationCompat.PRIORITY_DEFAULT);
    }

    /**
     * create a notification for recommending item of Movie class
     * @param movie
     */
    public void recommend(final int id, final Search movie, final int priority) {
        Log.i(TAG, "recommend");
        /* Run in background thread, since bitmap loading must be done in background */
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "recommendation in progress");
                Bitmap backgroundBitmap = prepareBitmap(movie.getPoster(), BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
                Bitmap cardImageBitmap = prepareBitmap(movie.getPoster(), CARD_WIDTH, CARD_HEIGHT);
                PendingIntent intent = buildPendingIntent(movie, id);

                RecommendationBuilder recommendationBuilder = new RecommendationBuilder(mContext)
                        .setSmallIcon(R.drawable.ic_app_header)
                        .setBackground(backgroundBitmap)
                        .setId(id)
                        .setPriority(priority)
                        .setTitle(movie.getTitle())
                        .setDescription(movie.getImdbID() + "\t" + movie.getYear())
                        .setIntent(intent)
                        .setBitmap(cardImageBitmap)
                        .setFastLaneColor(mContext.getResources().getColor(R.color.fastlane_background));
                Notification recommendNotification = recommendationBuilder.build();
                mNotificationManager.notify(id, recommendNotification);
            }}).start();
    }

    /**
     * prepare bitmap from URL string
     * @param url
     * @return
     */
    public Bitmap prepareBitmap(String url, int width, int height) {
        Bitmap bitmap = null;
        try {
            URI uri = new URI(url);
            bitmap = Glide.with(mContext)
                    .load(uri.toString())
                    .asBitmap()
                    .into(width, height)
                    .get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return bitmap;
    }

    private PendingIntent buildPendingIntent(Search movie, int id) {
        Intent detailsIntent = new Intent(mContext, VideoDetailsActivity.class);
        detailsIntent.putExtra("IMDB_MOVIE", movie);
        detailsIntent.putExtra("NOTIFICATION_ID", id);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(VideoDetailsActivity.class);
        stackBuilder.addNextIntent(detailsIntent);
        // Ensure a unique PendingIntents, otherwise all recommendations end up with the same
        // PendingIntent
        detailsIntent.setAction(Long.toString(movie.hashCode()));

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
