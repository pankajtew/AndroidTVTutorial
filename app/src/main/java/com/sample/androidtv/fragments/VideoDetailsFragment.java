package com.sample.androidtv.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.sample.androidtv.background.managers.GlideBackgroundManager;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.presenters.IMDBDescriptionPresenter;
import com.sample.androidtv.presenters.IMDBDetailsOverViewRowPresenter;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDetailsFragment extends DetailsFragment {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();
    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private ArrayObjectAdapter mAdapter;
    private IMDBDetailsOverViewRowPresenter mRowPresenter;
    private ClassPresenterSelector mClassPresenterSelector;
    private ListRow mRelatedVideoRow = null;

    private Search mSelectedMovie;
    private GlideBackgroundManager mBackgroundManager;

    public VideoDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRowPresenter = new IMDBDetailsOverViewRowPresenter(new IMDBDescriptionPresenter(),
                getActivity());
        mSelectedMovie = getActivity().getIntent().getParcelableExtra("IMDB_MOVIE");
        mBackgroundManager = new GlideBackgroundManager(getActivity());
        mBackgroundManager.updateBackgroundWithDelay(mSelectedMovie.getPoster());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mClassPresenterSelector = new ClassPresenterSelector();
        mClassPresenterSelector.addClassPresenter(DetailsOverviewRow.class, mRowPresenter);
        mAdapter = new ArrayObjectAdapter(mClassPresenterSelector);
        DetailsOverviewRow actionRow = new DetailsOverviewRow(mSelectedMovie);

           /* action setting*/
        SparseArrayObjectAdapter sparseArrayObjectAdapter = new SparseArrayObjectAdapter();
        sparseArrayObjectAdapter.set(0, new Action(0, "Play Video"));
        sparseArrayObjectAdapter.set(1, new Action(1, "Rent at $1.99"));
        sparseArrayObjectAdapter.set(2, new Action(2, "Purchase at $6.99"));
        actionRow.setActionsAdapter(sparseArrayObjectAdapter);

        try {
            // Bitmap loading must be done in background thread in Android.
            int width = (int) convertDpToPixel(DETAIL_THUMB_WIDTH, getActivity());
            int height = (int) convertDpToPixel(DETAIL_THUMB_HEIGHT, getActivity());
            Bitmap poster = Glide.with(getActivity())
                    .load(mSelectedMovie.getPoster())
                    .asBitmap()
                    .into(width, height)
                    .get();
            actionRow.setImageBitmap(getActivity(), poster);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mAdapter.add(actionRow);
        setAdapter(mAdapter);
    }
}
