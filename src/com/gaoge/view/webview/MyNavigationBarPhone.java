package com.gaoge.view.webview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gaoge.view.webview.MyUrlInputView.StateListener;
import com.orange.test.textImage.R;

public class MyNavigationBarPhone extends MyNavigationBarBase implements StateListener{

    private ImageButton mReadingButton;
    private ImageButton mStarButton;
    private ImageView mStarLine;
    private ImageView mVoiceButton;
    private ImageView mVoiceDivideLine;
    private Drawable mStopDrawable;
    private Drawable mRefreshDrawable;
    private Drawable mClearDrawable;
    
    public MyNavigationBarPhone(Context context) {
        super(context);
    }
    
    public MyNavigationBarPhone(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mReadingButton = (ImageButton) findViewById(R.id.reading);
        mReadingButton.setOnClickListener(this);
        mStarButton = (ImageButton) findViewById(R.id.star);
        mStarButton.setOnClickListener(this);
        mStarLine = (ImageView)findViewById(R.id.star_divide_line);
        
       
        mVoiceButton = (ImageView) findViewById(R.id.voice);
        mVoiceButton.setOnClickListener(this);
        mVoiceDivideLine = (ImageView)findViewById(R.id.voice_divide_line);
        Resources res = getContext().getResources();
        mStopDrawable = res.getDrawable(R.drawable.ic_stop);
        mRefreshDrawable = res.getDrawable(R.drawable.ic_refresh);
        mClearDrawable = res.getDrawable(R.drawable.ic_stop);
        mUrlInput.setStateListener(this);
        // mUrlInput.setOnFocusChangeListener(this);
        mUrlInput.setSelectAllOnFocus(true);
    }

    @Override
    public void onStateChanged(int state) {
        switch (state) {
            case StateListener.STATE_NORMAL:
                mReadingButton.setVisibility(View.VISIBLE);
                updateBookmarkIcons();
//                mUrlInput.setComboButton(mTitleBar == null || !mTitleBar.isInLoad() ? mRefreshDrawable : mStopDrawable);
                mVoiceButton.setVisibility(View.GONE);
                mVoiceDivideLine.setVisibility(View.GONE);
//                mUrlInput.showFavicon();
                break;
            case StateListener.STATE_HIGHLIGHTED:
                mReadingButton.setVisibility(View.GONE);
                mStarButton.setVisibility(View.GONE);
                mStarLine.setVisibility(View.GONE);
                mVoiceButton.setVisibility(View.VISIBLE);
                mVoiceDivideLine.setVisibility(View.VISIBLE);
//                mUrlInput.setComboButton(mClearDrawable);
//                mUrlInput.hideFavicon();
                break;
            case StateListener.STATE_EDITED:
                mReadingButton.setVisibility(View.GONE);
                mStarButton.setVisibility(View.GONE);
                mStarLine.setVisibility(View.GONE);
                mVoiceButton.setVisibility(View.VISIBLE);
                mVoiceDivideLine.setVisibility(View.VISIBLE);
//                mUrlInput.setComboButton(mClearDrawable);
//                mUrlInput.hideFavicon();
                break;
        }
    }
    
    @Override
    public void onProgressStopped() {
        super.onProgressStopped();
        judgeWhetherReadingButtonFlick();
    }
    
    private void judgeWhetherReadingButtonFlick(){
        mBaseUi.invokeShowEfficientReadTipOrNot();
    }

}
