
package com.gaoge.view.practise;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.test.textImage.R;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestScrollViewActivity extends Activity {

    final public String DEV_FILE = "/data/aaa.txt";// 测试文件
    final String TEXT_ENCODING = "UTF-8";
    ScrollView sv;
    TextView tv;
    public Button test;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollview);
        sv = (ScrollView) findViewById(R.id.sv);
        tv = (TextView) findViewById(R.id.txtView);
        String str;
        str = getinfo(DEV_FILE);
        String[] x;
        x = str.split("\r");
        tv.setText(x[0]);
        int i;
        for (i = 1; i <= x.length - 1; i++)
        {
            tv.append(x[i]);
        }

        test = (Button) findViewById(R.id.test);
        test.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                sv.scrollTo(0, 2222);
                DisplayToast(sv.getScrollY() + "");

            }

        });

    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        DisplayToast("123");
    }

    public String getinfo(String path)
    {
        File file;
        String str = "";
        FileInputStream in;
        try {
            // 打开文件file的InputStream
            file = new File(path);
            in = new FileInputStream(file);
            // 将文件内容全部读入到byte数组
            int length = (int) file.length();
            byte[] temp = new byte[length];
            in.read(temp, 0, length);
            // 将byte数组用UTF-8编码并存入display字符串中
            str = EncodingUtils.getString(temp, TEXT_ENCODING);
            // 关闭文件file的InputStream

            in.close();
        } catch (IOException e) {

            DisplayToast(e.toString());

        }
        return str;
    }

    public void DisplayToast(String str)
    {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
