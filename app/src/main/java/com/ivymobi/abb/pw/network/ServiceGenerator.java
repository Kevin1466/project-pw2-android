package com.ivymobi.abb.pw.network;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by renguangkai on 2016/7/21.
 */
public class ServiceGenerator {
    public static final String API_BASE_URL = "http://yangbentong.com/";
    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
    private static Retrofit.Builder builder= new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    public static <S> S createService(final Class<S> serviceClass) {
        final OkHttpClient okHttpClient = httpClientBuilder.build();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        //.header("If-None-Match", ApiCollection.oldEtagMap.get())
                        .header("Cache-Control", "public, max-age=31104000"); // 一年
                Request request = requestBuilder.build();
                Response originResponse = chain.proceed(request);
                String cacheControl = request.cacheControl().toString();
                return originResponse.newBuilder().header("Cache-Control", cacheControl).build();
            }
        });
        Retrofit retrofit = builder.client(okHttpClient).build();
        return retrofit.create(serviceClass);
    }
}
