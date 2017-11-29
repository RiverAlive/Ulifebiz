package com.butao.ulifebiz.mvp.activity.shop.delivery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.dialog.PhoneDialog;
import com.butao.ulifebiz.mvp.model.shop.delivery.DeliveryAreaModel;
import com.butao.ulifebiz.mvp.presenter.DeliveryPresenter;
import com.butao.ulifebiz.mvp.view.DeliveryView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/6.
 * 编写人 ：bodong
 * 功能描述 ：配送范围
 */
public class DeliveryAreaActivity extends MvpActivity<DeliveryPresenter> implements DeliveryView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    DeliveryAreaModel deliveryAreaModel = new DeliveryAreaModel();
    @Bind(R.id.txt_courier)
    TextView txtCourier;
    @Bind(R.id.txt_dfee)
    TextView txtDfee;
    @Bind(R.id.txt_dtime)
    TextView txtDtime;
    @Bind(R.id.txt_dprice)
    TextView txtDprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_area);
        ButterKnife.bind(this);
        txtTitle.setText("配送范围");
        txtRightTitle.setText("10KM");
        txtRightTitle.setTextSize(12);
        txtRightTitle.setTextColor(getResources().getColor(R.color.login_mian));
        mvpPresenter.loadDelivery();
    }

    @Override
    protected DeliveryPresenter createPresenter() {
        return new DeliveryPresenter(this);
    }

    @Override
    public void getDeliverySuccess(BaseModel<DeliveryAreaModel> model) {
        if ("200".equals(model.getCode())) {
            deliveryAreaModel = model.getData();
            if (deliveryAreaModel != null) {
                if (deliveryAreaModel.getDistribution() != null) {
                    if(!TextUtils.isEmpty(deliveryAreaModel.getDistribution().getPrice())){
                        txtDfee.setText("配送费Delivery fee:"+deliveryAreaModel.getDistribution().getPrice()+"£");
                    }else{
                        txtDfee.setText("配送费Delivery fee：");
                    }
                    if(!TextUtils.isEmpty(deliveryAreaModel.getDistribution().getStartPrice())){
                        txtDprice.setText("起送价Sort price:"+deliveryAreaModel.getDistribution().getStartPrice()+"£");
                    }else{
                        txtDprice.setText("起送价Sort price");
                    }
                    if(!TextUtils.isEmpty(deliveryAreaModel.getDistribution().getLongTime())){
                        txtDtime.setText("配送时长Delivery time:"+deliveryAreaModel.getDistribution().getLongTime()+"min");
                    }else{
                        txtDtime.setText("配送时长Delivery time");
                    }
                    if(!TextUtils.isEmpty(deliveryAreaModel.getDistribution().getSendUserName())){
                        txtCourier.setText(deliveryAreaModel.getDistribution().getSendUserName()+"专送");
                    }else{
                        txtCourier.setText("某某专送");
                    }
                    if(!TextUtils.isEmpty(deliveryAreaModel.getDistribution().getDistance())){
                        txtRightTitle.setText(deliveryAreaModel.getDistribution().getDistance()+"KM");
                    }else{
                        txtRightTitle.setText("");
                    }
                }
            }
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showLongToast(model);
    }



    @OnClick({R.id.ll_back, R.id.ll_right, R.id.txt_manage_phone, R.id.txt_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                break;
            case R.id.txt_manage_phone:
                if (deliveryAreaModel != null) {
                    if (deliveryAreaModel.getDistribution() != null) {
                        if(!TextUtils.isEmpty(deliveryAreaModel.getDistribution().getManagerPhone())){
                            PhoneDialog phoneDialog = new PhoneDialog(DeliveryAreaActivity.this,deliveryAreaModel.getDistribution().getManagerPhone());
                            phoneDialog.dialogShow();
                        }
                    }
                }
                break;
            case R.id.txt_contact:
                gotoActivity(DeliveryAreaActivity.this,ModifyDelieryActivity.class);
                break;
        }
    }
}
