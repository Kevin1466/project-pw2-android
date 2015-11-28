package com.ivymobi.abb.pw.activity;

import android.app.Activity;
import android.os.Bundle;

import com.ivymobi.abb.pw.R;

import org.androidannotations.annotations.EActivity;

@EActivity
public class CloudActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);
    }
}
