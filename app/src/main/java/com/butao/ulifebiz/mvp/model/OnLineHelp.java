package com.butao.ulifebiz.mvp.model;

import java.util.List;

/**
 * 创建时间 ：2017/9/27.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class OnLineHelp {
    private List<HelpModel> helps;

    public List<HelpModel> getHelps() {
        return helps;
    }

    public void setHelps(List<HelpModel> helps) {
        this.helps = helps;
    }

    public static class HelpModel{
        private String remark;
        private boolean select;
        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }
}
