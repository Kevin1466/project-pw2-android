package com.ivymobi.abb.pw;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.show();
        }

        videoView.setVideoURI(Uri.parse("android.resource://com.ivymobi.abb.pw/" + R.raw.abb_first));
        videoView.start();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:

                startActivity(new Intent(this, SettingActivity_.class));

                // Todo
                break;
            default:
                break;
        }

        return true;
    }
}
