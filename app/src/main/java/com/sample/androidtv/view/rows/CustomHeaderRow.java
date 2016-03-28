package com.sample.androidtv.view.rows;

import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.Row;

import com.sample.androidtv.view.model.CustomHeaderItem;

/**
 * Created by pankaj on 28/3/16.
 */
public class CustomHeaderRow extends Row {

    private final ObjectAdapter mAdapter;
    private CustomHeaderItem mMenuItem;
    private ListRow mListRow;

    public CustomHeaderRow(CustomHeaderItem menuItem, HeaderItem headerItem, ObjectAdapter adapter) {
        super(headerItem);
        mMenuItem = menuItem;
        mAdapter = adapter;
        mListRow = new ListRow(headerItem, adapter);
        verify();
    }

    public CustomHeaderRow(long id, CustomHeaderItem menuItem, HeaderItem headerItem, ObjectAdapter adapter) {
        super(id, headerItem);
        mMenuItem = menuItem;
        mAdapter = adapter;
        mListRow = new ListRow(id, headerItem, adapter);
        verify();
    }

    public CustomHeaderRow(ObjectAdapter adapter) {
        super();
        mAdapter = adapter;
        mListRow = new ListRow(adapter);
        verify();
    }

    /**
     * Returns the {@link ObjectAdapter} that represents a list of objects.
     */
    public final ObjectAdapter getAdapter() {
        return mAdapter;
    }

    private void verify() {
        if (mAdapter == null) {
            throw new IllegalArgumentException("ObjectAdapter cannot be null");
        }
    }

    public CustomHeaderItem getMenuHeaderItem() {
        return mMenuItem;
    }

    public ListRow getRow() {
        return mListRow;
    }
}
