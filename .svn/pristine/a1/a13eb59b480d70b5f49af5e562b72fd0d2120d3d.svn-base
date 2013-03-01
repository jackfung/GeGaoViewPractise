package com.gaoge.view.webview;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

public class MyDateSortedExpandableListAdapter2 implements ExpandableListAdapter {

    private Context mContext;
    
    private String[][] child = {   
            { "11111", "22222", "33333", "44444", "55555" },   
            { "11111", "22222", "33333", "44444", "55555" },   
            { "11111", "22222", "33333", "44444", "55555" },   
            { "11111", "22222", "33333", "44444", "55555" } };   
    private String group[] = { "group1", "group2", "group3", "group4", };  
    
    
    
    public MyDateSortedExpandableListAdapter2(Context context, Cursor cursor,
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
        TextView mTextView = new TextView(mContext);   
        mTextView.setText(child[groupPosition][childPosition]);   
        mTextView.setTextColor(Color.BLUE);   
        mTextView.setGravity(Gravity.CENTER);   
        mTextView.setTextSize(15);   
           
        return mTextView;  
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
        TextView mTextView = new TextView(mContext);   
        mTextView.setText(group[groupPosition]);   
        mTextView.setTextColor(Color.RED);   
        mTextView.setPadding(40, 0, 0, 0);   
        mTextView.setTextSize(20);   
           
        return mTextView;  
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
