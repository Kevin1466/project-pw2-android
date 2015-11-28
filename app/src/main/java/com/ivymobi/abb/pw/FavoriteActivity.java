package com.ivymobi.abb.pw;

import android.app.Activity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

@EActivity
public class FavoriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
    }
}
