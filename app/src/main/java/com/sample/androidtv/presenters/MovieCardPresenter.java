package com.sample.androidtv.presenters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.sample.androidtv.R;
import com.sample.androidtv.model.Search;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Created by pankaj on 17/2/16.
 */
public class MovieCardPresenter extends Presenter {

    private static final String TAG = MovieCardPresenter.class.getSimpleName();

    private static Context sContext;
    private static int CARD_WIDTH = 313;
    private static int CARD_HEIGHT = 176;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        sContext = parent.getContext();

        ImageCardView cardView = new ImageCardView(sContext, R.style.customImageView);

        cardView.setCardType(BaseCardView.CARD_TYPE_INFO_UNDER_WITH_EXTRA);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(sContext.getResources().getColor(R.color.search_opaque));
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Search movie = (Search) item;
        ((ViewHolder) viewHolder).setMovie(movie);
        String[] arrImg = {"http://i1.tribune.com.pk/wp-content/uploads/2013/02/506822-madman-1360770394-787-640x480.jpg", "https://www.washingtonpost.com/blogs/answer-sheet/files/2013/05/startrek.jpg", "http://images.thehollywoodgossip.com/iu/s--0NuQ8Iar--/t_slideshow/f_auto,fl_lossy,q_75/v1418328691/slides/10-most-popular-tv-shows-on-facebook-in-2014_how-i-met-your-mother.jpg"};

        Log.d(TAG, "onBindViewHolder");
        if (movie.getPoster() != null) {

            Random r = new Random();
            int Low = 0;
            int High = 2;
            int Result = r.nextInt(High - Low) + Low;
            //  movie.setImageUrl(arrImg[Result]);
            ((ViewHolder) viewHolder).mCardView.setTitleText(movie.getTitle());
            ((ViewHolder) viewHolder).mCardView.setContentText(movie.getYear());
            ((ViewHolder) viewHolder).mCardView.setBadgeImage(sContext.getResources().getDrawable(android.R.drawable.ic_btn_speak_now));
            ((ViewHolder) viewHolder).mCardView.setInfoAreaBackgroundColor(Color.GREEN);
            ((ViewHolder) viewHolder).mCardView.setCardType(BaseCardView.CARD_TYPE_INFO_UNDER_WITH_EXTRA);
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH + 100, CARD_HEIGHT + 100);
            try {
                ((ViewHolder) viewHolder).updateCardWithGlide(new URI(movie.getPoster()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            //((ViewHolder) viewHolder).mCardView.setMainImage(((ViewHolder) viewHolder).getDefaultCardImage());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
    }

    static class ViewHolder extends Presenter.ViewHolder {
        private Search movie;
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private GlideDrawableImageViewTarget mImageCardViewTarget;


        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
            mImageCardViewTarget = new GlideDrawableImageViewTarget(mCardView.getMainImageView());
            mDefaultCardImage = sContext.getResources().getDrawable(android.R.drawable.stat_notify_error);
        }

        public Search getMovie() {
            return movie;
        }

        public void setMovie(Search m) {
            movie = m;
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        public Drawable getDefaultCardImage() {
            return mDefaultCardImage;
        }

        protected void updateCardViewImage(URI uri) {
            /*Picasso.with(sContext)
                    .load(uri.toString())
                    .resize(Utils.convertDpToPixel(sContext, CARD_WIDTH),
                            Utils.convertDpToPixel(sContext, CARD_HEIGHT))
                    .placeholder(mDefaultCardImage)
                    .error(mDefaultCardImage)
                    .into(mImageCardViewTarget);*/
        }

        protected void updateCardWithGlide(URI uri) {
            Glide.with(sContext).
                    load(uri.toString()).
                    asBitmap().
                    placeholder(mDefaultCardImage).
                    error(mDefaultCardImage).
                    into(mImageCardViewTarget.getView());
        }


    }


}
