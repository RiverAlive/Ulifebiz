package com.butao.ulifebiz.mvp.fragment.shop.product;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpFragment;
import com.butao.ulifebiz.mvp.activity.shop.product.ProductSettingActivity;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.presenter.ProductPresenter;
import com.butao.ulifebiz.mvp.view.ProductsView;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建时间 ：2017/9/4.
 * 编写人 ：bodong
 * 功能描述 ：商品
 */
public class ShopProductFragment extends MvpFragment<ProductPresenter> implements ProductsView {
    @Bind(R.id.ry_pclass)
    RecyclerView ryPclass;
    @Bind(R.id.ry_wares)
    RecyclerView ryWares;
    private String status = "";
    ProductModel productModel = new ProductModel();
    CommonRecycleAdapter classAdapter;
    CommonRecycleAdapter wareAdapter;
    int classselect = 0;

    public static ShopProductFragment getInstance(String status) {
        ShopProductFragment sf = new ShopProductFragment();
        sf.status = status;
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_product, null);
        ButterKnife.bind(this, view);
        mvpPresenter.loadPWare();
        return view;
    }


    @Override
    protected ProductPresenter createPresenter() {
        return new ProductPresenter(this);
    }

    /**
     * 商品列表
     *
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

    @Override
    public void getProductStatusSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {

        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 商品删除此处不用
     *
     * @param model
     */
    @Override
    public void getProductdeleteSuccess(BaseModel model) {
    }

    /**
     * 商品排序此处不用
     *
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ryPclass.setLayoutManager(layoutManager);
        classAdapter = new CommonRecycleAdapter<ProductModel.PWareModel>(getActivity(), R.layout.adapter_product_class, productModel.getWareList()) {
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
        ryPclass.setAdapter(classAdapter);
    }

    /**
     * 商品适配器
     */
    private void initproductRyview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ryWares.setLayoutManager(linearLayoutManager);
        wareAdapter = new CommonRecycleAdapter<ProductModel.WareModel>(getActivity(), R.layout.adapter_shop_product, productModel.getWareList().get(classselect).getWares()) {
            @Override
            protected void convert(ViewHolder holder, final ProductModel.WareModel wareModel, final int position) {
                if (wareModel != null) {
                    if (!TextUtils.isEmpty(wareModel.getImgUrl())) {
                        holder.setImageRoundGlide(R.id.img_ware, wareModel.getImgUrl());
                    } else {
                        holder.setBackgroundRes(R.id.img_ware, R.mipmap.biz_zhanweitu);
                    }
                    if (!TextUtils.isEmpty(wareModel.getName())) {
                        String particular = wareModel.getName().replace("\\n", "\n");
                        holder.setText(R.id.txt_warename, particular);
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
                    if ("1".equals(cApplication.getStatus())) {
                        holder.setText(R.id.txt_status,"正常营业");
                        holder.setTextColor(R.id.txt_status,getResources().getColor(R.color.font_blue));
                    } else if ("4".equals(cApplication.getStatus())) {
                        holder.setText(R.id.txt_status,"暂停营业");
                        holder.setTextColor(R.id.txt_status,getResources().getColor(R.color.auxiliary_font));
                    } else if ("5".equals(cApplication.getStatus())) {
                        holder.setText(R.id.txt_status,"休息中");
                        holder.setTextColor(R.id.txt_status,getResources().getColor(R.color.login_mian));
                    }
                            holder.setText(R.id.txt_status, cApplication.getStatusName());
                }
            }
        };
        ryWares.setAdapter(wareAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
