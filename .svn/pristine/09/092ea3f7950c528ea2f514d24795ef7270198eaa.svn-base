package com.gaoge.view.practise.spanableString;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.test.textImage.R;



public class TestSpannableStringActivity extends Activity {
    TextView mSelectArticle;
    View mTip;
    RelativeLayout.LayoutParams mParams;
    FrameLayout mParent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        mTip = LayoutInflater.from(this).inflate(R.layout.tip_gesture_operate, null);
        mSelectArticle = (TextView) mTip.findViewById(R.id.gesture_tip);
        
        mParent = (FrameLayout) this.getWindow()
                .getDecorView().findViewById(android.R.id.content);
        
        mParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        mParent.addView(mTip, mParams);
        showTip(R.drawable.gesture_forward, R.string.gesture_prev_tip, R.drawable.tip_prev);
    }
    
    private void showTip(int getsturedRes, int tipId, int backgourdRes) {
        final String lightStr = this.getString(R.string.light_seperator);
        Drawable d = this.getResources().getDrawable(getsturedRes);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
        SpannableString ss = new SpannableString(this.getString(
                tipId, lightStr));
        int start = ss.toString().indexOf(lightStr);
        ss.setSpan(span, start, start + lightStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mSelectArticle.setBackgroundResource(backgourdRes);
        mSelectArticle.setText(ss);
    }

}
