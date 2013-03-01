package com.gaoge.view.webview;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;




public class MyDownloadPage extends ExpandableListActivity {
    
    private Context mContext;
    private ExpandableListView mListView;
    private MyDownloadAdapter mDownloadAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mContext = getApplicationContext();
        setContentView(R.layout.my_downloads_page);
        
        initExpandableListView();
        
    }

    private void initExpandableListView() {
        mListView = (ExpandableListView) findViewById(android.R.id.list);
        FrameLayout.LayoutParams params= new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL);
        params.setMargins(getResources().getDimensionPixelSize(R.dimen.list_view_padding_left), 0, getResources().getDimensionPixelSize(R.dimen.reading_list_padding_hori), 0);
        mListView.setLayoutParams(params);
        mListView.setGroupIndicator(getResources().getDrawable(R.drawable.expander_group));
        mListView.setDivider(null);
        mListView.setChildDivider(null);
        mListView.setSelector(R.drawable.list_selector_transparent);
        mListView.setCacheColorHint(0);
        mListView.setEmptyView(findViewById(R.id.empty));
        mListView.setVerticalScrollBarEnabled(false);
        
        
        mDownloadAdapter = new MyDownloadAdapter(this,
                null, -1);
        
        setListAdapter(mDownloadAdapter);
        
        mListView.setOnCreateContextMenuListener(this);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        LogHelper.d(LogHelper.TAG_DOWNLOAD,"#################### onCreateContextMenu");
        menu.add("Open");
        menu.add("Delete");
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
    
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
            int childPosition, long id) {
        LogHelper.d(LogHelper.TAG_DOWNLOAD,"#################### onChildClick");
        return super.onChildClick(parent, v, groupPosition, childPosition, id);
    }

}
