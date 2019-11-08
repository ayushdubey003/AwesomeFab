package com.ayush.awesomefab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class AwesomeFab extends LinearLayout {

    private Context mContext;
    private AttributeSet mAttributeSet;
    private int mStyleAttr;
    private View mView;
    private GradientDrawable mCircle;
    private final String LOG_TAG = "AwesomeFab";
    private boolean mUseMini;
    private LinearLayout mLinearLayout;

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
        this.mView = this;
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.AwesomeFab, mStyleAttr, 0);
        mUseMini = typedArray.getBoolean(R.styleable.AwesomeFab_useMini, false);
        ViewGroup.LayoutParams layoutParams;
        inflate(mContext, R.layout.awesome_fab, (ViewGroup) mView);

        mView = findViewById(R.id.view);
        mLinearLayout = findViewById(R.id.linear_layout);
        if (mUseMini) {
            int px = (int) convertDpToPixel(40, mContext);
            layoutParams = mLinearLayout.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mLinearLayout.setLayoutParams(layoutParams);

            layoutParams = mView.getLayoutParams();
            Log.e(LOG_TAG, layoutParams + " ");
            layoutParams.height = px;
            layoutParams.width = px;
            mView.setLayoutParams(layoutParams);
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
        }
        typedArray.recycle();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
