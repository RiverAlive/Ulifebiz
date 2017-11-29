package com.butao.ulifebiz.mvp.model.shop;

import java.util.List;

/**
 * 创建时间 ：2017/9/26.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class AccountModel {
    private String  totaPrice;
    private String orderNum;
    private List<AccountOrder> orderList;
    private String error;
    public String getTotaPrice() {
        return totaPrice;
    }

    public void setTotaPrice(String totaPrice) {
        this.totaPrice = totaPrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public List<AccountOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<AccountOrder> orderList) {
        this.orderList = orderList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class AccountOrder {
        private String createTime;
        private String sentTime;
        private String orderNo;
        private String address;
        private String statusName;
        private String orderName;
        private String userNote;
        private String orderId;
        private String totalPrice;
        private String phone;
        private String note;
        private String zipcode;
private boolean expend;
        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSentTime() {
            return sentTime;
        }

        public void setSentTime(String sentTime) {
            this.sentTime = sentTime;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getUserNote() {
            return userNote;
        }

        public void setUserNote(String userNote) {
            this.userNote = userNote;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public boolean isExpend() {
            return expend;
        }

        public void setExpend(boolean expend) {
            this.expend = expend;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
    }

}
