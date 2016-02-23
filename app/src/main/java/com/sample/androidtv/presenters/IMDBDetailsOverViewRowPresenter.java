package com.sample.androidtv.presenters;

import android.content.Context;
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;

import com.sample.androidtv.R;

/**
 * Created by pankaj on 18/2/16.
 */
public class IMDBDetailsOverViewRowPresenter extends DetailsOverviewRowPresenter {

    private static final String TAG = IMDBDetailsOverViewRowPresenter.class.getSimpleName();

    private Context mContext;

    /**
     * @param detailsPresenter The {@link Presenter} used to render the detailed
     *                         description of the row.
     * @param context          {@link android.app.Activity} context to represent in which activity
     *                         presenter is used
     */
    public IMDBDetailsOverViewRowPresenter(Presenter detailsPresenter, Context context) {
        super(detailsPresenter);
        mContext = context;
    }

    @Override
    protected void onRowViewAttachedToWindow(RowPresenter.ViewHolder vh) {
        Log.v(TAG, "onRowViewAttachedToWindow");
        super.onRowViewAttachedToWindow(vh);
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        Log.v(TAG, "onBindRowViewHolder");
        setBackgroundColor(mContext.getResources().getColor(R.color.default_background));
        setStyleLarge(true);
        // It must be called "after" above function call
        super.onBindRowViewHolder(holder, item);
    }

    @Override
    protected void onRowViewExpanded(RowPresenter.ViewHolder vh, boolean expanded) {
        Log.v(TAG, "onRowViewExpanded");
        super.onRowViewExpanded(vh, expanded);
    }

}
