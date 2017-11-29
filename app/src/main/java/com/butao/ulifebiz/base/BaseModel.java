package com.butao.ulifebiz.base;

/**
 * Created by bodong on 2017/3/7.
 */
public class BaseModel<M> {
    private String code;
    private String expires;
    private M data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public M getData() {
        return data;
    }

    public void setData(M data) {
        this.data = data;
    }
}
