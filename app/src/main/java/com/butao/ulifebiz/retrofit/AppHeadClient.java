package com.butao.ulifebiz.retrofit;


import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import com.butao.ulifebiz.BuildConfig;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.util.SysConst;
import com.butao.ulifebiz.util.TrustAllCerts;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请求方式
 */
public class AppHeadClient {
    public static Retrofit mRetrofit;
    public static String token = "";
    public static OkHttpClient.Builder builder;

    /**
     * 带头请求的
     *
     * @return
     */
    public static Retrofit Hearder() {
        CApplication cApplication = CApplication.getIntstance();
        if (!TextUtils.isEmpty(cApplication.getToken())) {
            token = cApplication.getToken();
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("url", "hearder+message====" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mRetrofit = null;
        if (mRetrofit == null) {
            builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(TrustAllCerts.createSSLSocketFactory());
            builder.hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier());

            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }
        /**
         * 请求头
         * @return
         */
        OkHttpClient httpClient =builder
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/json; charset=utf-8")
                                .addHeader("Authorization", "Basic " + token)
                                .build();
                        Log.i("url", "hearder==" + request.toString());
                        Log.i("url", "request-hearder====" + request.headers().toString());
                        Response proceed = chain.proceed(request);
                        int tryCount = 0;
                        while (!proceed.isSuccessful() && tryCount < 3) {
                            tryCount++;
                            proceed = chain.proceed(request);
                        }
                        Log.i("url", "proceed-hearder====" + proceed.headers().toString());
                        return proceed;
                    }
                }).addInterceptor(httpLoggingInterceptor)
                .build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(SysConst.HTTPSNEW_IP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient)
                    .build();
        }

        return mRetrofit;
    }

}
