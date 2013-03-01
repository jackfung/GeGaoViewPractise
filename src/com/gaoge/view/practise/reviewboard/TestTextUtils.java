
package com.gaoge.view.practise.reviewboard;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.StyleSpan;

import com.gaoge.view.practise.utils.LogHelper;

public class TestTextUtils extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        testCygnusEllipsize();
    }

    public void testEllipsize() {
        CharSequence s1 = "The quick brown fox jumps over \u00FEhe lazy dog.";
        // CharSequence s2 = new Wrapper(s1);
        CharSequence s2 = "The quick brown fox jumps over \u00FEhe lazy dog.";
        Spannable s3 = new SpannableString(s1);
        s3.setSpan(new StyleSpan(0), 5, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextPaint p = new TextPaint();
        p.setFlags(p.getFlags() & ~p.DEV_KERN_TEXT_FLAG);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 3; j++) {
                TextUtils.TruncateAt kind = null;

                switch (j) {
                    case 0:
                        kind = TextUtils.TruncateAt.START;
                        break;

                    case 1:
                        kind = TextUtils.TruncateAt.END;
                        break;

                    case 2:
                        kind = TextUtils.TruncateAt.MIDDLE;
                        break;
                }

                String out1 = TextUtils.ellipsize(s1, p, i, kind).toString();
                String out2 = TextUtils.ellipsize(s2, p, i, kind).toString();
                String out3 = TextUtils.ellipsize(s3, p, i, kind).toString();

                String keep1 = TextUtils.ellipsize(s1, p, i, kind, true, null).toString();
                String keep2 = TextUtils.ellipsize(s2, p, i, kind, true, null).toString();
                String keep3 = TextUtils.ellipsize(s3, p, i, kind, true, null).toString();

                String trim1 = keep1.replace("\uFEFF", "");

            }
        }

    }

    public void testCygnusEllipsize() {
        StringBuilder mToastStringBuilder = new StringBuilder();
        String toastStr = "您已经通过USB将手机链接至计算机。如果您要在计算机和Android" +
                "手机的SD卡之间复制文件，请点击下面的按钮";
        mToastStringBuilder.setLength(0);
        TextPaint tp = new TextPaint();

        mToastStringBuilder

                .append("\"")

                .append(TextUtils.ellipsize(

                        toastStr,

                        tp, 150,

                        TextUtils.TruncateAt.END)).append("\"");

        toastStr = mToastStringBuilder.toString();
        LogHelper.d("TextUtils", "toastStr: " + toastStr);
    }
}
