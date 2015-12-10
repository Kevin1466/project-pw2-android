package com.ivymobi.abb.pw.analytics;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;

public class Analytics {
    public static void log(Context context, String category, String action) {
        log(context, category, action, "-", "-");
    }

    public static void log(Context context, String category, String action, String label, String value) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = tm.getDeviceId();

        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", "id");
        requestParams.put("app_key", "7a94881a-df96-429d-9e01-dece4f46fee2");
        requestParams.put("category", category);
        requestParams.put("action", action);
        requestParams.put("label", label);
        requestParams.put("value", value);
        requestParams.put("user_uuid", androidId);
        requestParams.put("device_udid", deviceId);
        requestParams.put("ip", ip);
        requestParams.put("ua", "ABB");
        requestParams.put("created_at", Calendar.getInstance().getTimeInMillis() / 1000);

        Log.d("Analytics", requestParams.toString());

        client.post("http://www.yangbentong.com/api/7a94881a-df96-429d-9e01-dece4f46fee2/analytics", requestParams, new JsonHttpResponseHandler() {
        });
    }
}
