package com.butao.ulifebiz.mvp.model;

/**
 * 创建时间 ：2017/9/19.
 * 编写人 ：bodong
 * 功能描述 ：1.orderNum：订单完成数
 2.orderPrice：流水
 3.oldMember：老顾客
 4.unOldMember：新顾客
 5.orderPriceAvg：平均消费
 */

public class BriefModel {
    private String orderNum;
    private String orderPrice;
    private String oldMember;
    private String unOldMember;
    private String orderPriceAvg;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOldMember() {
        return oldMember;
    }

    public void setOldMember(String oldMember) {
        this.oldMember = oldMember;
    }

    public String getUnOldMember() {
        return unOldMember;
    }

    public void setUnOldMember(String unOldMember) {
        this.unOldMember = unOldMember;
    }

    public String getOrderPriceAvg() {
        return orderPriceAvg;
    }

    public void setOrderPriceAvg(String orderPriceAvg) {
        this.orderPriceAvg = orderPriceAvg;
    }
}
