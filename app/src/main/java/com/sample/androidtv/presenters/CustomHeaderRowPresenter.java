package com.sample.androidtv.presenters;

import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.RowPresenter;

import com.sample.androidtv.view.rows.CustomHeaderRow;

/**
 * Created by pankaj on 28/3/16.
 */
public class CustomHeaderRowPresenter extends ListRowPresenter {

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        ListRow listRowItem = ((CustomHeaderRow) item).getRow();
        super.onBindRowViewHolder(holder, listRowItem);
    }
}
