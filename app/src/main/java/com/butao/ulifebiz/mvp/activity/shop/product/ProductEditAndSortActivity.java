package com.butao.ulifebiz.mvp.activity.shop.product;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.presenter.ProductPresenter;
import com.butao.ulifebiz.mvp.view.ProductsView;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/16.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ProductEditAndSortActivity extends MvpActivity<ProductPresenter> implements ProductsView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_wares)
    RecyclerView ryWares;
    ProductModel productModel = new ProductModel();
    CommonRecycleAdapter wareAdapter;
    List<ProductModel.WareModel> wareModels = new ArrayList<>();
    ProductModel.WareModel WareModel = new ProductModel.WareModel();
    int sortposition=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editandsort);
        ButterKnife.bind(this);
        txtTitle.setText("编辑商品");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mvpPresenter.loadPWare();
    }

    @Override
    protected ProductPresenter createPresenter() {
        return new ProductPresenter(this);
    }
    /**
     * 商品列表
     * @param model
     */
    @Override
    public void getProductListSuccess(BaseModel<ProductModel> model) {
        if ("200".equals(model.getCode())) {
            productModel = model.getData();
            if(productModel!=null){
                if(!JsonUtil.isEmpty(productModel.getWareList())){
                    for(int i = 0;i<productModel.getWareList().size();i++){
                        wareModels.addAll(productModel.getWareList().get(i).getWares());
                        initproductRyview();
                    }
                }
            }

        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }
    /**
     * 商品删除
     * @param model
     */
    @Override
    public void getProductdeleteSuccess(BaseModel model) {
    }
    /**
     * 商品排序
     * @param model
     */
    @Override
    public void getProductSortSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            wareModels.remove(sortposition);
            wareModels.add(0,WareModel);
            wareAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getProductStatusSuccess(BaseModel model) {

    }

    @Override
    public void getFail(String model) {
        ToastUtils.showLongToast(model);
    }
    /**
     * 商品适配器
     */
    private void initproductRyview(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductEditAndSortActivity.this);
        ryWares.setLayoutManager(linearLayoutManager);
        wareAdapter = new CommonRecycleAdapter<ProductModel.WareModel>(ProductEditAndSortActivity.this,R.layout.adapter_product,wareModels) {
            @Override
            protected void convert(ViewHolder holder, final ProductModel.WareModel wareModel, final int position) {
                if(wareModel!=null){
                    if(!TextUtils.isEmpty(wareModel.getImgUrl())){
                        holder.setImageRoundGlide(R.id.img_ware,wareModel.getImgUrl());
                    }else{
                        holder.setBackgroundRes(R.id.img_ware,R.mipmap.biz_zhanweitu);
                    }
                    if(!TextUtils.isEmpty(wareModel.getName())){
                        holder.setText(R.id.txt_warename,wareModel.getName());
                    }else{
                        holder.setText(R.id.txt_warename,"");
                    }
                    if(!TextUtils.isEmpty(wareModel.getKuSum())){
                        holder.setText(R.id.txt_stock,"库存"+wareModel.getKuSum());
                    }else{
                        holder.setText(R.id.txt_stock,"");
                    }
                    if(!TextUtils.isEmpty(wareModel.getTotalSum())){
                        holder.setText(R.id.txt_sell,"月销售"+wareModel.getTotalSum());
                    }else{
                        holder.setText(R.id.txt_sell,"");
                    }
                    if(!TextUtils.isEmpty(wareModel.getPrice())){
                        holder.setText(R.id.txt_price,"£"+wareModel.getPrice());
                    }else{
                        holder.setText(R.id.txt_price,"");
                    }
                    if(!TextUtils.isEmpty(wareModel.getStatus())){
                        if("0".equals(wareModel.getStatus())){
                            holder.setText(R.id.txt_stoptime,"非可售时间");
                        }else{
                            TextView txtStoptime = holder.getView(R.id.txt_stoptime);
                            Drawable drawable_n = getResources().getDrawable(R.mipmap.chucan_time);
                            drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                            txtStoptime.setCompoundDrawables(drawable_n, null, null, null);
                            txtStoptime.setCompoundDrawablePadding(10);
                            holder.setText(R.id.txt_stoptime,"可售时间");
                        }
                    }else{
                        holder.setText(R.id.txt_stoptime,"非可售时间");
                    }
                    holder.setVisible(R.id.txt_delete,false);
                    holder.setVisible(R.id.txt_edit,true);
                    //txt_stopsale
                    holder.setOnClickListener(R.id.txt_edit, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString("type","edit");
                            bundle.putString("Json",JsonUtil.toJson(wareModels.get(position)));
                            if(productModel!=null){
                                if(!JsonUtil.isEmpty(productModel.getWareList())){
                                    for(int i = 0;i<productModel.getWareList().size();i++){
                                        for(int j=0;j<productModel.getWareList().get(i).getWares().size();j++){
                                            if(wareModel.getId().equals(productModel.getWareList().get(i).getWares().get(j).getId())){
                                                bundle.putString("cId",productModel.getWareList().get(i).getCid());
                                                bundle.putString("cName",productModel.getWareList().get(i).getCname());
                                            }
                                        }
                                    }
                                }
                            }
                            gotoActivity(ProductEditAndSortActivity.this,AddProductActivity.class,bundle);
                        }
                    });
                    holder.setText(R.id.txt_stopsale,"置顶");
                    holder.setOnClickListener(R.id.txt_stopsale, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sortposition = position;
                            WareModel = wareModel;
                            mvpPresenter.loadProductSort(wareModel.getId());
                        }
                    });
                }
            }
        };
        ryWares.setAdapter(wareAdapter);
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}
