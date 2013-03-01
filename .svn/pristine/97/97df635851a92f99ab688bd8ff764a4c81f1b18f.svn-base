package com.gaoge.view.practise.utils;

import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class AnimationUtilsSelf {
    
    //图片闪烁动画
    public static  void setFlickerAnimation(ImageView iv_chat_head) {   
        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible   
        animation.setDuration(500); // duration - half a second   
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate   
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely   
        animation.setRepeatMode(Animation.REVERSE); //    
        iv_chat_head.setAnimation(animation);   
    } 
    


}
