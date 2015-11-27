package com.ivymobi.abb.pw;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity
public class ContactActivity extends AppCompatActivity {

    @Click
    public void websiteButtonClicked() {
        Uri  uri = Uri.parse("http://www.abb.com.cn");
        Intent intent = new  Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }
}