package com.ayush.awesomefab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
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
    private int mId;
    private HashMap<Integer, ArrayList<AwesomeFabMenu>> mHashmap;

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
        mChild.push(R.id.relative_layout);
        mHashmap = new HashMap<>();
    }

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
        mId = typedArray.getResourceId(R.styleable.AwesomeFab_id, -1);
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
            if (!mToggle) {
                mToggle = true;
                mSrc.setImageDrawable(mAfterDrawable);
                ImageView imageView = null;
                TextView textView = null;
                if (mMenuType == 0) {
                    ArrayList<AwesomeFabMenu> mMenuList = mHashmap.get(mId);
                    if (mMenuList == null)
                        mMenuList = new ArrayList<>();

                    Log.e(LOG_TAG, mMenuList.size() + "");
                    for (int i = 0; i < mMenuList.size(); i++) {
                        int id = mChild.peek();

                        RelativeLayout relativeLayout = new RelativeLayout(mContext);
                        relativeLayout.setId(View.generateViewId());
                        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        lprams.addRule(RelativeLayout.ABOVE, id);
                        lprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        lprams.setMargins((int) convertDpToPixel(16, mContext), 0, (int) convertDpToPixel(8, mContext), (int) convertDpToPixel(16, mContext));
                        relativeLayout.setLayoutParams(lprams);

                        LinearLayout linearLayout = new LinearLayout(mContext);
                        linearLayout.setId(View.generateViewId());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        linearLayout.setLayoutParams(layoutParams);
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                        TextView label = new TextView(mContext);
                        label.setText(mMenuList.get(i).getmLabel());
                        LayoutParams tvLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        tvLp.setMargins((int) convertDpToPixel(8, mContext), 0, (int) convertDpToPixel(16, mContext), 0);
                        label.setGravity(CENTER_VERTICAL);
                        label.setLayoutParams(tvLp);

                        ImageView icon = new ImageView(mContext);
                        icon.setImageDrawable(mMenuList.get(i).getmDrawable());
                        icon.setPadding((int) convertDpToPixel(4, mContext), (int) convertDpToPixel(4, mContext), (int) convertDpToPixel(4, mContext), (int) convertDpToPixel(4, mContext));

                        linearLayout.addView(label);
                        linearLayout.addView(icon);

                        relativeLayout.addView(linearLayout);
                        mRelativeLayoutMain.addView(relativeLayout);

                        GradientDrawable bg = new GradientDrawable();
                        bg.setShape(GradientDrawable.OVAL);
                        bg.setColor(mFabcolor);
                        int radius = (int) convertDpToPixel(icon.getLayoutParams().height, mContext);
                        icon.setBackground(bg);

                        mChild.pop();
                        mChildrenId.add(relativeLayout.getId());
                        mChild.push(relativeLayout.getId());
                    }
                } else
                    Toast.makeText(mContext, "Radial", Toast.LENGTH_LONG).show();

            } else {
                mToggle = false;
                mSrc.setImageDrawable(mBeforeDrawable);
                for (int i = 0; i < mChildrenId.size(); i++)
                    mRelativeLayoutMain.removeView(findViewById(mChildrenId.get(i)));
                mChildrenId.clear();
                mChild.pop();
                mChild.push(R.id.relative_layout);
            }
        }
    }

    public void inflateMenu(String label, Drawable drawable, int id) {
        ArrayList<AwesomeFabMenu> arrayList = mHashmap.get(id);
        if (arrayList == null)
            arrayList = new ArrayList<>();
        arrayList.add(new AwesomeFabMenu(label, drawable));
        mHashmap.put(id, arrayList);
//        mView.performClick();
//        mView.performClick();
    }
}
