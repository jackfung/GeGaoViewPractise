package com.gaoge.view.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.Scroller;

import com.gaoge.view.practise.utils.LogHelper;

public class MyOGallery extends ViewGroup {

    private Scroller mScroller;
    private int mTouchSlop;
    
    
    //size variable
    private float mScale = 0.74f;
    private int mItemWidth;
    private int mItemHeight;
    private int mSpace;
    
    private int mCurrentItem = 0;
    
    private float mLastMotionX;
    private int mTouchState = TOUCH_STATE_REST;
    
    private final static int TOUCH_STATE_REST = 0;
    private final static int TOUCH_STATE_SCROLLING = 1;
    
    private final static int SNAP_VELOCITY = 500;
    
    private VelocityTracker mVelocityTracker;
    
    private boolean mFirstLayout = true;
    
    private final static int ANIMATION_DURATION = 500;
    
    public MyOGallery(Context context) {
        super(context);
    }

    public MyOGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyOGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    
    
    @Override
    protected void onLayout(boolean changed, int arg1, int arg2, int arg3, int arg4) {
        int childLeft = mSpace;
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(childLeft, 0, childLeft + mItemWidth,
                        getMeasuredHeight());
                LogHelper.d(LogHelper.TAG_WM, "@@@@@@@@@@@@@@@ onLayout(),child "
                        + i + ",left: "+ childLeft);
                childLeft += (mItemWidth + mSpace);
            }
        }
        if (mFirstLayout || changed) {
            snapToScreenWithoutScrolling(mCurrentItem);
            mFirstLayout = false;
        }
    }
    private void snapToScreenWithoutScrolling(int position) {
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        mCurrentItem = position;
        final int distanceToCenter = (int) ((1 - mScale) * getMeasuredWidth()
                / 2 - mSpace);
        final int oldX = getScrollX();
        final int newX = (int) (mCurrentItem * (mItemWidth + mSpace) - distanceToCenter);
        final int deltaX = newX - oldX;
        mScroller.startScroll(oldX, 0, deltaX, 0, 0);
//        if (mItemChangedListener != null) {
//            mItemChangedListener.onItemChanged(mCurrentItem);
//        }
    }
    
    private void init(Context context) {
        mScroller = new Scroller(context);
        final ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();
        this.setClickable(true);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mItemWidth = (int) (getMeasuredWidth() * mScale);
        mItemHeight = getMeasuredHeight();
        mSpace=(int) (getMeasuredWidth() * 0.038f);
        LogHelper.d(LogHelper.TAG_WM, "@@@@@@@@@@@@@@@ onMeasure(),getMeasuredWidth()： " +
                getMeasuredWidth() + ",mSpace: " + mSpace);
        int mMeasuredWidth = MeasureSpec.makeMeasureSpec(mItemWidth, MeasureSpec.EXACTLY);
        int mMeasuredHeight = MeasureSpec.makeMeasureSpec(mItemHeight, MeasureSpec.EXACTLY);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(mMeasuredWidth, mMeasuredHeight);
        }
    }
    
    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            LogHelper.d(LogHelper.TAG_SCROLL, "llllllllllllllll computeScroll() mScroller.getCurrX(): "
                    + mScroller.getCurrX() + ",mScroller.getCurrY(): "
                    + mScroller.getCurrY());
            /**
             * 如果把下面这行postInvalidate()注释掉，则虽然也可滑动，但是每次一个屏幕不能完全 滑过去，
             * 相见scroll_bug
             */
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float x = event.getX();
        
        switch(action){
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mTouchState =  (mScroller.isFinished()) ? TOUCH_STATE_SCROLLING : TOUCH_STATE_REST;
                
                break;
            case MotionEvent.ACTION_MOVE:
                final int xDiff = (int)Math.abs(mLastMotionX - x);
                if(xDiff > mTouchSlop){
                    mTouchState = TOUCH_STATE_SCROLLING;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return mTouchState != TOUCH_STATE_REST;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null == mVelocityTracker){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        
        
        final int action = event.getAction();
        final float x = event.getX();
        
        switch(action){
            case MotionEvent.ACTION_DOWN:
                LogHelper.d(LogHelper.TAG_SCROLL, "DDDDDDDDDDDDDDDDD onTouchEvent,ACTION_DOWN,x: " + x  );
                if(null != mScroller && !mScroller.isFinished()){
                    mScroller.forceFinished(true);
                }
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                
                int delaX = (int)(mLastMotionX - x);
                LogHelper.d(LogHelper.TAG_SCROLL, "DDDDDDDDDDDDDDDDD onTouchEvent,ACTION_MOVE,x: " + x  );
                scrollBy(delaX, 0);
                
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker vt = mVelocityTracker;
                vt.computeCurrentVelocity(1000);
                int xVelocity = (int)vt.getXVelocity();
                LogHelper.d(LogHelper.TAG_SCROLL, "DDDDDDDDDDDDDDDDD onTouchEvent,ACTION_UP,x: " + x  
                        + ",xVelocity: " + xVelocity);
                /**
                 * 手指快速向右滑，或向左滑
                 */
                if(xVelocity > SNAP_VELOCITY && mCurrentItem > 0){
                    snapToScreen(mCurrentItem - 1);
                }else if(xVelocity < -SNAP_VELOCITY && mCurrentItem < (getChildCount() - 1)){
                    snapToScreen(mCurrentItem + 1);
                }
                /**
                 *手指慢慢滑动，然后松开 
                 */
                else{
                    snapToDestination();
                }
                if(null != mVelocityTracker){
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                mTouchState = TOUCH_STATE_REST;
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return true;
    }
    
    private void snapToScreen(int screen){
        mCurrentItem = screen;
        if(mCurrentItem > (getChildCount() -1)){
            mCurrentItem = getChildCount() - 1;
        }
//        int dx = mCurrentItem * getWidth() - getScrollX();
        int oldX = getScrollX();
        final int distanceToCenter = (int) ((1 - mScale) * getMeasuredWidth()
                / 2 - mSpace);
        final int newX = (int) (mCurrentItem * (mItemWidth + mSpace) - distanceToCenter);
        final int deltaX = newX - oldX;
        LogHelper.d(LogHelper.TAG_SCROLL, "DDDDDDDDDDDDDDDDD snapToScreen,screen: " + mCurrentItem);
        mScroller.startScroll(oldX, 0, deltaX, 0,ANIMATION_DURATION);
        invalidate();
    }
    
    private void snapToDestination(){
        
        int destScreen = (getScrollX() + getWidth() / 2 ) / getWidth() ;
        LogHelper.d(LogHelper.TAG_SCROLL, "DDDDDDDDDDDDDDDDD snapToDestination,destScreen: " + destScreen);
        snapToScreen(destScreen);
    }
    
}
