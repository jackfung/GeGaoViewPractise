
package com.gaoge.view.practise;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.orange.test.textImage.R;

public class TestViewFlipperActivity extends Activity {
    private Button previous, next;
    private ViewFlipper flipper;
    GestureDetector mGestureDetector;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initiaViewFlipper(this);
        setContentView(flipper);
    }

    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG,"##########  GestureDetector onFling");
                // illegal finger gesture
                if (Math.abs(e1.getY() - e2.getY()) > 100) {
                    return false;
                }
                // left slide
                if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 100) {
                    flipper.showPrevious();
                }
                // right slide
                if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 100) {
                    flipper.showNext();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    String TAG = "TestViewFlipperActivity";
    /**
     * Initialize view
     */
    private void initiaViewFlipper(Context context) {

        initGestureDetector();
        
        flipper = new ViewFlipper(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        flipper.setLayoutParams(layoutParams);
        flipper.addView(addImageById(R.drawable.help_fullscreen),layoutParams);
        flipper.addView(addImageById(R.drawable.ic_launcher),layoutParams);
        flipper.addView(addImageById(R.drawable.efficient_read),layoutParams);
        flipper.addView(addImageById(R.drawable.btn_readmode_close),layoutParams);

        flipper.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG,"##########  flipper.setOnTouchListener onTouch");
                 mGestureDetector.onTouchEvent(event);
                 return true;
            }
        });

    }

    public View addImageById(int id) {
        ImageView iv = new ImageView(this);
        iv.setImageResource(id);
        return iv;
    }

}
