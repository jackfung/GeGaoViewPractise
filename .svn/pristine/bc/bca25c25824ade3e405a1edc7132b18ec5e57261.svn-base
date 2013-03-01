package com.gaoge.view.practise.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;

public class TestIntentChooserActivity extends Activity {
    Button bt;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_button);
        bt = (Button)findViewById(R.id.bt);
        
        bt.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
//                startAcitivity();
                startAcitivityForResult();
            }
        });
    }
    
    private void startAcitivityForResult(){
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, "stringToSend");

        try {
            Intent i = Intent.createChooser(send, "chooserDialogTitle");
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            TestIntentChooserActivity.this.startActivityForResult(i, 2);
            LogHelper.d(TAG,"startActivityForResult");
        } catch(android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private void startAcitivity(){
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, "stringToSend");

        try {
//            Intent i = Intent.createChooser(send, "chooserDialogTitle");
            Intent i = new Intent(this, ActivityB.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            TestIntentChooserActivity.this.startActivityForResult(i,2);
            LogHelper.d(TAG,"startActivityForResult");
        } catch(android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogHelper.d(TAG,"onActivityResult,resultCode: "+ resultCode 
                + ",data: " + data);
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 2:
                break;
        }
    }
    
    String TAG = "choose";
    @Override
    protected void onResume() {
        super.onResume();
        LogHelper.d(TAG,"onResume");
    }

}
