
package com.gaoge.view.practise.ExpandableListView.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ExpandableListView;

import com.orange.test.textImage.R;

import java.util.Timer;
import java.util.TimerTask;

public class Test_1Activity extends Activity {
    private Handler handler;
    MyExpandableListAdapter adapter;

    public Test_1Activity() {
        handler = new Handler();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_demo);
        adapter = new MyExpandableListAdapter(this);
        ExpandableListView expandListView = (ExpandableListView) findViewById(R.id.list);
        expandListView.setAdapter(adapter);
        expandListView.setOnCreateContextMenuListener(this);
        new Timer().schedule(new TimerTask() {
            public void run() {
                adapter.updateProgress();
                Runnable updater = new Runnable() {
                    public void run() {
                        ((MyExpandableListAdapter) adapter).notifyDataSetChanged();
                    }
                };
                handler.post(updater);
            };
        }, 0, 500);
    }


}
