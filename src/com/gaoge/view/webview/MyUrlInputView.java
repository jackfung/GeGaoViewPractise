package com.gaoge.view.webview;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.webview.MySuggestionsAdapter.CompletionListener;
import com.gaoge.view.webview.interfaces.MyUiController;
import com.orange.test.textImage.R;

public class MyUrlInputView extends AutoCompleteTextView implements OnEditorActionListener,
        OnItemClickListener, TextWatcher ,CompletionListener {

    private Context mContext;
    private int mState;
    private InputMethodManager mInputManager;
    private boolean mNeedsUpdate;
    private MySuggestionsAdapter mAdapter;
    
    static final String TYPED = "browser-type";
    static final String SUGGESTED = "browser-suggest";
    static final String VOICE = "voice-search";
    
    public MyUrlInputView(Context context) {
        super(context);
    }
    public MyUrlInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
//        TypedArray a = context.obtainStyledAttributes(
//                attrs, R.styleable.PopupWindow,
//                R.attr.autoCompleteTextViewStyle, 0);
//
//        Drawable popupbg = a.getDrawable(R.styleable.PopupWindow_popupBackground);
//        a.recycle();
//        mPopupPadding = new Rect();
//
//        //popupbg.getPadding(mPopupPadding);
//        mPopupPadding.set(0, 0, 0, 0);
        init(context);
    }

    private void init(Context ctx) {
        mInputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        setOnEditorActionListener(this);
        mAdapter = new MySuggestionsAdapter(ctx, this);
        setAdapter(mAdapter);
        setSelectAllOnFocus(true);
//        onConfigurationChanged(ctx.getResources().getConfiguration());
        setThreshold(1);
//        setOnItemClickListener(this);
        mNeedsUpdate = false;
        addTextChangedListener(this);
//
//        mState = StateListener.STATE_NORMAL;
    }

    public MyUrlInputView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.autoCompleteTextViewStyle);
    }

    @Override
    public void afterTextChanged(Editable arg0) {

    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        finishInput(getText().toString(), null, TYPED);
        return true;
    }
    
    private void finishInput(String url, String extra, String source) {
        LogHelper.d(LogHelper.URLINPUTVIEW, "@@@@@@@@@@@@@@@@ finishInput");
        mInputManager.hideSoftInputFromWindow(getWindowToken(), 0);
        mListener.onAction(url, extra, source);
    }
    
    void setController(MyUiController controller) {
    }
    
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        LogHelper.d(LogHelper.TAG_KEYCODE, "########### onTextChanged()");
    }
    
    interface UrlInputListener {

        public void onDismiss();

        public void onAction(String text, String extra, String source);

        public void onCopySuggestion(String text);

        public void onFaviconClicked();
        
        public void onComboButtonClicked();
    }
    
    private UrlInputListener   mListener;
    
    public void setUrlInputListener(UrlInputListener listener) {
        mListener = listener;
    }
    
    static interface StateListener {
        static final int STATE_NORMAL = 0;
        static final int STATE_HIGHLIGHTED = 1;
        static final int STATE_EDITED = 2;

        public void onStateChanged(int state);
    }
    
    
    private StateListener mStateListener;
    
    public void setStateListener(StateListener listener) {
        mStateListener = listener;
        // update listener
        changeState(mState);
    }
    private void changeState(int newState) {
        mState = newState;
        if (mStateListener != null) {
            mStateListener.onStateChanged(mState);
        }
    }
    
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        LogHelper.d(LogHelper.TAG_KEYCODE, "########### onFocusChanged");
        int state = -1;
        if (focused) {
            if (hasSelection()) {
                state = StateListener.STATE_HIGHLIGHTED;
            } else {
                state = StateListener.STATE_EDITED;
            }
        } else {
            // reset the selection state
            state = StateListener.STATE_NORMAL;
        }
        final int s = state;
        post(new Runnable() {
            public void run() {
                changeState(s);
            }
        });
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogHelper.d(LogHelper.TAG_KEYCODE,"vvvvvvvvvvvvvvvv MyUrlInputView onKeyDown");
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	 LogHelper.d(LogHelper.TAG_KEYCODE,"vvvvvvvvvvvvvvvv MyUrlInputView onKeyUp");
    	return super.onKeyUp(keyCode, event);
    }
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
    	LogHelper.d(LogHelper.TAG_KEYCODE,"vvvvvvvvvvvvvvvv MyUrlInputView dispatchKeyEventPreIme");
    	return super.dispatchKeyEventPreIme(event);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	LogHelper.d(LogHelper.TAG_KEYCODE,"vvvvvvvvvvvvvvvv MyUrlInputView dispatchKeyEvent");
    	return super.dispatchKeyEvent(event);
    }
    @Override
    public void onSearch(String search) {
        mListener.onCopySuggestion(search);
    }
    @Override
    public void onSelect(String url, int type, String extra) {
        finishInput(url, extra, (type == MySuggestionsAdapter.TYPE_VOICE_SEARCH)
                ? VOICE : SUGGESTED);
    }
    
    public void hideIME() {
        mInputManager.hideSoftInputFromWindow(getWindowToken(), 0);
    }

}
