package com.butao.ulifebiz.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/9/13.
 * 编写人 ：bodong
 * 功能描述 ：1.newOrderNum:新订单数
 2.newOrders：订单列表
 3.orderWarn：订单提醒
 4.orderNum：已完成数量
 5.orderPrice：流水
 6.createTime：创建时间
 7.sentTime：送达时间
 8.orderNo订单号
 9.address地址
 10.statusName：订单状态
 11.orderName：订单名称
 12.orderId：订单ID
 13.totalPrice：金额
 */

public class HomeTabModel {
    private String NewOrderNum;
    private List<HomeOrders> NewOrders;
    private List<HomeOrders> confirmOrders;
    private String orderWarn;
    private String orderNum;
    private String orderPrice;
    public String getNewOrderNum() {
        return NewOrderNum;
    }

    public void setNewOrderNum(String newOrderNum) {
        NewOrderNum = newOrderNum;
    }

    public List<HomeOrders> getNewOrders() {
        return NewOrders;
    }

    public void setNewOrders(List<HomeOrders> newOrders) {
        NewOrders = newOrders;
    }

    public String getOrderWarn() {
        return orderWarn;
    }

    public void setOrderWarn(String orderWarn) {
        this.orderWarn = orderWarn;
    }

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

    public List<HomeOrders> getConfirmOrders() {
        return confirmOrders;
    }

    public void setConfirmOrders(List<HomeOrders> confirmOrders) {
        this.confirmOrders = confirmOrders;
    }

    public static class HomeOrders{
        private String createTime;
        private String sentTime;
        private String orderNo;
        private String address;
        private String statusName;
        private String orderName;
        private String orderId;
        private String totalPrice;
        private String phone;
        private String note;
        private String sendNote;
        private boolean expand;
        private String status;
        private String zipcode;
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

        public boolean isExpand() {
            return expand;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSendNote() {
            return sendNote;
        }

        public void setSendNote(String sendNote) {
            this.sendNote = sendNote;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
