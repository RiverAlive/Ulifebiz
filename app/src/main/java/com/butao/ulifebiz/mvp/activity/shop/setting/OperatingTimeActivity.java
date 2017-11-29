package com.butao.ulifebiz.mvp.activity.shop.setting;

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
import com.butao.ulifebiz.mvp.model.shop.setting.OperatTimeModel;
import com.butao.ulifebiz.mvp.presenter.OperatTimePresenter;
import com.butao.ulifebiz.mvp.view.OperatTimeView;
import com.butao.ulifebiz.util.ButtonUtils;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.util.MathUtil;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.adapter.MultiItemTypeAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/5.
 * 编写人 ：bodong
 * 功能描述 ：营业时间设置
 */
public class OperatingTimeActivity extends MvpActivity<OperatTimePresenter> implements OperatTimeView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    List<OperatTimeModel.BTimeModel> bTimeModels = new ArrayList<>();
    @Bind(R.id.ry_weeks)
    RecyclerView ryWeeks;
    CommonRecycleAdapter recycleAdapter;
    int deleteNum=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operating_time);
        ButterKnife.bind(this);
        txtTitle.setText("营业时间设置");
        //adapter_operation_time.xml
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvpPresenter.loadOperatTime();
    }

    @Override
    protected OperatTimePresenter createPresenter() {
        return new OperatTimePresenter(this);
    }

    @Override
    public void getOperatTimeSuccess(BaseModel<OperatTimeModel> model) {
        if ("200".equals(model.getCode())) {
            bTimeModels = model.getData().getWeeks();
            initRyview();
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 营业时间删除
     * @param model
     */
    @Override
    public void getDeleteTimeSuccess(BaseModel<OperatTimeModel> model) {
        if ("200".equals(model.getCode())) {
            bTimeModels.remove(deleteNum);
            recycleAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    private void initRyview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OperatingTimeActivity.this);
        ryWeeks.setLayoutManager(linearLayoutManager);
        recycleAdapter = new CommonRecycleAdapter<OperatTimeModel.BTimeModel>(OperatingTimeActivity.this, R.layout.adapter_operation_time, bTimeModels) {
            @Override
            protected void convert(ViewHolder holder, final OperatTimeModel.BTimeModel bTimeModel, final int position) {
                if (bTimeModel != null) {
                    String times = "";
                    if (!TextUtils.isEmpty(bTimeModel.getStartTime())) {
                        times = bTimeModel.getStartTime();
                        if (!TextUtils.isEmpty(bTimeModel.getEndTime())) {
                            times = times+"-"+bTimeModel.getEndTime();
                        }
                    }
                    holder.setText(R.id.txt_time, times);
                    String[] weeks;
                    String weektxt = "";

                    if (!TextUtils.isEmpty(bTimeModel.getWeeks())) {
                        weeks = bTimeModel.getWeeks().split(",");
                        weektxt=  MathUtil.Week(weeks);
                    }
                    holder.setText(R.id.txt_weeks,weektxt);
                    holder.setOnClickListener(R.id.txt_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteNum=position;
                            mvpPresenter.loadDeleteTime(bTimeModel.getId());
                        }
                    });
                }
            }
        };
        ryWeeks.setAdapter(recycleAdapter);
        recycleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(!ButtonUtils.isFastDoubleClick(1000)) {
//                    Bundle  bundle = new Bundle();
//                    bundle.putString("type","revamp");
//                    bundle.putString("json", JsonUtil.toJson(bTimeModels.get(position)));
//                    gotoActivity(OperatingTimeActivity.this, AddOperatingTimeActivity.class,bundle);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.txt_add_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_add_time:
                if(!ButtonUtils.isFastDoubleClick(1000)) {
                    Bundle  bundle = new Bundle();
                    bundle.putString("type","add");
                    gotoActivity(OperatingTimeActivity.this, AddOperatingTimeActivity.class,bundle);
                }
                break;
        }
    }

}
