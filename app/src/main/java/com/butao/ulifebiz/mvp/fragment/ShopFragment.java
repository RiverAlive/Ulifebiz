package com.butao.ulifebiz.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpFragment;
import com.butao.ulifebiz.base.MvpLoadFragment;
import com.butao.ulifebiz.mvp.activity.shop.CustomerReplyActivity;
import com.butao.ulifebiz.mvp.activity.shop.delivery.DeliveryAreaActivity;
import com.butao.ulifebiz.mvp.activity.shop.event.FavorEventActivity;
import com.butao.ulifebiz.mvp.activity.shop.product.ProductSettingActivity;
import com.butao.ulifebiz.mvp.activity.shop.setting.BusinessSettingActivity;
import com.butao.ulifebiz.mvp.activity.shop.settle.AccountSettleActivity;
import com.butao.ulifebiz.mvp.fragment.shop.product.ShopProductFragment;
import com.butao.ulifebiz.mvp.model.shop.ShopInfoModel;
import com.butao.ulifebiz.mvp.presenter.ShopFragmentPresenter;
import com.butao.ulifebiz.mvp.view.ShopFragmentView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/20.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ShopFragment extends MvpLoadFragment<ShopFragmentPresenter> implements ShopFragmentView {
    @Bind(R.id.txt_todayquantum)
    TextView txtTodayquantum;
    @Bind(R.id.txt_quantum)
    TextView txtQuantum;
    @Bind(R.id.txt_todayincome)
    TextView txtTodayincome;
    @Bind(R.id.txt_income)
    TextView txtIncome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_tab, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_shop_tab;
    }

    @Override
    protected void lazyLoad() {
        mvpPresenter.loadShopInfo();
    }

    @Override
    protected ShopFragmentPresenter createPresenter() {
        return new ShopFragmentPresenter(this);
    }

    @Override
    public void getShopSuccess(BaseModel<ShopInfoModel> model) {
        if("200".equals(model.getCode())){
            ShopInfoModel shopInfoModel =model.getData();
            if(shopInfoModel!=null){
                if(!TextUtils.isEmpty(shopInfoModel.getDayOrderNum())){
                    txtTodayquantum.setText(shopInfoModel.getDayOrderNum());
                }else{
                    txtTodayquantum.setText("0");
                }
                if(!TextUtils.isEmpty(shopInfoModel.getUnDayOrderNum())){
                    txtQuantum.setText("Single quantity today\n昨日:"+shopInfoModel.getUnDayOrderNum());
                }else{
                    txtQuantum.setText("Single quantity today\n昨日:0");
                }
                if(!TextUtils.isEmpty(shopInfoModel.getUnDayOrderPrice())){
                    txtIncome.setText("net income\n昨日:"+shopInfoModel.getUnDayOrderPrice());
                }else{
                    txtIncome.setText("net income\n昨日:0");
                }
                if(!TextUtils.isEmpty(shopInfoModel.getDayOrderPrice())){
                    txtTodayincome.setText(shopInfoModel.getDayOrderPrice());
                }else{
                    txtTodayincome.setText("0");
                }
            }
        }else{
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_activity, R.id.ll_setting, R.id.ll_management, R.id.ll_delivery, R.id.ll_settlement, R.id.ll_evaluation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_activity://优惠活动
                startActivity(getActivity(), FavorEventActivity.class);
                break;
            case R.id.ll_setting://营业设置
                startActivity(getActivity(), BusinessSettingActivity.class);
                break;
            case R.id.ll_management://商品管理
                startActivity(getActivity(), ProductSettingActivity.class);
                break;
            case R.id.ll_delivery://配送范围
                startActivity(getActivity(), DeliveryAreaActivity.class);
                break;
            case R.id.ll_settlement://账户结算
                startActivity(getActivity(), AccountSettleActivity.class);
                break;
            case R.id.ll_evaluation://客户评价
                startActivity(getActivity(), CustomerReplyActivity.class);
                break;
        }
    }
}
