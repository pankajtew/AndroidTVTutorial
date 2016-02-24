package com.sample.androidtv.presenters;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.sample.androidtv.model.Search;

/**
 * Created by pankaj on 18/2/16.
 */
public class IMDBDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    private static final String TAG = IMDBDescriptionPresenter.class.getSimpleName();

    @Override

    protected void onBindDescription(ViewHolder vh, Object item) {

        Search movie = (Search) item;
        vh.getTitle().setText(movie.getTitle());
        vh.getSubtitle().setText(movie.getYear());
        vh.getBody().setText(movie.getImdbID() + "\n" + movie.toString());
    }
}
