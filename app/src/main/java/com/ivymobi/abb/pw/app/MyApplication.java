package com.ivymobi.abb.pw.app;

import android.view.MotionEvent;
import android.widget.ImageView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.Locale;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("abb.db").create();
        ActiveAndroid.initialize(dbConfiguration);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(new File(getExternalCacheDir() + "/images")))
                .diskCacheSize(50 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static boolean clickHit(ImageView imageView, MotionEvent event, float minX, float maxX, float minY, float maxY) {
        float widthScale = imageView.getDrawable().getIntrinsicWidth() / imageView.getWidth();
        float heightScale = imageView.getDrawable().getIntrinsicHeight() / imageView.getHeight();

        float x = event.getX();
        float y = event.getY();

        minX *= widthScale;
        maxX *= widthScale;
        minY *= heightScale;
        maxY *= heightScale;

        return x > minX && x < maxX && y > minY && y < maxY;
    }
}
