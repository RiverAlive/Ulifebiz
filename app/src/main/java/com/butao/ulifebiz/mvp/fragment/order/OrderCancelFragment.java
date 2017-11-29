package com.butao.ulifebiz.mvp.fragment.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpLoadFragment;
import com.butao.ulifebiz.mvp.model.order.RefundOrderModel;
import com.butao.ulifebiz.mvp.presenter.OrderListPresenter;
import com.butao.ulifebiz.mvp.view.OrderListView;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.LoadMoreView;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;
import com.butao.ulifebiz.view.recyclerview.wrapper.EmptyWrapper;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建时间 ：2017/8/28.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class OrderCancelFragment extends MvpLoadFragment<OrderListPresenter> implements OrderListView {
    @Bind(R.id.ry_order)
    RecyclerView ryOrder;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    private String status = "";
    int pageNum = 0;
    RefundOrderModel refundOrderModel = new RefundOrderModel();
    List<RefundOrderModel.RFOrderModel> rfOrderModels = new ArrayList<>();
    EmptyWrapper emptyWrapper;
    CommonRecycleAdapter recycleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status, null);
        ButterKnife.bind(this, view);
        initRFView();
        pageNum=0;
        refundOrderModel = new RefundOrderModel();
        rfOrderModels = new ArrayList<>();
        Bundle bundle = this.getArguments();
        status = bundle.getString("type");
        mvpPresenter.loadOrderList(pageNum,status);
        //adapter   adapter_order_status.xml
        return view;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_order_status;
    }

    @Override
    protected void lazyLoad() {
        initRFView();
        pageNum=0;
        mvpPresenter.loadOrderList(pageNum,status);
    }
    public void LoadOrder(String status) {
        OrderListPresenter presenter = new OrderListPresenter(this);
        pageNum=0;
        mvpPresenter.loadOrderList(pageNum,status);
    }
    @Override
    protected OrderListPresenter createPresenter() {
        return new OrderListPresenter(this);
    }

    /**
     * 订单列表
     *
     * @param model
     */
    @Override
    public void getOrderListSuccess(BaseModel<RefundOrderModel> model) {
        if ("200".equals(model.getCode())) {
            if (model.getData() != null) {
                refundOrderModel=model.getData();
                if(refundOrderModel!=null&&!JsonUtil.isEmpty(refundOrderModel.getOrderList())) {
                    if(pageNum==0){
                        rfOrderModels = refundOrderModel.getOrderList();
                    }else{
                        rfOrderModels.addAll(refundOrderModel.getOrderList());
                    }
                }else{
                    rfOrderModels=new ArrayList<>();
                }
                initRyView();
            }
        } else {
            ToastUtils.showLongToast(model.getData().getError());
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

    private void initRyView() {
        if (pageNum == 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            ryOrder.setLayoutManager(layoutManager);
            recycleAdapter = new CommonRecycleAdapter<RefundOrderModel.RFOrderModel>(getActivity(), R.layout.adapter_order_status, rfOrderModels) {
                @Override
                protected void convert(ViewHolder holder, final RefundOrderModel.RFOrderModel rfOrderModel, final int position) {
                    if (rfOrderModel != null) {
                        if (!TextUtils.isEmpty(rfOrderModel.getOrderNo())) {
                            holder.setText(R.id.txt_orderNo, rfOrderModel.getOrderNo());
                        } else {
                            holder.setText(R.id.txt_orderNo, "");
                        }
                        if (rfOrderModel.isExpand()) {
                            holder.setVisible(R.id.ll_child, true);
                        } else {
                            holder.setVisible(R.id.ll_child, false);
                        }
                        if (!TextUtils.isEmpty(rfOrderModel.getOrderName())) {
                            TextView textView= holder.getView(R.id.txt_orderName);
                            textView.setMaxLines(2);
                            textView.setEllipsize(TextUtils.TruncateAt.END);
                            holder.setText(R.id.txt_orderName, rfOrderModel.getOrderName().replace("\\n", "\n"));
                        } else {
                            holder.setText(R.id.txt_orderName, "");
                        }
                        if (!TextUtils.isEmpty(rfOrderModel.getAddress())) {
                            holder.setText(R.id.txt_delivery, rfOrderModel.getAddress());
                        } else {
                            holder.setText(R.id.txt_delivery, "");
                        }
                        if (!TextUtils.isEmpty(rfOrderModel.getTotalPrice())) {
                            holder.setText(R.id.txt_orderPrice, rfOrderModel.getTotalPrice() + "£");
                        } else {
                            holder.setText(R.id.txt_orderPrice, "");
                        }
                        if (!TextUtils.isEmpty(rfOrderModel.getCreateTime())) {
                            holder.setText(R.id.txt_orderTime, rfOrderModel.getCreateTime());
                        } else {
                            holder.setText(R.id.txt_orderTime, "");
                        }
                        if (!TextUtils.isEmpty(rfOrderModel.getPhone())) {
                            holder.setText(R.id.txt_sentTime, rfOrderModel.getPhone());
                        } else {
                            holder.setText(R.id.txt_sentTime, "");
                        }
                        if (!TextUtils.isEmpty(rfOrderModel.getStatusName())) {
                            holder.setText(R.id.txt_orderStatus, rfOrderModel.getStatusName());
                        } else {
                            holder.setText(R.id.txt_orderStatus, "");
                        }
                        if (!TextUtils.isEmpty(rfOrderModel.getZipcode())) {
                            holder.setText(R.id.txt_postcode, rfOrderModel.getZipcode());
                        } else {
                            holder.setText(R.id.txt_postcode, "");
                        }
                        if (!TextUtils.isEmpty(rfOrderModel.getNote())) {
                            holder.setText(R.id.txt_remark, rfOrderModel.getNote());
                        } else {
                            holder.setText(R.id.txt_remark, "");
                        }
                        holder.setOnClickListener(R.id.ll_order, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rfOrderModels.get(position).setExpand(!rfOrderModel.isExpand());
                                emptyWrapper.notifyDataSetChanged();
                            }
                        });
                    }
                }
            };
            emptyWrapper = new EmptyWrapper(recycleAdapter);
            emptyWrapper.setEmptyView(LayoutInflater.from(getActivity()).
                    inflate(R.layout.view_empty_util, ryOrder, false), getResources().getDrawable(R.mipmap.cuidantixing_no), "没有催单提醒\nNo reminder");
            ryOrder.setAdapter(emptyWrapper);
        } else {
            recycleAdapter.setupdateDatas(rfOrderModels);
            emptyWrapper.notifyDataSetChanged();
        }
        if (pageNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }
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
                mvpPresenter.loadOrderList(pageNum,status);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (refundOrderModel.getOrderList().size() > 9) {
                    pageNum++;
                    mvpPresenter.loadOrderList(pageNum,status);
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
