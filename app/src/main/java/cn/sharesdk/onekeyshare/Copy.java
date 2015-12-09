package cn.sharesdk.onekeyshare;


import android.content.Context;

import cn.sharesdk.framework.CustomPlatform;

public class Copy extends CustomPlatform {
    public static final String NAME = "Copy";

    public Copy(Context context) {
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
