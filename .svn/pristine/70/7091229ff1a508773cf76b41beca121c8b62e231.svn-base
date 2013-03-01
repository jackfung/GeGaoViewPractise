
package com.gaoge.view.webview;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyFindDialog extends MyWebDialog implements TextWatcher {

    private EditText mEditText;
    private View mNextButton;
    private View mPrevButton;
    private View mMatchesView;
    private TextView mMatches;
    private int mNumberOfMatches;
    private boolean mMatchesFound;
    
    

    public MyFindDialog(MyController controller) {
        super(controller);
        LayoutInflater factory = LayoutInflater.from(controller.getContext());
        factory.inflate(R.layout.my_browser_find, this);

        registCancelEvent();

        mEditText = (EditText) findViewById(R.id.edit);
        mEditText.addTextChangedListener(this);

        View button = findViewById(R.id.next);
        button.setOnClickListener(mFindListener);
        mNextButton = button;

        button = findViewById(R.id.previous);
        button.setOnClickListener(mFindPreviousListener);
        mPrevButton = button;

        mMatches = (TextView) findViewById(R.id.matches);
        mMatchesView = findViewById(R.id.matches_view);
        disableButtons();

    }

    @Override
    protected void show() {
        super.show();
        mMatchesView.setVisibility(View.INVISIBLE);
        mMatchesFound = false;
        mMatches.setText("0");
        mEditText.clearFocus();
        mEditText.requestFocus();
        Spannable span = (Spannable) mEditText.getText();
        int length = span.length();
        Selection.setSelection(span, 0, length);
        span.setSpan(this, 0, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        disableButtons();
        InputMethodManager imm = (InputMethodManager)
                mController.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, 0);
    }

    private void disableButtons() {
        mPrevButton.setEnabled(false);
        mNextButton.setEnabled(false);
        mPrevButton.setFocusable(false);
        mNextButton.setFocusable(false);
    }

    private void findNextKeyWord() {
        if (mWebView == null) {
            throw new AssertionError("No WebView for FindDialog::findNext");
        }
        mWebView.findNext(true);
        updateMatchesString();
        hideSoftInput();
    }

    private View.OnClickListener mFindListener = new View.OnClickListener() {
        public void onClick(View v) {
            findNextKeyWord();
        }
    };

    private View.OnClickListener mFindPreviousListener =
            new View.OnClickListener() {
                public void onClick(View v) {
                    if (mWebView == null) {
                        throw new AssertionError("No WebView for FindDialog::onClick");
                    }
                    findPreKeyWord();
                }

                private void findPreKeyWord() {
                    mWebView.findNext(false);
                    updateMatchesString();
                    hideSoftInput();
                }
            };

    private void updateMatchesString() {
        String matches_str = mNumberOfMatches + " "
                + mController.getContext().getResources().getString(R.string.matches_string);

        mMatches.setText(matches_str);
    }

    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager)
                mController.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mWebView.getWindowToken(), 0);
        
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void dismiss() {
        super.dismiss();
        Method m;
//        try {
////            m = WebView.class.getDeclaredMethod("notifyFindDialogDismissed");
////            m.setAccessible(true);
////            m.invoke(mWebView);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        hideSoftInput();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        findAll();
    }

    private void findAll() {

        if (mWebView == null) {
            throw new AssertionError(
                    "No WebView for FindDialog::findAll");
        }
        CharSequence find = mEditText.getText();
        if (0 == find.length()) {
            disableButtons();
            mWebView.clearMatches();
            mMatchesView.setVisibility(View.INVISIBLE);
        } else {
            mMatchesView.setVisibility(View.VISIBLE);
            int found = mWebView.findAll(find.toString());
            mMatchesFound = true;
            setMatchesFound(found);
            if (found < 2) {
                disableButtons();
                if (found == 0) {
                    // Cannot use getQuantityString, which ignores the "zero"
                    // quantity.
                    // FIXME: is this fix is beyond the scope
                    // of adding touch selection to gingerbread?
                    // mMatches.setText(mBrowserActivity.getResources().getString(
                    // R.string.no_matches));
                }
            } else {
                mPrevButton.setFocusable(true);
                mNextButton.setFocusable(true);
                mPrevButton.setEnabled(true);
                mNextButton.setEnabled(true);
            }
        }
    }

    private void setMatchesFound(int found) {
        mNumberOfMatches = found;
        updateMatchesString();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        LogHelper.d(LogHelper.TAG_FIND, "############## dispatchKeyEvent");
        int keyCode = event.getKeyCode();
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (keyCode == KeyEvent.KEYCODE_ENTER
                    && mEditText.hasFocus()) {
                if (mMatchesFound) {
                    findNextKeyWord();
                } else {
                    findAll();
                    // Set the selection to the end.
                    Spannable span = (Spannable) mEditText.getText();
                    Selection.setSelection(span, span.length());
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        LogHelper.d(LogHelper.TAG_FIND, "############## dispatchKeyEventPreIme");
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            KeyEvent.DispatcherState state = getKeyDispatcherState();
            if (state != null) {
                int action = event.getAction();
                if (KeyEvent.ACTION_DOWN == action
                        && event.getRepeatCount() == 0) {
                    state.startTracking(event, this);
                    return true;
                } else if (KeyEvent.ACTION_UP == action
                        && !event.isCanceled() && state.isTracking(event)) {
                    mController.closeDialogs();
                    return true;
                }
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }

    public EditText getEditText() {
        return mEditText;
    }
    
}
