
package com.gaoge.view.practise.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;

import com.orange.test.textImage.R;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Utils {
    static final String TAG = "Utils";

    public static int fullScreenWidth;
    public static int fullScreenHeight;
    public static int childHeight;
    public static int childCount = 4;

    public static void initScreenSizeInfo(Context context) {
        Resources resource = context.getResources();

        fullScreenHeight = resource.getDisplayMetrics().heightPixels;
        fullScreenWidth = resource.getDisplayMetrics().widthPixels;
        childHeight = fullScreenHeight / childCount;

        Log.d(TAG, "########## fullScreenWidth: " + fullScreenWidth + ",fullScreenHeight: "
                + fullScreenHeight);
    }

    public static int getFullScreenWidth() {
        return fullScreenWidth;
    }

    public static int getChildTop(int child_index) {
        return child_index * childHeight;
    }

    public static int getChildHeight() {
        return childHeight;
    }

    public static boolean isPlatformHoneycombAndAbove() {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean isPlatformICSAndAbove() {
        return Build.VERSION.SDK_INT >= 14;
    }

    // 将图片的四角圆化
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int roundPx, boolean needAlpha) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        // 得到画布
        Canvas canvas = new Canvas(output);

        // 将画布的四角圆化
        final int color = Color.RED;
        final Paint paint = new Paint();
        // 得到与图像相同大小的区域 由构造的四个值决定区域的位置以及大小
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        // 值越大角度越明显
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (needAlpha) {
            canvas.drawColor(Color.argb(255, 99, 99, 99), Mode.MULTIPLY);
        }
        return output;
    }

    public static Bitmap createScreenshot(WebView view, int thumbnailWidth, int thumbnailHeight) {
        if (view != null) {
            Bitmap mCapture;
            try {
                mCapture = Bitmap.createBitmap(thumbnailWidth, thumbnailHeight,
                        Bitmap.Config.RGB_565);
            } catch (OutOfMemoryError e) {
                return null;
            }
            Canvas c = new Canvas(mCapture);
            final int left = view.getScrollX();
            final int top = view.getScrollY();
            c.translate(-left, -top);
            c.scale(0.65f, 0.65f, left, top);
            try {
                // draw webview may nullpoint
                view.draw(c);
            } catch (Exception e) {
            }
            return mCapture;
        }
        return null;
    }

    public static final String DATABASES_DIR = "/data/data/com.orange.test.textImage/databases";
    public static final String DATABASE_NAME = "com.gaoge.view.practise.provider.BlackWhiteListObject.db";

    public static void copyDatabaseFile(Context context, boolean isfored) {

        File dir = new File(DATABASES_DIR);
        if (!dir.exists() || isfored) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File dest = new File(dir, DATABASE_NAME);
        if (dest.exists() && !isfored) {
            return;
        }

        try {
            if (dest.exists()) {
                dest.delete();
            }
            dest.createNewFile();
            InputStream in = context.getAssets().open(DATABASE_NAME);
            int size = in.available();
            byte buf[] = new byte[size];
            in.read(buf);
            in.close();
            FileOutputStream out = new FileOutputStream(dest);
            out.write(buf);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getSimCountry(Context context) {
        String mcc = null;

        TelephonyManager telmgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        if (telmgr != null) {
            if (telmgr.getSimState() != TelephonyManager.SIM_STATE_READY) {
                return null;
            }

            mcc = telmgr.getSimCountryIso();

            if (mcc != null && mcc.length() == 0) {
                mcc = null;
            }
        }
        return mcc;
    }

    public final static int INDEX_DEFAULT_COUNTRY = 0;
    public final static String JSON_HOME_URL_KEY = "home_url";
    public final static String JSON_COUNTRY_KEY = "country";

    public static String parseHomepage(Context context) {
        String simCountry = getSimCountry(context);
        JSONArray jsonArray = null;
        String defaultHomePageUrl = null;
        String homepageContentJson = EncodingUtils.getString(context.getString(R.string.homepage_base).getBytes(), "UTF-8");
        try {
            jsonArray = new JSONArray(homepageContentJson);
            defaultHomePageUrl = jsonArray.getJSONObject(INDEX_DEFAULT_COUNTRY)
                    .getString(JSON_HOME_URL_KEY);
            // In fact,this case couldn't be reached
            if (null == homepageContentJson) {
                return null;
            }
            if (null == simCountry) {
                return defaultHomePageUrl;
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String country = jsonObject.getString(JSON_COUNTRY_KEY);
                    if (simCountry.equals(country)) {
                        return jsonObject.getString(JSON_HOME_URL_KEY);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultHomePageUrl;
    }

}