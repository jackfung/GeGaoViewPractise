
package com.gaoge.view.practise.xml;

import android.util.Xml;

import com.gaoge.view.practise.provider.Constants;
import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.practise.xml.MyStackOverflowXmlParser.Entry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BlackWhiteListXmlParser {

    private static final String ns = null;

    private static final String START_TAG_RESOURCE = "resources";
    private static final String START_TAG_STRING_ARRAY = "string-array";
    private static final String START_TAG_ITEM = "item";

    private static final String TYPE_BLACKLIST = "blacklist";
    private static final String TYPE_WHITELIST = "whitelist";

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readResources(parser);
        } finally {
            in.close();
        }
    }

    private List readResources(XmlPullParser parser) {
        List<UrlItem> urlList = new ArrayList<UrlItem>();
        try{
            parser.require(XmlPullParser.START_TAG, ns, START_TAG_RESOURCE);
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals(START_TAG_STRING_ARRAY)) {
                    urlList.addAll(readStringArray(parser));
                } else {
                    skip(parser);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       
        return urlList;
    }

    int mCount = 0;

    private List<UrlItem> readStringArray(XmlPullParser parser){
        boolean addToList = true;
        List<UrlItem> urlList = new ArrayList<UrlItem>();
        try{
            parser.require(XmlPullParser.START_TAG, ns, START_TAG_STRING_ARRAY);
            String value = parser.getAttributeValue(0);
            int urlType = Constants.ITEM_UNKNOWN;

            LogHelper.d(LogHelper.TAG_PROVIDER, "SSSSSSSSSSSSSS readStringArray mCount: "
                    + (mCount++) + ",value: " + value);
           
            if (value.equals(TYPE_BLACKLIST)) {
                urlType = Constants.ITEM_BLACK;
                addToList = true;
            } else if (value.equals(TYPE_WHITELIST)) {
                urlType = Constants.ITEM_WHITE;
                addToList = true;
            }
            else{
                addToList = false;
            }
            
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals(START_TAG_ITEM)) {
                    UrlItem item =new UrlItem(readItem(parser), urlType);
                    if(addToList){
                        urlList.add(item);
                    }
                } else {
                    skip(parser);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return urlList;
    }

    // Processes title tags in the feed.
    private String readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, START_TAG_ITEM);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, START_TAG_ITEM);
        return title;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public static class UrlItem {
        public final String mUrl;
        public int mType;

        private UrlItem(String url, int type) {
            this.mUrl = url;
            this.mType = type;
        }
    }

}
