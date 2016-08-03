package com.ivymobi.abb.pw.network;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;

import com.ivymobi.abb.pw.activity.MainActivity;
import com.ivymobi.abb.pw.network.response.ABBChinaIntroduceResponse;
import com.ivymobi.abb.pw.network.response.ABBChinaPowerIntroduceResponse;
import com.ivymobi.abb.pw.network.response.ActivitiesResponse;
import com.ivymobi.abb.pw.network.response.DocumentListResponse;
import com.ivymobi.abb.pw.network.response.LocalIntrosResponse;
import com.ivymobi.abb.pw.network.response.SuccessCaseResponse;
import com.ivymobi.abb.pw.util.PreferenceUtil;
import com.ivymobi.abb.pw.util.SerializationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by renguangkai on 2016/7/23.
 */
public class ApiCollection {

    private Call<ABBChinaIntroduceResponse> call0;
    private Call<ABBChinaPowerIntroduceResponse> call1;
    private Call<LocalIntrosResponse> call2;
    private Call<SuccessCaseResponse> call3;
    private Call<ActivitiesResponse> call4;
    private Call<DocumentListResponse> call5;
    private Call<DocumentListResponse> call6;
    public static Map<String, String> oldEtagMap = new HashMap<>();

    public ApiCollection() {
        NetService testNetClient =  ServiceGenerator.createService(NetService.class);
        call0 = testNetClient.getIntroduceOfABBChina(PreferenceUtil.getString(CachedFileEnum.BUSINESS_ABB_INTRO.getNameEtag(), ""));
        call1 = testNetClient.getIntroduceOfABBChinaPower(PreferenceUtil.getString(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameEtag(), ""));
        call2 = testNetClient.getLocalIntros(PreferenceUtil.getString(CachedFileEnum.BUSINESS_LOCAL.getNameEtag(), ""));
        call3 = testNetClient.getSuccessCase(PreferenceUtil.getString(CachedFileEnum.BUSINESS_CASE.getNameEtag(), ""));
        call4 = testNetClient.getActivities(PreferenceUtil.getString(CachedFileEnum.ACTIVITY.getNameEtag(), ""));
        call5 = testNetClient.getDocumentsOfChinese(PreferenceUtil.getString(CachedFileEnum.DOCUMENT_CENTER.getNameEtag(), ""));
        call6 = testNetClient.getDocumentsOfEnglish(PreferenceUtil.getString(CachedFileEnum.DOCUMENT_CENTER.getNameEtag(), ""));
    }

