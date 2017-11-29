package com.butao.ulifebiz.mvp.activity.shop.event;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.activity.shop.product.AddProductActivity;
import com.butao.ulifebiz.mvp.activity.shop.product.AddProductClassActivity;
import com.butao.ulifebiz.mvp.activity.shop.product.ClassEditAndSortActivity;
import com.butao.ulifebiz.mvp.activity.shop.product.ProductEditAndSortActivity;
import com.butao.ulifebiz.mvp.activity.shop.product.ShopStoreActivity;
import com.butao.ulifebiz.mvp.dialog.ProductClassSettingDialog;
import com.butao.ulifebiz.mvp.dialog.ProductListener;
import com.butao.ulifebiz.mvp.dialog.ProductSettingDialog;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.presenter.ProductPresenter;
import com.butao.ulifebiz.mvp.view.ProductsView;
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
public class ProductSelectActivity extends MvpActivity<ProductPresenter> implements ProductsView {

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_content)
    TextView txt_content;
    @Bind(R.id.ll_setting)
    LinearLayout ll_setting;
    @Bind(R.id.ry_class)
    RecyclerView ryClass;
    @Bind(R.id.ry_wares)
    RecyclerView ryWares;
    ProductModel productModel = new ProductModel();
    CommonRecycleAdapter classAdapter;
    CommonRecycleAdapter wareAdapter;
    int classselect=0;
    int waredelete=0;
    String status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_setting);
        ButterKnife.bind(this);
        txtTitle.setText("商品管理");
        txt_content.setVisibility(View.GONE);
        ll_setting.setVisibility(View.GONE);
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
            initproductRyview();
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
     * 停售起售
     *
     * @param model
     */
    @Override
    public void getProductStatusSuccess(BaseModel model) {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductSelectActivity.this);
        ryClass.setLayoutManager(layoutManager);
        classAdapter = new CommonRecycleAdapter<ProductModel.PWareModel>(ProductSelectActivity.this,R.layout.adapter_product_class,productModel.getWareList()) {
            @Override
            protected void convert(ViewHolder holder, ProductModel.PWareModel pWareModel, final int position) {
                if(pWareModel!=null){
                    if(!TextUtils.isEmpty(pWareModel.getCname())){
                        holder.setText(R.id.txt_classname,pWareModel.getCname());
                    }else{
                        holder.setText(R.id.txt_classname,"");
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
                            classselect=position;
                            notifyDataSetChanged();
                            initproductRyview();
                        }
                    });
                }
            }
        };
        ryClass.setAdapter(classAdapter);
    }

    /**
     * 商品适配器
     */
    private void initproductRyview(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductSelectActivity.this);
        ryWares.setLayoutManager(linearLayoutManager);
        wareAdapter = new CommonRecycleAdapter<ProductModel.WareModel>(ProductSelectActivity.this,R.layout.adapter_product,productModel.getWareList().get(classselect).getWares()) {
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
                    holder.setTextColorRes(R.id.txt_stoptime,R.color.font_red);
                    TextView txtStoptime = holder.getView(R.id.txt_stoptime);
                    Drawable drawable_n = getResources().getDrawable(R.mipmap.chucan_time);
                    drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(), drawable_n.getMinimumHeight());  //此为必须写的
                    txtStoptime.setCompoundDrawables(null, null, null, null);
                    if(!TextUtils.isEmpty(wareModel.getPrice())){
                        holder.setText(R.id.txt_stoptime,"£"+wareModel.getPrice());
                    }else{
                        holder.setText(R.id.txt_stoptime,"");
                    }

                    holder.setVisible(R.id.ll_status,false);
                    holder.setOnClickListener(R.id.ll_ware, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("wareId", wareModel.getId());
                            intent.putExtra("wareName", wareModel.getName());
                            intent.putExtra("warePrice", wareModel.getPrice());
                            intent.putExtra("wareImg", wareModel.getImgUrl());
                            setResult(20, intent);
                            finish();
                        }
                    });
                    //txt_stopsale
                }
            }
        };
        ryWares.setAdapter(wareAdapter);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                setResult(0);
                finish();
                break;

        }
    }


}
