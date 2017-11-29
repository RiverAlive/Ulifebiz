package com.butao.ulifebiz.mvp.model.shop.event;

import java.util.List;

/**
 * 创建时间 ：2017/9/20.
 * 编写人 ：bodong
 * 功能描述 ：1.dayCuts:天天特价
 2.startTime：开始时间
 3.endTime：结束时间
 4.startDay：开始日期
 5.endDay：结束日期
 6.wares：商品列表
 7.wareName：商品名称
 8.discountPrice：优惠价
 9.warePrice:原价
 10.wareId：商品ID
 11.fullCuts：满减
 12.fullCutPrices：满减策略
 13.fullPrice：满多少
 14.cutPrice：减多少
 15.firstCutPrice：首次下单减免
 */

public class CutBuiltModel {
    private List<DayFullCut> dayCuts;
    private List<DayFullCut> fullCuts;
    private String firstCutPrice;
    private String error;
    public List<DayFullCut> getDayCuts() {
        return dayCuts;
    }

    public void setDayCuts(List<DayFullCut> dayCuts) {
        this.dayCuts = dayCuts;
    }

    public List<DayFullCut> getFullCuts() {
        return fullCuts;
    }

    public void setFullCuts(List<DayFullCut> fullCuts) {
        this.fullCuts = fullCuts;
    }

    public String getFirstCutPrice() {
        return firstCutPrice;
    }

    public void setFirstCutPrice(String firstCutPrice) {
        this.firstCutPrice = firstCutPrice;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class DayFullCut{
        private String startTime;
        private String id;
        private String endDay;
        private String startDay;
        private String endTime;
        private List<DayCutWare> wares;
        private List<FullCut> fullCutPrices;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public List<DayCutWare> getWares() {
            return wares;
        }

        public void setWares(List<DayCutWare> wares) {
            this.wares = wares;
        }

        public List<FullCut> getFullCutPrices() {
            return fullCutPrices;
        }

        public void setFullCutPrices(List<FullCut> fullCutPrices) {
            this.fullCutPrices = fullCutPrices;
        }
    }
    public static class DayCutWare{
        private String wareName;
        private String discountPrice;
        private String wareId;
        private String warePrice;

        public String getWareName() {
            return wareName;
        }

        public void setWareName(String wareName) {
            this.wareName = wareName;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

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
    }
    public static class FullCut{
        private String fullPrice;
        private String cutPrice;

        public String getFullPrice() {
            return fullPrice;
        }

        public void setFullPrice(String fullPrice) {
            this.fullPrice = fullPrice;
        }

        public String getCutPrice() {
            return cutPrice;
        }

        public void setCutPrice(String cutPrice) {
            this.cutPrice = cutPrice;
        }
    }
}
