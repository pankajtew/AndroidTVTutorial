package com.sample.androidtv.presenters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sample.androidtv.R;
import com.sample.androidtv.view.model.CustomHeaderItem;
import com.sample.androidtv.view.model.IMenuItems;

/**
 * Created by pankaj on 28/3/16.
 */
public class CustomHeaderItemPresenter extends RowHeaderPresenter {

    private static Context mContext;
    private float mUnselectedAlpha;

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        mUnselectedAlpha = viewGroup.getResources()
                .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);
        mContext = viewGroup.getContext();
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.layout_custom_header_item, null);
        view.setAlpha(mUnselectedAlpha); // Initialize icons to be at half-opacity.
        return new RowHeaderPresenter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        //CustomHeaderItem menuItem = ((CustomHeaderRow) item).getMenuHeaderItem();
        CustomHeaderItem menuItem = (CustomHeaderItem) ((ListRow) item).getHeaderItem();
        Drawable icon;
        Uri imageURI;

        /**
         * Initialize Menu Item Views with Ids
         */
        View rootView = viewHolder.view;
        LinearLayout linearLayoutMenuItem = (LinearLayout)
                rootView.findViewById(R.id.linear_layout_menu_item);
        View dividerView = rootView.findViewById(R.id.view_divider);
        ImageView iconView = (ImageView)
                rootView.findViewById(R.id.header_icon);
        TextView label = (TextView)
                rootView.findViewById(R.id.header_label);

        //Get Parent Layout Parameters
        FrameLayout.LayoutParams menuItemsParam = (FrameLayout.LayoutParams) linearLayoutMenuItem.getLayoutParams();

        //Initialize Parent Layout Margins & Divider visibility
        switch ((int) menuItem.getItemType()) {

            case (int) IMenuItems.MENU_DIVIDER_ITEM_WITH_ICON:
                menuItemsParam.setMargins(0, 5, 0, 5);
                iconView.setVisibility(View.VISIBLE);
                dividerView.setVisibility(View.VISIBLE);
                break;
            case (int) IMenuItems.MENU_DIVIDER_ITEM_WITHOUT_ICON:
                menuItemsParam.setMargins(0, 5, 0, 5);
                iconView.setVisibility(View.GONE);
                dividerView.setVisibility(View.VISIBLE);
                break;
            case (int) IMenuItems.MENU_ITEM_WITH_ICON:
                menuItemsParam.setMargins(0, 0, 0, 0);
                iconView.setVisibility(View.VISIBLE);
                break;
            case (int) IMenuItems.MENU_ITEM_WITHOUT_ICON:
                menuItemsParam.setMargins(0, 0, 0, 0);
                iconView.setVisibility(View.GONE);
                break;

        }

        /**
         * Set focusable or not
         */
        if (menuItem.isRequiresFocus()) {
            rootView.setFocusable(true);
        }
        /**
         * Set icon image url
         */
        else {
            rootView.setFocusable(false);
        }

        /**
         * Set icon drawable
         */
        if (menuItem.getIconDrawable() != 0) {
            icon = rootView.getResources().getDrawable(menuItem.getIconDrawable(), null);
            iconView.setImageDrawable(icon);
        }
        /**
         * Set icon image url
         */
        if (menuItem.getImageURI() != null) {
            imageURI = menuItem.getImageURI();
            Drawable mDefaultCardImage = mContext.getResources().getDrawable(android.R.drawable.ic_dialog_alert);
            Glide.with(mContext).
                    load(imageURI).
                    asBitmap().
                    placeholder(mDefaultCardImage).
                    error(mDefaultCardImage).
                    into(iconView);
        }


        /**
         * Set menu text
         */
        label.setText(menuItem.getName());

        /**
         * Set margins for menu item
         */
        linearLayoutMenuItem.setLayoutParams(menuItemsParam);
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
    }

    // TODO: This is a temporary fix. Remove me when leanback onCreateViewHolder no longer sets the
    // mUnselectAlpha, and also assumes the xml inflation will return a RowHeaderView.
    @Override
    protected void onSelectLevelChanged(RowHeaderPresenter.ViewHolder holder) {
        holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() *
                (1.0f - mUnselectedAlpha));
    }


}
