package com.sample.androidtv.Utils;

import android.media.MediaMetadataRetriever;
import android.os.Build;

import java.util.HashMap;

/**
 * Created by pankaj on 26/2/16.
 */
public class VideoUtils {

    public static long getDuration(String videoUrl) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mmr.setDataSource(videoUrl, new HashMap<String, String>());
        } else {
            mmr.setDataSource(videoUrl);
        }
        return Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }

}
