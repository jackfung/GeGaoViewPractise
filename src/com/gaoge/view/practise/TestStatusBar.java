package com.gaoge.view.practise;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;

public class TestStatusBar extends Activity {
	Button bt_show_status_bar;
	Button bt_hide_status_bar;
	Button bt_nothing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_status_bar);

		bt_show_status_bar = (Button) this
				.findViewById(R.id.bt_show_status_bar);
		bt_hide_status_bar = (Button) this
				.findViewById(R.id.bt_hide_status_bar);
		
		bt_nothing = (Button)this
				.findViewById(R.id.bt_nothing);

		bt_show_status_bar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showStatusBar();
			}
		});

		bt_nothing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LogHelper.d(LogHelper.TAG_STATUS_BAR, "############ bt_nothing click");
			}
		});
		
		bt_hide_status_bar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideStatusBar();
			}
		});
		
		addViewToWindowManager();
	}

	public void hideStatusBar() {
		LogHelper.d(LogHelper.TAG_STATUS_BAR, "+++++++++++++++ hideStatusBar()");
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
	}
	

	public void showStatusBar() {
		LogHelper.d(LogHelper.TAG_STATUS_BAR, "@@@@@@@@@@@@@@@@@ showStatusBar()");
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
	}

	WindowManager mWindowManager;
	View mTip;
	WindowManager.LayoutParams params,oldParams;
	
	private void addViewToWindowManager() {
		Window window = this.getWindow();
		mWindowManager =(WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		
		oldParams = params = window.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;

		int flag = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_APPLICATION + 3,
				flag,
				PixelFormat.TRANSLUCENT);
		if (mTip != null) {
			mWindowManager.removeView(mTip);
		}
		mTip = LayoutInflater.from(this).inflate(R.layout.tip_efficient_read, null);
		final View tip = mTip;
		window.setAttributes(params);
		mWindowManager.addView(mTip, params);
		// mWindowManager.addView(tip);
		View close = tip.findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				removeViewFromWindowManager();
			}
		});
	}
	
	public boolean removeViewFromWindowManager() {
        if (mTip != null) {
//            mWindowManager.removeView(mTip);
        	mWindowManager.removeViewImmediate(mTip);
            mTip = null;
            mWindowManager = null;
            this.getWindow().setAttributes(oldParams);
            params = null;
            return true;
        }
        return false;
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		LogHelper.d(LogHelper.TAG_STATUS_BAR, "onKeyDown");
		this.finish();
		return true;
	}

}
