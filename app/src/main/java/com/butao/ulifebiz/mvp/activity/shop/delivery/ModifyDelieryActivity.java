package com.butao.ulifebiz.mvp.activity.shop.delivery;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.presenter.ModfiyDelieryPresenter;
import com.butao.ulifebiz.mvp.view.ModifyDelieryView;
import com.butao.ulifebiz.permissiongen.PermissionGen;
import com.butao.ulifebiz.util.LocationUtils;
import com.butao.ulifebiz.util.RegexUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/10/10.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class ModifyDelieryActivity extends MvpActivity<ModfiyDelieryPresenter> implements ModifyDelieryView {

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.edt_deliveryfee)
    EditText edtDeliveryfee;
    @Bind(R.id.edt_sortprice)
    EditText edtSortprice;
    @Bind(R.id.edt_deliverytime)
    EditText edtDeliverytime;
    @Bind(R.id.edt_scope)
    EditText edtScope;
    String Lat;
    String Long;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_delivery);
        ButterKnife.bind(this);
        txtTitle.setText("编辑配送范围");
        txtRightTitle.setText("定位");
        txtRightTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtRightTitle.setVisibility(View.GONE);
    }

    @Override
    protected ModfiyDelieryPresenter createPresenter() {
        return new ModfiyDelieryPresenter(this);
    }

    @Override
    public void getModifyDelierySuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            finish();
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    private void locationInfo() {
        LocationUtils.register(ModifyDelieryActivity.this, 0, 2000, new LocationUtils.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                if (location != null) {
                    if (location != null) {
                        double Latitude = location.getLatitude();
                        double Longitude = location.getLongitude();
                        if (RegexUtils.isRealNumber(String.valueOf(Latitude)) && RegexUtils.isRealNumber(String.valueOf(Longitude))) {
                            Lat = String.valueOf(Latitude);
                            Long = String.valueOf(Longitude);
                        } else {
                            Lat = "";
                            Long = "";
                        }
                    } else {
                        String errText = "定位失败,";
                    }
                }
            }

            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    @OnClick({R.id.ll_back, R.id.ll_right, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                int recode = 0;
                PermissionGen.needPermission(this, recode, Manifest.permission.ACCESS_FINE_LOCATION);
                if (recode == 200) {
                    locationInfo();
                } else {
                    ToastUtils.showShortToast("请去设置中允许软件获取手机定位权限");
                }

                break;
            case R.id.txt_submit:
                String Deliveryfee = edtDeliveryfee.getText().toString();
                if (TextUtils.isEmpty(Deliveryfee)) {
                    ToastUtils.showShortToast("请输入配送费");
                    edtDeliveryfee.requestFocus();
                    return;
                }
                String Sortprice = edtSortprice.getText().toString();
                if (TextUtils.isEmpty(Sortprice)) {
                    ToastUtils.showShortToast("请输入起送价");
                    edtSortprice.requestFocus();
                    return;
                }
                String Deliverytime = edtDeliverytime.getText().toString();
                if (TextUtils.isEmpty(Deliverytime)) {
                    ToastUtils.showShortToast("请输入配送时长");
                    edtDeliverytime.requestFocus();
                    return;
                }
                String Scope = edtScope.getText().toString();
                if (TextUtils.isEmpty(Scope)) {
                    ToastUtils.showShortToast("请输入配送范围");
                    edtScope.requestFocus();
                    return;
                }
                mvpPresenter.loadModifyDeliery(Deliveryfee, Sortprice, Deliverytime, Scope);
                break;
        }
    }
}
