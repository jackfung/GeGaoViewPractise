package com.gaoge.view.webview;



import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.webview.interfaces.MyUiController;

public class MyHelpEnabledPhoneUi extends MyBaseUi {
    private MyBrowserAssociatedHelpWM mHelp;

    public MyHelpEnabledPhoneUi(Activity browser, MyUiController controller) {
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

    @Override
    public boolean onBackKey() {
        if (mHelp.onBackKey()) {
            return true;
        }
        return super.onBackKey();
    }

    @Override
    public void suggestHideTitleBar() {
        super.suggestHideTitleBar();
        /**
         * modify by gaoge 2012-04-20 because in BaseUi constructor,will call
         * setFullscreen, which will call showTitleBar,but now the mHelp is not
         * instanite already
         */
        if (null != mHelp) {
            mHelp.suggestHideTitleBar();
        }
    }

    @Override
    public void showTitleBar() {
        // TODO Auto-generated method stub
        super.showTitleBar();
        /**
         * modify by gaoge 2012-04-20 because in BaseUi constructor,will call
         * setFullscreen, which will call showTitleBar,but now the mHelp is not
         * instanite already
         */
        if (null != mHelp) {
            mHelp.showTitleBar();
        }
    }

    public void showEfficientTip() {
        mHelp.showEfficientTip();
    }

    public void showSelectArticlesTip() {
        mHelp.showSelectArticlesTip();
    }

    @Override
    public void invokeShowEfficientReadTipOrNot() {
        super.invokeShowEfficientReadTipOrNot();
        showEfficientTip();
    }

    @Override
    public void invokeBeforeStartEfficientRead() {
        super.invokeBeforeStartEfficientRead();
        showSelectArticlesTip();
    }
    
    @Override
    public void onQuitFullScreen() {
        // TODO Auto-generated method stub
        super.onQuitFullScreen();
        showEfficientTip();
    }
    @Override
    public boolean onkeyDown() {
        return mHelp.isSelectArticleTipShowing();
    }
    
    @Override
    public void startSelectArticlesAnimation() {
        super.startSelectArticlesAnimation();
        if(mHelp.isSelectArticleTipShowing()){
//            mHelp.startSelectArticlesAnimation();
        }
    }

}
