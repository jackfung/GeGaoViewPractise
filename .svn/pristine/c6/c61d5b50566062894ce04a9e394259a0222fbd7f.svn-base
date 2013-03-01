package com.gaoge.view.practise.ExpandableListView.demo;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orange.test.textImage.R;

import java.util.ArrayList;
import java.util.List;

public class MyExpandableListAdapterBackup extends BaseExpandableListAdapter{
    private List<String> group;
    private List<String> child;
    
    private ImageView iv;
    private TextView tv;
    private ProgressBar pb;
    
    private TextView re;
    
    private int[] progress ;
    Context mContext;
    
    public MyExpandableListAdapterBackup(Context context){
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
    
    
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        if (null == convertView || !(convertView instanceof RelativeLayout)) {

            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.download_item_demo, null);
        }
        iv = (ImageView) convertView.findViewById(R.id.download_icon);
        tv = (TextView) convertView.findViewById(R.id.download_filename);
        tv.setText(child.get(childPosition).toString());
        pb = (ProgressBar) convertView.findViewById(R.id.download_progressBar);
        pb.setProgress(progress[childPosition]);
        re = (TextView) convertView.findViewById(R.id.download_received);
        re.setText("���ؽ�� :  " + progress[childPosition] + "%");
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
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        LinearLayout ll = new LinearLayout(mContext);
        TextView textView = getTextView();
        textView.setText(getGroup(groupPosition).toString());
        ll.addView(textView);
        return ll;
    }

    public TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 50);
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(40, 0, 0, 0);
        return textView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
