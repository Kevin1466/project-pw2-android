package com.ivymobi.abb.pw;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import org.androidannotations.annotations.EActivity;

@EActivity
public class DownloadActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec downloadSpec = tabHost.newTabSpec("云资料");
        downloadSpec.setIndicator("云资料", getResources().getDrawable(R.mipmap.icon_download_gray));
        Intent downloadIntent = new Intent(this, CloudActivity_.class);
        downloadSpec.setContent(downloadIntent);

        TabHost.TabSpec downloadedSpec = tabHost.newTabSpec("已下载");
        downloadedSpec.setIndicator("已下载");
        Intent downloadedIntent = new Intent(this, LocalActivity_.class);
        downloadedSpec.setContent(downloadedIntent);

        TabHost.TabSpec FavoriteSpec = tabHost.newTabSpec("收藏夹");
        FavoriteSpec.setIndicator("收藏夹");
        Intent favoriteIntent = new Intent(this, FavoriteActivity_.class);
        FavoriteSpec.setContent(favoriteIntent);

        tabHost.addTab(downloadSpec);
        tabHost.addTab(downloadedSpec);
        tabHost.addTab(FavoriteSpec);
    }
}
