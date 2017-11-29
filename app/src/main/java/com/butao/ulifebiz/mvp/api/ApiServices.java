package com.butao.ulifebiz.mvp.api;


import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.mvp.model.BriefModel;
import com.butao.ulifebiz.mvp.model.BusinessLoginModel;
import com.butao.ulifebiz.mvp.model.HomeTabModel;
import com.butao.ulifebiz.mvp.model.OnLineHelp;
import com.butao.ulifebiz.mvp.model.StoreMessageModel;
import com.butao.ulifebiz.mvp.model.order.RefundOrderModel;
import com.butao.ulifebiz.mvp.model.shop.AccountModel;
import com.butao.ulifebiz.mvp.model.shop.BussinessInfo;
import com.butao.ulifebiz.mvp.model.shop.ShopEvaModel;
import com.butao.ulifebiz.mvp.model.shop.ShopInfoModel;
import com.butao.ulifebiz.mvp.model.shop.ShopStoreModel;
import com.butao.ulifebiz.mvp.model.shop.delivery.DeliveryAreaModel;
import com.butao.ulifebiz.mvp.model.shop.event.CutBuiltModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.model.shop.product.WareImgModel;
import com.butao.ulifebiz.mvp.model.shop.setting.OperatTimeModel;

import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 *不带头请求的请求
 */
public interface ApiServices {


    @POST("businessLogin.do")//商家登录
    Observable<BaseModel<BusinessLoginModel>> loadLogin(@QueryMap Map<String, String> map);

    @POST("user/changePassword.do")//修改密码
    Observable<BaseModel> loadChangePwd(@QueryMap Map<String, String> map);

    @POST("user/getWeeks.do")//获取营业时间
    Observable<BaseModel<OperatTimeModel>> loadOperatTime(@QueryMap Map<String, String> map);

    @POST("user/delWeeks.do")//营业时间删除
    Observable<BaseModel> loadDeleteTime(@QueryMap Map<String, String> map);

    @POST("user/homeDate.do")//商家版首页
    Observable<BaseModel<HomeTabModel>> loadHomeTab(@QueryMap Map<String, String> map);

    @POST("user/upWeeks.do")//修改或新增营业时间
    Observable<BaseModel> loadAddOTime(@QueryMap Map<String, String> map);

    @POST("user/getDistribution.do")//门店配送范围
    Observable<BaseModel<DeliveryAreaModel>> loadDelivery(@QueryMap Map<String, String> map);

    @POST("storeWare.do")//商品列表
    Observable<BaseModel<ProductModel>> loadProduct(@QueryMap Map<String, String> map);

    @POST("user/delStoreWare.do")//删除商品
    Observable<BaseModel> deleteWare(@QueryMap Map<String, String> map);

    @Multipart
    @POST("upload_file.do")//图片上传
    Observable<BaseModel<WareImgModel>> loadPushImg(@Part MultipartBody.Part imgValue);

    @POST("user/upStoreWare.do")//添加商品
    Observable<BaseModel> AddUpdateWare(@QueryMap Map<String, String> map);

    @POST("storeCategory.do")//商品分类
    Observable<BaseModel<ProdcutClassModel>> getWareclass(@QueryMap Map<String, String> map);

    @POST("user/upSortStoreWare.do")//商品排序，置顶
    Observable<BaseModel> loadProductSort(@QueryMap Map<String, String> map);

    @POST("user/upStoreCategory.do")//7.修改或新增 商品分类
    Observable<BaseModel> AddProductClass(@QueryMap Map<String, String> map);

    @POST("user/delStoreCategory.do")//8.删除 商品分类
    Observable<BaseModel> DelteProductClass(@QueryMap Map<String, String> map);

    @POST("user/upSortStoreCategory.do")//分类排序，置顶
    Observable<BaseModel> loadProductClassSort(@QueryMap Map<String, String> map);

    @POST("user/upWareStatus.do")//停售起售
    Observable<BaseModel> loadProductStatus(@QueryMap Map<String, String> map);

    @POST("user/reOrderList.do")//退款订单列表
    Observable<BaseModel<RefundOrderModel>> loadRefoundOrder(@QueryMap Map<String, String> map);

    @POST("user/order_refund.do")//退款订单列表
    Observable<BaseModel> loadRefound(@QueryMap Map<String, String> map);

    @POST("user/orderUrgesReply.do")//催单回复
    Observable<BaseModel> loadRemind(@QueryMap Map<String, String> map);

    @POST("user/orderUrges.do")//催单订单列表
    Observable<BaseModel<RefundOrderModel>> loadRemindOrder(@QueryMap Map<String, String> map);

