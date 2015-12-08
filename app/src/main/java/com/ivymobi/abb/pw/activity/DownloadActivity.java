package com.ivymobi.abb.pw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.app.MyApplication;
import com.ivymobi.abb.pw.fragment.CloudFragment_;
import com.ivymobi.abb.pw.fragment.DownloadedFragment_;
import com.ivymobi.abb.pw.fragment.FavoriteFragment_;
import com.ivymobi.abb.pw.fragment.TabRootFragment;
import com.ivymobi.abb.pw.listener.OttoBus;
import com.ivymobi.abb.pw.listener.UpdateShareModeEvent;
import com.ivymobi.abb.pw.util.PreferenceUtil;
import com.squareup.otto.Produce;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.Locale;

@EActivity
public class DownloadActivity extends AppCompatActivity {

    protected FragmentTabHost mTabHost;

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

    protected void addTab(String tag, View indicator, Class<? extends Fragment> clazz) {

        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tag).setIndicator(indicator);

        Bundle args = new Bundle();
        args.putString("root", clazz.getName());

        mTabHost.addTab(tabSpec, TabRootFragment.class, args);
    }

    protected TabRootFragment getCurrentFragment() {
        return (TabRootFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }

    @Bean
    protected OttoBus bus;

    protected boolean isShareMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceUtil.init(this);
        switchLanguage(PreferenceUtil.getString("language", "Chinese"));

        setTitle(R.string.download);

        setContentView(R.layout.activity_download);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_home);

//        setupTabs();
        initTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);

        mShareIconMenuItem = menu.findItem(R.id.action_share_icon);
        mCancelMenuItem = menu.findItem(R.id.action_cancel);
        mShareMenuItem = menu.findItem(R.id.action_share);

        return super.onCreateOptionsMenu(menu);
    }

    protected MenuItem mShareIconMenuItem;
    protected MenuItem mCancelMenuItem;
    protected MenuItem mShareMenuItem;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_share_icon) {
            isShareMode = true;
            invalidateOptionsMenu();
        } else if (item.getItemId() == R.id.action_cancel) {
            isShareMode = false;
            invalidateOptionsMenu();
        } else if (item.getItemId() == R.id.action_share) {
            isShareMode = false;
            invalidateOptionsMenu();
            //TODO:complete the share operation
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(!isShareMode);
        mShareIconMenuItem.setVisible(!isShareMode);
        mCancelMenuItem.setVisible(isShareMode);
        mShareMenuItem.setVisible(isShareMode);
        updateTabState(isShareMode);
        ((MyApplication) getApplication()).setmData(
                getResources().getString(R.string.share_mode), isShareMode);
        bus.post(produceEvent());
        return true;
    }

    @Produce
    public UpdateShareModeEvent produceEvent() {
        return new UpdateShareModeEvent(isShareMode);
    }

    protected void updateTabState(boolean isInShareMode) {
        if (isInShareMode) {
//            mTabHost.getTabWidget().getChildTabViewAt(0).setBackground(null);
            //TODO:change the tabview background
            mTabHost.getTabWidget().setEnabled(!isInShareMode);
        } else {
            //TODO:change the tabview background
            mTabHost.getTabWidget().setEnabled(!isInShareMode);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("getCurrentFragment():::::" + getCurrentFragment().getTag());

        getCurrentFragment().onActivityResult(requestCode, resultCode, data);
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
