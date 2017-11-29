package com.butao.ulifebiz.retrofit;


import android.util.Log;

import java.io.IOException;

import com.butao.ulifebiz.BuildConfig;
import com.butao.ulifebiz.util.SysConst;
import com.butao.ulifebiz.util.TrustAllCerts;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请求方式
 */
public class AppClient {
    public static Retrofit mRetrofit;
    public static String token ="" ;
    public static OkHttpClient.Builder builder;
    /**
     * 不带头请求的
     * @return
     */
    public static Retrofit retrofit() {
        if (mRetrofit == null) {
           builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(TrustAllCerts.createSSLSocketFactory());
            builder.hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier());

            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor  = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(SysConst.HTTPSNEW_IP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(InterceptorClient())
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 不带头请求的拦截器
     * @return
     */
    public static OkHttpClient InterceptorClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("url", "message====" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = builder.addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.i("url", request.toString());
                        Log.i("url", "request====" + request.headers().toString());
                        okhttp3.Response proceed = chain.proceed(request);
                        Log.i("url", "proceed====" + proceed.headers().toString());
                        return proceed;
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();
        return okHttpClient;
    }

}
