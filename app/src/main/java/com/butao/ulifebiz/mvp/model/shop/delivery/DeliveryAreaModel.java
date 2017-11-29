package com.butao.ulifebiz.mvp.model.shop.delivery;

/**
 * 创建时间 ：2017/9/14.
 * 编写人 ：bodong
 * 功能描述 ：sendUserName：配送人，sendPhone：配送手机号，managerPhone：商务经理手机号。。
 */

public class DeliveryAreaModel {
    private DeliveryModel distribution;

    public DeliveryModel getDistribution() {
        return distribution;
    }

    public void setDistribution(DeliveryModel distribution) {
        this.distribution = distribution;
    }

    public static  class  DeliveryModel{
        private String distance;
        private String price;
        private String startPrice;
        private String longTime;
        private String sendUserName;
        private String sendPhone;
        private String managerPhone;
        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStartPrice() {
            return startPrice;
        }

        public void setStartPrice(String startPrice) {
            this.startPrice = startPrice;
        }

        public String getLongTime() {
            return longTime;
        }

        public void setLongTime(String longTime) {
            this.longTime = longTime;
        }

        public String getSendUserName() {
            return sendUserName;
        }

        public void setSendUserName(String sendUserName) {
            this.sendUserName = sendUserName;
        }

        public String getSendPhone() {
            return sendPhone;
        }

        public void setSendPhone(String sendPhone) {
            this.sendPhone = sendPhone;
        }

        public String getManagerPhone() {
            return managerPhone;
        }

        public void setManagerPhone(String managerPhone) {
            this.managerPhone = managerPhone;
        }
    }
}
