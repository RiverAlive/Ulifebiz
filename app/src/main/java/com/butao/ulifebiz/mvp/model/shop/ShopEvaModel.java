package com.butao.ulifebiz.mvp.model.shop;

import java.util.List;

/**
 * 创建时间 ：2017/9/28.
 * 编写人 ：bodong
 * 功能描述 ：1.list：评论列表
 2.imgUrl：头像
 3.createTime：时间
 4.remark：内容
 5.userId：用户ID
 6.Score:打分
 7.reviewerName:评论人名称
 8.reviewerId：评论数据行ID
 9.storeId：门店ID
 10.reviewerReplies：回复列表
 11.unReviewerName：被回复人名称
 */

public class ShopEvaModel {
    private List<ShopEva> list;

    public List<ShopEva> getList() {
        return list;
    }

    public void setList(List<ShopEva> list) {
        this.list = list;
    }

    public static class ShopEva {
        private String imgUrl;
        private String createTime;
        private List<EvaModel> reviewerReplies;
        private String remark;
        private String userId;
        private String score;
        private String reviewerName;
        private String reviewerId;
        private String storeId;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<EvaModel> getReviewerReplies() {
            return reviewerReplies;
        }

        public void setReviewerReplies(List<EvaModel> reviewerReplies) {
            this.reviewerReplies = reviewerReplies;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getReviewerName() {
            return reviewerName;
        }

        public void setReviewerName(String reviewerName) {
            this.reviewerName = reviewerName;
        }

        public String getReviewerId() {
            return reviewerId;
        }

        public void setReviewerId(String reviewerId) {
            this.reviewerId = reviewerId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }
    }


    public static class EvaModel {
        private String imgUrl;
        private String remark;
        private String reviewerName;
        private String unReviewerName;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReviewerName() {
            return reviewerName;
        }

        public void setReviewerName(String reviewerName) {
            this.reviewerName = reviewerName;
        }

        public String getUnReviewerName() {
            return unReviewerName;
        }

        public void setUnReviewerName(String unReviewerName) {
            this.unReviewerName = unReviewerName;
        }
    }
}
