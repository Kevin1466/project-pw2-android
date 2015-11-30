package com.ivymobi.abb.pw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.ivymobi.abb.pw.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ImageActivity extends Activity {

    @ViewById
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");

        imageView.setImageResource(getResources().getIdentifier(image, "mipmap", getPackageName()));

    }
}