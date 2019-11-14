package com.ayush.awesomefab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class AwesomeFab extends RelativeLayout implements View.OnClickListener {

    public interface FabMenuClickListener {
        void onFabItemClick(int id, int ind);
    }

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
    private LinearLayout mLinearLayoutMain;
    private ArrayList<Integer> mChildrenId;
    private int mId;
    private HashMap<Integer, ArrayList<AwesomeFabMenu>> mHashmap;
    private ArrayList<AwesomeFabMenu> mMenuList;
    private LinearLayout mLinearTop;
    private LinearLayout mLinearBottom;
    private int mInflateMenuAt;
    private int mAlignFabTowards;
    private int mFabMenuAlignment;
    private int mIndClicked;
    private FabMenuClickListener fabMenuClickListener;

    public AwesomeFab(Context context) throws Exception {
        super(context);
        mContext = context;
        initialize();
    }

    public AwesomeFab(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        mContext = context;
        mAttributeSet = attrs;
        initialize();
    }

    public AwesomeFab(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mAttributeSet = attrs;
        mStyleAttr = defStyleAttr;
        initialize();
    }

    private void initialize() throws Exception {
        this.mRelativeLayout = this;
        inflate(mContext, R.layout.awesome_fab, mRelativeLayout);
        initInstanceVariables();
        setUpFab();
        ViewGroup.LayoutParams layoutParams;

        mView.setOnClickListener(this);
        if (mSrc != null)
            mSrc.setOnClickListener(this);

        //Material UI used which sets mini fab size to 40dp
        if (mUseMini) {
            int px = (int) convertDpToPixel(42, mContext);

            layoutParams = mLinearLayoutMain.getLayoutParams();
            layoutParams.height = px;
            layoutParams.width = px;
            mLinearLayoutMain.setLayoutParams(layoutParams);

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
        mLinearLayoutMain = findViewById(R.id.linear_layout_main);
        mChildrenId = new ArrayList<>();
        mHashmap = new HashMap<>();
        mLinearTop = findViewById(R.id.linear_top);
        mLinearBottom = findViewById(R.id.linear_bottom);
    }

    //Gathering user-defined styles from attributes
    private void setUpFab() throws Exception {
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
        mInflateMenuAt = typedArray.getInt(R.styleable.AwesomeFab_inflateMenuAt, 0);
        mAlignFabTowards = typedArray.getInt(R.styleable.AwesomeFab_alignFabTowards, 0);
        mId = typedArray.getResourceId(R.styleable.AwesomeFab_id, -1);
        mFabMenuAlignment = typedArray.getInt(R.styleable.AwesomeFab_fabMenuAlignment, 0);

        //The user must pass the app:id parameter otherwise the app throws an error. This is used in case of multiple fab menus used in the same view
        if (mId <= 0)
            throw new Exception("app:id is necessary attribute");

        mView.setColorFilter(mFabcolor);
        mRipple.setRippleColor(mRippleColor);
        mSrc.setImageDrawable(mBeforeDrawable);
        mShadow.setColorFilter(mShadowColor);
        typedArray.recycle();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.source_drawable || view.getId() == R.id.view) {
            //mToggle checks whether the fab menu is inflated or not
            if (!mToggle) {
                mToggle = true;
                mSrc.setImageDrawable(mAfterDrawable);
                ImageView imageView = null;
                TextView textView = null;
                if (mMenuType == 0) {
                    mMenuList = mHashmap.get(mId);

                    if (mMenuList == null)
                        mMenuList = new ArrayList<>();

                    if (mAlignFabTowards == 0) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRelativeLayout.getLayoutParams();
                        layoutParams.gravity = Gravity.LEFT;
                        mRelativeLayout.setLayoutParams(layoutParams);
                    } else {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRelativeLayout.getLayoutParams();
                        layoutParams.gravity = Gravity.RIGHT;
                        mRelativeLayout.setLayoutParams(layoutParams);
                    }

                    animateIn(0);

                } else
                    Toast.makeText(mContext, "Radial", Toast.LENGTH_LONG).show();

            } else {
                mToggle = false;
                mSrc.setImageDrawable(mBeforeDrawable);

                animateOut(0);

            }
        } else {
            boolean z = false;

            for (int i = 0; i < mChildrenId.size(); i++) {
                if (view.getId() == mChildrenId.get(i)) {
                    z = true;
                    mIndClicked = mChildrenId.get(i);
                }
            }
            if (z) {
                if (fabMenuClickListener != null) {
                    Log.e(LOG_TAG, mId + " ");
                    fabMenuClickListener.onFabItemClick(mId, mIndClicked);
                }
                mView.performClick();
            }
        }

    }

    public void inflateMenu(String label, Drawable drawable, int id) {
        ArrayList<AwesomeFabMenu> arrayList = mHashmap.get(id);
        if (arrayList == null)
            arrayList = new ArrayList<>();
        arrayList.add(new AwesomeFabMenu(label, drawable));
        mHashmap.put(id, arrayList);
    }

    public void setFabMenuClickListener(FabMenuClickListener listener) {
        this.fabMenuClickListener = listener;
    }

    private void animateOut(final int i) {
        if (i >= mChildrenId.size()) {
            mChildrenId = new ArrayList<>();
            mView.setClickable(true);
            return;
        }
        mView.setClickable(false);
        Animation fadeOut = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
        findViewById(mChildrenId.get(i)).startAnimation(fadeOut);
        if (mInflateMenuAt == 0)
            mLinearTop.removeView(findViewById(mChildrenId.get(i)));
        else
            mLinearBottom.removeView(findViewById(mChildrenId.get(i)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateOut(i + 1);
            }
        }, 500);
    }

    private void animateIn(final int i) {
        if (i >= mMenuList.size()) {
            mView.setClickable(true);
            return;
        }
        final AwesomeFab context = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.setClickable(false);
                mLinearLayoutMain.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setId(i + 1);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                CardView cardView = new CardView(mContext);
                cardView.setCardElevation(convertDpToPixel(6, mContext));
                cardView.setRadius((int) convertDpToPixel(2, mContext));
                cardView.setPadding((int) convertDpToPixel(8, mContext), (int) convertDpToPixel(4, mContext), (int) convertDpToPixel(8, mContext), (int) convertDpToPixel(4, mContext));
                tvLp.setMargins((int) convertDpToPixel(8, mContext), 0, (int) convertDpToPixel(8, mContext), 0);

                tvLp.gravity = CENTER_IN_PARENT;
                cardView.setLayoutParams(tvLp);

                TextView label = new TextView(mContext);
                label.setText(mMenuList.get(i).getmLabel());
                label.setGravity(CENTER_VERTICAL);
                label.setLayoutParams(tvLp);

                ImageView icon = new ImageView(mContext);
                icon.setImageDrawable(mMenuList.get(i).getmDrawable());
                icon.setPadding((int) convertDpToPixel(4, mContext), (int) convertDpToPixel(4, mContext), (int) convertDpToPixel(4, mContext), (int) convertDpToPixel(4, mContext));

                cardView.addView(label);

                if (mFabMenuAlignment == 0) {
                    linearLayout.addView(cardView);
                    linearLayout.addView(icon);
                } else {
                    linearLayout.addView(icon);
                    linearLayout.addView(cardView);
                }

                linearLayout.setEnabled(true);
                LayoutParams llp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                llp.setMargins(0, 0, 0, (int) convertDpToPixel(8, mContext));
                linearLayout.setLayoutParams(llp);

                Animation fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
                linearLayout.startAnimation(fadeIn);
                if (mInflateMenuAt == 0)
                    mLinearTop.addView(linearLayout);
                else
                    mLinearBottom.addView(linearLayout);

                GradientDrawable bg = new GradientDrawable();
                bg.setShape(GradientDrawable.OVAL);
                bg.setColor(mFabcolor);
                icon.setBackground(bg);

                mChildrenId.add(linearLayout.getId());
                findViewById(linearLayout.getId()).setOnClickListener(context);
                animateIn(i + 1);
            }
        }, 500);
    }
}
