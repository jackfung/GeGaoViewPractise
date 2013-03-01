
package com.gaoge.view.practise.bitmap.fun.test.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


import java.lang.ref.WeakReference;



public abstract class MyImageWorker {
    
    protected Context mContext;
    private Bitmap mLoadingBitmap;
    private MyImageCache mImageCache;
    private boolean mFadeInBitmap = true;
    private boolean mExitTasksEarly = false;
    
    protected MyImageWorkerAdapter mImageWorkerAdapter;
    
    private static final int FADE_IN_TIME = 200;
    

    protected MyImageWorker(Context context) {
        mContext = context;
    }

    /**
     * Load an image specified by the data parameter into an ImageView (override
     * {@link ImageWorker#processBitmap(Object)} to define the processing logic). A memory and disk
     * cache will be used if an {@link ImageCache} has been set using
     * {@link ImageWorker#setImageCache(ImageCache)}. If the image is found in the memory cache, it
     * is set immediately, otherwise an {@link AsyncTask} will be created to asynchronously load the
     * bitmap.
     *
     * @param data The URL of the image to download.
     * @param imageView The ImageView to bind the downloaded image to.
     */
    public void loadImage(Object data, ImageView imageView) {
        Bitmap bitmap = null;

        if (mImageCache != null) {
            bitmap = mImageCache.getBitmapFromMemCache(String.valueOf(data));
        }

        if (bitmap != null) {
            // Bitmap found in memory cache
            imageView.setImageBitmap(bitmap);
        } else if (cancelPotentialWork(data, imageView)) {
            final MyBitmapWorkerTask task = new MyBitmapWorkerTask(imageView);
            final MyAsyncDrawable MyAsyncDrawable =
                    new MyAsyncDrawable(mContext.getResources(), mLoadingBitmap, task);
            imageView.setImageDrawable(MyAsyncDrawable);
            task.execute(data);
        }
    }

    /**
     * Load an image specified from a set adapter into an ImageView (override
     * {@link ImageWorker#processBitmap(Object)} to define the processing logic). A memory and disk
     * cache will be used if an {@link ImageCache} has been set using
     * {@link ImageWorker#setImageCache(ImageCache)}. If the image is found in the memory cache, it
     * is set immediately, otherwise an {@link AsyncTask} will be created to asynchronously load the
     * bitmap. {@link ImageWorker#setAdapter(ImageWorkerAdapter)} must be called before using this
     * method.
     *
     * @param data The URL of the image to download.
     * @param imageView The ImageView to bind the downloaded image to.
     */
    public void loadImage(int num, ImageView imageView) {
        if (mImageWorkerAdapter != null) {
            loadImage(mImageWorkerAdapter.getItem(num), imageView);
        } else {
            throw new NullPointerException("Data not set, must call setAdapter() first.");
        }
    }
    public void setLoadingImage(Bitmap bitmap) {
        mLoadingBitmap = bitmap;
    }
    
