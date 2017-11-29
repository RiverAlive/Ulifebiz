package com.butao.ulifebiz.mvp.activity.shop.product;

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
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.mvp.presenter.ProductClassPresenter;
import com.butao.ulifebiz.mvp.view.ProductClassView;
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

public class ClassEditAndSortActivity extends MvpActivity <ProductClassPresenter> implements ProductClassView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_wares)
    RecyclerView ryWares;
    ProdcutClassModel prodcutClassModel = new ProdcutClassModel();
    CommonRecycleAdapter recycleAdapter;
    int sortposition=-1;
    ProdcutClassModel.PClassMosel classMosel;
    List<ProdcutClassModel.PClassMosel> pClassMosels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editandsort);
        ButterKnife.bind(this);
        txtTitle.setText("分类管理");
        mvpPresenter.loadProductClass();
    }

    @Override
    protected ProductClassPresenter createPresenter() {
        return new ProductClassPresenter(this);
    }

    /**
     * 分类添加修改不用
     * @param model
     */
    @Override
    public void getEditProductSuccess(BaseModel model) {
    }

    /**
     * 获取分类列表
     * @param model
     */
    @Override
    public void getProductClassSuccess(BaseModel<ProdcutClassModel> model) {
        if("200".equals(model.getCode())){
            prodcutClassModel = model.getData();
            pClassMosels = prodcutClassModel.getCategoryList();
            initRyClassView();
        }else{
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    /**
     * 分类删除
     * @param model
     */
    @Override
    public void getDeleteProductSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            pClassMosels.remove(sortposition);
            recycleAdapter.notifyDataSetChanged();
        }else{
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    /**
     * 分类排序置顶
     * @param model
     */
    @Override
    public void getProductClassSortSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            pClassMosels.remove(sortposition);
            pClassMosels.set(0,classMosel);
            recycleAdapter.setupdateData(pClassMosels);
        }else{
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

private void initRyClassView(){
    LinearLayoutManager layoutManager = new LinearLayoutManager(ClassEditAndSortActivity.this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    ryWares.setLayoutManager(layoutManager);
    recycleAdapter = new CommonRecycleAdapter<ProdcutClassModel.PClassMosel>(ClassEditAndSortActivity.this,R.layout.adapter_class_edit_sort,pClassMosels) {
        @Override
        protected void convert(ViewHolder holder, final ProdcutClassModel.PClassMosel pClassMosel, final int position) {
            if(!TextUtils.isEmpty(pClassMosel.getName())){
                holder.setText(R.id.txt_classname,pClassMosel.getName());
            }else{
                holder.setText(R.id.txt_classname,"");
            }
            holder.setOnClickListener(R.id.txt_edit, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sortposition=position;
                    Bundle bundle = new Bundle();
                    bundle.putString("type","edit");
                    bundle.putString("Json", JsonUtil.toJson(pClassMosel));
                    gotoActivity(ClassEditAndSortActivity.this,AddProductClassActivity.class,bundle);
                }
            });
            holder.setOnClickListener(R.id.txt_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sortposition=position;
                    classMosel = pClassMosel;
                    mvpPresenter.DeleteWareClas(pClassMosel.getId());
                }
            });
            holder.setOnClickListener(R.id.txt_sort, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sortposition=position;
                    classMosel = pClassMosel;
                    mvpPresenter.SortWareClass(pClassMosel.getId());
                }
            });
        }
    };
    ryWares.setAdapter(recycleAdapter);
}
    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}
