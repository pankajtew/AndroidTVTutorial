package com.sample.androidtv.presenters;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sample.androidtv.R;

/**
 * Created by pankaj on 17/2/16.
 */
public class GridItemImagePresenter extends Presenter {

    private static final String TAG = GridItemImagePresenter.class.getSimpleName();

    private static Context mContext;
    private static int GRID_ITEM_WIDTH = 313;
    private static int GRID_ITEM_HEIGHT = 176;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
        imageView.setFocusable(true);
        imageView.setFocusableInTouchMode(true);
        imageView.setBackgroundColor(mContext.getResources().getColor(R.color.default_background));
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if(item.toString().toLowerCase().equalsIgnoreCase("next")){
            ((ImageView) viewHolder.view).setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_media_next));
        }

        else if(item.toString().toLowerCase().equalsIgnoreCase("previous")){
            ((ImageView) viewHolder.view).setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_media_previous));
        }

        else if(item.toString().toLowerCase().equalsIgnoreCase("fastforward")){
            ((ImageView) viewHolder.view).setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_media_ff));
        }

        else if(item.toString().toLowerCase().equalsIgnoreCase("rewind")){
            ((ImageView) viewHolder.view).setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_media_rew));
        }

        else if(item.toString().toLowerCase().equalsIgnoreCase("play")){
            ((ImageView) viewHolder.view).setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_media_play));
        }

        else if(item.toString().toLowerCase().equalsIgnoreCase("pause")){
            ((ImageView) viewHolder.view).setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.ic_media_pause));
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
