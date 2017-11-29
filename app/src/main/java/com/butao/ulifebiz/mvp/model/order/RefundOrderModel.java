package com.butao.ulifebiz.mvp.model.order;

import java.util.List;

/**
 * 创建时间 ：2017/9/19.
 * 编写人 ：bodong
 * 功能描述 ：1.createTime：创建时间
 2.sentTime：送达时间
 3.orderNo订单号
 4.address地址
 5.statusName：订单状态
 6.orderName：订单名称
 7.orderId：订单ID
 8.totalPrice：金额
 9.refundNote：退款备注
 9.urgeId：催单数据行ID(操作催单时使用)
 */

public class RefundOrderModel {
    private List<RFOrderModel> orderList;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RFOrderModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<RFOrderModel> orderList) {
        this.orderList = orderList;
    }

    public static class RFOrderModel {
        private String createTime;
        private String sentTime;
        private String orderNo;
        private String address;
        private String statusName;
        private String orderName;
        private String totalPrice;
        private String urgeId;
        private String refundNote;
        private String userNote;
        private String orderId ;
        private String phone;
        private boolean expand;
        private String note;
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

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getUrgeId() {
            return urgeId;
        }

        public void setUrgeId(String urgeId) {
            this.urgeId = urgeId;
        }

        public String getRefundNote() {
            return refundNote;
        }

        public void setRefundNote(String refundNote) {
            this.refundNote = refundNote;
        }

        public String getUserNote() {
            return userNote;
        }

        public void setUserNote(String userNote) {
            this.userNote = userNote;
        }

        public boolean isExpand() {
            return expand;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
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
