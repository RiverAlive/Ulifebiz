package com.butao.ulifebiz.mvp.model.shop.product;

import java.util.List;

/**
 * 创建时间 ：2017/9/15.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ProductModel {
    private List<PWareModel> wareList;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public List<PWareModel> getWareList() {
        return wareList;
    }

    public void setWareList(List<PWareModel> wareList) {
        this.wareList = wareList;
    }

    public static class PWareModel{
        private String cname;
        private String cid;
        private List<WareModel> wares;

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public List<WareModel> getWares() {
            return wares;
        }

        public void setWares(List<WareModel> wares) {
            this.wares = wares;
        }
    }
    public static class WareModel{
        private String id;
        private String kuSum;
        private String totalSum;
        private String price;
        private String imgUrl;
        private String name;
        private String mainImg;
        private String status ;// =0时 为非可售时间
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKuSum() {
            return kuSum;
        }

        public void setKuSum(String kuSum) {
            this.kuSum = kuSum;
        }

        public String getTotalSum() {
            return totalSum;
        }

        public void setTotalSum(String totalSum) {
            this.totalSum = totalSum;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMainImg() {
            return mainImg;
        }

        public void setMainImg(String mainImg) {
            this.mainImg = mainImg;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
