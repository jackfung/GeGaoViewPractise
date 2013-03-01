
package com.gaoge.view.practise.blackwhitelist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gaoge.view.practise.blackwhitelist.operation.WhiteBlackList;
import com.orange.test.textImage.R;

public class TestServerJson extends Activity {

    public static String BLACKLIST_VERSION = "black_version";
    public static String WHITELIST_VERSION = "white_version";
    public static String WHITELIST_TYPE = "multiselect_whitelist";
    public static String BLACKLIST_TYPE = "multiselect_blacklist";

    Button bt_only;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_only_one_button);
        bt_only = (Button) this.findViewById(R.id.bt_only);
        bt_only.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                WhiteBlackList.synchronizeBlackWhiteListWithServer(getApplicationContext());
            }
        });
    }
}
