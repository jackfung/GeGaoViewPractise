
package com.gaoge.view.practise.provider.act;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gaoge.view.practise.provider.Constants;
import com.gaoge.view.practise.provider.GaoGeProviderConstants;
import com.gaoge.view.practise.provider.MyProviderContentObserver;
import com.gaoge.view.practise.provider.model.BlackUrlExclusionObjectDatabaseModel;
import com.gaoge.view.practise.provider.model.BlackUrlObjectDatabaseModel;
import com.gaoge.view.practise.provider.model.UrlDatabaseModel;
import com.gaoge.view.practise.provider.model.WhiteUrlExclusionObjectDatabaseModel;
import com.gaoge.view.practise.provider.model.WhiteUrlObjectDatabaseModel;
import com.gaoge.view.practise.provider.object.BlackExclusionObject;
import com.gaoge.view.practise.provider.object.BlackUrlObject;
import com.gaoge.view.practise.provider.object.WhiteUrlExclusionObject;
import com.gaoge.view.practise.provider.object.WhiteUrlObject;
import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.practise.utils.Utils;
import com.gaoge.view.practise.xml.BlackWhiteListXmlParser;
import com.gaoge.view.practise.xml.BlackWhiteListXmlParser.UrlItem;
import com.orange.test.textImage.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class TestJsonContentProvider extends Activity {
    Button bt_insert;
    Button bt_update;
    Button bt_query;
    Button bt_delete;
    Button bt_copy;
    
    TextView tv_query_result;
    WhiteUrlObjectDatabaseModel mWhiteUrldataModelSelf;
    WhiteUrlExclusionObjectDatabaseModel mWhiteExclusiondataModel;
    BlackUrlObjectDatabaseModel mBlackUrldataModel;
    BlackUrlExclusionObjectDatabaseModel mBlackExclusiondataModel;

    ContentResolver mContentResolver;
    String enName = "gaoge";

    String enName_china = "gaoge_china";

    String encode = "gbk";
    public static final String XML_FILE = "array_cp.xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_provider_json);
        mContentResolver = getContentResolver();

        mContentResolver.registerContentObserver(GaoGeProviderConstants.TABLE1_CONTENT_URI, true,
                new MyProviderContentObserver());
        initControls();
        
        mWhiteUrldataModelSelf = new WhiteUrlObjectDatabaseModel();
        mWhiteExclusiondataModel = new WhiteUrlExclusionObjectDatabaseModel();
        mBlackUrldataModel = new BlackUrlObjectDatabaseModel();
        mBlackExclusiondataModel = new BlackUrlExclusionObjectDatabaseModel();
    }

    private void initControls() {
        bt_insert = (Button) this.findViewById(R.id.bt_insert);

        bt_update = (Button) this.findViewById(R.id.bt_update);

        bt_query = (Button) this.findViewById(R.id.bt_query);

        bt_delete = (Button) this.findViewById(R.id.bt_delete);
        
        bt_copy = (Button)this.findViewById(R.id.bt_cp);

        bt_insert.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                insertFromArray();
                insertFromXml();
            }
        });

        bt_update.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                update();
            }
        });

        bt_query.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                query();
            }
        });

        bt_delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                delete();
            }
        });

        bt_copy.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Utils.copyDatabaseFile(TestJsonContentProvider.this, false);
            }
        });
        
        tv_query_result = (TextView) findViewById(R.id.tv_query_result);
    }

    String url = "http://www.sina.com.cn";
    
    private void insertFromArray() {
        String[] whitelist = this.getResources().getStringArray(R.array.whitelist);
        for (String  tmp: whitelist) {
            mWhiteUrldataModelSelf.insertItem(this, Constants.TYPE_EXACTLY,tmp,"host");
        }
//        
//        String[] blacklist = this.getResources().getStringArray(R.array.blacklist);
//        for (String  tmp: blacklist) {
//            mBlackUrldataModel.insertItem(this, Constants.TYPE_EXACTLY,tmp,"host");
//        }
        
    }
    
    private void insertFromXml() {
        try {
            InputStream is = getAssets().open(XML_FILE);
            BlackWhiteListXmlParser bwlXmlParser = new BlackWhiteListXmlParser();
            List<UrlItem> list = bwlXmlParser.parse(is);
            for(UrlItem tmp : list){
                LogHelper.d(LogHelper.TAG_PROVIDER, "################## url: " + tmp.mUrl
                        + ",type: " + tmp.mType);
                mWhiteUrldataModelSelf.insertItem(this, Constants.TYPE_EXACTLY,tmp.mUrl,"host");
                mWhiteExclusiondataModel.insertItem(this, Constants.TYPE_EXACTLY,tmp.mUrl,"host");
//                mBlackUrldataModel.insertItem(this, Constants.TYPE_EXACTLY,tmp.mUrl,"host");
//                mBlackExclusiondataModel.insertItem(this, Constants.TYPE_EXACTLY,tmp.mUrl,"host");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void query() {
//        BlackWhiteListObject item = mWhiteUrldataModel.getItem(this, url);
//        LogHelper.d(LogHelper.TAG_PROVIDER, "################## item.url: " + item.getUrl()
//                + ",item.getItemType: " + item.getItemType());
    }

    private void update() {
//        mWhiteUrldataModel.updateItem(this, url,Constants.ITEM_BLACK);
//        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Update Succesfully!");
    }

    private void delete() {
//        mWhiteUrldataModel.deleteItem(this, "http://3g.renren.com");
//        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Delete Succesfully!");
    }
}
