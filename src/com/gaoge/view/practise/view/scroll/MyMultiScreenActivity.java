
package com.gaoge.view.practise.view.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.orange.test.textImage.R;

public class MyMultiScreenActivity extends Activity implements OnClickListener{

    public static int screenWidth;
    public static int scrrenHeight;
    
    private Button bt_scrollLeft;
    private Button bt_scrollRight;
    private MyMultiViewGroup mulTiViewGroup  ;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_multiview);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        scrrenHeight = metric.heightPixels;

        mulTiViewGroup = (MyMultiViewGroup) findViewById(R.id.mymultiViewGroup);

        bt_scrollLeft = (Button) findViewById(R.id.bt_scrollLeft);
        bt_scrollRight = (Button) findViewById(R.id.bt_scrollRight);

        bt_scrollLeft.setOnClickListener(this);
        bt_scrollRight.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_scrollLeft:
                mulTiViewGroup.startMove();
                break;
            case R.id.bt_scrollRight:
                mulTiViewGroup.stopMove();
                break;
        }
    }

}
