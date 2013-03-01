
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

public class BlackWhiteListXmlParser2 {

    private static final String ns = null;

    private static final String START_TAG_LIST = "list";
    private static final String START_TAG_SITE = "site";
    private static final String START_TAG_EXCLUDE = "exclude";

    private static final int TYPE_VALUE_INDEX = 0;
    private static final int URL_VALUE_INDEX = 1;

    private static final String TYPE_BLACKLIST = "blacklist";
    private static final String TYPE_WHITELIST = "whitelist";

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            ArrayList<SiteItem> list = readList(parser);
//            ArrayList<SiteItem> excludeList = readExclude(parser);
//            list.addAll(excludeList);
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            in.close();
        }
        return null;
    }

    private ArrayList<SiteItem> readList(XmlPullParser parser) {
        ArrayList<SiteItem> urlList = new ArrayList<SiteItem>();
        try {
            parser.require(XmlPullParser.START_TAG, ns, START_TAG_LIST);
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals(START_TAG_SITE)) {
                    urlList.add(readSite(parser));
                } else {
                    skip(parser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlList;
    }
    
    private ArrayList<SiteItem> readExclude(XmlPullParser parser) {
        ArrayList<SiteItem> urlList = new ArrayList<SiteItem>();
        try {
            parser.require(XmlPullParser.START_TAG, ns, START_TAG_EXCLUDE);
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equals(START_TAG_SITE)) {
                    urlList.add(readSite(parser));
                } else {
                    skip(parser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlList;
    }

    int mCount = 0;
    private SiteItem readSite(XmlPullParser parser) {
        try {
            String typeValue = parser.getAttributeValue(TYPE_VALUE_INDEX);
            String urlValue = parser.getAttributeValue(URL_VALUE_INDEX);
            parser.nextTag();
            return new SiteItem(urlValue, typeValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // For the tags title and summary, extracts their text values.
    // private String readText(XmlPullParser parser) throws IOException,
    // XmlPullParserException {
    // String result = "";
    // if (parser.next() == XmlPullParser.TEXT) {
    // result = parser.getText();
    // parser.nextTag();
    // }
    // return result;
    // }

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

    public static class SiteItem {
        public final String mUrl;
        public String mType;

        private SiteItem(String url, String type) {
            this.mUrl = url;
            this.mType = type;
        }
    }

}
