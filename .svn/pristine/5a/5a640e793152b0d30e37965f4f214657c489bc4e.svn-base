package com.gaoge.view.practise.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gaoge.view.practise.utils.LogHelper;

public class TestAdbReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent intent) {
        if("com.gaoge.TestAdbReceiver".equals(intent.getAction())){
            String homepageUrl = intent.getStringExtra("homepage");
            LogHelper.d("receiver","receiver the intent,homepageUrl: " + homepageUrl);
        }
    }

}
