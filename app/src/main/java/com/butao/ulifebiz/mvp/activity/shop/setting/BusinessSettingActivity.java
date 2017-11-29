package com.butao.ulifebiz.mvp.activity.shop.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.dialog.PhoneDialog;
import com.butao.ulifebiz.mvp.dialog.ReceiptDialog;
import com.butao.ulifebiz.mvp.model.shop.BussinessInfo;
import com.butao.ulifebiz.mvp.presenter.BussinessPresenter;
import com.butao.ulifebiz.mvp.presenter.ModfiyBoxPresenter;
import com.butao.ulifebiz.mvp.presenter.ModfiyPhonePresenter;
import com.butao.ulifebiz.mvp.view.BussinessView;
import com.butao.ulifebiz.util.JsonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/30.
 * 编写人 ：bodong
 * 功能描述 ：营业设置
 */
public class BusinessSettingActivity extends MvpActivity<BussinessPresenter> implements BussinessView {

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_bstatus)
    TextView txtBstatus;
    @Bind(R.id.txt_bTime)
    TextView txtBTime;
    @Bind(R.id.txt_undefined)
    TextView txtUndefined;
    @Bind(R.id.txt_defined)
    TextView txtDefined;
    @Bind(R.id.txt_bprice)
    TextView txtBprice;
    @Bind(R.id.txt_bphone)
    TextView txtBphone;
    @Bind(R.id.txt_receipt)
    TextView txtReceipt;
    BussinessInfo bussinessInfo = new BussinessInfo();
    String time = "",receipt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_setting);
        ButterKnife.bind(this);
        txtTitle.setText("营业设置");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvpPresenter.loadBussinessInfo();
    }

    @Override
    protected BussinessPresenter createPresenter() {
        return new BussinessPresenter(this);
    }

    /**
     * 门店信息
     *
     * @param model
     */
    @Override
    public void getBussinessInfoSuccess(BaseModel<BussinessInfo> model) {
        if ("200".equals(model.getCode())) {
            bussinessInfo = model.getData();
            if (bussinessInfo != null) {
                String statusNmae="";
                if (!TextUtils.isEmpty(bussinessInfo.getStatus())) {//1.status:状态：1：正常营业，4：暂停营业,5:休息
                    if ("1".equals(bussinessInfo.getStatus())) {
                        txtBstatus.setText("正常营业");
                        txtBstatus.setTextColor(getResources().getColor(R.color.font_blue));
                        statusNmae ="正常营业";
                    } else if ("4".equals(bussinessInfo.getStatus())) {
                        txtBstatus.setText("暂停营业");
                        statusNmae ="暂停营业";
                        txtBstatus.setTextColor(getResources().getColor(R.color.auxiliary_font));
                    } else if ("5".equals(bussinessInfo.getStatus())) {
                        txtBstatus.setText("休息中");
                        statusNmae ="休息中";
                        txtBstatus.setTextColor(getResources().getColor(R.color.login_mian));
                    }
                    cApplication.setStatus(bussinessInfo.getStatus());
                    cApplication.setStatusName(statusNmae);
                } else {
                    txtBstatus.setText("");
                }
                if (!TextUtils.isEmpty(bussinessInfo.getPackPrice())) {
                    txtBprice.setText("£" + bussinessInfo.getPackPrice());
                } else {
                    txtBprice.setText("");
                }
                if (!TextUtils.isEmpty(bussinessInfo.getSendPhone())) {
                    txtBphone.setText(bussinessInfo.getSendPhone());
                } else {
                    txtBphone.setText("");
                }
                if (!TextUtils.isEmpty(bussinessInfo.getAutomatic())) {//automatic：是否自动接单1：自动接单，0：手动
                    receipt = bussinessInfo.getAutomatic();
                    if ("1".equals(bussinessInfo.getAutomatic())) {
                        txtReceipt.setText("自动接单");
                        txtUndefined.setTextColor(getResources().getColor(R.color.white));
                        txtUndefined.setBackgroundResource(R.drawable.shape_left_event_true);
                        txtDefined.setTextColor(getResources().getColor(R.color.login_mian));
                        txtDefined.setBackgroundResource(R.drawable.shape_right_event);
                    } else {
                        txtReceipt.setText("手动接单");
                        txtUndefined.setTextColor(getResources().getColor(R.color.login_mian));
                        txtUndefined.setBackgroundResource(R.drawable.shape_left_event);
                        txtDefined.setTextColor(getResources().getColor(R.color.white));
                        txtDefined.setBackgroundResource(R.drawable.shape_right_event_true);
                    }
                }
                time ="";
                if (!JsonUtil.isEmpty(bussinessInfo.getDoBusinessTimes())) {
                    for (int i = 0; i < bussinessInfo.getDoBusinessTimes().size(); i++) {
                        if (!TextUtils.isEmpty(bussinessInfo.getDoBusinessTimes().get(i).getTime())) {
                            if(TextUtils.isEmpty(time)){
                                time = bussinessInfo.getDoBusinessTimes().get(i).getTime();
                            }else{
                                time = time + "," + bussinessInfo.getDoBusinessTimes().get(i).getTime();
                            }
                        }
                    }
                }
                txtBTime.setText(time);
            }
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    /**
     * 自动接单
     *
     * @param model
     */
    @Override
    public void getStoreAutomaticSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("成功");
            mvpPresenter.loadBussinessInfo();
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }


    @OnClick({R.id.ll_back, R.id.ll_timesetting,
            R.id.ll_status, R.id.ll_btime, R.id.txt_undefined, R.id.txt_defined, R.id.ll_phone,R.id.ll_receipt,R.id.ll_boxfee})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_timesetting:
                gotoActivity(BusinessSettingActivity.this, OperatingTimeActivity.class);
                break;
            case R.id.ll_status:
                gotoActivity(BusinessSettingActivity.this, ModifyStateActivity.class);
                break;
            case R.id.ll_btime:
                break;
            case R.id.ll_boxfee:
                gotoActivity(BusinessSettingActivity.this, ModifyBoxActivity.class);
                break;
            case R.id.ll_receipt:
                String type="";
                if ("1".equals(bussinessInfo.getAutomatic())) {
                    type="手动接单";
                } else {
                    type="自动接单";
                }
                ReceiptDialog receiptDialog = new ReceiptDialog(BusinessSettingActivity.this,type);
                receiptDialog.dialogShow();
                receiptDialog.setRefundlistener(new ReceiptDialog.Refundlistener() {
                    @Override
                    public void Refund() {
                        if("1".equals(receipt)){
                            mvpPresenter.loadStoreAutomatic("0");
                        }else{
                            mvpPresenter.loadStoreAutomatic("1");
                        }
                    }
                });
                break;
            case R.id.txt_undefined:
                txtUndefined.setTextColor(getResources().getColor(R.color.white));
                txtUndefined.setBackgroundResource(R.drawable.shape_left_event_true);
                txtDefined.setTextColor(getResources().getColor(R.color.login_mian));
                txtDefined.setBackgroundResource(R.drawable.shape_right_event);
                mvpPresenter.loadStoreAutomatic("1");
                break;
            case R.id.txt_defined:
                txtUndefined.setTextColor(getResources().getColor(R.color.login_mian));
                txtUndefined.setBackgroundResource(R.drawable.shape_left_event);
                txtDefined.setTextColor(getResources().getColor(R.color.white));
                txtDefined.setBackgroundResource(R.drawable.shape_right_event_true);
                mvpPresenter.loadStoreAutomatic("0");
                break;
            case R.id.ll_phone:
                gotoActivity(BusinessSettingActivity.this, ModifyPhoneActivity.class);
//                if (!TextUtils.isEmpty(bussinessInfo.getSendPhone())) {
//                    PhoneDialog phoneDialog = new PhoneDialog(BusinessSettingActivity.this, bussinessInfo.getSendPhone());
//                    phoneDialog.dialogShow();
//                }
                break;
        }
    }
}
