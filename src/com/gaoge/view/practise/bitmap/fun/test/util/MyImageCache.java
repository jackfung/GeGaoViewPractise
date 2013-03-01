package com.gaoge.view.practise.bitmap.fun.test.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LruCache;
import android.util.Log;



import java.io.File;




public class MyImageCache {
    private static final String TAG = "MyImageCache";
    
 // Default memory cache size
    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 5; // 5MB

    // Default disk cache size
    private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    
    private static final CompressFormat DEFAULT_COMPRESS_FORMAT = CompressFormat.JPEG;
    private static final int DEFAULT_COMPRESS_QUALITY = 70;
    
    private static final boolean DEFAULT_MEM_CACHE_ENABLED = true;
    private static final boolean DEFAULT_DISK_CACHE_ENABLED = true;
    private static final boolean DEFAULT_CLEAR_DISK_CACHE_ON_START = false;
    
    private MyDiskLruCache mDiskCache;
    private LruCache<String, Bitmap> mMemoryCache;
    
    public MyImageCache(Context context, MyImageCacheParams cacheParams) {
        init(context, cacheParams);
    }
    
    private void init(Context context, MyImageCacheParams cacheParams) {
        final File diskCacheDir = MyDiskLruCache.getDiskCacheDir(context, cacheParams.uniqueName);
        
     // Set up disk cache
        if (cacheParams.diskCacheEnabled) {
            mDiskCache = MyDiskLruCache.openCache(context, diskCacheDir, cacheParams.diskCacheSize);
            mDiskCache.setCompressParams(cacheParams.compressFormat, cacheParams.compressQuality);
            if (cacheParams.clearDiskCacheOnStart) {
                mDiskCache.clearCache();
            }
        }
        
        // Set up memory cache
        if (cacheParams.memoryCacheEnabled) {
            mMemoryCache = new LruCache<String, Bitmap>(cacheParams.memCacheSize) {
                /**
                 * Measure item size in bytes rather than units which is more practical for a bitmap
                 * cache
                 */
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return MyUtils.getBitmapSize(bitmap);
                }
            };
        }
        
    }
    
    public void addBitmapToCache(String data, Bitmap bitmap) {
        if (data == null || bitmap == null) {
            return;
        }

        // Add to memory cache
        if (mMemoryCache != null && mMemoryCache.get(data) == null) {
            mMemoryCache.put(data, bitmap);
        }

        // Add to disk cache
        if (mDiskCache != null && !mDiskCache.containsKey(data)) {
            mDiskCache.put(data, bitmap);
        }
    }
    
    
    /**
     * Get from memory cache.
     *
     * @param data Unique identifier for which item to get
     * @return The bitmap if found in cache, null otherwise
     */
    public Bitmap getBitmapFromMemCache(String data) {
        if (mMemoryCache != null) {
            final Bitmap memBitmap = mMemoryCache.get(data);
            if (memBitmap != null) {
                if (MyBuildConfig.DEBUG) {
                    Log.d(TAG, "Memory cache hit");
                }
                return memBitmap;
            }
        }
        return null;
    }

    public void clearCaches() {
        mDiskCache.clearCache();
        mMemoryCache.evictAll();
    }

    
    /**
     * Get from disk cache.
     *
     * @param data Unique identifier for which item to get
     * @return The bitmap if found in cache, null otherwise
     */
    public Bitmap getBitmapFromDiskCache(String data) {
        if (mDiskCache != null) {
            return mDiskCache.get(data);
        }
        return null;
    }
    
    public static class MyImageCacheParams {
        public String uniqueName;
        public int memCacheSize = DEFAULT_MEM_CACHE_SIZE;
        public int diskCacheSize = DEFAULT_DISK_CACHE_SIZE;
        public CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
        public int compressQuality = DEFAULT_COMPRESS_QUALITY;
        public boolean memoryCacheEnabled = DEFAULT_MEM_CACHE_ENABLED;
        public boolean diskCacheEnabled = DEFAULT_DISK_CACHE_ENABLED;
        public boolean clearDiskCacheOnStart = DEFAULT_CLEAR_DISK_CACHE_ON_START;

        public MyImageCacheParams(String uniqueName) {
            this.uniqueName = uniqueName;
        }
    }
    
    /**
     * Find and return an existing MyImageCache stored in a {@link MyRetainFragment}, if not found a new
     * one is created with defaults and saved to a {@link MyRetainFragment}.
     *
     * @param activity The calling {@link FragmentActivity}
     * @param uniqueName A unique name to append to the cache directory
     * @return An existing retained MyImageCache object or a new one if one did not exist.
     */
    public static MyImageCache findOrCreateCache(
            final FragmentActivity activity, final String uniqueName) {
        return findOrCreateCache(activity, new MyImageCacheParams(uniqueName));
    }
    
    /**
     * Find and return an existing MyImageCache stored in a {@link MyRetainFragment}, if not found a new
     * one is created using the supplied params and saved to a {@link MyRetainFragment}.
     *
     * @param activity The calling {@link FragmentActivity}
     * @param cacheParams The cache parameters to use if creating the MyImageCache
     * @return An existing retained MyImageCache object or a new one if one did not exist
     */
    public static MyImageCache findOrCreateCache(
            final FragmentActivity activity, MyImageCacheParams cacheParams) {

        // Search for, or create an instance of the non-UI MyRetainFragment
        final MyRetainFragment mRetainFragment = MyRetainFragment.findOrCreateRetainFragment(
                activity.getSupportFragmentManager());

        // See if we already have an MyImageCache stored in MyRetainFragment
        MyImageCache MyImageCache = (MyImageCache) mRetainFragment.getObject();

        // No existing MyImageCache, create one and store it in MyRetainFragment
        if (MyImageCache == null) {
            MyImageCache = new MyImageCache(activity, cacheParams);
            mRetainFragment.setObject(MyImageCache);
        }

        return MyImageCache;
    }

}
