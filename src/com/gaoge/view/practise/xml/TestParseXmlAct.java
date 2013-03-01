package com.gaoge.view.practise.xml;

import android.app.Activity;
import android.os.Bundle;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.practise.xml.BlackWhiteListXmlParser.UrlItem;
import com.gaoge.view.practise.xml.BlackWhiteListXmlParser2.SiteItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class TestParseXmlAct extends Activity {
    public static String LOG_TAG = "parser";
    public static String XML_FILE_NAME = "multi_select_white_list.xml";
    public static String XML_FILE_NAME_1= "array_cp.xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        BlackWhiteListXmlParser2 parser = new BlackWhiteListXmlParser2();
//        BlackWhiteListXmlParser parser1 = new BlackWhiteListXmlParser();
        try {
            List<SiteItem> list = parser.parse(getAssets().open(XML_FILE_NAME));
            if(null != list){
                for(SiteItem tmp : list){
                    LogHelper.d(LOG_TAG, "##### type: " + tmp.mType + ",url: " + tmp.mUrl);
                }
            }
            
//            List<UrlItem> list = parser1.parse(getAssets().open(XML_FILE_NAME_1));
//            for(UrlItem tmp : list){
//                LogHelper.d(LOG_TAG, "##### type: " + tmp.mType + ",url: " + tmp.mUrl);
//            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
