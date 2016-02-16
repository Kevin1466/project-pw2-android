package com.ivymobi.abb.pw.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;
import com.ivymobi.abb.pw.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyApplication extends Application {
    public static UMSocialService umSocialService = UMServiceFactory.getUMSocialService("com.umeng.share");
    public static Boolean umSocialServiceInit = false;

    private Map<String, Object> mData;

    public Object getmData(String key) {
        return mData.get(key);
    }
    public void setmData(String key,Object value){
        mData.put(key,value);
    }
    public static List<com.ivymobi.abb.pw.beans.File> mChecked = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        mData = new HashMap<>();
        boolean isShareMode = false;
        mData.put(getResources().getString(R.string.share_mode),isShareMode);

        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("abb.db").create();
        ActiveAndroid.initialize(dbConfiguration);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(new File(getExternalCacheDir() + "/images")))
                .diskCacheSize(50 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static boolean clickHit(ImageView imageView, MotionEvent event, float minX, float maxX, float minY, float maxY, float scale) {
        float widthScale = (imageView.getDrawable().getIntrinsicWidth() / scale) / (imageView.getWidth() / scale);
        float heightScale = (imageView.getDrawable().getIntrinsicHeight() / scale) / (imageView.getHeight() / scale);

        float x = event.getX() / scale;
        float y = event.getY() / scale;

        minX *= widthScale;
        maxX *= widthScale;
        minY *= heightScale;
        maxY *= heightScale;

        System.out.println(x);
        System.out.println(y);
        System.out.println(x > minX && x < maxX && y > minY && y < maxY);

        return x > minX && x < maxX && y > minY && y < maxY;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.i("NetWorkState", "Unavailabel");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.i("NetWorkState", "Availabel");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
