package com.sample.androidtv.view.model;

import android.net.Uri;
import android.support.v17.leanback.widget.HeaderItem;

import static android.support.v17.leanback.widget.ObjectAdapter.NO_ID;

/**
 * Created by pankaj on 6/1/16.
 */
public class CustomHeaderItem extends HeaderItem {

    private final long mId;
    private final String mName;
    private final int mIcon;
    private final Uri mImageURI;
    private final long mItemType;
    private final boolean mRequiresFocus;
    private HeaderItem headerItem;

    /**
     * Create a header item.  All fields are optional.
     */
    public CustomHeaderItem(long id, String name) {
        super(id, name);
        this.mId = id;
        this.mName = name;
        mIcon = 0;
        mImageURI = null;
        this.mItemType = IMenuItems.MENU_ITEM_WITHOUT_ICON;
        this.mRequiresFocus = true;
        setHeaderItem(generateHeaderItem(id, name));
    }

    public CustomHeaderItem(long id, String name, long itemType) {
        super(id, name);
        if (itemType != IMenuItems.MENU_DIVIDER_ITEM_WITHOUT_ICON
                || itemType != IMenuItems.MENU_ITEM_WITHOUT_ICON) {
            this.mId = id;
            this.mName = name;
            mIcon = 0;
            mImageURI = null;
            this.mItemType = itemType;
            this.mRequiresFocus = true;
            setHeaderItem(generateHeaderItem(id, name));
        } else {
            throw new IllegalStateException("Menu Item without drawable/uri must be " +
                    "MENU ITEM WITHOUT ICON OR DIVIDER MENU ITEM WITHOUT ICON ");

        }
    }

    public CustomHeaderItem(long id, String name, long itemType, boolean isFocusable) {
        super(id, name);
        if (itemType != IMenuItems.MENU_DIVIDER_ITEM_WITHOUT_ICON
                || itemType != IMenuItems.MENU_ITEM_WITHOUT_ICON) {
            this.mId = id;
            this.mName = name;
            mIcon = 0;
            mImageURI = null;
            this.mItemType = itemType;
            this.mRequiresFocus = isFocusable;
            setHeaderItem(generateHeaderItem(id, name));
        } else {
            throw new IllegalStateException("Menu Item without drawable/uri must be " +
                    "MENU ITEM WITHOUT ICON OR DIVIDER MENU ITEM WITHOUT ICON ");

        }
    }

    public CustomHeaderItem(long id, String name, int iconDrawable) {
        super(id, name);

        this.mId = id;
        this.mName = name;
        this.mIcon = iconDrawable;
        mImageURI = null;
        this.mItemType = IMenuItems.MENU_ITEM_WITH_ICON;
        this.mRequiresFocus = true;
        setHeaderItem(generateHeaderItem(id, name));
    }

    public CustomHeaderItem(long id, String name, int iconDrawable, long itemType) {
        super(id, name);
        if (itemType == IMenuItems.MENU_DIVIDER_ITEM_WITH_ICON || itemType == IMenuItems.MENU_ITEM_WITH_ICON) {
            this.mId = id;
            this.mName = name;
            this.mIcon = iconDrawable;
            mImageURI = null;
            this.mItemType = itemType;
            this.mRequiresFocus = true;
            setHeaderItem(generateHeaderItem(id, name));
        } else {
            throw new IllegalStateException("Menu Item with drawable must be " +
                    "MENU ITEM WITH ICON OR DIVIDER ITEM WITH ICON");
        }
    }

    public CustomHeaderItem(long id, String name, int iconDrawable, long itemType, boolean isFocusable) {
        super(id, name);
        if (itemType == IMenuItems.MENU_DIVIDER_ITEM_WITH_ICON || itemType == IMenuItems.MENU_ITEM_WITH_ICON) {
            this.mId = id;
            this.mName = name;
            this.mIcon = iconDrawable;
            mImageURI = null;
            this.mItemType = itemType;
            this.mRequiresFocus = isFocusable;
            setHeaderItem(generateHeaderItem(id, name));
        } else {
            throw new IllegalStateException("Menu Item with drawable must be " +
                    "MENU ITEM WITH ICON OR DIVIDER ITEM WITH ICON");
        }
    }

    public CustomHeaderItem(long id, String name, String imageURL) {
        super(id, name);
        this.mId = id;
        this.mName = name;
        this.mIcon = 0;
        this.mItemType = IMenuItems.MENU_ITEM_WITH_ICON;
        this.mImageURI = Uri.parse(imageURL);
        this.mRequiresFocus = true;
        setHeaderItem(generateHeaderItem(id, name));
    }

    public CustomHeaderItem(long id, String name, String imageURL, long itemType) {
        super(id, name);
        if (itemType == IMenuItems.MENU_DIVIDER_ITEM_WITH_ICON || itemType == IMenuItems.MENU_ITEM_WITH_ICON) {
            this.mId = id;
            this.mName = name;
            this.mIcon = 0;
            this.mItemType = itemType;
            this.mImageURI = Uri.parse(imageURL);
            this.mRequiresFocus = true;
            setHeaderItem(generateHeaderItem(id, name));
        } else {
            throw new IllegalStateException("Menu Item with image URL must be" +
                    "MENU ITEM WITH ICON OR DIVIDER ITEM WITH ICON");
        }
    }

    public CustomHeaderItem(long id, String name, String imageURL, long itemType, boolean isFocusable) {
        super(id, name);
        if (itemType == IMenuItems.MENU_DIVIDER_ITEM_WITH_ICON || itemType == IMenuItems.MENU_ITEM_WITH_ICON) {
            this.mId = id;
            this.mName = name;
            this.mIcon = 0;
            this.mItemType = itemType;
            this.mImageURI = Uri.parse(imageURL);
            this.mRequiresFocus = isFocusable;
            setHeaderItem(generateHeaderItem(id, name));
        } else {
            throw new IllegalStateException("Menu Item with image URL must be" +
                    "MENU ITEM WITH ICON OR DIVIDER ITEM WITH ICON");
        }
    }


    /**
     * Create an Icon header item.
     */
    public CustomHeaderItem(String name) {
        this(NO_ID, name);
        setHeaderItem(generateHeaderItem(name));
    }


    public int getIconDrawable() {
        return mIcon;
    }

    public Uri getImageURI() {
        return mImageURI;
    }

    private HeaderItem generateHeaderItem(String name) {
        return new HeaderItem(NO_ID, name);
    }

    private HeaderItem generateHeaderItem(long id, String name) {
        return new HeaderItem(id, name);
    }

    public long getItemType() {
        return mItemType;
    }

    public boolean isRequiresFocus() {
        return mRequiresFocus;
    }

    public HeaderItem getHeaderItem() {
        return headerItem;
    }

    public void setHeaderItem(HeaderItem headerItem) {
        this.headerItem = headerItem;
    }
}
