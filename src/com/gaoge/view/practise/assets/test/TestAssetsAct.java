package com.gaoge.view.practise.assets.test;

import android.app.Activity;
import android.os.Bundle;

import com.gaoge.view.practise.utils.LogHelper;

import java.io.IOException;
import java.io.InputStream;

public class TestAssetsAct extends Activity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String icon = "html/images/jandan.png";
    String icon1 = "html/images/kaixin.png";
    boolean flag = iconFileExist(icon1);
    boolean flag1 = iconFileExist(icon);
    LogHelper.d("asset", "flag: " + flag + ",flag1: " + flag1);
}

private boolean iconFileExist(String icon1) {
    try {
        getAssets().open(icon1);
        return true;
    } catch (IOException e) {
        return false;
    }
}
}
