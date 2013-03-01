package com.gaoge.view.webview;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.orange.test.textImage.R;



public class MyDownloadAdapter extends MyDateSortedExpandableListAdapter3 {

    public MyDownloadAdapter(Context context, Cursor cursor, int dateIndex) {
        super(context, cursor, dateIndex);
    }
    
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
//            View convertView, ViewGroup parent) {
//        Context context = getContext();
//        
//        if (null == convertView || !(convertView instanceof RelativeLayout)) {
//            convertView = LayoutInflater.from(context).inflate(
//                    R.layout.my_download_item, null);
//            convertView.setPadding(convertView.getPaddingLeft() + 10,
//                    convertView.getPaddingTop(),
//                    convertView.getPaddingRight(),
//                    convertView.getPaddingBottom());
//        }
//        return convertView;
//    }

}
