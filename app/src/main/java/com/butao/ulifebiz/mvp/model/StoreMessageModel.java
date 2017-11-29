package com.butao.ulifebiz.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/9/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class StoreMessageModel {
    private List<MessageModel> list;
    private String error;

    public List<MessageModel> getList() {
        return list;
    }

    public void setList(List<MessageModel> list) {
        this.list = list;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class MessageModel{
         private String id;
         private String title;
         private String remark;
         private String creatTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }
    }
}
