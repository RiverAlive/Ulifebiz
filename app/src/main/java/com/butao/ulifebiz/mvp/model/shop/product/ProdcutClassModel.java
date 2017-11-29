package com.butao.ulifebiz.mvp.model.shop.product;

import java.util.List;

/**
 * 创建时间 ：2017/9/15.
 * 编写人 ：bodong
 * 功能描述 ：32.id:分类ID
 33.name:类别名称
 */

public class ProdcutClassModel {
    private List<PClassMosel> categoryList;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    public List<PClassMosel> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<PClassMosel> categoryList) {
        this.categoryList = categoryList;
    }

    public static  class PClassMosel{
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
