package com.butao.ulifebiz.mvp.activity.shop.product;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.presenter.ProductClassPresenter;
import com.butao.ulifebiz.mvp.view.ProductClassView;
import com.butao.ulifebiz.util.JsonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/17.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class AddProductClassActivity extends MvpActivity<ProductClassPresenter> implements ProductClassView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_remark)
    EditText edtContent;
    @Bind(R.id.txt_submit)
    TextView txtSubmit;
    @Bind(R.id.txt_phone)
    TextView txtPhone;

    String type="",cId="";
    ProdcutClassModel.PClassMosel pClassMosel=new ProdcutClassModel.PClassMosel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        ButterKnife.bind(this);
        txtTitle.setText("添加分类");
        txtSubmit.setPadding(25,8,25,8);
        txtSubmit.setText("保存 Save");
        edtContent.setHint("请输入您要新增的分类名称  如：招牌菜");
        txtPhone.setVisibility(View.GONE);
        Bundle bundle= getIntent().getExtras();
        type = bundle.getString("type");
        if("edit".equals(type)){
            pClassMosel = JsonUtil.fromJson(bundle.getString("Json"), ProdcutClassModel.PClassMosel.class);
            if(pClassMosel!=null){
                if(!TextUtils.isEmpty(pClassMosel.getName())){
                    edtContent.setText(pClassMosel.getName());
                }
                cId = pClassMosel.getId();
            }
        }
    }

    @Override
    protected ProductClassPresenter createPresenter() {
        return new ProductClassPresenter(this);
    }
    /**
     * 分类添加
     * @param model
     */
    @Override
    public void getEditProductSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            if("edit".equals(type)){
                ToastUtils.showShortToast("分类修改成功");
            }else{
                ToastUtils.showShortToast("分类添加成功");
            }
            finish();
        }else{
            ToastUtils.showShortToast(model.getData().toString());
        }
    }
    /**
     * 获取分类列表不用
     * @param model
     */
    @Override
    public void getProductClassSuccess(BaseModel<ProdcutClassModel> model) {

    }
    /**
     * 分类删除不用
     * @param model
     */
    @Override
    public void getDeleteProductSuccess(BaseModel model) {

    }
    /**
     * 分类排序置顶不用
     * @param model
     */
    @Override
    public void getProductClassSortSuccess(BaseModel model) {

    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    @OnClick({R.id.ll_back, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_submit:
                String className = edtContent.getText().toString();
                if(TextUtils.isEmpty(className)){
                    ToastUtils.showShortToast("请输入分类名称");
                    edtContent.requestFocus();
                    return;
                }
                mvpPresenter.AddWareClas(cId,className);
                break;
        }
    }
}
