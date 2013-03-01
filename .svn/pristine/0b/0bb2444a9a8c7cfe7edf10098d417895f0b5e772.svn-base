package com.gaoge.view.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoge.view.practise.utils.Utils;
import com.gaoge.view.webview.interfaces.MyUiController;
import com.orange.test.textImage.R;

import java.util.HashMap;

public class MyWindowsManager extends FrameLayout implements OnClickListener{

    private MyUiController mUiController;
    private MyBaseUi mBaseUi;
    private FrameLayout mParent;
    private MyTabControl mTabControl;
    private HashMap<MyTab, Bitmap> mBitmapCache;
    private Context mContext;
    public boolean mWindowManagerIsShowing;
    
    
    
    private ImageView mNew;
    private TextView mCurrentWindowTitle;
    private TextView mCurrentWindowUrl;
    private MyWindowIndicator mDot;
    private MyOGallery mWindowGallery;
    
    
    public MyWindowsManager(Context context, MyUiController controller, MyBaseUi ui,
            FrameLayout parent) {
        super(context);
        mContext = context;
        mUiController = controller;
        mBaseUi = ui;
        mParent = parent;
        mTabControl = mUiController.getTabControl();
        mBitmapCache = new HashMap<MyTab, Bitmap>();
        initLayout(context);
    }
    
    private void initLayout(Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        factory.inflate(R.layout.my_window_manager, this);
        
        mNew = (ImageView) findViewById(R.id.wm_new);
        mNew.setOnClickListener(this);
        
        mCurrentWindowTitle = (TextView) findViewById(R.id.wm_title);
        mCurrentWindowUrl = (TextView) findViewById(R.id.wm_url);
        mDot = (MyWindowIndicator) findViewById(R.id.wm_dot);
        mWindowGallery = (MyOGallery) findViewById(R.id.wm_gallery);
    }
    @Override
    public void onClick(View v) {
      
        
    }
    void show() {
        mNew.setEnabled(false);
        if(mParent.indexOfChild(this)==-1){
            mParent.addView(this);
        }
        mParent.setVisibility(View.VISIBLE);
        mBaseUi.mWebViewContainer.setVisibility(View.GONE);
//        addTestChildView();
        int tabCount = mTabControl.getTabCount();
        
        int thumbnailWidth=(int) (mBaseUi.mContentView.getWidth()*0.65f);
        int thumbnailHeight=(int) (mBaseUi.mContentView.getHeight()*0.65f);
        
        for(int i=0;i < tabCount;i++){
            final MyTab tab = mTabControl.getTab(i);
            final MyOWindow window = new MyOWindow(mContext);
            initMyOWindow(thumbnailWidth, thumbnailHeight, i, tab, window);
            mWindowGallery.addView(window);
        }
        
        mWindowManagerIsShowing=true;
    }

    void hidden(){
        mParent.setVisibility(View.GONE);
        mBaseUi.mWebViewContainer.setVisibility(View.VISIBLE);
        
        mWindowGallery.removeAllViews();
    }
    
    private void initMyOWindow(int thumbnailWidth, int thumbnailHeight, int i, final MyTab tab,
            final MyOWindow window) {
        window.setTab(tab);
        Bitmap bitmap = mBitmapCache.get(tab);
        if(null == bitmap){
            bitmap = Utils.createScreenshot(tab.getWebView(), thumbnailWidth, thumbnailHeight);
            mBitmapCache.put(tab, bitmap);
        }
        window.setBitmap(bitmap);
        window.getCloseBt().setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
            }
        });
        window.getThumnail().setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
            }
        });
        window.setIndex(i);
    }
   
    @SuppressWarnings("unused")
    private void addTestChildView() {
        ImageView imageView1 = new ImageView(mContext);
        imageView1.setImageResource(R.drawable.ic_gaoge_launcher);
        mWindowGallery.addView(imageView1);
        
        ImageView imageView2 = new ImageView(mContext);
        imageView2.setImageResource(R.drawable.ic_gaoge_launcher);
        mWindowGallery.addView(imageView2);
        
        ImageView imageView3 = new ImageView(mContext);
        imageView3.setImageResource(R.drawable.ic_gaoge_launcher);
        mWindowGallery.addView(imageView3);
    }

}
