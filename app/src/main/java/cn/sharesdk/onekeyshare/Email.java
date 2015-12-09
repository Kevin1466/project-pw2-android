package cn.sharesdk.onekeyshare;


import android.content.Context;

import cn.sharesdk.framework.CustomPlatform;

public class Email extends CustomPlatform {
    public static final String NAME = "CEmail";

    public Email(Context context) {
        super(context);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected boolean checkAuthorize(int i, Object o) {
        return false;
    }

    @Override
    protected void doShare(ShareParams params) {
        super.doShare(params);

        System.out.println("do share");
    }
}
