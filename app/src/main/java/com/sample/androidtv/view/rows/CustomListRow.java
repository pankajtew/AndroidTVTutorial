package com.sample.androidtv.view.rows;

import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ObjectAdapter;

/**
 * Created by pankaj on 28/3/16.
 */
public class CustomListRow extends ListRow {

    private static final String TAG = CustomListRow.class.getSimpleName();
    private int mNumRows = 1;

    public CustomListRow(HeaderItem header, ObjectAdapter adapter) {
        super(header, adapter);
    }

    public CustomListRow(long id, HeaderItem header, ObjectAdapter adapter) {
        super(id, header, adapter);
    }

    public CustomListRow(ObjectAdapter adapter) {
        super(adapter);
    }

    public int getNumRows() {
        return mNumRows;
    }

    public void setNumRows(int mNumRows) {
        this.mNumRows = mNumRows;
    }
}
