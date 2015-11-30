package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.ivymobi.abb.pw.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class EventActivity extends AppCompatActivity implements View.OnTouchListener {

    @ViewById(R.id.hdhg)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final Rect rect = new Rect();
        v.getHitRect(rect);

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (x > 300 && x < 400 && y > 160 && y < 260) {
                    Intent intent = new Intent(this, VideoActivity_.class);
                    intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/abb_pw_2014_en.mp4");
                    startActivity(intent);
                } else if (x > 0 && x < 248 && y > 576 && y < 626) {
                    Uri uri = Uri.parse("http://new.abb.com/cn/power-world-2014/seminars-ppt-sharing");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else if (x > 0 && x < 352 && y > 4826 && y < 5068) {
                    Intent intent = new Intent(this, VideoActivity_.class);
                    intent.putExtra("url", "http://ivymobi-storage.qiniudn.com/abbpw/Video/ABB_PW_2011_cn.mp4");
                    startActivity(intent);
                }

                break;

        }
        return true;
    }
}
