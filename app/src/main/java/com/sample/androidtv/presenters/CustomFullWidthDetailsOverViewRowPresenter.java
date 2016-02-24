package com.sample.androidtv.presenters;


import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;

/**
 * Created by pankaj on 24/2/16.
 */
public class CustomFullWidthDetailsOverViewRowPresenter extends FullWidthDetailsOverviewRowPresenter {

    private static final String TAG = CustomFullWidthDetailsOverViewRowPresenter.class.getSimpleName();

    public CustomFullWidthDetailsOverViewRowPresenter(Presenter presenter) {
        super(presenter);
    }

    @Override
    protected void onRowViewAttachedToWindow(RowPresenter.ViewHolder vh) {
        Log.v(TAG, "onRowViewAttachedToWindow");
        super.onRowViewAttachedToWindow(vh);
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        Log.v(TAG, "onBindRowViewHolder");
        super.onBindRowViewHolder(holder, item);
    }

    @Override
    protected void onLayoutOverviewFrame(ViewHolder viewHolder, int oldState, boolean logoChanged) {
        Log.v(TAG, "onLayoutOverviewFrame");

        setState(viewHolder, FullWidthDetailsOverviewRowPresenter.STATE_HALF);  // Default behavior

        super.onLayoutOverviewFrame(viewHolder, oldState, logoChanged);
    }
}
