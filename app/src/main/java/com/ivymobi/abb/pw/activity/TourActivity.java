package com.ivymobi.abb.pw.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.app.MyApplication;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class TourActivity extends BaseActivity implements View.OnTouchListener {

    @ViewById(R.id.imageView4)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        imageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final Rect rect = new Rect();
        v.getHitRect(rect);

        float scale = getResources().getDisplayMetrics().density;

        if (scale > 2) {
            scale /= 2;
        } else {
            scale = 1.0f;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (MyApplication.clickHit(imageView, event, 600, 750, 0, 120, scale)) {
                    finish();

                    System.out.println("finish");
                }

                break;
        }
        return true;
    }
}