    public void setLoadingImage(int resId) {
        mLoadingBitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);
    }
    
    public void setImageCache(MyImageCache cacheCallback){
        mImageCache = cacheCallback;
    }
    
    public MyImageCache getImageCache() {
        return mImageCache;
    }
    
    public void setImageFadeIn(boolean fadeIn) {
        mFadeInBitmap = fadeIn;
    }
    
    public void setExitTasksEarly(boolean exitTasksEarly) {
        mExitTasksEarly = exitTasksEarly;
    }
    
    protected abstract Bitmap processBitmap(Object data);
    
    public static void cancelWork(ImageView imageView) {
        final MyBitmapWorkerTask MyBitmapWorkerTask = getBitmapWorkerTask(imageView);
        if(null != MyBitmapWorkerTask){
            MyBitmapWorkerTask.cancel(true);
        }
    }
    
    public static boolean cancelPotentialWork(Object data, ImageView imageView) {
        final MyBitmapWorkerTask MyBitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (MyBitmapWorkerTask != null) {
            final Object bitmapData = MyBitmapWorkerTask.data;
            if (bitmapData == null || !bitmapData.equals(data)) {
                MyBitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress.
                return false;
            }
        }
        return true;
    }
    
    
    private void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (mFadeInBitmap) {
            // Transition drawable with a transparent drwabale and the final bitmap
            final TransitionDrawable td =
                    new TransitionDrawable(new Drawable[] {
                            new ColorDrawable(android.R.color.transparent),
                            new BitmapDrawable(mContext.getResources(), bitmap)
                    });
            // Set background to loading bitmap
            imageView.setBackgroundDrawable(
                    new BitmapDrawable(mContext.getResources(), mLoadingBitmap));

            imageView.setImageDrawable(td);
            td.startTransition(FADE_IN_TIME);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }
    
    private static MyBitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof MyAsyncDrawable) {
                final MyAsyncDrawable MyAsyncDrawable = (MyAsyncDrawable) drawable;
                return MyAsyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
    
    public void setAdapter(MyImageWorkerAdapter adapter) {
        mImageWorkerAdapter = adapter;
    }

    /**
     * Get the current adapter.
     *
     * @return
     */
    public MyImageWorkerAdapter getAdapter() {
        return mImageWorkerAdapter;
    }
    
    private class MyBitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {
        
        private Object data;
        private final WeakReference<ImageView> imageViewReference;
        
        public MyBitmapWorkerTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }
        
        @Override
        protected Bitmap doInBackground(Object... params) {
            data = params[0];
            final String dataString = String.valueOf(data);
            Bitmap bitmap = null;

            // If the image cache is available and this task has not been cancelled by another
            // thread and the ImageView that was originally bound to this task is still bound back
            // to this task and our "exit early" flag is not set then try and fetch the bitmap from
            // the cache
            if (mImageCache != null && !isCancelled() && getAttachedImageView() != null
                    && !mExitTasksEarly) {
                bitmap = mImageCache.getBitmapFromDiskCache(dataString);
            }

            // If the bitmap was not found in the cache and this task has not been cancelled by
            // another thread and the ImageView that was originally bound to this task is still
            // bound back to this task and our "exit early" flag is not set, then call the main
            // process method (as implemented by a subclass)
            if (bitmap == null && !isCancelled() && getAttachedImageView() != null
                    && !mExitTasksEarly) {
                bitmap = processBitmap(params[0]);
            }

            // If the bitmap was processed and the image cache is available, then add the processed
            // bitmap to the cache for future use. Note we don't check if the task was cancelled
            // here, if it was, and the thread is still running, we may as well add the processed
            // bitmap to our cache as it might be used again in the future
            if (bitmap != null && mImageCache != null) {
                mImageCache.addBitmapToCache(dataString, bitmap);
            }

            return bitmap;
        }
        
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // if cancel was called on this task or the "exit early" flag is set then we're done
            if (isCancelled() || mExitTasksEarly) {
                bitmap = null;
            }

            final ImageView imageView = getAttachedImageView();
            if (bitmap != null && imageView != null) {
                setImageBitmap(imageView, bitmap);
            }
        }
        
        /**
         * Returns the ImageView associated with this task as long as the ImageView's task still
         * points to this task as well. Returns null otherwise.
         */
        private ImageView getAttachedImageView() {
            final ImageView imageView = imageViewReference.get();
            final MyBitmapWorkerTask MyBitmapWorkerTask = getBitmapWorkerTask(imageView);

            if (this == MyBitmapWorkerTask) {
                return imageView;
            }

            return null;
        }
        
    }
    
    private static class MyAsyncDrawable extends BitmapDrawable {
        private final WeakReference<MyBitmapWorkerTask> bitmapWorkerTaskReference;

        public MyAsyncDrawable(Resources res, Bitmap bitmap, MyBitmapWorkerTask MyBitmapWorkerTask) {
            super(res, bitmap);

            bitmapWorkerTaskReference =
                new WeakReference<MyBitmapWorkerTask>(MyBitmapWorkerTask);
        }

        public MyBitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }
    
    public static abstract class MyImageWorkerAdapter {
        public abstract Object getItem(int num);
        public abstract int getSize();
    }
    
    
    

}
