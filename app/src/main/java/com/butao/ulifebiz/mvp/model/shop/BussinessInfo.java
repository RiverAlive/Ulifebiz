package com.butao.ulifebiz.mvp.model.shop;

import java.util.List;

/**
 * 创建时间 ：2017/9/27.
 * 编写人 ：bodong
 * 功能描述 ：sendPhone：配送电话
 status：状态
 automatic：是否自动接单1：自动接单，0：手动
 packPrice：打包费
 doBusinessTimes：营业时间
 */

public class BussinessInfo {
    private String sendPhone;
    private String status;
    private String automatic;
    private String packPrice;
    private int  isSound;
    private List<BusinessTime> doBusinessTimes;

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAutomatic() {
        return automatic;
    }

    public void setAutomatic(String automatic) {
        this.automatic = automatic;
    }

    public String getPackPrice() {
        return packPrice;
    }

    public void setPackPrice(String packPrice) {
        this.packPrice = packPrice;
    }

    public List<BusinessTime> getDoBusinessTimes() {
        return doBusinessTimes;
    }

    public void setDoBusinessTimes(List<BusinessTime> doBusinessTimes) {
        this.doBusinessTimes = doBusinessTimes;
    }

    public int getIsSound() {
        return isSound;
    }

    public void setIsSound(int isSound) {
        this.isSound = isSound;
    }

    public static class BusinessTime{
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

}
