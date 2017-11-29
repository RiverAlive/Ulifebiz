package com.butao.ulifebiz.mvp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.base.MvpLoadFragment;
import com.butao.ulifebiz.mvp.activity.order.OrderRemindActivity;
import com.butao.ulifebiz.mvp.dialog.OrderCanelDialog;
import com.butao.ulifebiz.mvp.model.BriefModel;
import com.butao.ulifebiz.mvp.model.HomeTabModel;
import com.butao.ulifebiz.mvp.presenter.HomeTabPresenter;
import com.butao.ulifebiz.mvp.view.HomeTabView;
import com.butao.ulifebiz.util.ButtonUtils;
import com.butao.ulifebiz.util.DoubleUtil;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/20.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class HomeFragment extends MvpLoadFragment<HomeTabPresenter> implements HomeTabView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_order)
    RecyclerView ryOrder;
    @Bind(R.id.ry_haveorder)
    RecyclerView ryHaveorder;
    @Bind(R.id.ll_situation)
    LinearLayout llSituation;
    @Bind(R.id.ll_order_remind)
    LinearLayout llOrderRemind;
    @Bind(R.id.img_brief)
    ImageView imgBrief;
    @Bind(R.id.img_neworder)
    ImageView imgNeworder;
    @Bind(R.id.rl_brief_main)
    RelativeLayout rlBriefMain;
    @Bind(R.id.txt_status)
    TextView txtStatus;
    HomeTabModel homeTabModel = new HomeTabModel();
    @Bind(R.id.txt_newOrderNum)
    TextView txtNewOrderNum;
    @Bind(R.id.txt_warn)
    TextView txtWarn;
    @Bind(R.id.txt_orderNum)
    TextView txtOrderNum;
    @Bind(R.id.txt_orderPrice)
    TextView txtOrderPrice;
    @Bind(R.id.txt_brief_orderNum)
    TextView txtBriefOrderNum;
    @Bind(R.id.txt_brief_orderPrice)
    TextView txtBriefOrderPrice;
    @Bind(R.id.pr_view)
    SwipeRefreshLayout prView;
    boolean brief = false;//营业简报
    boolean NewOrder = false;//新订单
    boolean HaveOrder = false;//已接订单
    CommonRecycleAdapter recycleAdapter;
    CommonRecycleAdapter haveAdapter;
    BriefModel briefModel = new BriefModel();
    @Bind(R.id.txt_oldCustomer)
    TextView txtOldCustomer;
    @Bind(R.id.txt_newCustomer)
    TextView txtNewCustomer;
    @Bind(R.id.txt_average)
    TextView txtAverage;
    int select = -1;
    int selecthave = -1;
    @Bind(R.id.txt_haveOrderNum)
    TextView txtHaveOrderNum;
    @Bind(R.id.img_haveorder)
    ImageView imgHaveorder;
    String type = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, null);
        ButterKnife.bind(this, view);
        imgBack.setVisibility(View.GONE);
        txtTitle.setText("商家端");
        rlBriefMain.setVisibility(View.GONE);
        brief = false;
        NewOrder = false;
        HaveOrder = false;
        if (!TextUtils.isEmpty(cApplication.getStatusName())) {
            txtStatus.setText(cApplication.getStatusName());
        }
        registerReceiver();
        prView.setColorSchemeResources(R.color.login_mian);
        prView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (!ButtonUtils.isFastDoubleClick(R.id.pr_view)) {
                            mvpPresenter.loadHomeTab();
                            mvpPresenter.loadBrief();
                            prView.setRefreshing(false);
                        }
                    }

                }, 2000);
            }
        });


        //adapter   adapter_order_parent.xml  adapter_order_child.xml
        return view;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_home_tab;
    }

    @Override
    protected void lazyLoad() {
        mvpPresenter.loadHomeTab();
        mvpPresenter.loadBrief();
    }

    @Override
    public void onResume() {
        super.onResume();
        mvpPresenter.loadHomeTab();
    }

    private void registerReceiver() {
        IntentFilter itFilter = new IntentFilter("Home");
        getActivity().registerReceiver(myReceiver, itFilter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("Home")) {
                mvpPresenter.loadHomeTab();
                mvpPresenter.loadBrief();
            }
        }
    };

    @Override
    protected HomeTabPresenter createPresenter() {
        return new HomeTabPresenter(this);
    }

    /**
     * 商家首页
     *
     * @param model
     */
    @Override
    public void getHomeSuccess(BaseModel<HomeTabModel> model) {
        if ("200".equals(model.getCode())) {
            homeTabModel = model.getData();
            initHomeView();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CApplication.getIntstance().setSound(false);
                }
            }, 25000);
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 简介信息
     *
     * @param model
     */
    @Override
    public void getBriefSuccess(BaseModel<BriefModel> model) {
        if ("200".equals(model.getCode())) {
            briefModel = model.getData();
            if (briefModel != null) {
                if (!TextUtils.isEmpty(briefModel.getOrderNum())) {
                    txtBriefOrderNum.setText(briefModel.getOrderNum());
                } else {
                    txtBriefOrderNum.setText("");
                }
                if (!TextUtils.isEmpty(briefModel.getOrderPrice())) {
                    txtBriefOrderPrice.setText(DoubleUtil.KeepTwoDecimal(briefModel.getOrderPrice()));
                } else {
                    txtBriefOrderPrice.setText("");
                }
                if (!TextUtils.isEmpty(briefModel.getOldMember())) {
                    txtOldCustomer.setText(briefModel.getOldMember());
                } else {
                    txtOldCustomer.setText("");
                }
                if (!TextUtils.isEmpty(briefModel.getOrderPriceAvg())) {
                    txtAverage.setText(DoubleUtil.KeepTwoDecimal(briefModel.getOrderPriceAvg()));
                } else {
                    txtAverage.setText("");
                }
                if (!TextUtils.isEmpty(briefModel.getUnOldMember())) {
                    txtNewCustomer.setText(DoubleUtil.KeepTwoDecimal(briefModel.getUnOldMember()));
                } else {
                    txtNewCustomer.setText("");
                }
            }
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 取消订单
     *
     * @param model
     */
    @Override
    public void getRefundSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("订单取消成功");
            if ("new".equals(type)) {
                homeTabModel.getNewOrders().remove(select);
                recycleAdapter.notifyDataSetChanged();
            } else {
                homeTabModel.getConfirmOrders().remove(selecthave);
                haveAdapter.notifyDataSetChanged();
            }
            mvpPresenter.loadHomeTab();
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 接单
     *
     * @param model
     */
    @Override
    public void getAcceptOrderSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("接单成功");
            homeTabModel.getNewOrders().remove(select);
            recycleAdapter.notifyDataSetChanged();
            mvpPresenter.loadHomeTab();
        } else {
            ToastUtils.showLongToast(String.valueOf(model.getData()));
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
        getActivity().unregisterReceiver(myReceiver);
    }

    private void initHomeView() {
        if (homeTabModel != null) {
            if (!TextUtils.isEmpty(homeTabModel.getNewOrderNum())) {
                SpannableString spannableString = new SpannableString("您有" + homeTabModel.getNewOrderNum() + "个新订单");
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#c10808")), 2, 2 + homeTabModel.getNewOrderNum().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtNewOrderNum.setText(spannableString);
//                txtNewOrderNum.setText("您有" + homeTabModel.getNewOrderNum() + "个新订单");
            } else {
                txtNewOrderNum.setText("暂无新订单");
            }
            if (!TextUtils.isEmpty(homeTabModel.getOrderWarn())) {
                txtWarn.setText(homeTabModel.getOrderWarn());
            } else {
                txtWarn.setText("");
            }
            if (!TextUtils.isEmpty(homeTabModel.getOrderNum())) {
                txtOrderNum.setText(homeTabModel.getOrderNum());
            } else {
                txtOrderNum.setText("");
            }
            if (!TextUtils.isEmpty(homeTabModel.getOrderPrice())) {
                txtOrderPrice.setText(DoubleUtil.KeepTwoDecimal(homeTabModel.getOrderPrice()));
            } else {
                txtOrderPrice.setText("");
            }
            if (!JsonUtil.isEmpty(homeTabModel.getNewOrders())) {
                initOrderRyView();
                if (NewOrder) {
                    ryOrder.setVisibility(View.VISIBLE);
                    imgNeworder.animate().setDuration(150).rotation(180);
                } else {
                    ryOrder.setVisibility(View.GONE);
                    imgNeworder.animate().setDuration(150).rotation(0);
                }
            }
            if (!JsonUtil.isEmpty(homeTabModel.getConfirmOrders())) {
                SpannableString spannableString = new SpannableString("已接订单(" + homeTabModel.getConfirmOrders().size() + ")");
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#f39801")), 5, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtHaveOrderNum.setText(spannableString);
                initHaveOrderRyView();
                if (HaveOrder) {
                    ryHaveorder.setVisibility(View.VISIBLE);
                    imgHaveorder.animate().setDuration(150).rotation(180);
                } else {
                    ryHaveorder.setVisibility(View.GONE);
                    imgHaveorder.animate().setDuration(150).rotation(0);
                }
            } else {
                SpannableString spannableString = new SpannableString("已接订单(" + 0 + ")");
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#f39801")), 5, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                txtHaveOrderNum.setText(spannableString);
                ryHaveorder.setVisibility(View.GONE);
                imgHaveorder.animate().setDuration(150).rotation(0);
            }
        }
    }

    /**
     * 新订单提醒
     */
    private void initOrderRyView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ryOrder.setLayoutManager(linearLayoutManager);
        recycleAdapter = new CommonRecycleAdapter<HomeTabModel.HomeOrders>(getActivity(), R.layout.adapter_order_parent, homeTabModel.getNewOrders()) {
            @Override
            protected void convert(final ViewHolder holder, final HomeTabModel.HomeOrders homeOrders, final int position) {
                if (homeOrders != null) {
                    if (!TextUtils.isEmpty(homeOrders.getOrderNo())) {
                        holder.setText(R.id.txt_orderNo, homeOrders.getOrderNo());
                    } else {
                        holder.setText(R.id.txt_orderNo, "");
                    }
                    if (homeOrders.isExpand()) {
                        holder.setVisible(R.id.ll_child, true);
                    } else {
                        holder.setVisible(R.id.ll_child, false);
                    }
                    holder.setVisible(R.id.ll_sendTime, true);
                    if (!TextUtils.isEmpty(homeOrders.getSendNote())) {
                        holder.setText(R.id.txt_sendTime, homeOrders.getSendNote());
                    } else {
                        holder.setText(R.id.txt_sendTime, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getOrderName())) {
                        holder.setText(R.id.txt_orderName, homeOrders.getOrderName().replace("\\n", "\n"));
                    } else {
                        holder.setText(R.id.txt_orderName, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getAddress())) {
                        holder.setText(R.id.txt_delivery, homeOrders.getAddress());
                    } else {
                        holder.setText(R.id.txt_delivery, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getTotalPrice())) {
                        holder.setText(R.id.txt_orderPrice, DoubleUtil.KeepTwoDecimal(homeOrders.getTotalPrice()) + "£");
                    } else {
                        holder.setText(R.id.txt_orderPrice, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getCreateTime())) {
                        holder.setText(R.id.txt_orderTime, homeOrders.getCreateTime());
                    } else {
                        holder.setText(R.id.txt_orderTime, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getPhone())) {
                        holder.setText(R.id.txt_sentTime, homeOrders.getPhone());
                    } else {
                        holder.setText(R.id.txt_sentTime, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getStatusName())) {
                        holder.setText(R.id.txt_orderStatus, homeOrders.getStatusName());
                    } else {
                        holder.setText(R.id.txt_orderStatus, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getZipcode())) {
                        holder.setText(R.id.txt_postcode, homeOrders.getZipcode());
                    } else {
                        holder.setText(R.id.txt_postcode, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getNote())) {
                        holder.setText(R.id.txt_remark, homeOrders.getNote());
                    } else {
                        holder.setText(R.id.txt_remark, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getStatus())) {
                        if ("11".equals(homeOrders.getStatus())) {
                            holder.setText(R.id.txt_receive, "接单");
                        } else {
                            holder.setText(R.id.txt_receive, "已接单");
                        }
                    }
                    holder.setOnClickListener(R.id.ll_order, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeTabModel.getNewOrders().get(position).setExpand(!homeOrders.isExpand());
                            notifyDataSetChanged();
                        }
                    });
                    holder.setVisible(R.id.ll_button, true);
                    holder.setOnClickListener(R.id.txt_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            type = "new";
                            select = position;
                            OrderCanelDialog canelDialog = new OrderCanelDialog(getActivity(), "cancel");
                            canelDialog.dialogShow();
                            canelDialog.setOrderCanellistener(new OrderCanelDialog.OrderCanellistener() {
                                @Override
                                public void OrderCanel() {
                                    mvpPresenter.loadRefund(homeOrders.getOrderId());
                                }
                            });
                        }
                    });
                    holder.setOnClickListener(R.id.txt_receive, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("11".equals(homeOrders.getStatus())) {
                                select = position;
                                mvpPresenter.loadacceptOrder(homeOrders.getOrderId());
                            } else {
                                ToastUtils.showShortToast("商家已自动接单");
                            }

                        }
                    });
                }
            }
        };
        ryOrder.setAdapter(recycleAdapter);
    }

    /**
     * 已接订单提醒
     */
    private void initHaveOrderRyView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ryHaveorder.setLayoutManager(linearLayoutManager);
        haveAdapter = new CommonRecycleAdapter<HomeTabModel.HomeOrders>(getActivity(), R.layout.adapter_order_parent, homeTabModel.getConfirmOrders()) {
            @Override
            protected void convert(final ViewHolder holder, final HomeTabModel.HomeOrders homeOrders, final int position) {
                if (homeOrders != null) {
                    if (!TextUtils.isEmpty(homeOrders.getOrderNo())) {
                        holder.setText(R.id.txt_orderNo, homeOrders.getOrderNo());
                    } else {
                        holder.setText(R.id.txt_orderNo, "");
                    }
                    if (homeOrders.isExpand()) {
                        holder.setVisible(R.id.ll_child, true);
                    } else {
                        holder.setVisible(R.id.ll_child, false);
                    }
                    holder.setVisible(R.id.ll_sendTime, true);
                    if (!TextUtils.isEmpty(homeOrders.getSendNote())) {
                        holder.setText(R.id.txt_sendTime, homeOrders.getSendNote());
                    } else {
                        holder.setText(R.id.txt_sendTime, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getOrderName())) {
                        holder.setText(R.id.txt_orderName, homeOrders.getOrderName());
                    } else {
                        holder.setText(R.id.txt_orderName, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getAddress())) {
                        holder.setText(R.id.txt_delivery, homeOrders.getAddress());
                    } else {
                        holder.setText(R.id.txt_delivery, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getTotalPrice())) {
                        holder.setText(R.id.txt_orderPrice, DoubleUtil.KeepTwoDecimal(homeOrders.getTotalPrice()) + "£");
                    } else {
                        holder.setText(R.id.txt_orderPrice, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getCreateTime())) {
                        holder.setText(R.id.txt_orderTime, homeOrders.getCreateTime());
                    } else {
                        holder.setText(R.id.txt_orderTime, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getPhone())) {
                        holder.setText(R.id.txt_sentTime, homeOrders.getPhone());
                    } else {
                        holder.setText(R.id.txt_sentTime, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getStatusName())) {
                        holder.setText(R.id.txt_orderStatus, homeOrders.getStatusName());
                    } else {
                        holder.setText(R.id.txt_orderStatus, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getZipcode())) {
                        holder.setText(R.id.txt_postcode, homeOrders.getZipcode());
                    } else {
                        holder.setText(R.id.txt_postcode, "");
                    }
                    if (!TextUtils.isEmpty(homeOrders.getNote())) {
                        holder.setText(R.id.txt_remark, homeOrders.getNote());
                    } else {
                        holder.setText(R.id.txt_remark, "");
                    }
                    holder.setOnClickListener(R.id.ll_order, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            homeTabModel.getConfirmOrders().get(position).setExpand(!homeOrders.isExpand());
                            notifyDataSetChanged();
                        }
                    });
                    holder.setVisible(R.id.ll_button, true);
                    holder.setVisible(R.id.txt_receive, false);
                    holder.setOnClickListener(R.id.txt_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            type = "have";
                            selecthave = position;
                            OrderCanelDialog canelDialog = new OrderCanelDialog(getActivity(), "cancel");
                            canelDialog.dialogShow();
                            canelDialog.setOrderCanellistener(new OrderCanelDialog.OrderCanellistener() {
                                @Override
                                public void OrderCanel() {
                                    mvpPresenter.loadRefund(homeOrders.getOrderId());
                                }
                            });
                        }
                    });
                }
            }
        };
        ryHaveorder.setAdapter(haveAdapter);
    }


    @OnClick({R.id.ll_situation, R.id.ll_order_remind, R.id.ll_brief, R.id.ll_neworder, R.id.ll_haveorder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_situation://营业情况
                break;
            case R.id.ll_order_remind://订单提醒
                startActivity(getActivity(), OrderRemindActivity.class);
                break;
            case R.id.ll_brief://营业简报
                if (brief) {
                    imgBrief.animate().setDuration(150).rotation(180);
                    rlBriefMain.setVisibility(View.VISIBLE);
                    brief = false;
                } else {
                    imgBrief.animate().setDuration(150).rotation(0);
                    rlBriefMain.setVisibility(View.GONE);
                    brief = true;
                }
                break;
            case R.id.ll_neworder://新訂單
                if (NewOrder) {
                    imgNeworder.animate().setDuration(150).rotation(180);
                    ryOrder.setVisibility(View.VISIBLE);
                    NewOrder = false;
                } else {
                    imgNeworder.animate().setDuration(150).rotation(0);
                    ryOrder.setVisibility(View.GONE);
                    NewOrder = true;
                }
                break;
            case R.id.ll_haveorder://新訂單
                if (HaveOrder) {
                    imgHaveorder.animate().setDuration(150).rotation(180);
                    ryHaveorder.setVisibility(View.VISIBLE);
                    HaveOrder = false;
                } else {
                    imgHaveorder.animate().setDuration(150).rotation(0);
                    ryHaveorder.setVisibility(View.GONE);
                    HaveOrder = true;
                }
                break;
        }
    }


}
