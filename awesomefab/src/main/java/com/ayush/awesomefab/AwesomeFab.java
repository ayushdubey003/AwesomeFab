package com.ayush.awesomefab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AwesomeFab extends LinearLayout {

    private Context mContext;
    private AttributeSet mAttributeSet;
    private int mStyleAttr;
    private ImageView mView;
    private GradientDrawable mCircle;
    private final String LOG_TAG = "AwesomeFab";
    private boolean mUseMini;
    private LinearLayout mLinearLayout;
    private int mFabcolor;
    private Drawable mDrawable;

    public AwesomeFab(Context context) {
        super(context);
        mContext = context;
        initialize();
    }

    public AwesomeFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttributeSet = attrs;
        initialize();
    }

    public AwesomeFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mAttributeSet = attrs;
        mStyleAttr = defStyleAttr;
        initialize();
    }


    private void initialize() {
        this.mLinearLayout = this;
        inflate(mContext, R.layout.awesome_fab, mLinearLayout);

        TypedArray typedArray = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.AwesomeFab, mStyleAttr, 0);
        mUseMini = typedArray.getBoolean(R.styleable.AwesomeFab_useMini, false);
        mFabcolor = typedArray.getColor(R.styleable.AwesomeFab_fabColor, 0x0);
        mDrawable = typedArray.getDrawable(R.styleable.AwesomeFab_src);
        ViewGroup.LayoutParams layoutParams;

        mView = findViewById(R.id.view);
        mLinearLayout = findViewById(R.id.linear_layout);
        if (mUseMini) {
            int px = (int) convertDpToPixel(40, mContext);
            int pad = (int) convertDpToPixel(8, mContext);
            layoutParams = mLinearLayout.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mLinearLayout.setLayoutParams(layoutParams);

            layoutParams = mView.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mView.setPadding(pad, pad, pad, pad);
            mView.setImageDrawable(mDrawable);

            GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{mFabcolor, mFabcolor, mFabcolor});
            g.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            g.setCornerRadius(px * 1.0f);
            mView.setBackground(g);
        } else {
            int px = (int) convertDpToPixel(56, mContext);
            layoutParams = mLinearLayout.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mLinearLayout.setLayoutParams(layoutParams);

            layoutParams = mView.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mView.setLayoutParams(layoutParams);
            mView.setImageDrawable(mDrawable);
        }
        typedArray.recycle();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
