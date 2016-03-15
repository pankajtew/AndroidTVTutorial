package com.sample.androidtv.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.support.v4.app.Fragment;

import com.sample.androidtv.R;
import com.sample.androidtv.activities.PlaybackActivity;
import com.sample.androidtv.background.managers.GlideBackgroundManager;
import com.sample.androidtv.background.tasks.ImageLoadTask;
import com.sample.androidtv.background.tasks.listeners.ImageTaskListener;
import com.sample.androidtv.model.Search;
import com.sample.androidtv.presenters.CustomFullWidthDetailsOverViewRowPresenter;
import com.sample.androidtv.presenters.IMDBDescriptionPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDetailsFragment extends DetailsFragment {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;


    private static final String MOVIE = "Movie";

    private ArrayObjectAdapter mAdapter;
    private CustomFullWidthDetailsOverViewRowPresenter mFwdorPresenter;
    private GlideBackgroundManager backgroundManager;
    private ClassPresenterSelector mClassPresenterSelector;
    private Search movie;
    private SpinnerFragment spinnerFragment;
    private ImageLoadTask imageLoadTask;
    public VideoDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFwdorPresenter = new CustomFullWidthDetailsOverViewRowPresenter(new IMDBDescriptionPresenter());
        movie = getActivity().getIntent().getParcelableExtra("IMDB_MOVIE");
        backgroundManager = new GlideBackgroundManager(getActivity());
        backgroundManager.updateBackgroundWithDelay(movie.getPoster());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mClassPresenterSelector = new ClassPresenterSelector();
        mClassPresenterSelector.addClassPresenter(DetailsOverviewRow.class, mFwdorPresenter);
        mAdapter = new ArrayObjectAdapter(mClassPresenterSelector);

        imageLoadTask = new ImageLoadTask(getActivity(), movie.getPoster(), DETAIL_THUMB_HEIGHT, DETAIL_THUMB_WIDTH, new ImageTaskListener() {
            @Override
            public void onImageLoadingStart(String url) {
                spinnerFragment = new SpinnerFragment();
                getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, spinnerFragment).commit();
            }

            @Override
            public void onImageLoadingComplete(Bitmap bitmap) {
                DetailsOverviewRow actionRow;
                actionRow = new DetailsOverviewRow(movie);

           /* action setting*/
                SparseArrayObjectAdapter sparseArrayObjectAdapter = new SparseArrayObjectAdapter();
                sparseArrayObjectAdapter.set(0, new Action(0, "Play Video"));
                sparseArrayObjectAdapter.set(1, new Action(1, "Rent at $1.99"));
                sparseArrayObjectAdapter.set(2, new Action(2, "Purchase at $6.99"));
                actionRow.setActionsAdapter(sparseArrayObjectAdapter);
                actionRow.setImageBitmap(getActivity(), bitmap);

                mFwdorPresenter.setOnActionClickedListener(new OnActionClickedListener() {
                    @Override
                    public void onActionClicked(Action action) {
                        switch ((int) action.getId()) {
                            case 0:
                                Intent intent = new Intent(getActivity(), PlaybackActivity.class);
                                intent.putExtra("IMDB_MOVIE", movie);
                                startActivity(intent);
                                break;
                        }
                    }
                });
                mAdapter.add(actionRow);
                setAdapter(mAdapter);
                getFragmentManager().beginTransaction().remove(spinnerFragment).commit();
            }
        });
        imageLoadTask.execute();
    }
}
