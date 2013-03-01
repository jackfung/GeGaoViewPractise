package com.gaoge.view.webview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.webview.Carousel.OnItemChangedListener;
import com.gaoge.view.webview.Carousel.OnScrollingListener;
import com.gaoge.view.webview.interfaces.MyUiController;
import com.orange.test.textImage.R;

public class MyBottomBarMenu extends LinearLayout implements OnClickListener {

    private Carousel mCarousel;
    private MenuIndicator mIndicator;
    private MyUiController mUiController;
    private MyBaseUi mBaseUi;
    ViewGroup mParent;
    private Context mContext;
    private TextView mBookmark, mHistory, mDownload, mShare, mInFullScreen, mSetting, mPageInfo,
            mFind, mSave, mHelp, mExit, mRead;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    boolean mAnimating;
    private int mVisibleHeight;
    private static final int TIME_INTERVAL = 25;
    private static final int HEIGHT_STEP = 50;
    private static final int ACCELERATON_FACTOR = 2;
    boolean mIsShown;
    
    
    public MyBottomBarMenu(Context context) {
        super(context);
    }
    
    public MyBottomBarMenu(Context context, MyUiController controller, MyBaseUi ui,
            ViewGroup parent) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.my_browser_menu, this);
        setWillNotDraw(false);
        mCarousel = (Carousel) findViewById(R.id.carousel);
        mIndicator = (MenuIndicator) findViewById(R.id.menu_indicator);
        mUiController = controller;
        mBaseUi = ui;
        mParent = parent;
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        LayoutInflater flater = LayoutInflater.from(context);
        View page_one = flater.inflate(R.layout.menu_page_1, null);
        mCarousel.addView(page_one);
        mBookmark = (TextView) page_one.findViewById(R.id.menu_bookmark);
        mBookmark.setOnClickListener(this);
        mHistory = (TextView) page_one.findViewById(R.id.menu_history);
        mHistory.setOnClickListener(this);
        mSetting = (TextView) page_one.findViewById(R.id.menu_setting);
        mSetting.setOnClickListener(this);
        mShare = (TextView) page_one.findViewById(R.id.menu_share);
        mShare.setOnClickListener(this);
        mInFullScreen = (TextView) page_one.findViewById(R.id.menu_in_fullscreen);
        mInFullScreen.setOnClickListener(this);
        mExit = (TextView) page_one.findViewById(R.id.menu_exit);
        mExit.setOnClickListener(this);
        mDownload = (TextView) page_one.findViewById(R.id.menu_download);
        mDownload.setOnClickListener(this);
        mRead = (TextView) page_one.findViewById(R.id.menu_read);
        mRead.setOnClickListener(this);

        View page_two = flater.inflate(R.layout.menu_page_2, null);
        mCarousel.addView(page_two);
        mSave = (TextView) page_two.findViewById(R.id.menu_save);
        mSave.setOnClickListener(this);
        mPageInfo = (TextView) page_two.findViewById(R.id.menu_pageinfo);
        mPageInfo.setOnClickListener(this);
        mHelp = (TextView) page_two.findViewById(R.id.menu_help);
        mHelp.setOnClickListener(this);
        mFind = (TextView) page_two.findViewById(R.id.menu_find);
        mFind.setOnClickListener(this);
        mCarousel.snapToItemWithoutScrolling(0);
        mCarousel.setScrollingListener(new OnScrollingListener() {

            @Override
            public void onScrolling(float offset) {
                // TODO Auto-generated method stub
                mIndicator.update(offset);
            }
        });
        mCarousel.setOnItemChangedListener(new OnItemChangedListener() {

            @Override
            public void onItemChanged(int screen, int carouselId) {
                // TODO Auto-generated method stub
                mIndicator.stop(screen);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.clipRect(new Rect(0, mMeasuredHeight - mVisibleHeight, mMeasuredWidth,
                mMeasuredHeight));
        canvas.drawColor(getContext().getResources().getColor(R.color.gray));
        super.draw(canvas);
    }
    
    public void show() {
        LogHelper.d(LogHelper.TAG_BOTTOMBAR, "################ MyBottomBarMenu.show()");
        if (!mAnimating) {
            mAnimating = true;
            mParent.addView(this, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
            mBaseUi.getAndroidRootView().bringChildToFront(this);
//            bringToFront();
            postDelayed(mUnveil, TIME_INTERVAL);
            mCarousel.snapToItemWithoutScrolling(0);
            mIndicator.stop(0);
//            setButtonEnable(!mBaseUi.isHomePageShowing());
            MyBrowserSettings settings = MyBrowserSettings.getInstance(mContext);
            mInFullScreen.setCompoundDrawablesWithIntrinsicBounds(null,
                    settings.useFullscreen() ? mContext
                            .getResources().getDrawable(R.drawable.menu_out_fullscreen) : mContext
                            .getResources().getDrawable(R.drawable.menu_in_fullscreen), null, null);
            mInFullScreen.setText(settings.useFullscreen() ? R.string.menu_out_fullscreen
                    : R.string.menu_in_fullscreen);
            invalidate();
        }
    }

    private Runnable mVeil = new Runnable() {

        @Override
        public void run() {
            if (mVisibleHeight > HEIGHT_STEP * ACCELERATON_FACTOR) {
                mVisibleHeight -= HEIGHT_STEP;
            } else if (mVisibleHeight > ACCELERATON_FACTOR){
                mVisibleHeight -= mVisibleHeight / ACCELERATON_FACTOR;
            } else {
                mVisibleHeight -= 1;
            }
            if (mVisibleHeight > 0) {
                postDelayed(this, TIME_INTERVAL);
            } else {
                mVisibleHeight = 0;
                mParent.removeView(MyBottomBarMenu.this);
                mIsShown = false;
                mAnimating = false;
            }
//            mBaseUi.mFullscreenSwitcher.setMenuHeight(mVisibleHeight);
            invalidate();
        }
       
    };
    
    public void hide() {
        if (!mAnimating) {
            mAnimating = true;
            setClickable(false);
            postDelayed(mVeil, TIME_INTERVAL);
            invalidate();
        }
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_setting:
                mUiController.openSetting();
                break;
            case R.id.menu_exit:
                mUiController.exitBrowser();
                break; 
            case R.id.menu_find:
                mUiController.openFindDialog();
                break;
            case R.id.menu_in_fullscreen:
                MyBrowserSettings settings = MyBrowserSettings.getInstance(mContext);
                settings.setFullscreen(!settings.useFullscreen());
                break;
        }
       hide();
    }
    
    private Runnable mUnveil = new Runnable() {

        @Override
        public void run() {
            int distance = mMeasuredHeight - mVisibleHeight;
            if (distance > HEIGHT_STEP * ACCELERATON_FACTOR) {
                mVisibleHeight += HEIGHT_STEP;
            } else if (distance > ACCELERATON_FACTOR) {
                mVisibleHeight += distance / ACCELERATON_FACTOR;
            } else {
                mVisibleHeight += 1;
            }
            if (mMeasuredHeight > mVisibleHeight) {
                postDelayed(this, TIME_INTERVAL);
            } else {
                mVisibleHeight = mMeasuredHeight;
                mIsShown = true;
                mAnimating = false;
                setClickable(true);
            }
//            mBaseUi.mFullscreenSwitcher.setMenuHeight(mVisibleHeight);
            invalidate();
        }
    };

}
