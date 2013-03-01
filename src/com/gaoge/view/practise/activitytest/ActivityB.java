package com.gaoge.view.practise.activitytest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.orange.test.textImage.R;

public class ActivityB extends Activity {
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_button);
        bt = (Button)findViewById(R.id.bt);
        bt.setText("ActivityB");
    }

}
