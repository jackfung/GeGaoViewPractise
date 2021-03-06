
package com.gaoge.view.practise.packageInfo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.orange.test.textImage.R;

public class TestPackageInfoAct extends Activity {
    Button bt_getPack;
    TextView tv_show;
    String CYGNUSBROWSER_PACKANGE_NAME = "com.orange.browser";
    String SINA_WEIBO_PACKANGE_NAME = "com.sina.weibo";
    String TAOBAO_PACKANGE_NAME = "com.taobao.taobao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_packageinfo);

        bt_getPack = (Button) this.findViewById(R.id.bt_getpac);
        tv_show = (TextView) this.findViewById(R.id.tv_show);

        bt_getPack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = getLaunchComponentName(CYGNUSBROWSER_PACKANGE_NAME, -1);
                tv_show.setText(url);
                // if (url.startsWith(SCHEME_APP)) {
                // Intent i = new Intent(Intent.ACTION_MAIN, null);
                // i.addCategory(Intent.CATEGORY_LAUNCHER);
                // i.setComponent(ComponentName.unflattenFromString(url.substring(SCHEME_APP.length())));
                // startActivity(i);
                // }
            }
        });
    }

    PackageManager pm;
    public static final String SCHEME_APP = "app:";

    public String getLaunchComponentName(String packageName, int signature) {
        PackageManager pm = getPackageManager();
        try {
            Signature[] signatures = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            for (Signature sig : signatures) {
                int hashcode = sig.hashCode();
                if (signature == -1) {
                    return pm.getLaunchIntentForPackage(packageName).getComponent()
                            .flattenToString();
                }
            }
        } catch (NameNotFoundException e) {
            return "null";
        }
        return "null";
    }

}
