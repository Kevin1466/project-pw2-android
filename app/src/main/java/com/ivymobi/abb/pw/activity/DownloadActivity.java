package com.ivymobi.abb.pw.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.fragment.CloudFragment_;
import com.ivymobi.abb.pw.fragment.DownloadedFragment_;
import com.ivymobi.abb.pw.fragment.FavoriteFragment_;

import org.androidannotations.annotations.EActivity;

@EActivity
public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.content);

        View tabView1 = createTabView(tabHost.getContext(), getString(R.string.tab_sample_list));
        View tabView2 = createTabView(tabHost.getContext(), getString(R.string.tab_downloaded));
        View tabView3 = createTabView(tabHost.getContext(), getString(R.string.tab_favorited));

        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setIndicator(tabView1);
        tabHost.addTab(tab1, CloudFragment_.class, null);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setIndicator(tabView2);
        tabHost.addTab(tab2, DownloadedFragment_.class, null);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3");
        tab3.setIndicator(tabView3);
        tabHost.addTab(tab3, FavoriteFragment_.class, null);
    }

    private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }
}
