package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.analytics.Analytics;
import com.umeng.analytics.MobclickAgent;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity_.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                SplashActivity.this.finish();
            }
        }, 3000);
    }

    public void onResume() {
        super.onResume();

        Analytics.log(this, "app", "launched");
    }
}
