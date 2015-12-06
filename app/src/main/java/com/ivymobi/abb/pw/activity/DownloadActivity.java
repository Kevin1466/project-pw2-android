package com.ivymobi.abb.pw.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.fragment.CloudFragment_;
import com.ivymobi.abb.pw.fragment.DownloadedFragment_;
import com.ivymobi.abb.pw.fragment.FavoriteFragment_;
import com.ivymobi.abb.pw.fragment.TabRootFragment;

import org.androidannotations.annotations.EActivity;

@EActivity
public class DownloadActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;

    @Override
    public void onBackPressed() {
        if (!getCurrentFragment().popBackStack()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }

    private void initTabs() {

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.content);

        View tabView1 = createTabView(mTabHost.getContext(), 0);
        View tabView2 = createTabView(mTabHost.getContext(), 1);
        View tabView3 = createTabView(mTabHost.getContext(), 2);


        addTab("tab1", tabView1, CloudFragment_.class);
        addTab("tab2", tabView2, DownloadedFragment_.class);
        addTab("tab3", tabView3, FavoriteFragment_.class);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                getCurrentFragment().clearBackStack();
            }
        });

    }

    private void addTab(String tag, View indicator, Class<? extends Fragment> clazz) {

        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tag).setIndicator(indicator);

        Bundle args = new Bundle();
        args.putString("root", clazz.getName());

        mTabHost.addTab(tabSpec, TabRootFragment.class, args);
    }

    private TabRootFragment getCurrentFragment() {
        return (TabRootFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.download);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_download);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_home);

//        setupTabs();
        initTabs();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private static View createTabView(final Context context, final Integer position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        LinearLayout tabsLayout = (LinearLayout) view.findViewById(R.id.tabsLayout);

        switch (position) {
            case 0:
                tabsLayout.setBackgroundResource(R.drawable.tab_bg_1);
                break;
            case 1:
                tabsLayout.setBackgroundResource(R.drawable.tab_bg_2);
                break;
            default:
                tabsLayout.setBackgroundResource(R.drawable.tab_bg_3);
                break;
        }

        return view;
    }
}
