package com.ivymobi.abb.pw.app;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Configuration dbConfiguration = new Configuration.Builder(this).setDatabaseName("abb.db").create();
        ActiveAndroid.initialize(dbConfiguration);
    }
}
