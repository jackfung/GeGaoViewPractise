package com.gaoge.view.webview;

import android.app.Activity;
import android.content.res.Configuration;

import com.gaoge.view.webview.interfaces.MyUiController;

public class TestUi extends MyBaseUi {
    private MyBrowserAssociatedHelpWM mHelp;

    public TestUi(Activity browser, MyUiController controller) {
        super(browser, controller);
        mHelp = new MyBrowserAssociatedHelpWM(browser, this, controller);
        
    }
    
    public MyBrowserAssociatedHelpWM getBrowserAssociatedHelp() {
        return mHelp;
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        mHelp.onConfigurationChanged(config);
    }
    
    public void showEfficientTip() {
        mHelp.showEfficientTip();
    }

    public void showSelectArticlesTip() {
        mHelp.showSelectArticlesTip();
    }
    
    @Override
    public void startSelectArticlesAnimation() {
        super.startSelectArticlesAnimation();
        if(mHelp.isSelectArticleTipShowing()){
//            mHelp.startSelectArticlesAnimation();
        }
    }
    
    @Override
    public void invokeShowEfficientReadTipOrNot() {
        super.invokeShowEfficientReadTipOrNot();
        showEfficientTip();
    }

}
