package com.gaoge.view.webview;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.test.textImage.R;

public class MyDateSortedExpandableListAdapter3 implements ExpandableListAdapter {

    private Context mContext;
    
    private String[][] child = {   
            { "11111", "22222"},   
            { "11111", "22222" },   
            };   
    private String group[] = { "group1" };  
    
    
    
    public MyDateSortedExpandableListAdapter3(Context context, Cursor cursor,
            int dateIndex) {
        this.mContext = context;
    }
    
    Context getContext() {
        return mContext;
    }
    
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child[groupPosition][childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
        if (null == convertView || !(convertView instanceof RelativeLayout)) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.my_download_item, null);
            convertView.setPadding(convertView.getPaddingLeft() + 10,
                    convertView.getPaddingTop(),
                    convertView.getPaddingRight(),
                    convertView.getPaddingBottom());
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child[groupPosition].length;
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        View item;
        if (null == convertView || !(convertView instanceof TextView)) {
            LayoutInflater factory = LayoutInflater.from(mContext);
            item = factory.inflate(R.layout.history_header, null);
        } else {
            item =  convertView;
        }
        String label = "yy-mm-dd";
        ((TextView)item.findViewById(R.id.textview)).setText(label);
        return item;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

}
