package com.ayush.awesomefab;

import android.graphics.drawable.Drawable;

public class AwesomeFabMenu {
    private String mLabel;
    private Drawable mDrawable;

    public AwesomeFabMenu(String label, Drawable drawable) {
        mLabel = label;
        mDrawable = drawable;
    }

    public String getmLabel() {
        return mLabel;
    }

    public Drawable getmDrawable() {
        return mDrawable;
    }

}
