package com.butao.ulifebiz.mvp.model.shop.product;

/**
 * 创建时间 ：2017/7/13.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class WareImgModel {
    private ImgModel uploadFile = new ImgModel();
    private String error;

    public ImgModel getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(ImgModel uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public static class ImgModel{
    private String fileName;
    private String path;
    private String uploadFileId;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUploadFileId() {
        return uploadFileId;
    }

    public void setUploadFileId(String uploadFileId) {
        this.uploadFileId = uploadFileId;
    }


    }
}
