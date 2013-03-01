package com.gaoge.view.practise.view.scroll;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.gaoge.view.practise.utils.LogHelper;

public class MyMultiViewGroup extends ViewGroup {

    private Context mContext;
    private Scroller mScroller;
    
    private int curScreen = 0 ;
    
    float mLastMotionX;
    float mLastMotionY;
    
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;
    private int mTouchState = TOUCH_STATE_REST;
    
    private int mTouchSlop = 0 ;
    public static int  SNAP_VELOCITY = 600 ;
    
    private VelocityTracker mVelocityTracker;
    
    public MyMultiViewGroup(Context context) {
        super(context);
        mContext = context;
        init();
        
    }

    public MyMultiViewGroup(Context context, AttributeSet attrs) {
        super(context,attrs);
        mContext = context;
        init();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.measure(getWidth(), MyMultiScreenActivity.scrrenHeight);
        }
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int startLeft = 0; 
        int startTop = 10; 
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            
            if(child.getVisibility() != View.GONE)
                child.layout(startLeft, startTop, 
                       startLeft + getWidth(), 
                       startTop + MyMultiScreenActivity.scrrenHeight );
                
                startLeft = startLeft + getWidth() ; 
        }
    }
    
    private void init() {
        mScroller = new Scroller(mContext);
        
        LinearLayout oneLL = new LinearLayout(mContext);
        oneLL.setBackgroundColor(Color.RED);
        addView(oneLL);
        
        LinearLayout twoLL = new LinearLayout(mContext);
        twoLL.setBackgroundColor(Color.YELLOW);
        addView(twoLL);
        
        LinearLayout threeLL = new LinearLayout(mContext);
        threeLL.setBackgroundColor(Color.BLUE);
        addView(threeLL);   
        
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }
    
    public void startMove(){
        LogHelper.d(LogHelper.TAG_SCROLL, "!!!!!!!!!!!!!!! startMove");
        curScreen ++ ;
        mScroller.startScroll((curScreen-1) *getWidth(), 0, 
                getWidth(), 0,3000);
        invalidate();
    }
    
    public void stopMove(){
        LogHelper.d(LogHelper.TAG_SCROLL, "!!!!!!!!!!!!!!!! stopMove");
        if(null != mScroller){
            if(!mScroller.isFinished()){
                int currX = mScroller.getCurrX();
                int destScreen = (currX + getWidth()/2) / getWidth();
                
                
                scrollTo(destScreen * getWidth(), 0);
                
                /**
                 * It seems that either mScroller.abortAnimation(); or  mScroller.forceFinished(true) works fine
                 */
             
                mScroller.abortAnimation();
//                mScroller.forceFinished(true);
                curScreen = destScreen;
            }
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
        }else{
            LogHelper.d(LogHelper.TAG_SCROLL, "@@@@@@@@@@@@@@@@@ computeScroll() have done the scoller");
        }
    }
    
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();
        
        switch(action){
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
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
                if(xVelocity > SNAP_VELOCITY && curScreen > 0){
                    snapToScreen(curScreen - 1);
                }else if(xVelocity < -SNAP_VELOCITY && curScreen < (getChildCount() - 1)){
                    snapToScreen(curScreen + 1);
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
        curScreen = screen;
        if(curScreen > (getChildCount() -1)){
            curScreen = getChildCount() - 1;
        }
        int dx = curScreen * getWidth() - getScrollX();
        LogHelper.d(LogHelper.TAG_SCROLL, "DDDDDDDDDDDDDDDDD snapToScreen,screen: " + curScreen);
        mScroller.startScroll(getScrollX(), 0, dx, 0,Math.abs(dx) * 2);
        invalidate();
    }
    
    private void snapToDestination(){
        
        int destScreen = (getScrollX() + getWidth() / 2 ) / getWidth() ;
        LogHelper.d(LogHelper.TAG_SCROLL, "DDDDDDDDDDDDDDDDD snapToDestination,destScreen: " + destScreen);
        snapToScreen(destScreen);
    }
}
