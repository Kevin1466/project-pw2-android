package com.ivymobi.abb.pw.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ivymobi.abb.pw.R;

import org.androidannotations.annotations.EActivity;

@EActivity
public class TourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
    }
}
