package com.butao.ulifebiz.mvp.model.shop.event;

import java.util.List;

/**
 * 创建时间 ：2017/9/25.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class FullDownModel {
    private String token;
    private String endDay;
    private String startDay;
    private String startTime;
    private String endTime;
    private List<FullCutPrice> fullCutPrices;

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

    public List<FullCutPrice> getFullCutPrices() {
        return fullCutPrices;
    }

    public void setFullCutPrices(List<FullCutPrice> fullCutPrices) {
        this.fullCutPrices = fullCutPrices;
    }

    public static class FullCutPrice{
        private String cutPrice;
        private String fullPrice;

        public String getCutPrice() {
            return cutPrice;
        }

        public void setCutPrice(String cutPrice) {
            this.cutPrice = cutPrice;
        }

        public String getFullPrice() {
            return fullPrice;
        }

        public void setFullPrice(String fullPrice) {
            this.fullPrice = fullPrice;
        }
    }
}
