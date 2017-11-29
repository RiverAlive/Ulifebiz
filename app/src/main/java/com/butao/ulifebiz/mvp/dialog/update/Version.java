package com.butao.ulifebiz.mvp.dialog.update;

/**
 * Created by User on 2016/6/28.
 */
public class Version {
    private String path;//路径
    private  String versionNo;//版本号
    private  String remark;//版本内容
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
