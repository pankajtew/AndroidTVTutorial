package com.sample.androidtv.presenters;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sample.androidtv.R;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.view.widget.SearchCardView;

/**
 * Created by pankaj on 6/5/16.
 */
public class SearchPresenter extends Presenter {

    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private static Context sContext;
    private RowPresenter rowPresenter;

    private static void updateCardBackgroundColor(SearchCardView view, boolean selected) {
        view.setBackgroundColor(selected ? sSelectedBackgroundColor : sDefaultBackgroundColor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        sDefaultBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.primary);
        sSelectedBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.primary_dark);
        sContext = parent.getContext();
        SearchCardView cardView = new SearchCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (item instanceof Search) {
            Search result = (Search) item;
            SearchCardView cardView = (SearchCardView) viewHolder.view;

            if (result.getPoster() != null && !result.getPoster().equalsIgnoreCase("N/A")) {
                Glide.with(sContext).load(result.getPoster()).into((ImageView) cardView.findViewById(R.id.image_icon));
                cardView.setCardText(result.getTitle());

            }
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
