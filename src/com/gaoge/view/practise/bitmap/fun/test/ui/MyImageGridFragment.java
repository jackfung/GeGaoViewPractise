package com.gaoge.view.practise.bitmap.fun.test.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.gaoge.view.practise.bitmap.fun.test.provider.MyImages;
import com.gaoge.view.practise.bitmap.fun.test.util.MyBuildConfig;
import com.gaoge.view.practise.bitmap.fun.test.util.MyImageCache;
import com.gaoge.view.practise.bitmap.fun.test.util.MyImageCache.MyImageCacheParams;
import com.gaoge.view.practise.bitmap.fun.test.util.MyImageFetcher;
import com.gaoge.view.practise.bitmap.fun.test.util.MyImageResizer;
import com.gaoge.view.practise.bitmap.fun.test.util.MyUtils;
import com.orange.test.textImage.R;


public class MyImageGridFragment extends Fragment implements AdapterView.OnItemClickListener{
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private MyImageResizer mImageWorker;
    
    private static final String IMAGE_CACHE_DIR = "thumbs";
    
    MyImageAdapter mAdapter;
    
    public MyImageGridFragment(){}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
        
        mAdapter = new MyImageAdapter(getActivity());
        mImageWorker = new MyImageFetcher(getActivity(), mImageThumbSize);
        
        MyImageCacheParams cacheParams = new MyImageCacheParams(IMAGE_CACHE_DIR);
        
     // Allocate a third of the per-app memory limit to the bitmap memory cache. This value
        // should be chosen carefully based on a number of factors. Refer to the corresponding
        // Android Training class for more discussion:
        // http://developer.android.com/training/displaying-bitmaps/
        // In this case, we aren't using memory for much else other than this activity and the
        // ImageDetailActivity so a third lets us keep all our sample image thumbnails in memory
        // at once.
        cacheParams.memCacheSize = 1024 * 1024 * MyUtils.getMemoryClass(getActivity()) / 3;
        
        // The ImageWorker takes care of loading images into our ImageView children asynchronously
        mImageWorker = new MyImageFetcher(getActivity(), mImageThumbSize);
        mImageWorker.setAdapter(MyImages.imageThumbWorkerUrlsAdapter);
        mImageWorker.setLoadingImage(R.drawable.empty_photo);
        mImageWorker.setImageCache(MyImageCache.findOrCreateCache(getActivity(), cacheParams));
        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.my_image_grid_fragment, container, false);
        final GridView mGridView = (GridView) v.findViewById(R.id.gridView);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            
            @Override
            public void onGlobalLayout() {
                if (mAdapter.getNumColumns() == 0) {
                    final int numColumns = (int) Math.floor(
                            mGridView.getWidth() / (mImageThumbSize + mImageThumbSpacing));
                    if (numColumns > 0) {
                        final int columnWidth =
                                (mGridView.getWidth() / numColumns) - mImageThumbSpacing;
                        mAdapter.setNumColumns(numColumns);
                        mAdapter.setItemHeight(columnWidth);
                        if (MyBuildConfig.DEBUG) {
                        }
                    }
                }
            }
        });
        return v;
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void onPause() {
        super.onPause();
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    private class MyImageAdapter extends BaseAdapter{

        private final Context mContext;
        private GridView.LayoutParams mImageViewLayoutParams;
        private int mNumColumns = 0;
        private int mItemHeight = 0;
        private int mActionBarHeight = -1;
        
        public MyImageAdapter(Context context){
            super();
            mContext = context;
            mImageViewLayoutParams = new GridView.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        @Override
        public int getCount() {
            return mImageWorker.getAdapter().getSize() + mNumColumns;
        }

        @Override
        public Object getItem(int position) {
            return position < mNumColumns ?
                    null : mImageWorker.getAdapter().getItem(position - mNumColumns);
        }

        @Override
        public long getItemId(int position) {
            return position < mNumColumns ? 0 : position - mNumColumns;
        }

        @Override
        public int getViewTypeCount() {
            // Two types of views, the normal ImageView and the top row of empty views
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return (position < mNumColumns) ? 1 : 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
         // First check if this is the top row
            if (position < mNumColumns) {
                if (convertView == null) {
                    convertView = new View(mContext);
                }
                // Calculate ActionBar height
                if (mActionBarHeight < 0) {
                    TypedValue tv = new TypedValue();
                    if (mContext.getTheme().resolveAttribute(
                            android.R.attr.actionBarSize, tv, true)) {
                        mActionBarHeight = TypedValue.complexToDimensionPixelSize(
                                tv.data, mContext.getResources().getDisplayMetrics());
                    } else {
                        // No ActionBar style (pre-Honeycomb or ActionBar not in theme)
                        mActionBarHeight = 0;
                    }
                }
                // Set empty view with height of ActionBar
                convertView.setLayoutParams(new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, mActionBarHeight));
                return convertView;
            }
            
         // Now handle the main ImageView thumbnails
            ImageView imageView;
            if (convertView == null) { // if it's not recycled, instantiate and initialize
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(mImageViewLayoutParams);
            } else { // Otherwise re-use the converted view
                imageView = (ImageView) convertView;
            }
            
         // Check the height matches our calculated column width
            if (imageView.getLayoutParams().height != mItemHeight) {
                imageView.setLayoutParams(mImageViewLayoutParams);
            }
            
         // Finally load the image asynchronously into the ImageView, this also takes care of
            // setting a placeholder image while the background thread runs
            mImageWorker.loadImage(position - mNumColumns, imageView);
            return imageView;
        }
        
        /**
         * self methods start
         */
        public void setNumColumns(int numColumns) {
            mNumColumns = numColumns;
        }

        public int getNumColumns() {
            return mNumColumns;
        }
        
        /**
         * Sets the item height. Useful for when we know the column width so the height can be set
         * to match.
         *
         * @param height
         */
        public void setItemHeight(int height) {
            if (height == mItemHeight) {
                return;
            }
            mItemHeight = height;
            mImageViewLayoutParams =
                    new GridView.LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
            mImageWorker.setImageSize(height);
            notifyDataSetChanged();
        }
        
        /**
         * selft methods end
         */
        
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        final Intent i = new Intent(getActivity(), MyImageDetailActivity.class);
        i.putExtra(MyImageDetailActivity.EXTRA_IMAGE, (int) id);
        startActivity(i);
    }

}
