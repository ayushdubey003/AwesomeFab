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
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AwesomeFab extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private AttributeSet mAttributeSet;
    private int mStyleAttr;
    private ImageView mView;
    private ImageView mSrc;
    private GradientDrawable mCircle;
    private final String LOG_TAG = "AwesomeFab";
    private boolean mUseMini;
    private RelativeLayout mRelativeLayout;
    private int mFabcolor;
    private Drawable mBeforeDrawable;
    private Drawable mAfterDrawable;
    private int mRippleColor;
    private com.balysv.materialripple.MaterialRippleLayout mRipple;
    private ImageView mShadow;
    private int mShadowColor;

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
        this.mRelativeLayout = this;
        inflate(mContext, R.layout.awesome_fab, mRelativeLayout);
        initInstanceVariables();
        setUpFab();
        ViewGroup.LayoutParams layoutParams;

        mView.setOnClickListener(this);
        if (mSrc != null)
            mSrc.setOnClickListener(this);

        if (mUseMini) {
            int px = (int) convertDpToPixel(42, mContext);
            layoutParams = mRelativeLayout.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mRelativeLayout.setLayoutParams(layoutParams);

            layoutParams = mShadow.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mShadow.setLayoutParams(layoutParams);

            px = (int) convertDpToPixel(40, mContext);

            layoutParams = mRipple.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;

            layoutParams = mView.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;

            GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{mFabcolor, mFabcolor, mFabcolor});
            g.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            g.setCornerRadius(px * 1.0f);
            mView.setImageDrawable(g);
        } else {
            int px = (int) convertDpToPixel(58, mContext);
            layoutParams = mRelativeLayout.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mRelativeLayout.setLayoutParams(layoutParams);

            layoutParams = mShadow.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mShadow.setLayoutParams(layoutParams);

            px = (int) convertDpToPixel(56, mContext);
            layoutParams = mView.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;

            mView.setLayoutParams(layoutParams);
            GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{mFabcolor, mFabcolor, mFabcolor});
            g.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            g.setCornerRadius(px * 1.0f);
            mView.setImageDrawable(g);
        }

    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void initInstanceVariables() {
        mView = findViewById(R.id.view);
        mRelativeLayout = findViewById(R.id.relative_layout);
        mSrc = findViewById(R.id.source_drawable);
        mRipple = findViewById(R.id.ripple);
        mShadow = findViewById(R.id.shadow);
    }

    private void setUpFab() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.AwesomeFab, mStyleAttr, 0);
        mUseMini = typedArray.getBoolean(R.styleable.AwesomeFab_useMini, false);
        mFabcolor = typedArray.getColor(R.styleable.AwesomeFab_fabColor, 0x0);
        mRippleColor = typedArray.getColor(R.styleable.AwesomeFab_rippleColor, 0x42f);
        mBeforeDrawable = typedArray.getDrawable(R.styleable.AwesomeFab_beforeClickSrc);
        mAfterDrawable = typedArray.getDrawable(R.styleable.AwesomeFab_afterClickSrc);
        mShadowColor = typedArray.getColor(R.styleable.AwesomeFab_shadowColor, 0xd3d3d3);

        mRipple.setRippleColor(mRippleColor);
        mSrc.setImageDrawable(mBeforeDrawable);
        mShadow.setColorFilter(mShadowColor);
        typedArray.recycle();
    }

    @Override
    public void onClick(View view) {
        mSrc.setImageDrawable(mAfterDrawable);
    }

}
