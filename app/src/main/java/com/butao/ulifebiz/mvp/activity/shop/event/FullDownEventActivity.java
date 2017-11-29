package com.butao.ulifebiz.mvp.activity.shop.event;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.butao.ulifebiz.mvp.activity.shop.setting.OperatingTimeActivity;
import com.butao.ulifebiz.mvp.dialog.CalendarPopView;
import com.butao.ulifebiz.mvp.dialog.PickTime;
import com.butao.ulifebiz.mvp.model.shop.event.FullDownModel;
import com.butao.ulifebiz.mvp.presenter.FullDownPresenter;
import com.butao.ulifebiz.mvp.view.FullDownView;
import com.butao.ulifebiz.util.TimeUtil;
import com.butao.ulifebiz.view.LastInputEditText;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.butao.ulifebiz.R.layout.adapter_full_down_price;

/**
 * 创建时间 ：2017/8/25.
 * 编写人 ：bodong
 * 功能描述 ：店铺满减
 */
public class FullDownEventActivity extends MvpActivity<FullDownPresenter> implements FullDownView {

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_startDay)
    TextView txtStartDay;
    @Bind(R.id.txt_stopDay)
    TextView txtStopDay;
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
    @Bind(R.id.ry_child)
    RecyclerView ryChild;
    @Bind(R.id.cb_deal)
    CheckBox cbDeal;
    List<FullDownModel.FullCutPrice> fullCutPrices = new ArrayList<>();
    CommonRecycleAdapter recycleAdapter;
    boolean flagDeal=true;
    String startTime="",endTime="",startDay="",endDay="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_down_event);
        ButterKnife.bind(this);
        txtTitle.setText("店铺满减");
        txtStartDay.setText(TimeUtil.getDate());
        txtStopDay.setText(TimeUtil.getDate());
        startDay = TimeUtil.getDate();
        endDay=TimeUtil.getDate();
        llTime.setVisibility(View.GONE);
        FullDownModel.FullCutPrice fullCutPrice = new FullDownModel.FullCutPrice();
        fullCutPrices.add(fullCutPrice);
        initFullDownView();
        startTime="00:01";
        endTime="23:59";
    }

    @Override
    protected FullDownPresenter createPresenter() {
        return new FullDownPresenter(this);
    }

    @Override
    public void getFullDownSuccess(BaseModel model) {
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

    private void initFullDownView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(FullDownEventActivity.this);
        ryChild.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<FullDownModel.FullCutPrice>(FullDownEventActivity.this, R.layout.adapter_full_down_price,fullCutPrices) {
            @Override
            protected void convert(ViewHolder holder, FullDownModel.FullCutPrice fullCutPrice, final int position) {
                final LastInputEditText edtFull = (LastInputEditText) holder.getView(R.id.edt_full);
                final LastInputEditText edtCut = (LastInputEditText) holder.getView(R.id.edt_cut);
                //解决edittext嵌套在recyclerview中 被复用导致数据错乱问题
                if (edtFull.getTag() instanceof TextWatcher) {
                    edtFull.removeTextChangedListener((TextWatcher) edtFull.getTag());
                }
                if (edtCut.getTag() instanceof TextWatcher) {
                    edtCut.removeTextChangedListener((TextWatcher) edtCut.getTag());
                }
                if(!TextUtils.isEmpty(fullCutPrices.get(position).getFullPrice())){
                    edtFull.setText(fullCutPrices.get(position).getFullPrice());
                }
                if(!TextUtils.isEmpty(fullCutPrices.get(position).getCutPrice())){
                    edtCut.setText(fullCutPrices.get(position).getCutPrice());
                }
                TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int posDot = s.toString().indexOf(".");
                        if (posDot == 0) {
                            s.delete(posDot, posDot + 1);
                            ToastUtils.showLongToast("输入金额第一位不能是小数");
                        } else {
                            int num = 0;
                            for (int i = 0; i < s.toString().length(); i++) {
                                if (s.toString().substring(i, (i + 1)).indexOf('.') != -1) {
                                    num = num + 1;
                                }
                            }
                            if (num > 1) {
                                s.delete(s.toString().length() - 1, s.toString().length());
                                ToastUtils.showLongToast("请输入正确字符");
                            }
                        }
                        fullCutPrices.get(position).setFullPrice(s.toString());
                    }

                };
                edtFull.addTextChangedListener(watcher);
                edtFull.setTag(watcher);
                TextWatcher cutwatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int posDot = s.toString().indexOf(".");
                        if (posDot == 0) {
                            s.delete(posDot, posDot + 1);
                            ToastUtils.showLongToast("输入金额第一位不能是小数");
                        } else {
                            int num = 0;
                            for (int i = 0; i < s.toString().length(); i++) {
                                if (s.toString().substring(i, (i + 1)).indexOf('.') != -1) {
                                    num = num + 1;
                                }
                            }
                            if (num > 1) {
                                s.delete(s.toString().length() - 1, s.toString().length());
                                ToastUtils.showLongToast("请输入正确字符");
                            }
                        }
                        if(Double.parseDouble(s.toString())>Double.parseDouble(fullCutPrices.get(position).getFullPrice())){
                            ToastUtils.showShortToast("优惠金额应小于满赠金额");
                            fullCutPrices.get(position).setCutPrice(fullCutPrices.get(position).getFullPrice());
                        }else {
                            fullCutPrices.get(position).setCutPrice(s.toString());
                        }
                    }

                };
                edtCut.addTextChangedListener(cutwatcher);
                edtCut.setTag(cutwatcher);
                holder.setOnClickListener(R.id.txt_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fullCutPrices.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        };
        ryChild.setAdapter(recycleAdapter);

    }

    @OnClick({R.id.ll_back, R.id.ll_startDay, R.id.ll_stopDay, R.id.txt_undefined,
            R.id.txt_defined, R.id.txt_startTime, R.id.txt_endTime, R.id.txt_true,
            R.id.txt_addPrice,R.id.cb_deal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_startDay:
                CalendarPopView startpopView = new CalendarPopView(FullDownEventActivity.this);
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
                CalendarPopView endpopView = new CalendarPopView(FullDownEventActivity.this);
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
                final PickTime startPick = new PickTime(FullDownEventActivity.this);
                startPick.setPickTimelistener(new PickTime.PickTimelistener() {
                    @Override
                    public void time(String time) {
                        startTime =time;
                        txtStartTime.setText("开始时间："+time);
                    }
                });
                break;
            case R.id.txt_endTime:
                final PickTime endPick = new PickTime(FullDownEventActivity.this);
                endPick.setPickTimelistener(new PickTime.PickTimelistener() {
                    @Override
                    public void time(String time) {
                        endTime =time;
                        txtEndTime.setText("结束时间："+time);
                    }
                });
                break;
            case R.id.txt_addPrice:
                FullDownModel.FullCutPrice fullCutPrice = new FullDownModel.FullCutPrice();
                fullCutPrices.add(fullCutPrice);
                recycleAdapter.notifyDataSetChanged();
                break;
            case R.id.cb_deal:
                if(cbDeal.isChecked()){
                    flagDeal=true;
                }else{
                    flagDeal=false;
                }
                break;
            case R.id.txt_true:
                FullDownModel fullDownModel = new FullDownModel();
                fullDownModel.setStartDay(startDay);
                fullDownModel.setEndDay(endDay);
                fullDownModel.setToken(cApplication.getToken());
                fullDownModel.setStartTime(startTime);
                fullDownModel.setEndTime(endTime);
                fullDownModel.setFullCutPrices(fullCutPrices);
                if(flagDeal) {
                    mvpPresenter.loadFullDown(fullDownModel);
                }else{
                    ToastUtils.showShortToast("请阅读点击已阅读商家自主营销协议");
                }
                break;
        }
    }

}
