package com.gaoge.view.practise;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.orange.test.textImage.R;

public class TestPopUpWindowAct extends Activity {
    PopupWindow mPopupWindow;
    
    LinearLayout add_to_bookmark_or_homescreen;
    
    private void initPopUpWindow(){
        LayoutInflater mLayoutInflater = (LayoutInflater)this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        add_to_bookmark_or_homescreen = (LinearLayout)mLayoutInflater.inflate(
                R.layout.add_to_bookmark_homescreen, null);
        mPopupWindow = new PopupWindow(add_to_bookmark_or_homescreen, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, true);

        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
                    mPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
       
        
    }
    
    Button bt_pop_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_window);
        bt_pop_up = (Button)this.findViewById(R.id.bt_pop);
        bt_pop_up.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showPopUpWindow();
            }
        });
        initPopUpWindow();
    }
    
    private void showPopUpWindow(){
        mPopupWindow.showAsDropDown(bt_pop_up, 0, 0);
    }

}
