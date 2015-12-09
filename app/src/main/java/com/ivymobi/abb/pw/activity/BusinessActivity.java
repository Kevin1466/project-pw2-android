package com.ivymobi.abb.pw.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.ivymobi.abb.pw.R;
import com.ivymobi.abb.pw.fragment.CaseFragmentContent;
import com.ivymobi.abb.pw.fragment.FragmentContent;
import com.ivymobi.abb.pw.fragment.LocalFragmentContent;
import com.ivymobi.abb.pw.view.MyJazzyViewPager;
import com.ivymobi.abb.pw.view.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.EActivity;

@EActivity
public class BusinessActivity extends BaseActivity {

    private DisplayMetrics dm;
    private PagerSlidingTabStrip tabs;
    private MyJazzyViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.business);

        setContentView(R.layout.activity_business);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.icon_home);

        dm = getResources().getDisplayMetrics();
        pager = (MyJazzyViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabs.setViewPager(pager);
        setTabsValue();

        tabs.setFadeEnabled(true);
        pager.setCurrentItem(0);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        tabs.setShouldExpand(true);
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setUnderlineHeight(0);
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, dm));
        tabs.setIndicatorColor(Color.parseColor("#0174bd"));
        tabs.setSelectedTextColor(Color.parseColor("#0174bd"));
        tabs.setTextColor(Color.parseColor("#999999"));
        tabs.setTabBackground(0);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {getString(R.string.business1), getString(R.string.business2), getString(R.string.business3), getString(R.string.business4)};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle b = new Bundle();
            b.putInt("position", position);

            switch (position) {
                case 2:
                    return LocalFragmentContent.getInstance(b);

                case 3:
                    return CaseFragmentContent.getInstance(b);

                default:
                    return FragmentContent.getInstance(b);

            }
        }
    }
}
