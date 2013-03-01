
package com.gaoge.view.practise.bitmap.fun.test.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gaoge.view.practise.bitmap.fun.test.util.MyImageWorker;
import com.orange.test.textImage.R;

public class MyImageDetailFragment extends Fragment {

    private static final String IMAGE_DATA_EXTRA = "resId";
    private int mImageNum;
    private ImageView mImageView;
    private MyImageWorker mImageWorker;

    public MyImageDetailFragment() {
        
    }

    /**
     * Factory method to generate a new instance of the fragment given an image
     * number.
     * 
     * @param imageNum The image number within the parent adapter to load
     * @return A new instance of ImageDetailFragment with imageNum extras
     */
    public static MyImageDetailFragment newInstance(int imageNum) {
        final MyImageDetailFragment f = new MyImageDetailFragment();

        final Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, imageNum);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageNum = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.my_image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.imageView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Use the parent activity to load the image asynchronously into the
        // ImageView (so a single
        // cache can be used over all pages in the ViewPager
        if (MyImageDetailActivity.class.isInstance(getActivity())) {
            mImageWorker = ((MyImageDetailActivity) getActivity()).getImageWorker();
            mImageWorker.loadImage(mImageNum, mImageView);
        }
    }
    
    /**
     * Cancels the asynchronous work taking place on the ImageView, called by the adapter backing
     * the ViewPager when the child is destroyed.
     */
    public void cancelWork() {
        MyImageWorker.cancelWork(mImageView);
        mImageView.setImageDrawable(null);
        mImageView = null;
    }
    
}
