package com.butao.ulifebiz.mvp.model;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class BusinessLoginModel {
    //3.storeName:门店名称
    //4.status：状态
    //5.statusName:状态名称
    //6.token：token
    //7.Img门店头像
    //storeId:门店ID
    public BLoginModel store;
private String error;
    public BLoginModel getStore() {
        return store;
    }

    public void setStore(BLoginModel store) {
        this.store = store;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class BLoginModel {
        private String storeName;
        private String status;
        private String token;
        private String statusName;
        private String img;
        private String storeId;

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }
    }
}
