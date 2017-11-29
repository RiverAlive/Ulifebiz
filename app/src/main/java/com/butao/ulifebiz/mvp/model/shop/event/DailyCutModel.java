package com.butao.ulifebiz.mvp.model.shop.event;

import java.util.List;

/**
 * 创建时间 ：2017/9/26.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class DailyCutModel {
    private String token;
    private String endDay;
    private String startDay;
    private String startTime;
    private String endTime;
    private String remark;
    private int isSend;
    private int wareNum;
    private int orderNum;
    private List<DailyWareModel>  dayCutWareForms;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public int getWareNum() {
        return wareNum;
    }

    public void setWareNum(int wareNum) {
        this.wareNum = wareNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public List<DailyWareModel> getDayCutWareForms() {
        return dayCutWareForms;
    }

    public void setDayCutWareForms(List<DailyWareModel> dayCutWareForms) {
        this.dayCutWareForms = dayCutWareForms;
    }

    public static class DailyWareModel{
        private String wareId;
        private String warePrice;
        private String discountPrice;
        private String wareName;
        private String imgUrl;
        public String getWareId() {
            return wareId;
        }

        public void setWareId(String wareId) {
            this.wareId = wareId;
        }

        public String getWarePrice() {
            return warePrice;
        }

        public void setWarePrice(String warePrice) {
            this.warePrice = warePrice;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getWareName() {
            return wareName;
        }

        public void setWareName(String wareName) {
            this.wareName = wareName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
