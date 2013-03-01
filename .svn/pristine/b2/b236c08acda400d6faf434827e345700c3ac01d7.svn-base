package com.gaoge.view.practise;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.orange.test.textImage.R;

public class TextImageView extends ImageView {

	public TextImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		 setImageResource(R.drawable.btn_window_regist);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint countPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);   
        countPaint.setColor(Color.RED);   
        countPaint.setTextSize(20f);   
        countPaint.setTypeface(Typeface.DEFAULT_BOLD); 
        canvas.drawText("5", 40, 45, countPaint);
	}

}
