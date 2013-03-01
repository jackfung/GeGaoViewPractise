package com.gaoge.view.practise.ExpandableListView.cygnus;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaoge.view.webview.Constants;
import com.orange.test.textImage.R;

import java.util.ArrayList;
import java.util.List;

public class MyDateSortedExpandableListAdapter3   extends BaseExpandableListAdapter{

    private Context mContext;
    
    private List<String> group;
    private List<String> child;
    
    private int[] progress ;
    
    ProgressBar pb;
    public MyDateSortedExpandableListAdapter3(Context context, Cursor cursor,
            int dateIndex) {
        this.mContext = context;
        group = new ArrayList<String>();
        child = new ArrayList<String>();
        
        add(group, new String[] {
                "group1", "group2"
        });
        add(child, new String[] {
                "child1", "child2", "child3"
        });
        
        progress = new int[child.size()];
    }
    
    public void add(List<String> list, String[] c) {
        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }

    }
    
    public void updateProgress(){
        for (int i = 0; i < progress.length; i++) {
            progress[i] = progress[i] + i + 1;
        }
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
        return child.get(childPosition);
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
        }
        pb = (ProgressBar) convertView.findViewById(R.id.download_progress);
        pb.setProgress(progress[childPosition]);
        if (progress[childPosition] < 100) {
            if (getGroup(groupPosition).toString() == "group1") {
                pb.setVisibility(View.VISIBLE);
            }
            else {
                pb.setVisibility(View.GONE);
            }
        }
        else {
            if (getGroup(groupPosition).toString() == "group2") {
                pb.setVisibility(View.GONE);
            }
            else {
                pb.setVisibility(View.GONE);
            }
        }
        convertView.setTag(R.id.cancel, childPosition);
        convertView.setTag(R.id.guidePages, groupPosition);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return group.size();
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
        item.setTag(R.id.cancel, Constants.NOT_CHILD);
        item.setTag(R.id.guidePages, groupPosition);
        return item;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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

    /**
     * 
    This two method can't be overrided because which may cause the child view can't refresh immediately
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
     */ 
}
