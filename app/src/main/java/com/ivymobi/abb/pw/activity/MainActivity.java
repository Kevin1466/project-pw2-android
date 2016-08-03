package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.app.MyApplication;
import com.ivymobi.abb.pw.network.ApiCollection;
import com.ivymobi.abb.pw.network.CachedFileEnum;
import com.ivymobi.abb.pw.util.PreferenceUtil;
import com.umeng.update.UmengUpdateAgent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.lang.ref.WeakReference;
import java.util.Locale;

@EActivity
public class MainActivity extends BaseActivity {

    @ViewById
    VideoView videoView;

    @ViewById
    ImageView businessImageView;

    @ViewById
    ImageView eventImageView;

    @ViewById
    ImageView i3dImageView;

    @ViewById
    ImageView solutionImageView;

    @ViewById
    ImageView contactImageView;

    @ViewById
    ImageView downloadImageView;

    @ViewById
    LinearLayout iconLine;

    @ViewById
    TextView tvBusinessUpdateNotify;

    @ViewById
    TextView tvEventUpdateNotify;

    @ViewById
    TextView tvImageUpdateNotify;

    public static int count;

    private ActionBar mActionBar;

    private ApiCollection mApiCollection;

    public MyHandler mHandler = new MyHandler(this);

    // // TODO: 2016/7/24 此处应声明为static
    public static class MyHandler extends Handler {

        WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mActivity.get().setUpdateRedDot();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceUtil.init(this);
        switchLanguage(PreferenceUtil.getString("language", "Chinese"));

        mActionBar = getSupportActionBar();
        mActionBar.setCustomView(R.layout.actionbar);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_main);

        videoView.setVideoURI(Uri.parse("android.resource://com.ivymobi.abb.pw/" + R.raw.abb_first));
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });

        float scale = getResources().getDisplayMetrics().density;

        if (scale == 2.75) { // xiaomi note
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iconLine.getLayoutParams();
            layoutParams.setMargins(layoutParams.getMarginEnd(), layoutParams.getMarginEnd(), layoutParams.getMarginEnd(), 30);
            iconLine.setLayoutParams(layoutParams);
        }

        UmengUpdateAgent.update(this);

        if (mApiCollection == null) {
            mApiCollection = new ApiCollection();
        }
        count = 6;
        mApiCollection.callsEnqueue(this, mHandler);
//        mApiCollection.callTest(this, mHandler);
    }

    /**
     * 设置内容更新的小红点标识
     */
    private void setUpdateRedDot() {
        System.out.println("-------------------------------start Main------------------------");
        // 有更新且未读
        if ((PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_ABB_INTRO.getNameUpdate(), false)
                && PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_ABB_INTRO.getNameRead(), false))
                || PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameUpdate(), false)
                || PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_CASE.getNameUpdate(), false)
                || PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_LOCAL.getNameUpdate(), false)) {
            tvBusinessUpdateNotify.setVisibility(View.VISIBLE);
        }

        // 精彩活动--有更新且未被读过（第一次安装，虽然update为true，但read为false；当点击查看以后，虽然update为false，但是read为true，所以表面上看符合产品需求，但逻辑上是有问题的）
        if (PreferenceUtil.getBoolean(CachedFileEnum.ACTIVITY.getNameUpdate(), false)
                && PreferenceUtil.getBoolean(CachedFileEnum.ACTIVITY.getNameRead(), false)) {
            tvEventUpdateNotify.setVisibility(View.VISIBLE);
        }

        if (PreferenceUtil.getBoolean(CachedFileEnum.DOCUMENT_CENTER.getNameUpdate(), false)
                && PreferenceUtil.getBoolean(CachedFileEnum.DOCUMENT_CENTER.getNameRead(), false)) {
            tvImageUpdateNotify.setVisibility(View.VISIBLE);
        }

        mActionBar.setHomeAsUpIndicator(getDrawable());

    }

    /**
     * 布局资源转为Drawable
     * @return
     */
    private Drawable getDrawable() {
        if (hasUpdated()) {
            LayoutInflater inflater = getLayoutInflater();
            View viewHelper = inflater.inflate(R.layout.layout_setting, null);
            int height = 60;
            int width = 90;
            viewHelper.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
            viewHelper.layout(0, 0, width, height);
            viewHelper.buildDrawingCache();
            Bitmap bitmap = viewHelper.getDrawingCache();
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            return drawable;
        } else {
            return getResources().getDrawable(R.mipmap.icon_setting);
        }
    }

    private boolean hasUpdated() {
        return PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_ABB_INTRO.getNameUpdate(), false)
                || PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameUpdate(), false)
                || PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_CASE.getNameUpdate(), false)
                || PreferenceUtil.getBoolean(CachedFileEnum.BUSINESS_LOCAL.getNameUpdate(), false)
                || PreferenceUtil.getBoolean(CachedFileEnum.DOCUMENT_CENTER.getNameUpdate(), false)
                || PreferenceUtil.getBoolean(CachedFileEnum.ACTIVITY.getNameUpdate(), false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (!MyApplication.isNetworkAvailable(this)) {
            Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
        }
    }

    @AfterViews
    public void checkNetwork() {
        if (!MyApplication.isNetworkAvailable(this)) {
            Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_SHORT).show();
        }
    }

    @Click
    public void businessImageViewClicked() {
        startActivity(new Intent(this, BusinessActivity_.class));
        // 业务标记
    }

    @Click
    public void eventImageViewClicked() {
        // 存储已读状态
        PreferenceUtil.commitBoolean(CachedFileEnum.ACTIVITY.getNameRead(), true);
        startActivity(new Intent(this, EventActivity_.class));
    }

    @Click
    public void i3dImageViewClicked() {
        startActivity(new Intent(this, TourActivity_.class));
    }

    @Click
    public void solutionImageViewClicked() {
        startActivity(new Intent(this, SolutionActivity_.class));
    }

    @Click
    public void contactImageViewClicked() {
        startActivity(new Intent(this, ContactActivity_.class));
    }

    @Click
    public void downloadImageViewClicked() {
        Intent intent = new Intent(this, DownloadActivity_.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
//        startActivity(new Intent(this, DownloadActivity_.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, SettingActivity_.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchLanguage(String language) {

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        if (language.equals("Chinese")) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            config.locale = Locale.ENGLISH;
        }

        resources.updateConfiguration(config, dm);
    }

}
