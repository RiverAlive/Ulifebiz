package com.butao.ulifebiz.mvp.model.shop;

import com.butao.ulifebiz.mvp.model.shop.event.CutBuiltModel;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;

import java.util.List;

/**
 * 创建时间 ：2017/9/25.
 * 编写人 ：bodong
 * 功能描述 ：
 * 2.dayCuts：天天特价
 3.imgUrl：门店图片
 4.fullCuts：满减
 5.storeName：门店名称
 6.sendPrice：配送费
 7.startPrice：起送价
 8.firstCutPrice：首次减免
 9.longTime：配送时长
 exchangeRate:汇率
 */

public class ShopStoreModel {

    private String imgUrl;
    private List<DayCutModel> dayCuts;
    private FullDownModel fullCuts;
    private String storeName;
    private String sendPrice;
    private String startPrice;
    private String firstCutPrice;
    private String longTime;
    private double exchangeRate;

    public List<DayCutModel> getDayCuts() {
        return dayCuts;
    }

    public void setDayCuts(List<DayCutModel> dayCuts) {
        this.dayCuts = dayCuts;
    }

    public FullDownModel getFullCuts() {
        return fullCuts;
    }

    public void setFullCuts(FullDownModel fullCuts) {
        this.fullCuts = fullCuts;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(String sendPrice) {
        this.sendPrice = sendPrice;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getFirstCutPrice() {
        return firstCutPrice;
    }

    public void setFirstCutPrice(String firstCutPrice) {
        this.firstCutPrice = firstCutPrice;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static class DayCutModel{
        private String id;
        private List<CutBuiltModel.DayCutWare> wares;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<CutBuiltModel.DayCutWare> getWares() {
            return wares;
        }

        public void setWares(List<CutBuiltModel.DayCutWare> wares) {
            this.wares = wares;
        }
    }
    public static class FullCutModel{
        private String id;
        private List<CutBuiltModel.FullCut> fullCutPrices;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<CutBuiltModel.FullCut> getFullCutPrices() {
            return fullCutPrices;
        }

        public void setFullCutPrices(List<CutBuiltModel.FullCut> fullCutPrices) {
            this.fullCutPrices = fullCutPrices;
        }
    }
}
