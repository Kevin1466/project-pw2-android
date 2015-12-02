package com.ivymobi.abb.pw.app;

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

    public Locale getLocale() {
        return getResources().getConfiguration().locale;
    }
}
