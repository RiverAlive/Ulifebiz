package com.butao.ulifebiz.mvp.activity.shop.setting;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.dialog.TimeWheelView;
import com.butao.ulifebiz.mvp.model.shop.setting.OperatTimeModel;
import com.butao.ulifebiz.mvp.model.shop.setting.WeekModel;
import com.butao.ulifebiz.mvp.presenter.AddOTimePresenter;
import com.butao.ulifebiz.mvp.view.AddOTimeView;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;
import com.butao.ulifebiz.view.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/6.
 * 编写人 ：bodong
 * 功能描述 ：新建营业时间
 */
public class AddOperatingTimeActivity extends MvpActivity<AddOTimePresenter> implements AddOTimeView {
    public static AddOperatingTimeActivity timeActivity = null;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.wv_hour)
    WheelView wvHour;
    @Bind(R.id.wv_minute)
    WheelView wvMinute;
    @Bind(R.id.ry_weeks)
    RecyclerView ryWeeks;
    CommonRecycleAdapter recycleAdapter;
    List<WeekModel> weekModels = new ArrayList<>();
    TimeWheelView timeWheelView;
    String startTime = "";
    String stopTime = "",sstime="";
    boolean startstop = false;
    String weeks = "";
    Bundle bundle;
    String[] timeWeek;
    OperatTimeModel.BTimeModel bTimeModel;
    @Bind(R.id.txt_meal_time)
    TextView txtMealTime;
    @Bind(R.id.txt_stop_time)
    TextView txtStopTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operating_time);
        ButterKnife.bind(this);
        timeActivity = this;
        txtTitle.setText("营业时间设置");
        timeWheelView = new TimeWheelView(this, wvHour, wvMinute);
        startstop = true;
        bundle = getIntent().getExtras();
        if ("revamp".equals(bundle.getString("type"))) {
            bTimeModel = JsonUtil.fromJson(bundle.getString("json"), OperatTimeModel.BTimeModel.class);
            startTime = bTimeModel.getStartTime();
            stopTime = bTimeModel.getEndTime();
            if (!TextUtils.isEmpty(startTime)) {
                timeWheelView.setTime(Integer.parseInt(startTime.split(":")[0]),
                        Integer.parseInt(startTime.split(":")[1]), startstop);
            } else {
                timeWheelView.setTime(0, 0, startstop);
            }
            timeWeek = bTimeModel.getWeeks().split(",");
        } else {
            timeWheelView.setTime();
        }
        txtStopTime.setText(stopTime);
        txtMealTime.setText(startTime);
        initRyView();
    }

    public void setStartStopTime(String time) {
        sstime=time;

//        if (startstop) {
//            startTime = time;
//          txtMealTime.setText(startTime);
//        } else {
//            stopTime = time;
//            txtStopTime.setText(stopTime);
//        }
    }

    @Override
    protected AddOTimePresenter createPresenter() {
        return new AddOTimePresenter(this);
    }

    @Override
    public void getTimeSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            if (bTimeModel != null) {
                ToastUtils.showLongToast("修改营业时间成功");
            } else {
                ToastUtils.showLongToast("添加营业时间成功");
            }
            finish();
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showLongToast(model);
    }

    /**
     * 加载周列表
     */
    private void initRyView() {
        for (int i = 1; i <= 7; i++) {
            WeekModel weekModel = new WeekModel();
            if (i == 7) {
                weekModel.setWeek("Sun");
            } else {
                weekModel.setWeek("" + i);
            }
            weekModels.add(weekModel);
        }
        if (timeWeek != null && timeWeek.length > 0) {
            for (int i = 0; i < weekModels.size(); i++) {
                for (int j = 0; j < timeWeek.length; j++) {
                    if (weekModels.get(i).getWeek().equals(timeWeek[j])) {
                        weekModels.get(i).setSelect(true);
                    }
                }
            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AddOperatingTimeActivity.this, 7);
        ryWeeks.setLayoutManager(gridLayoutManager);
        recycleAdapter = new CommonRecycleAdapter<WeekModel>(AddOperatingTimeActivity.this, R.layout.adapter_week_item, weekModels) {
            @Override
            protected void convert(ViewHolder holder, final WeekModel weekModel, final int position) {
                if (weekModel != null) {
                    if (weekModel.isSelect()) {
                        holder.setBackgroundRes(R.id.txt_week, R.drawable.bg_week_select);
                        holder.setTextColorRes(R.id.txt_week, R.color.white);
                    } else {
                        holder.setBackgroundRes(R.id.txt_week, R.drawable.bg_week_unselect);
                        holder.setTextColorRes(R.id.txt_week, R.color.login_mian);
                    }
                    if (!TextUtils.isEmpty(weekModel.getWeek())) {
                        holder.setText(R.id.txt_week, weekModel.getWeek());
                    } else {
                        holder.setText(R.id.txt_week, "");
                    }
                    holder.setOnClickListener(R.id.txt_week, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            weekModels.get(position).setSelect(!weekModels.get(position).isSelect());
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        };
        ryWeeks.setAdapter(recycleAdapter);
    }

    @OnClick({R.id.ll_back, R.id.ll_Meal, R.id.ll_stop, R.id.ll_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_Meal:
                startstop = true;
//                if (!TextUtils.isEmpty(startTime)) {
//                    timeWheelView.setTime(Integer.parseInt(startTime.split(":")[0]),
//                            Integer.parseInt(startTime.split(":")[1]), startstop);
//                } else {
//                    timeWheelView.setTime(0, 0, startstop);
//                }
                startTime=sstime;
                txtMealTime.setText(startTime);
                break;
            case R.id.ll_stop:
                startstop = false;
//                if (!TextUtils.isEmpty(stopTime)) {
//                    timeWheelView.setTime(Integer.parseInt(stopTime.split(":")[0]),
//                            Integer.parseInt(stopTime.split(":")[1]), startstop);
//                } else {
//                    timeWheelView.setTime(0, 0, startstop);
//                }
                stopTime=sstime;
                txtStopTime.setText(stopTime);
                break;
            case R.id.ll_save:
                weeks="";
                for (int i = 0; i < weekModels.size(); i++) {
                    if (weekModels.get(i).isSelect()) {
                        if (weekModels.get(i).getWeek().equals("Sun")) {
                            if (!TextUtils.isEmpty(weeks)) {
                                weeks = weeks + "," + "7";
                            } else {
                                weeks = "7";
                            }
                        } else {
                            if (!TextUtils.isEmpty(weeks)) {
                                weeks = weeks + "," + weekModels.get(i).getWeek();
                            } else {
                                weeks = weekModels.get(i).getWeek();
                            }
                        }
                    }
                }
                if(!TextUtils.isEmpty(startTime)&&!TextUtils.isEmpty(stopTime)) {
                    if (!TextUtils.isEmpty(weeks)) {
                        if (bTimeModel != null) {
                            mvpPresenter.loadAddOTime(bTimeModel.getId(), startTime, stopTime, weeks);
                        } else {
                            mvpPresenter.loadAddOTime("", startTime, stopTime, weeks);
                        }
                    } else {
                        ToastUtils.showShortToast("请选择周几营业");
                    }
                }else{
                    ToastUtils.showShortToast("请选择开始营业时间和结束营业时间");
                }
                break;
        }
    }
}
