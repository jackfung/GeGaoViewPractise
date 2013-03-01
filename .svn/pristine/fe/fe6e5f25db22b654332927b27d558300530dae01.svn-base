package com.gaoge.view.practise;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.orange.test.textImage.R;

public class TestSpannableStringAct extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.test_text_spannable);
        View txtInfo =findViewById(R.id.tv);
        //SpannableString文本类，包含不可变的文本但可以用已有对象替换和分离。
        //可变文本类参考SpannableStringBuilder
        SpannableString ss = new SpannableString(getString(R.string.help_select_articles_light,getString(R.string.light_seperator)));  
        //用颜色标记文本
//        ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 2,  
//                //setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        //用超链接标记文本
//        ss.setSpan(new URLSpan("tel:4155551212"), 2, 5,  
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        //用样式标记文本（斜体）
//        ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 5, 7,  
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        //用删除线标记文本
//        ss.setSpan(new StrikethroughSpan(), 7, 10,  
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        //用下划线标记文本
//        ss.setSpan(new UnderlineSpan(), 10, 16,  
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        //用颜色标记
//        ss.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 13,  
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        //获取Drawable资源
        Drawable d = getResources().getDrawable(R.drawable.help_light);  
        d.setBounds(0, 10, d.getIntrinsicWidth(), d.getIntrinsicHeight()+10);
        //创建ImageSpan
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
        //用ImageSpan替换文本
        int start = ss.toString().indexOf("***");
        int end = ss.toString().lastIndexOf("***") + 3;
        ss.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);  
        ((TextView)txtInfo).setText(ss);
//        txtInfo.setMovementMethod(LinkMovementMethod.getInstance()); //实现文本的滚动   
    }
}