    public void callsEnqueue(final Context ctx, final Handler handler) {
        call0.enqueue(new Callback<ABBChinaIntroduceResponse>() {
            @Override
            public void onResponse(Call<ABBChinaIntroduceResponse> call, Response<ABBChinaIntroduceResponse> response) {
                PreferenceUtil.commitString(CachedFileEnum.BUSINESS_ABB_INTRO.getNameEtag(), response.headers().get("Etag"));
                if (response.code() == 200) {
                    SerializationUtil.saveObject(ctx, CachedFileEnum.BUSINESS_ABB_INTRO.getNameEtag(), response.body());
                    PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_ABB_INTRO.getNameUpdate(), true);   // 表示内容有更新
                } else if (response.code() == 304) {
                    PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_ABB_INTRO.getNameUpdate(), false);  // 表示内容未更新
                }
                handler.sendEmptyMessage(MainActivity.count--);
            }

            @Override
            public void onFailure(Call<ABBChinaIntroduceResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        call1.enqueue(new Callback<ABBChinaPowerIntroduceResponse>() {
            @Override
            public void onResponse(Call<ABBChinaPowerIntroduceResponse> call, Response<ABBChinaPowerIntroduceResponse> response) {
                System.out.println(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.name() + "Etag == " + response.headers().get("Etag") + " -------> " + SystemClock.currentThreadTimeMillis());
                PreferenceUtil.commitString(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameEtag(), response.headers().get("Etag"));
                if (response.code() == 200) {
                    SerializationUtil.saveObject(ctx, CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameEtag(), response.body());
                    PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameUpdate(), true);
                } else if (response.code() == 304) {
                    PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.getNameUpdate(), false);
                }
                handler.sendEmptyMessage(MainActivity.count--);
            }

            @Override
            public void onFailure(Call<ABBChinaPowerIntroduceResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        call2.enqueue(new Callback<LocalIntrosResponse>() {
            @Override
            public void onResponse(Call<LocalIntrosResponse> call, Response<LocalIntrosResponse> response) {
                System.out.println("-------------------------------end 2------------------------");
                if (response.code() == 200) {
                    SerializationUtil.saveObject(ctx, CachedFileEnum.BUSINESS_LOCAL.getNameEtag(), response.body());
                    PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_LOCAL.getNameUpdate(), true);
                } else if (response.code() == 304) {
                    PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_LOCAL.getNameUpdate(), false);
                }
                handler.sendEmptyMessage(MainActivity.count--);
            }

            @Override
            public void onFailure(Call<LocalIntrosResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        call3.enqueue(new Callback<SuccessCaseResponse>() {
            @Override
            public void onResponse(Call<SuccessCaseResponse> call, Response<SuccessCaseResponse> response) {
                System.out.println("-------------------------------end 3------------------------");
                if (response.code() == 200) {
                    SerializationUtil.saveObject(ctx, CachedFileEnum.BUSINESS_CASE.getNameEtag(), response.body());
                    PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_CASE.getNameUpdate(), true);
                } else if (response.code() == 304) {
                    PreferenceUtil.commitBoolean(CachedFileEnum.BUSINESS_CASE.getNameUpdate(), false);
                }
                handler.sendEmptyMessage(MainActivity.count--);
            }

            @Override
            public void onFailure(Call<SuccessCaseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        call4.enqueue(new Callback<ActivitiesResponse>() {
            @Override
            public void onResponse(Call<ActivitiesResponse> call, Response<ActivitiesResponse> response) {
                PreferenceUtil.commitString(CachedFileEnum.ACTIVITY.getNameEtag(), response.headers().get("Etag"));
                if (response.code() == 200) {
                    SerializationUtil.saveObject(ctx, CachedFileEnum.ACTIVITY.getNameEtag(), response.body());
                    PreferenceUtil.commitBoolean(CachedFileEnum.ACTIVITY.getNameUpdate(), true);
                } else if (response.code() == 304) {
                    PreferenceUtil.commitBoolean(CachedFileEnum.ACTIVITY.getNameUpdate(), false);
                }
                handler.sendEmptyMessage(MainActivity.count--);
            }

            @Override
            public void onFailure(Call<ActivitiesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        call5.enqueue(new Callback<DocumentListResponse>() {
            @Override
            public void onResponse(Call<DocumentListResponse> call, Response<DocumentListResponse> response) {
                if (response.code() == 200) {
                    System.out.println("-------------------------------end 5------------------------");
                    SerializationUtil.saveObject(ctx, CachedFileEnum.DOCUMENT_CENTER.getNameEtag(), response.body());
                    PreferenceUtil.commitBoolean(CachedFileEnum.DOCUMENT_CENTER.getNameUpdate(), true);
                } else if (response.code() == 304) {
                    PreferenceUtil.commitBoolean(CachedFileEnum.DOCUMENT_CENTER.getNameUpdate(), false);
                }
                handler.sendEmptyMessage(MainActivity.count--);
            }

            @Override
            public void onFailure(Call<DocumentListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        call6.enqueue(new Callback<DocumentListResponse>() {
            @Override
            public void onResponse(Call<DocumentListResponse> call, Response<DocumentListResponse> response) {
                if (response.code() == 200) {
                    System.out.println("-------------------------------end 6------------------------");
                    SerializationUtil.saveObject(ctx, CachedFileEnum.DOCUMENT_CENTER.getNameEtag(), response.body());
                    PreferenceUtil.commitBoolean(CachedFileEnum.DOCUMENT_CENTER.getNameUpdate(), true);
                } else if (response.code() == 304) {
                    PreferenceUtil.commitBoolean(CachedFileEnum.DOCUMENT_CENTER.getNameUpdate(), false);
                }
                handler.sendEmptyMessage(MainActivity.count--);
            }

            @Override
            public void onFailure(Call<DocumentListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void callTest(final Context ctx, final Handler handler) {
        //call4.request().newBuilder().header("If-None-Match", PreferenceUtil.getString(CachedFileEnum.ACTIVITY.getNameEtag(), ""));
        System.out.println("request header If-None-Match" + call4.request().headers().get("If-None-Match"));
        System.out.println("request header etag" + call4.request().headers().get("Etag"));
        call4.enqueue(new Callback<ActivitiesResponse>() {
            @Override
            public void onResponse(Call<ActivitiesResponse> call, Response<ActivitiesResponse> response) {
                System.out.println(CachedFileEnum.ACTIVITY.getNameEtag() + ", Etag == " + response.headers().get("Etag"));
                PreferenceUtil.commitString(CachedFileEnum.ACTIVITY.getNameEtag(), response.headers().get("Etag"));
                if (response.code() == 200) {
                    System.out.println("response.code() == 200");
                    SerializationUtil.saveObject(ctx, CachedFileEnum.ACTIVITY.getNameEtag(), response.body());
                    PreferenceUtil.commitBoolean(CachedFileEnum.ACTIVITY.getNameUpdate(), true);
                } else if (response.code() == 304) {
                    System.out.println("response.code() == 304");
                    PreferenceUtil.commitBoolean(CachedFileEnum.ACTIVITY.getNameUpdate(), false);
                }
                handler.sendEmptyMessage(123);
            }

            @Override
            public void onFailure(Call<ActivitiesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
