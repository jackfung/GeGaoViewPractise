package com.gaoge.view.practise;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.orange.test.textImage.R;

import java.util.ArrayList;

public class Testv4Page extends Activity {
    private ViewPager mViewPagerHelpGuide;
    private ArrayList<View> mPageViews;
    MyOnPageChangeListener mMyOnPageChangeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        
        mViewPagerHelpGuide = (ViewPager)findViewById(R.id.guidePages); 
        LayoutInflater inflater = getLayoutInflater();    
        mPageViews = new ArrayList<View>();    
        
        mViewPagerHelpGuide.setAdapter(new GuidePageAdapter(mPageViews)); 
        int[]resourece=new int[]{R.drawable.read_guide_1,R.drawable.read_guide_2,R.drawable.read_guide_3,R.drawable.read_guide_4};
        
        for(int i=0;i<resourece.length;i++){
            View view=inflater.inflate(R.layout.reding_guide_item, null);
            ((ImageView)view.findViewById(R.id.reading_guide_image)).setImageResource(resourece[i]);
            view.findViewById(R.id.close_guide).setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mViewPagerHelpGuide.setVisibility(View.GONE);
                }
            });
            mPageViews.add(view); 
                
        }
        initGestureDetector();
        mViewPagerHelpGuide.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        int currentChildIndex = -1;
        
        mMyOnPageChangeListener = new MyOnPageChangeListener();
        mViewPagerHelpGuide.setOnPageChangeListener(mMyOnPageChangeListener);
        
    }
    GestureDetector mGestureDetector;
    String TAG = "4page";
    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                
                // illegal finger gesture
                if (Math.abs(e1.getY() - e2.getY()) > 100) {
                    Log.d(TAG,"##########  illegal finger gesture onFling");
                    return false;
                }
                // left slide
                if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 100) {
                    Log.d(TAG,"##########  left onFling");
                }
                if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 100) {
                    Log.d(TAG,"##########  right slide");
                    if(mMyOnPageChangeListener.getCurrentPageIndex() == (mViewPagerHelpGuide.getChildCount())){
                        mViewPagerHelpGuide.setVisibility(View.GONE);
                    }
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }
    
    
    
    class MyOnPageChangeListener implements OnPageChangeListener{

    	boolean readyToDismiss = false;
    	
        public MyOnPageChangeListener(){
            Log.d("MyOnPageChangeListener", "MyOnPageChangeListener()");
            currentPageIndex = -1;
            dismissPositon = mPageViews.size() - 1;
        }
        public int getCurrentPageIndex(){
            return currentPageIndex;
        }
        int currentPageIndex;
        int dismissPositon;
        
        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d("index", "EEEEEEEEEEEEEEEEEEEEEEEEE onPageScrollStateChanged currentPageIndex: " + 
            		currentPageIndex + "state: " + state);
            if(dismissPositon == currentPageIndex && ViewPager.SCROLL_STATE_IDLE == state && readyToDismiss){
            	Log.d("flag", "^^^^^^^^^^^^^^^ guide gone now");
            	mViewPagerHelpGuide.setVisibility(View.GONE);
            }
            if(dismissPositon == currentPageIndex && ViewPager.SCROLL_STATE_IDLE == state && !readyToDismiss){
            	readyToDismiss = true;
            	Log.d("flag", "^^^^^^^^^^^^^^^ guide ready to gone ");
            }
            if((dismissPositon != currentPageIndex) && (ViewPager.SCROLL_STATE_IDLE == state && readyToDismiss)){
            	readyToDismiss = false;
            	Log.d("flag", "^^^^^^^^^^^^^^^ guide no ready to gone,set to flase again ");
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // TODO Auto-generated method stub
            
            Log.d("index", "@@@@@@@@@@@@@@@@@@@@ onPageScrolled currentPageIndex: " + currentPageIndex
                    + ",dismissPositon : " + dismissPositon);
//            if(dismissPositon == currentPageIndex){
//                mViewPagerHelpGuide.setVisibility(View.GONE);
//            }
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub'
            Log.d("index", "SSSSSSSSSSSSSSSSSSSSS onPageSelected current page position: " + position);
            currentPageIndex = position;
            
        }
        
    }
}
