package com.gaoge.view.practise.bitmap.fun.test.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;

import com.gaoge.view.practise.bitmap.fun.test.provider.MyImages;
import com.gaoge.view.practise.bitmap.fun.test.util.MyImageCache;
import com.gaoge.view.practise.bitmap.fun.test.util.MyImageFetcher;
import com.gaoge.view.practise.bitmap.fun.test.util.MyImageResizer;
import com.gaoge.view.practise.bitmap.fun.test.util.MyImageWorker;
import com.orange.test.textImage.R;




public class MyImageDetailActivity extends FragmentActivity {
    private static final String IMAGE_CACHE_DIR = "images";
    public static final String EXTRA_IMAGE = "extra_image";
    
    private MyImageResizer mImageWorker;
    private ViewPager mPager;
    private MyImagePagerAdapter mAdapter;
    
    
    
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.my_image_detail_pager);
        
     // Fetch screen height and width, to use as our max size when loading images as this
        // activity runs full screen
        final DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int height = displaymetrics.heightPixels;
        final int width = displaymetrics.widthPixels;
        final int longest = height > width ? height : width;
        
     // The ImageWorker takes care of loading images into our ImageView children asynchronously
        mImageWorker = new MyImageFetcher(this, longest);
        mImageWorker.setAdapter(MyImages.imageWorkerUrlsAdapter);
        mImageWorker.setImageCache(MyImageCache.findOrCreateCache(this, IMAGE_CACHE_DIR));
        mImageWorker.setImageFadeIn(false);
        
        // Set up ViewPager and backing adapter
        mAdapter = new MyImagePagerAdapter(getSupportFragmentManager(),
                mImageWorker.getAdapter().getSize());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin((int) getResources().getDimension(R.dimen.image_detail_pager_margin));
        
        // Set up activity to go full screen
        getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);
        
     // Set the current item based on the extra passed in to this activity
        final int extraCurrentItem = getIntent().getIntExtra(EXTRA_IMAGE, -1);
        if (extraCurrentItem != -1) {
            mPager.setCurrentItem(extraCurrentItem);
        }
    }
    
    /**
     * Called by the ViewPager child fragments to load images via the one ImageWorker
     *
     * @return
     */
    public MyImageWorker getImageWorker() {
        return mImageWorker;
    }
    
    /**
     * The main adapter that backs the ViewPager. A subclass of FragmentStatePagerAdapter as there
     * could be a large number of items in the ViewPager and we don't want to retain them all in
     * memory at once but create/destroy them on the fly.
     */
    private class MyImagePagerAdapter extends FragmentStatePagerAdapter {

        private final int mSize;
        
        public MyImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }
        @Override
        public Fragment getItem(int position) {
            return MyImageDetailFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mSize;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            final MyImageDetailFragment fragment = (MyImageDetailFragment) object;
            // As the item gets destroyed we try and cancel any existing work.
            fragment.cancelWork();
            super.destroyItem(container, position, object);
        }
        
    }
}
