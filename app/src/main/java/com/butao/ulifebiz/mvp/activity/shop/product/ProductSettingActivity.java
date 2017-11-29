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
import com.butao.ulifebiz.mvp.dialog.ProductClassSettingDialog;
import com.butao.ulifebiz.mvp.dialog.ProductListener;
import com.butao.ulifebiz.mvp.dialog.ProductSettingDialog;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.presenter.ProductPresenter;
import com.butao.ulifebiz.mvp.view.ProductsView;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/4.
 * 编写人 ：bodong
 * 功能描述 ：商品设置
 */
public class ProductSettingActivity extends MvpActivity<ProductPresenter> implements ProductsView {

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    @Bind(R.id.ry_class)
    RecyclerView ryClass;
    @Bind(R.id.ry_wares)
    RecyclerView ryWares;
    ProductModel productModel = new ProductModel();
    CommonRecycleAdapter classAdapter;
    CommonRecycleAdapter wareAdapter;
    int classselect=-1;
    int waredelete=-1;
    String status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_setting);
        ButterKnife.bind(this);
        txtTitle.setText("商品管理");
        txtRightTitle.setText("浏览商店");
        Drawable drawable_n = getResources().getDrawable(R.mipmap.eye);
        drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
        txtRightTitle.setCompoundDrawables(drawable_n, null, null, null);
        txtRightTitle.setCompoundDrawablePadding(10);
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
            initClassRyView();
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
        if ("200".equals(model.getCode())) {
            productModel.getWareList().get(classselect).getWares().remove(waredelete);
            wareAdapter.notifyDataSetChanged();
            ToastUtils.showLongToast("商品删除成功");
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 停售起售
     *
     * @param model
     */
    @Override
    public void getProductStatusSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            productModel.getWareList().get(classselect).getWares().get(waredelete).setStatus(status);
            ToastUtils.showLongToast("设置成功");
            wareAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }
    /**
     * 商品排序
     * @param model
     */
    @Override
    public void getProductSortSuccess(BaseModel model) {

    }

    @Override
    public void getFail(String model) {
        ToastUtils.showLongToast(model);
    }

    /**
     * 分类适配器
     */
    private void initClassRyView() {
        if(!JsonUtil.isEmpty(productModel.getWareList())) {
            classselect=0;
            initproductRyview();
            LinearLayoutManager layoutManager = new LinearLayoutManager(ProductSettingActivity.this);
            ryClass.setLayoutManager(layoutManager);
            classAdapter = new CommonRecycleAdapter<ProductModel.PWareModel>(ProductSettingActivity.this, R.layout.adapter_product_class, productModel.getWareList()) {
                @Override
                protected void convert(ViewHolder holder, ProductModel.PWareModel pWareModel, final int position) {
                    if (pWareModel != null) {
                        if (!TextUtils.isEmpty(pWareModel.getCname())) {
                            String particular = pWareModel.getCname().replace("\\n", "\n");
                            holder.setText(R.id.txt_classname, particular);
                        } else {
                            holder.setText(R.id.txt_classname, "");
                        }
                        if (classselect == position) {
                            holder.setTextColorRes(R.id.txt_classname, R.color.login_mian);
                            holder.setVisible(R.id.img_right, true);
                        } else {
                            holder.setTextColorRes(R.id.txt_classname, R.color.login_text);
                            holder.setVisible(R.id.img_right, false);
                        }
                        holder.setOnClickListener(R.id.txt_classname, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                classselect = position;
                                notifyDataSetChanged();
                                initproductRyview();
                            }
                        });
                    }
                }
            };
            ryClass.setAdapter(classAdapter);
        }
    }

    /**
     * 商品适配器
     */
    private void initproductRyview(){
        if(!JsonUtil.isEmpty(productModel.getWareList().get(classselect).getWares())) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductSettingActivity.this);
            ryWares.setLayoutManager(linearLayoutManager);
            wareAdapter = new CommonRecycleAdapter<ProductModel.WareModel>(ProductSettingActivity.this, R.layout.adapter_product, productModel.getWareList().get(classselect).getWares()) {
                @Override
                protected void convert(ViewHolder holder, final ProductModel.WareModel wareModel, final int position) {
                    if (wareModel != null) {
                        if (!TextUtils.isEmpty(wareModel.getImgUrl())) {
                            holder.setImageRoundGlide(R.id.img_ware, wareModel.getImgUrl());
                        } else {
                            holder.setBackgroundRes(R.id.img_ware, R.mipmap.biz_zhanweitu);
                        }
                        if (!TextUtils.isEmpty(wareModel.getName())) {
                            holder.setText(R.id.txt_warename, wareModel.getName().replace("\\n", "\n"));
                        } else {
                            holder.setText(R.id.txt_warename, "");
                        }
                        if (!TextUtils.isEmpty(wareModel.getKuSum())) {
                            holder.setText(R.id.txt_stock, "库存" + wareModel.getKuSum());
                        } else {
                            holder.setText(R.id.txt_stock, "");
                        }
                        if (!TextUtils.isEmpty(wareModel.getTotalSum())) {
                            holder.setText(R.id.txt_sell, "月销售" + wareModel.getTotalSum());
                        } else {
                            holder.setText(R.id.txt_sell, "");
                        }
                        if (!TextUtils.isEmpty(wareModel.getPrice())) {
                            holder.setText(R.id.txt_price, "£" + wareModel.getPrice());
                        } else {
                            holder.setText(R.id.txt_price, "");
                        }
                        if (!TextUtils.isEmpty(wareModel.getStatus())) {
                            if ("0".equals(wareModel.getStatus())) {
                                holder.setText(R.id.txt_stoptime, "非可售时间");
                                status = "1";
                                holder.setText(R.id.txt_stopsale, "起售");
                            } else {
                                TextView txtStoptime = holder.getView(R.id.txt_stoptime);
                                Drawable drawable_n = getResources().getDrawable(R.mipmap.chucan_time);
                                drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                                txtStoptime.setCompoundDrawables(drawable_n, null, null, null);
                                txtStoptime.setCompoundDrawablePadding(10);
                                holder.setText(R.id.txt_stoptime, "可售时间");
                                status = "0";
                                holder.setText(R.id.txt_stopsale, "停售");
                            }
                        } else {
                            status = wareModel.getStatus();
                            holder.setText(R.id.txt_stoptime, "非可售时间");
                        }
                        //txt_stopsale
                        holder.setOnClickListener(R.id.txt_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                waredelete = position;
                                mvpPresenter.deleteWares(wareModel.getId());
                            }
                        });

                        holder.setOnClickListener(R.id.txt_stopsale, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                waredelete = position;
                                status = wareModel.getStatus();
                                if ("0".equals(status)) {
                                    status = "1";
                                } else {
                                    status = "0";
                                }
                                mvpPresenter.loadProductStatus(wareModel.getId(), status);
                            }
                        });
                    }
                }
            };
            ryWares.setAdapter(wareAdapter);
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_right, R.id.txt_pclass, R.id.txt_psetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                gotoActivity(ProductSettingActivity.this, ShopStoreActivity.class);
                finish();
                break;
            case R.id.txt_pclass:
                ProductClassSettingDialog shareDialog1 = new ProductClassSettingDialog(ProductSettingActivity.this);
                shareDialog1.showPopupWindow(findViewById(R.id.txt_pclass));
                shareDialog1.setProductListener(productListener);
                break;
            case R.id.txt_psetting:
                ProductSettingDialog shareDialog2 = new ProductSettingDialog(ProductSettingActivity.this);
                shareDialog2.showPopupWindow(findViewById(R.id.txt_psetting));
                shareDialog2.setProductListener(productListener);
                break;
        }
    }
    ProductListener productListener = new ProductListener(){
        @Override
        public void AddandEdit(String type, String type1) {
            if("product".equals(type)){
                if("add".equals(type1)){
                    if(!JsonUtil.isEmpty(productModel.getWareList())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "add");
                        gotoActivity(ProductSettingActivity.this, AddProductActivity.class, bundle);
                    }else{
                        ToastUtils.showShortToast("请先新建分类");
                    }
                }else{
                    gotoActivity(ProductSettingActivity.this,ProductEditAndSortActivity.class);
                }
            }else if("class".equals(type)){
                if("add".equals(type1)){
                    Bundle bundle = new Bundle();
                    bundle.putString("type","add");
                    gotoActivity(ProductSettingActivity.this,AddProductClassActivity.class,bundle);
                }else{
                    gotoActivity(ProductSettingActivity.this,ClassEditAndSortActivity.class);
                }
            }
        }
    };

}
