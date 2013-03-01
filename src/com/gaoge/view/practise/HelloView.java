
package com.gaoge.view.practise;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;

import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;

public class HelloView extends View {

    public HelloView(Context context) {
        super(context);
    }
    String TAG = "HelloView";
    
    public HelloView(Context context,AttributeSet attrs) {
        super(context,attrs);
        Log.d(TAG, "HelloView() width: " + this.getWidth() + ",height: " + this.getHeight());
    }

    private void drawLine(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setTextSize(30);

        canvas.drawLine(0, 0, this.getWidth(), this.getHeight(), paint); 

    }
    
    
    private void drawText(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setTextSize(30);

        canvas.drawText("高歌，你一定要加油，胜利一定属于你!", 10, 100, paint);
    }
    
    private void drawBitmap(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        BitmapDrawable bd = (BitmapDrawable)getResources().getDrawable(R.drawable.efficient_read);
        Bitmap bitmap = bd.getBitmap();
        canvas.drawBitmap(bitmap, 0, 0,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure() width: " + this.getWidth() + ",height: " + this.getHeight());
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Log.d(TAG, "onDraw() width: " + this.getWidth() + ",height: " + this.getHeight());
        drawBitmap(canvas);
        
        drawLine(canvas);
       
    }
    
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        LogHelper.log(TAG, "Child*Child*Child*Child*Child*Child*Child*  onTouchEvent:event.getX() "
//                + event.getX() + ",event.getY(): " + event.getY() + ",event.getAction(): " + event.getAction() , "MotionEvent");
//        return false;
//    }
    
    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
        menu.add("HelloView--contextMenu1");
        menu.add("HelloView--contextMenu2");
    }
    
    
    

}
