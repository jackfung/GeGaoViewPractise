package com.gaoge.view.webview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gaoge.view.webview.interfaces.MyUiController;
import com.orange.test.textImage.R;


public class MyBottomBar extends RelativeLayout implements OnClickListener {

    private MyUiController mUiController;
    private MyBaseUi mBaseUi;
    private FrameLayout mParent;
    private ImageView mBackward, mForward, mRead, mHome, mWindow, mFunction;
    protected View mBottomBarNav;
    private boolean mShowing = false;
    
    public MyBottomBar(Context context) {
        super(context);
    }
    
    public MyBottomBar(Context context, MyUiController controller, MyBaseUi ui,
            FrameLayout parent){
        super(context, null);
        mUiController = controller;
        mBaseUi = ui;
        mParent = parent;
        initLayout(context);
    }
    
    private void initLayout(Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        factory.inflate(R.layout.my_bottom_bar, this);
        
        mBottomBarNav = findViewById(R.id.bottom_bar_nav);
        
        mBackward = (ImageView) findViewById(R.id.bottom_button_backward);
        mForward = (ImageView) findViewById(R.id.bottom_button_forward);
//        mRead = (ImageView) findViewById(R.id.bottom_button_read);
        mHome = (ImageView) findViewById(R.id.bottom_button_home);
        mWindow = (ImageView) findViewById(R.id.bottom_button_window);
        mFunction = (ImageView) findViewById(R.id.bottom_button_function);
        mBackward.setOnClickListener(this);
        mForward.setOnClickListener(this);
//        mRead.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mWindow.setOnClickListener(this);
        mFunction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bottom_button_function:
                if(mBaseUi.mMenu.isShown()){
                    mBaseUi.mMenu.hide();
                }else{
                    mBaseUi.mMenu.show();
                }
                break;
            case R.id.bottom_button_window:
                mBaseUi.openWindowManager();
                break;
        }
    }
    
    void show() {
        if (getParent() == null) {
            mParent.addView(this);
        }
        setVisibility(View.VISIBLE);
        mShowing = true;
    }

    void hide() {
        setVisibility(View.GONE);
        mShowing = false;
    }

}