    @POST("user/briefingInfo.do")//簡報 信息
    Observable<BaseModel<BriefModel>> loadBriefModel(@QueryMap Map<String, String> map);

    @POST("user/orderListSuccess.do")//订单信息
    Observable<BaseModel<RefundOrderModel>> loadOrderList(@QueryMap Map<String, String> map);

    @POST("user/cutList.do")//优惠列表
    Observable<BaseModel<CutBuiltModel>> loadCutList(@QueryMap Map<String, String> map);

    @POST("storeInfo.do")//门店信息
    Observable<BaseModel<ShopStoreModel>> loadShopStore(@QueryMap Map<String, String> map);

    @POST("json/upFullCut.do")//满减优惠活动
    Observable<BaseModel> loadFullDown(@Body RequestBody map);

    @POST("json/dayCut.do")//天天特价活动
    Observable<BaseModel> loadDayCut(@Body RequestBody map);

    @POST("user/dayOrderNum.do")//32.获取当日和昨天数据
    Observable<BaseModel<ShopInfoModel>> loadShopInfo(@QueryMap Map<String, String> map);

    @POST("storeRegister.do")//门店注册
    Observable<BaseModel> loadStoreRegister(@QueryMap Map<String, String> map);

    @POST("validCode.do")//门店注册获取验证码
    Observable<BaseModel> loadValidCode(@QueryMap Map<String, String> map);

    @POST("user/orderListEnd.do")//门店注册获取验证码
    Observable<BaseModel<AccountModel>> loadAccountrder(@QueryMap Map<String, String> map);

    @POST("user/cutDel.do")//删除活动
    Observable<BaseModel> loadLookDeleteEvent(@QueryMap Map<String, String> map);

    @POST("getManagePhone.do")//联系我们电话
    Observable<BaseModel<Map<String, String>>> loadContactPhone();

    @POST("insertFeedBack.do")//新增反馈
    Observable<BaseModel> loadFeedBack(@QueryMap Map<String, String> map);

    @POST("insertHelp.do")//新增在线帮助
    Observable<BaseModel> loadONHelp(@QueryMap Map<String, String> map);

    @POST("insertCallus.do.do")//新增联系我们
    Observable<BaseModel> loadCallus(@QueryMap Map<String, String> map);

    @POST("getHelps.do")//在线帮助列表
    Observable<BaseModel<OnLineHelp>> loadHelps();

    @POST("getStoreBussinessInfo.do")//获取门店经营信息
    Observable<BaseModel<BussinessInfo>> loadBussinessInfo(@QueryMap Map<String, String> map);

    @POST("user/upStoreAutomatic.do")//修改自动接单
    Observable<BaseModel> loadStoreAutomatic(@QueryMap Map<String, String> map);

    @POST("user/upStoreStatus.do")//48.修改状态
    Observable<BaseModel> loadStoreStatus(@QueryMap Map<String, String> map);


    @POST("user/upStoreSound.do")//修改语音提示  isSound 1提示 0关闭
    Observable<BaseModel> loadStoreSound(@QueryMap Map<String, String> map);

    @POST("reviewerList.do")//用户评论列表
    Observable<BaseModel<ShopEvaModel>> loadShopEvas(@QueryMap Map<String, String> map);

    @POST("reviewerListStore.do")//门店评论列表
    Observable<BaseModel<ShopEvaModel>> loadShopStoreEvas(@QueryMap Map<String, String> map);

    @POST("user/storeMassages.do")//消息列表
    Observable<BaseModel<StoreMessageModel>> loadStoreMassages(@QueryMap Map<String, String> map);

    @POST("reviewerReply.do")//回复，用户版商家版共用
    Observable<BaseModel> loadStoreReply(@QueryMap Map<String, String> map);

    @POST("user/upStoreInfo.do")//信息补全
    Observable<BaseModel> loadStoreInfo(@QueryMap Map<String, String> map);

    @POST("user/acceptOrder.do")//商家接单
    Observable<BaseModel> loadacceptOrder(@QueryMap Map<String, String> map);

    @POST("user/upStorePhone.do")//48.修改手机
    Observable<BaseModel> loadModifyPhone(@QueryMap Map<String, String> map);

    @POST("user/upStorePackPrice.do")//48.修改手机
    Observable<BaseModel> loadModifyBoxFee(@QueryMap Map<String, String> map);

    @POST("user/upStoreFirstCut.do")//59.满减设置
    Observable<BaseModel> loadModifyFirst(@QueryMap Map<String, String> map);

    @POST("user/upStoreSendInfo.do")//59.满减设置
    Observable<BaseModel> loadModifyDeliery(@QueryMap Map<String, String> map);

}
