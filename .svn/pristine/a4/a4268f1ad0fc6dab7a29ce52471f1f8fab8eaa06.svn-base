package com.gaoge.view.webview;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoge.view.webview.search.MySearchEngine;
import com.orange.test.textImage.R;

import dep.android.provider.BrowserContract;

import java.util.ArrayList;
import java.util.List;


public class MySuggestionsAdapter extends BaseAdapter implements Filterable,
OnClickListener{

    public static final int TYPE_BOOKMARK = 0;
    public static final int TYPE_HISTORY = 1;
    public static final int TYPE_SUGGEST_URL = 2;
    public static final int TYPE_SEARCH = 3;
    public static final int TYPE_SUGGEST = 4;
    public static final int TYPE_VOICE_SEARCH = 5;
    
    Context mContext;
    SuggestFilter mFilter;
    List<String> mVoiceResults;
    MyBrowserSettings mSettings;
    boolean mIncognitoMode;
    List<SuggestItem> mSuggestResults, mFilterResults;
    SuggestionResults mMixedResults;
    boolean mLandscapeMode;
    final int mLinesPortrait;
    final int mLinesLandscape;
    final Object mResultsLock = new Object();
    List<CursorSource> mSources;
    final CompletionListener mListener;
    
    interface CompletionListener {

        public void onSearch(String txt);

        public void onSelect(String txt, int type, String extraData);

    }
    
    private static final String COMBINED_SELECTION =
            "(url LIKE ? OR url LIKE ? OR url LIKE ? OR url LIKE ? OR title LIKE ?)";
    
    public MySuggestionsAdapter(Context ctx, CompletionListener listener) {
        mContext = ctx;
        mFilter = new SuggestFilter();
        mSettings = MyBrowserSettings.getInstance(ctx);
        mLinesPortrait = mContext.getResources().
                getInteger(R.integer.max_suggest_lines_portrait);
        mLinesLandscape = mContext.getResources().
                getInteger(R.integer.max_suggest_lines_landscape);
        mListener = listener;
        addSource(new CombinedCursor());
    }
    
    public void addSource(CursorSource c) {
        if (mSources == null) {
            mSources = new ArrayList<CursorSource>(5);
        }
        mSources.add(c);
    }
    
    public void clearCache() {
        mFilterResults = null;
        mSuggestResults = null;
        //TODO:Fix null pointer crash
        //notifyDataSetInvalidated();
    }

    public void setIncognitoMode(boolean incognito) {
        mIncognitoMode = incognito;
        clearCache();
    }
    
    public class SuggestItem {
        public String title;
        public String url;
        public int type;
        public String extra;

        public SuggestItem(String text, String u, int t) {
            title = text;
            url = u;
            type = t;
        }

    }
    
    abstract class CursorSource {

        Cursor mCursor;

        boolean moveToNext() {
            return mCursor.moveToNext();
        }

        public abstract void runQuery(CharSequence constraint);

        public abstract SuggestItem getItem();

        public int getCount() {
            return (mCursor != null) ? mCursor.getCount() : 0;
        }

        public void close() {
            if (mCursor != null) {
                mCursor.close();
            }
        }
    }
    
    class SuggestCursor extends CursorSource {

        @Override
        public void runQuery(CharSequence constraint) {
            if (mCursor != null) {
                mCursor.close();
            }
            MySearchEngine searchEngine = mSettings.getSearchEngine();
            if (!TextUtils.isEmpty(constraint)) {
                if (searchEngine != null && searchEngine.supportsSuggestions()) {
                    mCursor = searchEngine.getSuggestions(mContext, constraint.toString());
                    if (mCursor != null) {
                        mCursor.moveToFirst();
                    }
                }
            } else {
                if (searchEngine.wantsEmptyQuery()) {
                    mCursor = searchEngine.getSuggestions(mContext, "");
                }
                mCursor = null;
            }
        }

        @Override
        public SuggestItem getItem() {
            if (mCursor != null) {
                String title = mCursor.getString(
                        mCursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
                String text2 = mCursor.getString(
                        mCursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2));
                String url = mCursor.getString(
                        mCursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2_URL));
                String uri = mCursor.getString(
                        mCursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_INTENT_DATA));
                int type = (TextUtils.isEmpty(url)) ? TYPE_SUGGEST : TYPE_SUGGEST_URL;
                SuggestItem item = new SuggestItem(title, url, type);
                item.extra = mCursor.getString(
                        mCursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA));
                return item;
            }
            return null;
        }
        
    }
    class CombinedCursor extends CursorSource {

        @Override
        public void runQuery(CharSequence constraint) {
//            if (mCursor != null) {
//                mCursor.close();
//            }
//            String like = constraint + "%";
//            String[] args = null;
//            String selection = null;
//            if (like.startsWith("http") || like.startsWith("file")) {
//                args = new String[1];
//                args[0] = like;
//                selection = "url LIKE ?";
//            } else {
//                args = new String[5];
//                args[0] = "http://" + like;
//                args[1] = "http://www." + like;
//                args[2] = "https://" + like;
//                args[3] = "https://www." + like;
//                // To match against titles.
//                args[4] = like;
//                selection = COMBINED_SELECTION;
//            }
//            Uri.Builder ub = OmniboxSuggestions.CONTENT_URI.buildUpon();
//            ub.appendQueryParameter(BrowserContract.PARAM_LIMIT,
//                    Integer.toString(Math.max(mLinesLandscape, mLinesPortrait)));
//            mCursor =
//                    mContext.getContentResolver().query(ub.build(), COMBINED_PROJECTION,
//                            selection, (constraint != null) ? args : null, null);
//            if (mCursor != null) {
//                mCursor.moveToFirst();
//            }
        }

        @Override
        public SuggestItem getItem() {
            return null;
        }
        
    }
    
    
    class SuggestionResults {

        ArrayList<SuggestItem> items;
        // count per type
        int[] counts;

        SuggestionResults() {
            items = new ArrayList<SuggestItem>(24);
            // n of types:
            counts = new int[5];
        }

        int getTypeCount(int type) {
            return counts[type];
        }

        void addResult(SuggestItem item) {
            int ix = 0;
            while ((ix < items.size()) && (item.type >= items.get(ix).type))
                ix++;
            items.add(ix, item);
            counts[item.type]++;
        }

        int getLineCount() {
            return Math.min((mLandscapeMode ? mLinesLandscape : mLinesPortrait), items.size());
        }

        @Override
        public String toString() {
            if (items == null) return null;
            if (items.size() == 0) return "[]";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                SuggestItem item = items.get(i);
                sb.append(item.type + ": " + item.title);
                if (i < items.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }
    
    class SlowFilterTask extends AsyncTask<CharSequence, Void, List<SuggestItem>> {

        @Override
        protected List<SuggestItem> doInBackground(CharSequence... params) {
            SuggestCursor cursor = new SuggestCursor();
            cursor.runQuery(params[0]);
            List<SuggestItem> results = new ArrayList<SuggestItem>();
            int count = cursor.getCount();
            for (int i = 0; i < count; i++) {
                results.add(cursor.getItem());
                cursor.moveToNext();
            }
            cursor.close();
            return results;
        }

        @Override
        protected void onPostExecute(List<SuggestItem> items) {
            mSuggestResults = items;
            mMixedResults = buildSuggestionResults();
            notifyDataSetChanged();
        }
    }
    
    SuggestionResults buildSuggestionResults() {
        SuggestionResults mixed = new SuggestionResults();
        List<SuggestItem> filter, suggest;
        synchronized (mResultsLock) {
            filter = mFilterResults;
            suggest = mSuggestResults;
        }
        if (filter != null) {
            for (SuggestItem item : filter) {
                mixed.addResult(item);
            }
        }
        if (suggest != null) {
            for (SuggestItem item : suggest) {
                mixed.addResult(item);
            }
        }
        return mixed;
    }
    
    
    class SuggestFilter extends Filter {
        
        void startSuggestionsAsync(final CharSequence constraint) {
            if (!mIncognitoMode) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    
                    @Override
                    public void run() {
                        new SlowFilterTask().execute(constraint);
                    }
                });
            }
        }
        

        private boolean shouldProcessEmptyQuery() {
            final MySearchEngine searchEngine = mSettings.getSearchEngine();
            return searchEngine.wantsEmptyQuery();
        }
        
        
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults res = new FilterResults();
            if (mVoiceResults == null) {
                if (TextUtils.isEmpty(constraint) && !shouldProcessEmptyQuery()) {
                    res.count = 0;
                    res.values = null;
                    return res;
                }
                startSuggestionsAsync(constraint);
                List<SuggestItem> filterResults = new ArrayList<SuggestItem>();
                if (constraint != null) {
                    for (CursorSource sc : mSources) {
                        sc.runQuery(constraint);
                    }
                    mixResults(filterResults);
                }
                synchronized (mResultsLock) {
                    mFilterResults = filterResults;
                }
                SuggestionResults mixed = buildSuggestionResults();
                res.count = mixed.getLineCount();
                res.values = mixed;
            }else {
                res.count = mVoiceResults.size();
                res.values = mVoiceResults;
            }
            
            return res;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults fresults) {
            if (fresults.values instanceof SuggestionResults) {
                mMixedResults = (SuggestionResults) fresults.values;
                notifyDataSetChanged();
            }
        }
        
        void mixResults(List<SuggestItem> results) {
            int maxLines = getMaxLines();
            for (int i = 0; i < mSources.size(); i++) {
                CursorSource s = mSources.get(i);
                int n = Math.min(s.getCount(), maxLines);
                maxLines -= n;
                boolean more = false;
                for (int j = 0; j < n; j++) {
                    results.add(s.getItem());
                    more = s.moveToNext();
                }
            }
        }
    }
    
    
    private int getMaxLines() {
        int maxLines = mLandscapeMode ? mLinesLandscape : mLinesPortrait;
        maxLines = (int) Math.ceil(maxLines / 2.0);
        return maxLines;
    }
    
    
    @Override
    public int getCount() {
        if (mVoiceResults != null) {
            return mVoiceResults.size();
        }
        return (mMixedResults == null) ? 0 : mMixedResults.getLineCount();
    }

    @Override
    public SuggestItem getItem(int position) {
        if (mVoiceResults != null) {
            SuggestItem item = new SuggestItem(mVoiceResults.get(position),
                    null, TYPE_VOICE_SEARCH);
            item.extra = Integer.toString(position);
            return item;
        }
        if (mMixedResults == null) {
            return null;
        }
        return mMixedResults.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.my_suggestion_item, parent, false);
        }
        bindView(view, getItem(position));
        return view;
    }
    
    
    private void bindView(View view, SuggestItem item) {
        // store item for click handling
        view.setTag(item);
        TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
        TextView tv2 = (TextView) view.findViewById(android.R.id.text2);
        ImageView ic1 = (ImageView) view.findViewById(R.id.icon1);
        View ic2 = view.findViewById(R.id.icon2);
        //View div = view.findViewById(R.id.divider);
        tv1.setText(Html.fromHtml(item.title));
        if (TextUtils.isEmpty(item.url)) {
            tv2.setVisibility(View.GONE);
            tv1.setMaxLines(2);
        } else {
            tv2.setVisibility(View.VISIBLE);
            tv2.setText(item.url);
            tv1.setMaxLines(1);
        }
        int id = -1;
        switch (item.type) {
            case TYPE_SUGGEST:
            case TYPE_SEARCH:
            case TYPE_VOICE_SEARCH:
                id = R.drawable.ic_search_category_suggest;
                break;
            case TYPE_BOOKMARK:
                id = R.drawable.ic_search_category_bookmark;
                break;
            case TYPE_HISTORY:
                id = R.drawable.ic_search_category_history;
                break;
            case TYPE_SUGGEST_URL:
                id = R.drawable.ic_search_category_browser;
                break;
            default:
                id = -1;
        }
        if (id != -1) {
            ic1.setImageDrawable(mContext.getResources().getDrawable(id));
        }
        ic2.setVisibility(((TYPE_SUGGEST == item.type)
                || (TYPE_SEARCH == item.type)
                || (TYPE_VOICE_SEARCH == item.type))
                ? View.VISIBLE : View.GONE);
        //div.setVisibility(ic2.getVisibility());
        ic2.setOnClickListener(this);
        view.findViewById(R.id.suggestion).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SuggestItem item = (SuggestItem) ((View) v.getParent()).getTag();

        if (R.id.icon2 == v.getId()) {
            // replace input field text with suggestion text
            mListener.onSearch(getSuggestionUrl(item));
        } else {
            mListener.onSelect(getSuggestionUrl(item), item.type, item.extra);
        }
    }
    
    static String getSuggestionTitle(SuggestItem item) {
        // There must be a better way to strip HTML from things.
        // This method is used in multiple places. It is also more
        // expensive than a standard html escaper.
        return (item.title != null) ? Html.fromHtml(item.title).toString() : null;
    }

    static String getSuggestionUrl(SuggestItem item) {
        final String title = MySuggestionsAdapter.getSuggestionTitle(item);

        if (TextUtils.isEmpty(item.url)) {
            return title;
        }
        return item.url;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

}
