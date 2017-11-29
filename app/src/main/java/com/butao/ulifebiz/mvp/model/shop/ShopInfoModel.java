package com.butao.ulifebiz.mvp.model.shop;

/**
 * 创建时间 ：2017/9/26.
 * 编写人 ：bodong
 * 功能描述 ：1.dayOrderPrice:今天营业额
 2.unDayOrderPrice：昨天营业额
 3.unDayOrderNum:昨天订单数
 4.dayOrderNum：今天订单数
 */

public class ShopInfoModel {
   private String dayOrderPrice;
    private String unDayOrderPrice;
    private String unDayOrderNum;
    private String dayOrderNum;
private String error;
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDayOrderPrice() {
        return dayOrderPrice;
    }

    public void setDayOrderPrice(String dayOrderPrice) {
        this.dayOrderPrice = dayOrderPrice;
    }

    public String getUnDayOrderPrice() {
        return unDayOrderPrice;
    }

    public void setUnDayOrderPrice(String unDayOrderPrice) {
        this.unDayOrderPrice = unDayOrderPrice;
    }

    public String getUnDayOrderNum() {
        return unDayOrderNum;
    }

    public void setUnDayOrderNum(String unDayOrderNum) {
        this.unDayOrderNum = unDayOrderNum;
    }

    public String getDayOrderNum() {
        return dayOrderNum;
    }

    public void setDayOrderNum(String dayOrderNum) {
        this.dayOrderNum = dayOrderNum;
    }
}
