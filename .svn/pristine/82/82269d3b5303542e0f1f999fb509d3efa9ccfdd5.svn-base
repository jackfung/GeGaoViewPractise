package com.gaoge.view.practise.textview;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.orange.test.textImage.R;

public class TestCDATA extends Activity {
    TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_textview);
        
        mTv = (TextView)findViewById(R.id.tv);
        mTv.setText(Html.fromHtml(getString(R.string.test_cdata)));
    }

}
