package com.ivymobi.abb.pw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ivymobi.abb.pw.R;

/**
 * Created by renguangkai on 2016/7/21.
 */
public class ContentUpdateNotifyView extends RelativeLayout {

    private TextView updateNotifyTv;

    public void ifShowUpdateNotify(boolean show) {
        updateNotifyTv.setVisibility(show ? VISIBLE : GONE);
    }

    public ContentUpdateNotifyView(Context context) {
        this(context, null);
    }

    public ContentUpdateNotifyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContentUpdateNotifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        updateNotifyTv = (TextView) findViewById(R.id.tv_update_flag);
    }

}
