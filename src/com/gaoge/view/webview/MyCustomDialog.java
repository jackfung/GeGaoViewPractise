package com.gaoge.view.webview;



import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.orange.test.textImage.R;

public class MyCustomDialog extends Dialog {
    private TextView mTitle, mMessage;
    private Button mConfirm, mCancel;
    private FrameLayout mCustorm;
    private View mTopPanel, mContentPanel, mCustomPanel, mButtonPanel;
    private ListView mItems;

    public MyCustomDialog(Context context) {
        super(context, R.style.mydialog);
        setContentView(R.layout.customdialog);
        LayoutParams params = getWindow().getAttributes();
        params.height = LayoutParams.WRAP_CONTENT;
        params.width = context.getResources().getDimensionPixelSize(
                R.dimen.dialog_width);
        getWindow().setAttributes(params);
        setupView();
    }

    private void setupView() {
        mTitle = (TextView) findViewById(R.id.alertTitle);
        mConfirm = (Button) findViewById(R.id.confirm);
        mConfirm.setVisibility(View.GONE);
        mCancel = (Button) findViewById(R.id.cancle);
        mCancel.setVisibility(View.GONE);
        mCustorm = (FrameLayout) findViewById(R.id.custom);
        mMessage = (TextView) findViewById(R.id.message);
        mTopPanel = findViewById(R.id.topPanel);
        mTopPanel.setVisibility(View.GONE);
        mContentPanel = findViewById(R.id.contentPanel);
        mContentPanel.setVisibility(View.GONE);
        mCustomPanel = findViewById(R.id.customPanel);
        mCustomPanel.setVisibility(View.GONE);
        mButtonPanel = findViewById(R.id.buttonPanel);
        mButtonPanel.setVisibility(View.GONE);
        mItems = (ListView) findViewById(R.id.itemlist);
    }

    public void setMessage(CharSequence message) {
        mContentPanel.setVisibility(View.VISIBLE);
        mMessage.setVisibility(View.VISIBLE);
        mMessage.setText(message);
    }

    public void setMessage(int messageId) {
        mContentPanel.setVisibility(View.VISIBLE);
        mMessage.setVisibility(View.VISIBLE);
        mMessage.setText(messageId);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTopPanel.setVisibility(View.VISIBLE);
        mTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        mTopPanel.setVisibility(View.VISIBLE);
        mTitle.setText(titleId);
    }

    public void setConfirmButton(String text, View.OnClickListener listener) {
        mConfirm.setText(text);
        setConfirmButton(listener);
    }

    public void setConfirmButton(int textId, View.OnClickListener listener) {
        mConfirm.setText(textId);
        setConfirmButton(listener);
    }

    public void setConfirmButton(View.OnClickListener listener) {
        mButtonPanel.setVisibility(View.VISIBLE);
        mConfirm.setVisibility(View.VISIBLE);
        findViewById(R.id.button_divider)
                .setVisibility(
                        (mConfirm.getVisibility() == View.VISIBLE && mCancel.getVisibility() == View.VISIBLE) ? View.VISIBLE
                                : View.GONE);
        if (listener != null) {
            mConfirm.setOnClickListener(listener);
        } else {
            mConfirm.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    MyCustomDialog.this.dismiss();
                }
            });
        }
    }

    public void setCustomView(View view) {
        mCustomPanel.setVisibility(View.VISIBLE);
        mCustorm.addView(view);
    }

    public void setCustomView(int layoutId) {
        mCustomPanel.setVisibility(View.VISIBLE);
        mCustorm.addView(LayoutInflater.from(getContext()).inflate(layoutId,
                null));
    }

    public void setItems(ListAdapter adapter, OnItemClickListener listener) {
        mContentPanel.setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView).setVisibility(View.GONE);
        if (adapter != null) {
            mItems.setAdapter(adapter);
        }
        mItems.setOnItemClickListener(listener);
    }

    public void setItems(String[] items, OnItemClickListener listener) {
        mContentPanel.setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView).setVisibility(View.GONE);
        if (items != null) {
            ListAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.dialog_list_item,
                    items);
            mItems.setAdapter(adapter);
        }
        mItems.setOnItemClickListener(listener);
    }

    public void setCancleButton(String text, View.OnClickListener listener) {
        mCancel.setText(text);
        setCancleButton(listener);
    }

    public void setCancleButton(int textId, View.OnClickListener listener) {
        mCancel.setText(textId);
        setCancleButton(listener);
    }

    public void setCancleButton(View.OnClickListener listener) {
        mButtonPanel.setVisibility(View.VISIBLE);
        mCancel.setVisibility(View.VISIBLE);
        findViewById(R.id.button_divider)
                .setVisibility(
                        (mConfirm.getVisibility() == View.VISIBLE && mCancel.getVisibility() == View.VISIBLE) ? View.VISIBLE
                                : View.GONE);
        if (listener != null) {
            mCancel.setOnClickListener(listener);
        } else {
            mCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    MyCustomDialog.this.dismiss();
                }
            });
        }
    }
    public void setIcon(int resouceId){
        mTopPanel.setVisibility(View.VISIBLE);
        ((ImageView)findViewById(R.id.icon)).setImageResource(resouceId);
    }
}
