package com.butao.ulifebiz.mvp.api;


import retrofit2.http.POST;
import rx.Observable;

/**
 *带头请求的请求
 */
public interface ApiHeaderServices {


    @POST("index")//老板首页
    Observable<Object> loadBossMain();
}
