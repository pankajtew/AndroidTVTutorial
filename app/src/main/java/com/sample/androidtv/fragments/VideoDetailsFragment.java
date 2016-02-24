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

import com.sample.androidtv.R;
import com.sample.androidtv.background.managers.GlideBackgroundManager;
import com.sample.androidtv.background.tasks.ImageLoadTask;
import com.sample.androidtv.background.tasks.listeners.ImageTaskListener;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.presenters.IMDBDescriptionPresenter;
import com.sample.androidtv.presenters.IMDBDetailsOverViewRowPresenter;

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
    private SpinnerFragment spinnerFragment;

    private Search mSelectedMovie;
    private GlideBackgroundManager mBackgroundManager;
    private ImageLoadTask imageLoadTask;
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

        imageLoadTask = new ImageLoadTask(getActivity(), mSelectedMovie.getPoster(), DETAIL_THUMB_HEIGHT, DETAIL_THUMB_WIDTH, new ImageTaskListener() {
            @Override
            public void onImageLoadingStart(String url) {
                spinnerFragment = new SpinnerFragment();
                getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, spinnerFragment).commit();
            }

            @Override
            public void onImageLoadingComplete(Bitmap bitmap) {
                DetailsOverviewRow actionRow;
                actionRow = new DetailsOverviewRow(mSelectedMovie);

           /* action setting*/
                SparseArrayObjectAdapter sparseArrayObjectAdapter = new SparseArrayObjectAdapter();
                sparseArrayObjectAdapter.set(0, new Action(0, "Play Video"));
                sparseArrayObjectAdapter.set(1, new Action(1, "Rent at $1.99"));
                sparseArrayObjectAdapter.set(2, new Action(2, "Purchase at $6.99"));
                actionRow.setActionsAdapter(sparseArrayObjectAdapter);
                actionRow.setImageBitmap(getActivity(), bitmap);
                mAdapter.add(actionRow);
                setAdapter(mAdapter);
                getFragmentManager().beginTransaction().remove(spinnerFragment).commit();
            }
        });
        imageLoadTask.execute();




    }
}
