package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.ivymobi.abb.pw.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@EActivity
public class ImageActivity extends AppCompatActivity {

    @ViewById
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");

        imageView.setImageResource(getResources().getIdentifier(image, "mipmap", getPackageName()));

    }
}
