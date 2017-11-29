package com.butao.ulifebiz.mvp.view;

import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BaseView;
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.model.shop.product.WareImgModel;

/**
 * 创建时间 ：2017/9/11.
 * 编写人 ：bodong
 * 功能描述 ：36.cid:分类ID
 37.cname:类别名称
 38.wares:商品集合
 39.id:商品ID
 40.name:商品名称
 41.price:商品价格
 42.kuSum：库存
 43.totalSum：销量
 44.imgUrl:图片路径
 */
public interface AddProductView extends BaseView {
    void getProductClassSuccess(BaseModel<ProdcutClassModel> model);
    void getAddProductSuccess(BaseModel model);
    void getIMGSuccess(BaseModel<WareImgModel> model);
    void getFail(String model);
}
