
package com.gaoge.view.practise;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.orange.test.textImage.R;

public class TestOrangeBrowserTitleBarSecond extends Activity {
    TextView url;
    ImageButton mStarButton;
    View mNavPhone;
    Drawable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.title_bar_nav_cp);
        initTextViewCompoundDrawable();
    }
    
    private void initTextViewCompoundDrawable(){
        int rightOffset = (int)this.getResources().getDimension(R.dimen.autocompletetextview_compound_drawable_rightoffset);
        url = (TextView)this.findViewById(R.id.url);
        Drawable refresh = this.getResources().getDrawable(R.drawable.ic_refresh);
        refresh.setBounds(rightOffset, 0, refresh.getIntrinsicWidth() + rightOffset, refresh.getIntrinsicHeight());
        url.setCompoundDrawables(null, null, refresh, null);
    }



}
