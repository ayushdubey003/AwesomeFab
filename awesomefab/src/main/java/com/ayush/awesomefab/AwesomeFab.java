package com.ayush.awesomefab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

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
    private int mMenuType;
    private boolean mToggle;
    private RelativeLayout mRelativeLayoutMain;
    private ArrayList<Integer> mChildrenId;
    private Stack<Integer> mChild;
    private static ArrayList<AwesomeFabMenu> mMenuList;

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

            layoutParams = mRelativeLayoutMain.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mRelativeLayoutMain.setLayoutParams(layoutParams);

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

            mView.setLayoutParams(layoutParams);

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
        mToggle = false;
        mRelativeLayoutMain = findViewById(R.id.relative_layout_main);
        mChildrenId = new ArrayList<>();
        mChild = new Stack<>();
        mChild.push(R.id.relative_layout_main);
        mMenuList = new ArrayList<>();
    }

    private void setUpFab() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.AwesomeFab, mStyleAttr, 0);
        mUseMini = typedArray.getBoolean(R.styleable.AwesomeFab_useMini, false);
        mFabcolor = typedArray.getColor(R.styleable.AwesomeFab_fabColor, 0x0);
        mRippleColor = typedArray.getColor(R.styleable.AwesomeFab_rippleColor, 0x42ffff);
        mBeforeDrawable = typedArray.getDrawable(R.styleable.AwesomeFab_beforeClickSrc);
        mAfterDrawable = typedArray.getDrawable(R.styleable.AwesomeFab_afterClickSrc);
        if (mAfterDrawable == null)
            mAfterDrawable = mBeforeDrawable;
        mShadowColor = typedArray.getColor(R.styleable.AwesomeFab_shadowColor, 0xd3d3d3);
        mMenuType = typedArray.getInt(R.styleable.AwesomeFab_menuType, 0);

        mView.setColorFilter(mFabcolor);
        mRipple.setRippleColor(mRippleColor);
        //mRipple.setRippleDuration(125);
        mSrc.setImageDrawable(mBeforeDrawable);
        mShadow.setColorFilter(mShadowColor);
        typedArray.recycle();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.source_drawable || view.getId() == R.id.view) {
            Log.e(LOG_TAG, "Clicked");
            if (!mToggle) {
                mToggle = true;
                mSrc.setImageDrawable(mAfterDrawable);
                if (mMenuType == 0)
                    Toast.makeText(mContext, "Linear", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(mContext, "Radial", Toast.LENGTH_LONG).show();

                ViewGroup.LayoutParams layoutParams = mRelativeLayoutMain.getLayoutParams();
                layoutParams.height = layoutParams.height + (int) convertDpToPixel(32, mContext);

                RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                        (int) convertDpToPixel(16, mContext),
                        (int) convertDpToPixel(16, mContext));

                lprams.setMargins(0, 0, (int) convertDpToPixel(8, mContext), (int) convertDpToPixel(16, mContext));
                lprams.addRule(RelativeLayout.ABOVE, mRelativeLayout.getId());
                lprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(lprams);
                imageView.setId(View.generateViewId());
                mChildrenId.add(imageView.getId());
                imageView.setImageDrawable(mBeforeDrawable);
                mRelativeLayoutMain.addView(imageView);
                Log.e(LOG_TAG, mMenuList.size() + " ");
            } else {
                mToggle = false;
                mSrc.setImageDrawable(mBeforeDrawable);
                for (int i = 0; i < mChildrenId.size(); i++)
                    findViewById(mChildrenId.get(i)).setVisibility(GONE);
            }
        }
    }

    public void inflateMenu(String label, Drawable drawable) {
        mMenuList.add(new AwesomeFabMenu(label, drawable));
    }
}
