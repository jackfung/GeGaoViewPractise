
package com.gaoge.view.webview.help;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.orange.test.textImage.R;

public class TestHelpViewClickActivity extends Activity {
    Button bt_fullscreen_content;
    Button bt_fullscreen_wm;
    Button bt_firstLink;
    Button bt_secondLink;

    TextView tv_tip;

    FrameLayout mContent;
    View mTip;

    WindowManager mWindowManager;
    WindowManager.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_test_click);
        mContent = (FrameLayout) this.getWindow()
                .getDecorView().findViewById(android.R.id.content);
        initWindowManagerLayoutParams();

        bt_fullscreen_content = (Button) this.findViewById(R.id.fullscreen_content);
        bt_fullscreen_wm = (Button) this.findViewById(R.id.fullscreen_wm);

        bt_firstLink = (Button) this.findViewById(R.id.firstLink);
        bt_secondLink = (Button) this.findViewById(R.id.secondLink);

        mTip = LayoutInflater.from(this).inflate(R.layout.tip_fullscreen, null);
        tv_tip = (TextView) mTip.findViewById(R.id.select_article);
        bt_fullscreen_content.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mTip.isShown()) {
                    mContent.removeView(mTip);
                } else {
                    mContent.addView(mTip);
                }
            }
        });

        bt_fullscreen_wm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mTip.isShown()) {
                    mWindowManager.removeView(mTip);
                } else {
                    mWindowManager.addView(mTip, params);
                }
            }
        });

        bt_firstLink.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                tv_tip.setText(R.string.help_select_second_link);

                final String lightStr = TestHelpViewClickActivity.this
                        .getString(R.string.light_seperator);
                Drawable d = TestHelpViewClickActivity.this.getResources().getDrawable(
                        R.drawable.bulb);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                SpannableString ss = new SpannableString(TestHelpViewClickActivity.this.getString(
                        R.string.help_select_first_link, lightStr));
                int start = ss.toString().indexOf(lightStr);
                ss.setSpan(span, start, start + lightStr.length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tv_tip.setText(ss);
            }
        });

        bt_secondLink.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tv_tip.setText(R.string.help_select_second_link);
            }
        });
    }

    private void initWindowManagerLayoutParams() {
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();

        params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION + 3;
        params.format = PixelFormat.RGBA_8888;

        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    }

}
