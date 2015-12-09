package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.app.MyApplication;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

@EActivity
public class EventActivity extends BaseActivity implements View.OnTouchListener {

    @ViewById(R.id.hdhg)
    ImageView imageView;

    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.event);

        setContentView(R.layout.activity_event);

        locale = getResources().getConfiguration().locale;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_home);

        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final Rect rect = new Rect();
        v.getHitRect(rect);

        float scale = getResources().getDisplayMetrics().density;

        if (scale == 3.0) {
            scale = 1.5f;
        } else {
            scale = 1.0f;
        }

        if (locale == Locale.ENGLISH) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (MyApplication.clickHit(imageView, event, 300, 400, 160, 260, scale)) {
                        Intent intent = new Intent(this, VideoActivity_.class);
                        intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/abb_pw_2014_en.mp4");
                        startActivity(intent);
                    } else if (MyApplication.clickHit(imageView, event, 0, 358, 617, 667, scale)) {
                        Uri uri = Uri.parse("http://new.abb.com/cn/power-world-2014/seminars-ppt-sharing");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else if (MyApplication.clickHit(imageView, event, 0, 352, 5299, 5541, scale)) {
                        Intent intent = new Intent(this, VideoActivity_.class);
                        intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_PW_2011_cn.mp4");
                        startActivity(intent);
                    }

                    break;
            }
        } else {

            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (MyApplication.clickHit(imageView, event, 300, 400, 160, 260, scale)) {
                        Intent intent = new Intent(this, VideoActivity_.class);
                        intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/abb_pw_2014_en.mp4");
                        startActivity(intent);
                    } else if (MyApplication.clickHit(imageView, event, 0, 248, 576, 626, scale)) {
                        Uri uri = Uri.parse("http://new.abb.com/cn/power-world-2014/seminars-ppt-sharing");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } else if (MyApplication.clickHit(imageView, event, 0, 352, 4826, 5068, scale)) {
                        Intent intent = new Intent(this, VideoActivity_.class);
                        intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_PW_2011_cn.mp4");
                        startActivity(intent);
                    }

                    break;
            }
        }
        return true;
    }
}
