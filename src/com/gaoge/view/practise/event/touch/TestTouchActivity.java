package com.gaoge.view.practise.event.touch;

import com.orange.test.textImage.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestTouchActivity extends Activity {
    Button bt_menu;
    Button bt_full;
    View mSonView;
    View mParentView;
    View mFullscreenSwitch;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_touch);
        
        bt_menu = (Button)findViewById(R.id.bt_function);
        mSonView = findViewById(R.id.sonView);
        mFullscreenSwitch = findViewById(R.id.fullscreen_switcher);
        
        bt_full = (Button)findViewById(R.id.bt_full);
        bt_full.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(mFullscreenSwitch.isShown()){
                    mFullscreenSwitch.setVisibility(View.INVISIBLE);
                    bt_full.setText("Show Full");
                }else{
                    mFullscreenSwitch.setVisibility(View.VISIBLE);
                    bt_full.setText("Hide Full");
                }
            }
        });
        
        bt_menu.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(mSonView.isShown()){
                    mSonView.setVisibility(View.INVISIBLE);
                    bt_menu.setText("Show Bottom");
                }else{
                    mSonView.setVisibility(View.VISIBLE);
                    bt_menu.setText("Hide Bottom");
                }
                
            }
        });
    }
}
