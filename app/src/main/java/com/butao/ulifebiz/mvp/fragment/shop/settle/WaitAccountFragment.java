package com.butao.ulifebiz.mvp.fragment.shop.settle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpFragment;
import com.butao.ulifebiz.mvp.activity.shop.settle.AccountSettleActivity;
import com.butao.ulifebiz.mvp.model.shop.AccountModel;
import com.butao.ulifebiz.mvp.presenter.AccountPresenter;
import com.butao.ulifebiz.mvp.view.AccountView;
import com.butao.ulifebiz.util.DoubleUtil;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.util.TimeUtil;
import com.butao.ulifebiz.view.LoadMoreView;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.maning.calendarlibrary.widget.DateScrollerDialog;
import com.maning.calendarlibrary.widget.data.Type;
import com.maning.calendarlibrary.widget.listener.OnDateSetListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/6.
 * 编写人 ：bodong
 * 功能描述 ：代入帐
 */
public class WaitAccountFragment extends MvpFragment<AccountPresenter> implements AccountView {
    AccountModel accountModel = new AccountModel();
    @Bind(R.id.txt_accounttime)
    TextView txtAccounttime;
    @Bind(R.id.txt_orderNum)
    TextView txtOrderNum;
    @Bind(R.id.txt_orderprice)
    TextView txtOrderprice;
    @Bind(R.id.ry_account)
    RecyclerView ryAccount;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    int pageNum=0;
    List<AccountModel.AccountOrder> accountOrders = new ArrayList<>();
    CommonRecycleAdapter recycleAdapter;
    String starttime="",endTime="";
    double totalprice = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_account, null);
        ButterKnife.bind(this, view);
        pageNum=0;
        starttime = TimeUtil.getDate();
        endTime = TimeUtil.getDate();
        txtAccounttime.setText(TimeUtil.getDayDate()+"-"+TimeUtil.getDayDate());
        mvpPresenter.loadAccount(pageNum,1+"",starttime,endTime);
        initRFView();
        return view;
    }
    public void loadAccount() {
        mvpPresenter.loadAccount(pageNum,1+"",starttime,endTime);
    }
    @Override
    protected AccountPresenter createPresenter() {
        return new AccountPresenter(this);
    }

    @Override
    public void getAccountSuccess(BaseModel<AccountModel> model) {
        if ("200".equals(model.getCode())) {
            accountModel = model.getData();
            if (accountModel != null) {
                if (!TextUtils.isEmpty(accountModel.getTotaPrice())) {
                    if (pageNum == 0) {
                        totalprice =Double.parseDouble(accountModel.getTotaPrice());
                    } else {
                        totalprice =totalprice+Double.parseDouble(accountModel.getTotaPrice());
                    }
                    AccountSettleActivity.settleActivity.setTextView(String.valueOf(totalprice));
                    SpannableString spannableString = new SpannableString("价格:" + DoubleUtil.KeepTwoDecimal(String.valueOf(totalprice) ));
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#f39801")), 3,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    txtOrderprice.setText(spannableString);
                }
                if (!TextUtils.isEmpty(accountModel.getOrderNum())) {
                    SpannableString spannableString = new SpannableString("数量:" + accountModel.getOrderNum());
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#f39801")), 3,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    txtOrderNum.setText(spannableString);
                }
                if (pageNum == 0) {
                    if(!JsonUtil.isEmpty(accountModel.getOrderList())) {
                        accountOrders = accountModel.getOrderList();
                        initAccountView();
                    }
                } else {
                    accountOrders.addAll(accountModel.getOrderList());
                    recycleAdapter.setDatas(accountOrders);
                    recycleAdapter.notifyDataSetChanged();
                }
            }
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
        if (pageNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
        if (pageNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }

    private void initAccountView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ryAccount.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<AccountModel.AccountOrder>(getActivity(),R.layout.adapter_order_account,accountOrders) {
            @Override
            protected void convert(ViewHolder holder, final AccountModel.AccountOrder accountOrder, final int position) {
                if(accountOrder!=null){
                    if (!TextUtils.isEmpty(accountOrder.getOrderNo())) {
                        holder.setText(R.id.txt_orderNo, "单号："+accountOrder.getOrderNo());
                    } else {
                        holder.setText(R.id.txt_orderNo, "");
                    }
                    if (!TextUtils.isEmpty(accountOrder.getCreateTime())) {
                        holder.setText(R.id.txt_time, TimeUtil.timeToString3(TimeUtil.DataOne(accountOrder.getCreateTime())));
                    } else {
                        holder.setText(R.id.txt_time, "");
                    }
                    if (!TextUtils.isEmpty(accountOrder.getTotalPrice())) {
                        holder.setText(R.id.txt_orderInfo, accountOrder.getTotalPrice() + "£");
                    } else {
                        holder.setText(R.id.txt_orderInfo, "");
                    }
                    if (accountOrder.isExpend()) {
                        holder.setVisible(R.id.ll_child, true);
                    } else {
                        holder.setVisible(R.id.ll_child, false);
                    }
                    if (!TextUtils.isEmpty(accountOrder.getOrderName())) {
                        holder.setText(R.id.txt_orderName, accountOrder.getOrderName().replace("\\n", "\n"));
                    } else {
                        holder.setText(R.id.txt_orderName, "");
                    }
                    if (!TextUtils.isEmpty(accountOrder.getAddress())) {
                        holder.setText(R.id.txt_delivery, accountOrder.getAddress());
                    } else {
                        holder.setText(R.id.txt_delivery, "");
                    }
                    if (!TextUtils.isEmpty(accountOrder.getTotalPrice())) {
                        holder.setText(R.id.txt_orderPrice, accountOrder.getTotalPrice() + "£");
                    } else {
                        holder.setText(R.id.txt_orderPrice, "");
                    }
                    if (!TextUtils.isEmpty(accountOrder.getCreateTime())) {
                        holder.setText(R.id.txt_orderTime, accountOrder.getCreateTime());
                    } else {
                        holder.setText(R.id.txt_orderTime, "");
                    }
                    if (!TextUtils.isEmpty(accountOrder.getSentTime())) {
                        holder.setText(R.id.txt_sentTime, accountOrder.getSentTime());
                    } else {
                        holder.setText(R.id.txt_sentTime, "");
                    }
                    if (!TextUtils.isEmpty(accountOrder.getStatusName())) {
                        holder.setText(R.id.txt_orderStatus, accountOrder.getStatusName());
                    } else {
                        holder.setText(R.id.txt_orderStatus, "");
                    }
//                    holder.setOnClickListener(R.id.txt_orderInfo, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            accountOrders.get(position).setExpend(!accountOrder.isExpend());
//                            notifyDataSetChanged();
//                        }
//                    });
                }
            }
        };
        ryAccount.setAdapter(recycleAdapter);
    }

    @OnClick(R.id.txt_accounttime)
    public void onViewClicked() {
        long HUNDRED_YEARS = 100L * 365 * 1000 * 60 * 60 * 24L; // 100年
        DateScrollerDialog dialog = new DateScrollerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setTitleStringId("请选择日期")
                .setMinMilliseconds(System.currentTimeMillis() - HUNDRED_YEARS)
                .setMaxMilliseconds(System.currentTimeMillis() + HUNDRED_YEARS)
                .setCallback(mOnDateSetListener)
                .build();

        if (dialog != null) {
            if (!dialog.isAdded()) {
                dialog.show(getChildFragmentManager(), "year_month_day");
            }
        }
    }
    // 数据的回调
    private OnDateSetListener mOnDateSetListener = new OnDateSetListener() {
        @Override public void onDateSet(DateScrollerDialog timePickerView, long milliseconds, long milliFinishseconds) {
            String text = TimeUtil.TimeString(String.valueOf(milliseconds));
            String text2 = TimeUtil.TimeString(String.valueOf(milliFinishseconds));
            txtAccounttime.setText(text + "-" + text2);
            starttime=TimeUtil.TimeYearToString(String.valueOf(milliseconds));
            endTime=TimeUtil.TimeYearToString(String.valueOf(milliFinishseconds));
            mvpPresenter.loadAccount(pageNum,"1",starttime,endTime);
        }
    };
    /**
     * 初始化刷新控件
     */
    public void initRFView() {
        SinaRefreshView headerView = new SinaRefreshView(getActivity());
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        trlView.setHeaderView(headerView);
        LoadMoreView loadingView = new LoadMoreView(getActivity());
        trlView.setBottomView(loadingView);
        trlView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 0;
                mvpPresenter.loadAccount(pageNum,1+"",starttime,endTime);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (accountModel.getOrderList().size() > 9) {
                    pageNum++;
                    mvpPresenter.loadAccount(pageNum,1+"",starttime,endTime);
                } else {
                    refreshLayout.finishLoadmore();
                    ToastUtils.showShortToast("暂无更多数据");
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
