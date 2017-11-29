package com.butao.ulifebiz.mvp.activity.shop.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.dialog.CalendarPopView;
import com.butao.ulifebiz.mvp.dialog.PickTime;
import com.butao.ulifebiz.mvp.model.shop.event.DailyCutModel;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.presenter.DailyCutPresenter;
import com.butao.ulifebiz.mvp.view.DailyCutView;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.util.TimeUtil;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/25.
 * 编写人 ：bodong
 * 功能描述 ：天天特价
 */
public class DailySpecialActivity extends MvpActivity<DailyCutPresenter> implements DailyCutView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_startDay)
    TextView txtStartDay;
    @Bind(R.id.txt_stopDay)
    TextView txtStopDay;
    @Bind(R.id.txt_longtime)
    TextView txtLongtime;
    @Bind(R.id.txt_undefined)
    TextView txtUndefined;
    @Bind(R.id.txt_defined)
    TextView txtDefined;
    @Bind(R.id.txt_startTime)
    TextView txtStartTime;
    @Bind(R.id.txt_endTime)
    TextView txtEndTime;
    @Bind(R.id.ll_time)
    LinearLayout llTime;
    @Bind(R.id.txt_productNum)
    TextView txtProductNum;
    @Bind(R.id.ry_product)
    RecyclerView ryProduct;
    @Bind(R.id.edt_content)
    EditText edtContent;
    @Bind(R.id.txt_discount)
    TextView txtDiscount;
    @Bind(R.id.txt_undiscount)
    TextView txtUndiscount;
    @Bind(R.id.txt_share)
    TextView txtShare;
    @Bind(R.id.txt_unshare)
    TextView txtUnshare;
    @Bind(R.id.cb_deal)
    CheckBox cbDeal;
    boolean flagDeal=true;
    String startTime="",endTime="",startDay="",endDay="";
    int isSend=0,wareNum=1,orderNum=1;
    List<DailyCutModel.DailyWareModel> dailyWareModels = new ArrayList<>();
    CommonRecycleAdapter recycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_specials);
        ButterKnife.bind(this);
        txtTitle.setText("新建特价活动");
        txtStartDay.setText(TimeUtil.getDate());
        txtStopDay.setText(TimeUtil.getDate());
        startDay = TimeUtil.getDate();
        endDay=TimeUtil.getDate();
        llTime.setVisibility(View.GONE);
        startTime="00:01";
        endTime="23:59";
    }

    @Override
    protected DailyCutPresenter createPresenter() {
        return new DailyCutPresenter(this);
    }

    @Override
    public void getDailyCutSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("创建成功");
            finish();
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }


    private void initWareView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(DailySpecialActivity.this);
        ryProduct.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<DailyCutModel.DailyWareModel>(DailySpecialActivity.this,R.layout.adapter_add_dailyware,dailyWareModels) {
            @Override
            protected void convert(ViewHolder holder, DailyCutModel.DailyWareModel dailyWareModel, int position) {
                if(dailyWareModel!=null){
                    if(!TextUtils.isEmpty(dailyWareModel.getImgUrl())){
                        holder.setImageRoundGlide(R.id.img_ware,dailyWareModel.getImgUrl());
                    }else{
                        holder.setBackgroundRes(R.id.img_ware,R.mipmap.biz_zhanweitu);
                    }
                    if(!TextUtils.isEmpty(dailyWareModel.getWareName())){
                        holder.setText(R.id.txt_warename,dailyWareModel.getWareName());
                    }else{
                        holder.setText(R.id.txt_warename,"");
                    }
                    if(!TextUtils.isEmpty(dailyWareModel.getWarePrice())){
                        holder.setText(R.id.txt_wareprice,"原价"+dailyWareModel.getWarePrice()+"£");
                    }else{
                        holder.setText(R.id.txt_wareprice,"");
                    }
                    if(!TextUtils.isEmpty(dailyWareModel.getDiscountPrice())){
                        holder.setText(R.id.txt_disprice,"优惠"+dailyWareModel.getDiscountPrice()+"£");
                    }else{
                        holder.setText(R.id.txt_disprice,"");
                    }
                }
            }
        };
        ryProduct.setAdapter(recycleAdapter);
    }

    @OnClick({R.id.ll_back, R.id.ll_startDay, R.id.ll_stopDay, R.id.txt_undefined,
            R.id.txt_defined, R.id.txt_startTime, R.id.txt_endTime, R.id.cb_deal, R.id.txt_discount,
            R.id.txt_undiscount, R.id.txt_share, R.id.txt_unshare, R.id.txt_true,R.id.ll_addware})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_startDay:
                CalendarPopView startpopView = new CalendarPopView(DailySpecialActivity.this);
                startpopView.showPopupWindow();
                startpopView.setCalendarListener(new CalendarPopView.CalendarListener() {
                    @Override
                    public void onSelect(String time) {
                        startDay=time;
                        txtStartDay.setText(time);
                    }
                });
                break;
            case R.id.ll_stopDay:
                CalendarPopView endpopView = new CalendarPopView(DailySpecialActivity.this);
                endpopView.showPopupWindow();
                endpopView.setCalendarListener(new CalendarPopView.CalendarListener() {
                    @Override
                    public void onSelect(String time) {
                        endDay=time;
                        txtStopDay.setText(time);
                    }
                });
                break;
            case R.id.txt_undefined:
                txtUndefined.setTextColor(getResources().getColor(R.color.white));
                txtUndefined.setBackgroundResource(R.drawable.shape_left_event_true);
                txtDefined.setTextColor(getResources().getColor(R.color.login_mian));
                txtDefined.setBackgroundResource(R.drawable.shape_right_event);
                llTime.setVisibility(View.GONE);
                startTime="00:01";
                endTime="23:59";
                break;
            case R.id.txt_defined:
                txtUndefined.setTextColor(getResources().getColor(R.color.login_mian));
                txtUndefined.setBackgroundResource(R.drawable.shape_left_event);
                txtDefined.setTextColor(getResources().getColor(R.color.white));
                txtDefined.setBackgroundResource(R.drawable.shape_right_event_true);
                llTime.setVisibility(View.VISIBLE);
                startTime=TimeUtil.getTimeDate();
                endTime=TimeUtil.getTimeDate();
                break;
            case R.id.txt_startTime:
                final PickTime startPick = new PickTime(DailySpecialActivity.this);
                startPick.setPickTimelistener(new PickTime.PickTimelistener() {
                    @Override
                    public void time(String time) {
                        startTime =time;
                        txtStartTime.setText("开始时间："+time);
                    }
                });
                break;
            case R.id.txt_endTime:
                final PickTime endPick = new PickTime(DailySpecialActivity.this);
                endPick.setPickTimelistener(new PickTime.PickTimelistener() {
                    @Override
                    public void time(String time) {
                        endTime =time;
                        txtEndTime.setText("结束时间："+time);
                    }
                });
                break;
            case R.id.cb_deal:
                if(cbDeal.isChecked()){
                    flagDeal=true;
                }else{
                    flagDeal=false;
                }
                break;
            case R.id.txt_discount:
                txtDiscount.setTextColor(getResources().getColor(R.color.white));
                txtDiscount.setBackgroundResource(R.drawable.shape_left_event_true);
                txtUndiscount.setTextColor(getResources().getColor(R.color.login_mian));
                txtUndiscount.setBackgroundResource(R.drawable.shape_right_event);
                isSend=0;
                break;
            case R.id.txt_undiscount:
                txtDiscount.setTextColor(getResources().getColor(R.color.login_mian));
                txtDiscount.setBackgroundResource(R.drawable.shape_left_event);
                txtUndiscount.setTextColor(getResources().getColor(R.color.white));
                txtUndiscount.setBackgroundResource(R.drawable.shape_right_event_true);
                isSend=1;
                break;
            case R.id.txt_share:
                txtShare.setTextColor(getResources().getColor(R.color.white));
                txtShare.setBackgroundResource(R.drawable.shape_left_event_true);
                txtUnshare.setTextColor(getResources().getColor(R.color.login_mian));
                txtUnshare.setBackgroundResource(R.drawable.shape_right_event);
                break;
            case R.id.txt_unshare:
                txtShare.setTextColor(getResources().getColor(R.color.login_mian));
                txtShare.setBackgroundResource(R.drawable.shape_left_event);
                txtUnshare.setTextColor(getResources().getColor(R.color.white));
                txtUnshare.setBackgroundResource(R.drawable.shape_right_event_true);
                break;
            case R.id.ll_addware:
                if(dailyWareModels.size()>=10){
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(DailySpecialActivity.this,AddDailyWareActivity.class);
                startActivityForResult(intent, 20);
                break;
            case R.id.txt_true:
                String remark = edtContent.getText().toString();
                DailyCutModel dailyCutModel = new DailyCutModel();
                dailyCutModel.setStartDay(startDay);
                dailyCutModel.setEndDay(endDay);
                dailyCutModel.setToken(cApplication.getToken());
                dailyCutModel.setStartTime(startTime);
                dailyCutModel.setEndTime(endTime);
                dailyCutModel.setIsSend(isSend);
                dailyCutModel.setOrderNum(orderNum);
                dailyCutModel.setWareNum(wareNum);
                dailyCutModel.setRemark(remark);
                for(int i= 0;i<dailyWareModels.size();i++){
                    dailyWareModels.get(i).setImgUrl(null);
                }
                dailyCutModel.setDayCutWareForms(dailyWareModels);
                if(!JsonUtil.isEmpty(dailyWareModels)) {
                    if (flagDeal) {
                        mvpPresenter.loadDayCut(dailyCutModel);
                    } else {
                        ToastUtils.showShortToast("请阅读点击已阅读商家自主营销协议");
                    }
                }else{
                    ToastUtils.showShortToast("请选择商品");
                }
                break;
        }
    }
    /**
     * 跳转返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，
            case 20://选择折扣回调
                dailyWareModels.add(JsonUtil.fromJson(data.getStringExtra("Json"), DailyCutModel.DailyWareModel.class));
                initWareView();
                txtProductNum.setText("特价商品(可添加10，已添加"+dailyWareModels.size()+")");
                break;
            default:
                break;
        }
    }
}
