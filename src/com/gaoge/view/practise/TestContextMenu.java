
package com.gaoge.view.practise;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.webview.MyDownloadAdapter;
import com.orange.test.textImage.R;

public class TestContextMenu extends Activity {
    LinearLayout mContent;
    ExpandableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_context_menu);

        mContent = (LinearLayout) this.findViewById(R.id.content);
        mContent.setOnCreateContextMenuListener(this);

        mListView = (ExpandableListView) this.findViewById(R.id.listView);
        mListView.setAdapter(new MyDownloadAdapter(this, null, -1));
        mListView.setOnCreateContextMenuListener(this);

        // mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
        //
        // @Override
        // public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int
        // arg2, long arg3) {
        // LogHelper.d(LogHelper.TAG_DOWNLOAD,"#################### setOnItemLongClickListener ");
        // return false;
        // }
        // });
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//
//        if (v instanceof LinearLayout) {
//            LogHelper.d(LogHelper.TAG_DOWNLOAD,
//                    "#################### onCreateContextMenu LinearLayout");
//        } else if (v instanceof ListView) {
//            ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
//            // 为测试做了个title，下面用在child长按事件中
//            String title = "试试";
//            // type获取判定类型！
//            int type = ExpandableListView.getPackedPositionType(info.packedPosition);
//
//            if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {// 上面的type设定这里类型的判定！这里是child判定！
//                int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); // 在child判定里面，获取该child所属group！
//                int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); // 在child判定里面，获取该child所属position！
//
//                // 测试的toast！
//                Toast.makeText(TestContextMenu.this,
//                        title + ": Child " + childPos + " clicked in group " + groupPos,
//                        Toast.LENGTH_SHORT).show();
//
//            }
//
//            else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {// child做完，试试group效果！
//                int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); // 这里只需获取group位置，child不需搭理。
//                int addgroupPos = groupPos;
//                Toast.makeText(TestContextMenu.this, title + ": Group " + groupPos + " clicked",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.add("Open");
    	menu.add("Delete");
    }
    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView || !(convertView instanceof RelativeLayout)) {
                convertView = LayoutInflater.from(TestContextMenu.this).inflate(
                        R.layout.my_download_item, null);
                convertView.setPadding(convertView.getPaddingLeft() + 10,
                        convertView.getPaddingTop(),
                        convertView.getPaddingRight(),
                        convertView.getPaddingBottom());
            }
            return convertView;
        }

    }

}
