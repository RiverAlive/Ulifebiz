package com.butao.ulifebiz.retrofit;


import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import com.butao.ulifebiz.util.SysConst;

/**
 * 接口返回调用
 * @param <M>
 */
public abstract class ApiCallback<M> extends Subscriber<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable e) {
         e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            if (code == 401) {
//                CApplication cApplication = CApplication.getIntstance();
//                cApplication.sendBroadcast(new Intent("GoLogin"));
//                msg = "AuthToken异常，请重新登录";
                onFinish();
            }
            onFailure(msg);
        }else if(e instanceof ConnectException ||e instanceof SocketTimeoutException){
            onFailure("服务器断开连接，请检查服务器"+ SysConst.HTTPSNEW_IP);
        } else {
            onFailure(e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(M model) {
            onSuccess(model);
    }

    @Override
    public void onCompleted() {
        onFinish();
    }
}
