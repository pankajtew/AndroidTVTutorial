package com.sample.androidtv.adapters;

import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

import com.sample.androidtv.presenters.SearchPresenter;

/**
 * Created by pankaj on 17/5/16.
 */
public class SearchAdapter extends ArrayObjectAdapter {

    private SearchPresenter mPresenter;

    public SearchAdapter(SearchPresenter searchPresenter) {
        mPresenter = searchPresenter;
        setPresenter();
    }

    private void setPresenter() {
        setPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return mPresenter;
            }
        });
    }
}
