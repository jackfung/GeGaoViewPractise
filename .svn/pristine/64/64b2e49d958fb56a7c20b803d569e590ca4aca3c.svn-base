
package com.gaoge.view.webview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orange.test.textImage.R;

public class MyBrowsingContainer extends RelativeLayout {

    public MyBrowsingContainer(Context context) {
        super(context);
    }

    public MyBrowsingContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        View web = findViewById(R.id.main_content);
        web.requestFocus(direction, previouslyFocusedRect);
        return true;
    }

}
