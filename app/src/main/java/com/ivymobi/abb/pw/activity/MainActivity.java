package com.ivymobi.abb.pw.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.app.MyApplication;
import com.ivymobi.abb.pw.util.PreferenceUtil;
import com.umeng.update.UmengUpdateAgent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_setting);

        PreferenceUtil.init(this);
        switchLanguage(PreferenceUtil.getString("language", "Chinese"));

        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        setTitle(getString(R.string.activity_main));

        setContentView(R.layout.activity_main);

        videoView.setVideoURI(Uri.parse("android.resource://com.ivymobi.abb.pw/" + R.raw.abb_first));
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
            }
        });

        UmengUpdateAgent.update(this);
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
    }

    @Click
    public void eventImageViewClicked() {
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
        startActivity(new Intent(this, DownloadActivity_.class));
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
