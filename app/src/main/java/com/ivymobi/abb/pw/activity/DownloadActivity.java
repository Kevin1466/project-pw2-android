package com.ivymobi.abb.pw.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
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
import com.ivymobi.abb.pw.fragment.ListItemFragment;
import com.ivymobi.abb.pw.fragment.TabRootFragment;
import com.ivymobi.abb.pw.listener.OttoBus;
import com.ivymobi.abb.pw.listener.UpdateShareEvent;
import com.ivymobi.abb.pw.listener.UpdateShareModeEvent;
import com.ivymobi.abb.pw.util.PreferenceUtil;
import com.squareup.otto.Produce;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.Locale;

@EActivity
public class DownloadActivity extends BaseActivity {

    protected FragmentTabHost mTabHost;
    public static ListItemFragment listItemFragment = null;
    View maskView = null;

    public static boolean isFirst = true;

    @Override
    public void onBackPressed() {
//        getCurrentFragment().clearBackStack();
//        super.onBackPressed();
        if (!getCurrentFragment().popBackStack()) {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost.clearAllTabs();
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
        mSearchMenuItem = menu.findItem(R.id.action_search);

        return super.onCreateOptionsMenu(menu);
    }

    protected MenuItem mShareIconMenuItem;
    protected MenuItem mCancelMenuItem;
    protected MenuItem mShareMenuItem;
    protected MenuItem mSearchMenuItem;

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
            bus.post(produceShareEvent());
        } else if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity_.class);

            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(!isShareMode);
        mShareIconMenuItem.setVisible(!isShareMode);
        mCancelMenuItem.setVisible(isShareMode);
        mShareMenuItem.setVisible(isShareMode);
        mSearchMenuItem.setVisible(!isShareMode);
        updateTabState(isShareMode);
        ((MyApplication) getApplication()).setmData(
                getResources().getString(R.string.share_mode), isShareMode);
        bus.post(produceShareModeEvent());
        return true;
    }

    @Produce
    public UpdateShareModeEvent produceShareModeEvent() {
        return new UpdateShareModeEvent(isShareMode);
    }
    @Produce
    public UpdateShareEvent produceShareEvent() {
        return new UpdateShareEvent(true);
    }
    protected void updateTabState(boolean isInShareMode) {
        if (isInShareMode) {

            if (maskView == null)  {
                maskView = new View(this);
                maskView.setLayoutParams(new LinearLayout.LayoutParams(mTabHost.getWidth(), mTabHost.getTabWidget().getHeight()));
                maskView.setBackgroundColor(Color.parseColor("#000000"));
                maskView.setAlpha(0.8f);
            }

            mTabHost.addView(maskView);

            mTabHost.getTabWidget().setEnabled(!isInShareMode);
        } else {

            MyApplication.mChecked.clear();

            if (maskView != null) {
                mTabHost.removeView(maskView);
            }

            mTabHost.getTabWidget().setEnabled(!isInShareMode);
        }
    }

    private View createTabView(final Context context, final Integer position) {
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

        if (listItemFragment != null) {
            listItemFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void switchLanguage(String language) {

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        if (language.equals("Chinese")) {

            config.locale = Locale.SIMPLIFIED_CHINESE;

        } else {

            config.setLocale(Locale.ENGLISH);

        }

        resources.updateConfiguration(config, dm);
    }
}
