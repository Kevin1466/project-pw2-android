package com.ivymobi.abb.pw.network;

import com.ivymobi.abb.pw.network.response.ABBChinaIntroduceResponse;
import com.ivymobi.abb.pw.network.response.ABBChinaPowerIntroduceResponse;
import com.ivymobi.abb.pw.network.response.ActivitiesResponse;
import com.ivymobi.abb.pw.network.response.DocumentListResponse;
import com.ivymobi.abb.pw.network.response.LocalIntrosResponse;
import com.ivymobi.abb.pw.network.response.SuccessCaseResponse;

import okhttp3.Callback;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by renguangkai on 2016/7/21.
 */
public interface NetService {

    /**
     * 业务介绍--ABB中国介绍
     * @return
     */
    @GET("/api/v2/712cb1d07f2a4651a9f7de14c987b3c1/entries/company_intro")
    Call<ABBChinaIntroduceResponse> getIntroduceOfABBChina(@Header("If-None-Match") String eTag);

    /**
     * 业务介绍--ABB中国电力业务介绍
     * @return
     */
    @GET("/api/v2/712cb1d07f2a4651a9f7de14c987b3c1/entries/power_intro")
    Call<ABBChinaPowerIntroduceResponse> getIntroduceOfABBChinaPower(@Header("If-None-Match") String eTag);

    /**
     * 业务介绍--本地企业
     * @return
     */
    @GET("/api/v2/712cb1d07f2a4651a9f7de14c987b3c1/entries/local_intro")
    Call<LocalIntrosResponse> getLocalIntros(@Header("If-None-Match") String eTag);

    /**
     * 业务介绍--成功案例
     * @return
     */
    @GET("/api/v2/712cb1d07f2a4651a9f7de14c987b3c1/entries/casestudy")
    Call<SuccessCaseResponse> getSuccessCase(@Header("If-None-Match") String eTag);

    /**
     * 业务介绍--精彩活动
     * @return
     */
    @GET("/api/v2/712cb1d07f2a4651a9f7de14c987b3c1/entries/activities")
    Call<ActivitiesResponse> getActivities(@Header("If-None-Match") String eTag);

    /**
     * 资料下载--资料中心--中文
     * @return
     */
    @GET("/api/v2/712cb1d07f2a4651a9f7de14c987b3c1/entries/documents_list_cn?type=all&sort=weight")
    Call<DocumentListResponse> getDocumentsOfChinese(@Header("If-None-Match") String eTag);

    /**
     * 业务介绍--资料中心--英文
     * @return
     */
    @GET("/api/v2/712cb1d07f2a4651a9f7de14c987b3c1/entries/documents_list_cn?type=all&sort=weight")
    Call<DocumentListResponse> getDocumentsOfEnglish(@Header("If-None-Match") String eTag);

}
