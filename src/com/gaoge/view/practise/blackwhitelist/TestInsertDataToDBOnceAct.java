package com.gaoge.view.practise.blackwhitelist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gaoge.view.practise.blackwhitelist.databaseObject.ListObject;
import com.gaoge.view.practise.blackwhitelist.operation.WhiteBlackList;
import com.orange.test.textImage.R;

public class TestInsertDataToDBOnceAct<T extends ListObject> extends Activity {

    Button bt_insert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_insert_db_only_once);
        bt_insert = (Button)this.findViewById(R.id.bt_insert);
        bt_insert.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                saveBlackWhiteListToDatabase();
            }
        });
    }
    
    private void saveBlackWhiteListToDatabase(){
        WhiteBlackList.getBlackList().saveBlackWhiteListToDatabase(this,null);
        WhiteBlackList.getWhiteList().saveBlackWhiteListToDatabase(this,null);
    }
}
