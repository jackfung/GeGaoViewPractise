
package com.gaoge.view.practise;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.orange.test.textImage.R;

public class TestAnimation extends Activity {
    ImageView mBtnReadMode;
    AnimationDrawable frameAnimation;
    Button bt_start;
    Button bt_stop;
    int animTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.animation);
        bt_start = (Button) this.findViewById(R.id.bt_start);
        bt_stop = (Button) this.findViewById(R.id.bt_stop);

        mBtnReadMode = (ImageView) this.findViewById(R.id.readmode_close);
        mBtnReadMode.setBackgroundResource(R.drawable.efficient_read_switch);

        frameAnimation = (AnimationDrawable) mBtnReadMode.getBackground();

        // Start the animation (looped playback by default).

        bt_start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("flick", "############# bt_again click");
                if(frameAnimation.isRunning()){
//                    mBtnReadMode.setClickable(true);
                    Log.d("flick", "############# is animation running before stop: " + frameAnimation.isRunning());
                    frameAnimation.stop();
                }
                frameAnimation.start();
              
            }
        });
        
//        bt_stop.setOnClickListener(new OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                Log.d("flick", "############# is animation running before stop: " + frameAnimation.isRunning());
//                frameAnimation.stop();
//                Log.d("flick", "############# is animation running after stop: " + frameAnimation.isRunning());
//                
//            }
//        });
        
       
        // AnimationUtilsSelf.setFlickerAnimation(mBtnReadMode);

    }

    // @Override
    // public void onWindowFocusChanged(boolean hasFocus) {
    // // TODO Auto-generated method stub
    // super.onWindowFocusChanged(hasFocus);
    // if(hasFocus){
    //
    // }
    // }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            frameAnimation.start();
//            return true;
//        }
//        return super.onTouchEvent(event);
//        // TODO Auto-generated method stub
//    }

}
