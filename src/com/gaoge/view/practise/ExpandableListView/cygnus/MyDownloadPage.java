
package com.gaoge.view.practise.ExpandableListView.cygnus;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.webview.Constants;
import com.gaoge.view.webview.MyCustomDialog;
import com.orange.test.textImage.R;

import java.util.Timer;
import java.util.TimerTask;

public class MyDownloadPage extends ExpandableListActivity {

    private Context mContext;
    private ExpandableListView mListView;
    private MyDownloadAdapter mDownloadAdapter;
    private Handler mHandler;
    private Button mBtn;
    private MyCustomDialog mDialog;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        setContentView(R.layout.my_downloads_page);
        mHandler = new Handler();
        initExpandableListView();
        updateProgressValue();
    }
    

    int mProgressValue;
    private void updateProgressValue() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                mDownloadAdapter.updateProgress();
                mProgressValue += 1;
                Runnable updater = new Runnable() {
                    public void run() {
                        ((BaseExpandableListAdapter) mDownloadAdapter).notifyDataSetChanged();
                    }
                };
                mHandler.post(updater);
            };
        }, 0, 500);
    }

    private void initExpandableListView() {
        mListView = (ExpandableListView) findViewById(android.R.id.list);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,
//                LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL);
//        params.setMargins(getResources().getDimensionPixelSize(R.dimen.list_view_padding_left), 0,
//                getResources().getDimensionPixelSize(R.dimen.reading_list_padding_hori), 0);
//        mListView.setLayoutParams(params);
//        mListView.setGroupIndicator(getResources().getDrawable(R.drawable.expander_group));
//        mListView.setDivider(null);
//        mListView.setChildDivider(null);
//        mListView.setSelector(R.drawable.list_selector_transparent);
//        mListView.setCacheColorHint(0);
//        mListView.setEmptyView(findViewById(R.id.empty));
//        mListView.setVerticalScrollBarEnabled(false);

        mDownloadAdapter = new MyDownloadAdapter(this,
                null, -1, mHandler);

        setListAdapter(mDownloadAdapter);
//        mListView.setOnCreateContextMenuListener(this);
        mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
                
                int childPos = (Integer)view.getTag(R.id.cancel);
                int groupPos = (Integer)view.getTag(R.id.guidePages);
                if(Constants.NOT_CHILD == childPos ){
                	LogHelper.d(LogHelper.TAG_DOWNLOAD, "group item click,group pos : " + groupPos);
                }else{
                	LogHelper.d(LogHelper.TAG_DOWNLOAD, "child item click,group pos : " + groupPos + ",childPos: " + childPos);
                }
                return false;
            }
        });
    }
    
    @Override 
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);menu.setHeaderTitle("menu header");
        
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.download, menu); 
        
        ExpandableListView.ExpandableListContextMenuInfo expandMenuInfo= (ExpandableListView.ExpandableListContextMenuInfo)menuInfo;
        long pos = expandMenuInfo.packedPosition;
        long id = expandMenuInfo.id;
        View view = expandMenuInfo.targetView;
    }
    
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        LogHelper.d(LogHelper.TAG_DOWNLOAD, "#################### onCreateContextMenu");
//
//        if(mDialog!=null&&mDialog.isShowing()){
//            mDialog.dismiss();
//            return;
//        }
//        String[] menus;
//        
//        mDialog = new MyCustomDialog(this);
//        if (mProgressValue > 100) {
//            menus = new String[] {
//                    getString(R.string.download_menu_open),
//                    getString(R.string.download_delete_file)
//            };
//            mDialog.setItems(menus, new OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                    mDialog.dismiss();
//                    switch (arg2) {
//                        case 0:
////                            hideCompletedDownload();
////                            openOrDeleteCurrentDownload(false);
//                            break;
//                        case 1:
//                            new AlertDialog.Builder(MyDownloadPage.this)
//                                    .setTitle(R.string.download_delete_file)
//                                    .setIcon(android.R.drawable.ic_dialog_alert)
//                                    .setMessage("title")
//                                    .setNegativeButton(R.string.cancel, null)
//                                    .setPositiveButton(R.string.ok,
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog,
//                                                        int whichButton) {
////                                                    openOrDeleteCurrentDownload(true);
//                                                }
//                                            })
//                                    .show();
//                            break;
//
//                    }
//                }
//            });
//        }else {
//            menus = new String[] {
//                    getString(R.string.download_menu_cancel),
//                    getString(R.string.download_menu_resume)
//            };
//            mDialog.setItems(menus, new OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                    mDialog.dismiss();
//                    switch (arg2) {
//                        case 0:
////                            clearFromDownloads(mDownloadCursor.getLong(mIdColumnId));
//                            break;
//
//                    }
//                }
//            });
//        }
//        mDialog.show();
//        
//    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
            int childPosition, long id) {
        LogHelper.d(LogHelper.TAG_DOWNLOAD, "#################### onChildClick");
        return super.onChildClick(parent, v, groupPosition, childPosition, id);
    }

}
