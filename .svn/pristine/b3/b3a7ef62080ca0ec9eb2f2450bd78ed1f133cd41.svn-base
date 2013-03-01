package com.gaoge.view.practise;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.practise.utils.Utils;

public class MyViewGroup extends ViewGroup {

    String TAG = "MyViewGroup";
    public MyViewGroup(Context context) {
        super(context);
        Utils.initScreenSizeInfo(context);
        addView(new HelloView(context));
    }

    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
        int count = getChildCount();
        Log.d(TAG, "########## child count is: " + count);
        
        for(int i=0;i<count;i++){
            View child = this.getChildAt(i);
            childLayoutSelf(child,i);
        }
    }
    private void childLayoutSelf(View child,int child_index){
        int childTop = Utils.getChildTop(child_index);
        int childLeft = 0;
        int childRight = Utils.getFullScreenWidth();
        int childBottom = childTop + Utils.getChildHeight();
        
        child.layout(childLeft, childTop, childRight, childBottom);
    }
    
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        LogHelper.log(TAG, "############## onTouchEvent:event.getX() "
//                + event.getX() + ",event.getY(): " + event.getY()
//                + ",event.getAction(): ", "MotionEvent");
//        return false;
//    }
    
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        LogHelper.log(TAG, "^^^^^^^^^^^^^^^ onInterceptTouchEvent:event.getX() "
//                + event.getX() + ",event.getY(): " + event.getY()
//                + ",onInterceptTouchEvent: " + super.onInterceptTouchEvent(event),"MotionEvent");
//        return false;
//    }
//    @Override
//    protected void onCreateContextMenu(ContextMenu menu) {
//        super.onCreateContextMenu(menu);
//        menu.add("MyViewGroup--contextMenu1");
//        menu.add("MyViewGroup--contextMenu2");
//    }

}
