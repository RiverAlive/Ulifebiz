package com.butao.ulifebiz.mvp.model.shop.setting;

import java.util.List;

/**
 * 创建时间 ：2017/9/12.
 * 编写人 ：bodong
 * 功能描述 ：
 * 18.startTime:开始时间
 19.endTime：结束时间
 20.weeks：周几，逗号分开
 21.id:id
 */

public class OperatTimeModel {
    private List<BTimeModel> weeks;

    public List<BTimeModel> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<BTimeModel> weeks) {
        this.weeks = weeks;
    }

    public static class BTimeModel {
        private String startTime;
        private String id;
        private String endTime;
        private String weeks;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getWeeks() {
            return weeks;
        }

        public void setWeeks(String weeks) {
            this.weeks = weeks;
        }
    }
}
