package com.butao.ulifebiz.mvp.activity.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.model.StoreMessageModel;
import com.butao.ulifebiz.mvp.model.shop.ShopEvaModel;
import com.butao.ulifebiz.mvp.presenter.StoreMessagePresenter;
import com.butao.ulifebiz.mvp.view.StoreMessageView;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.ExpandableTextView;
import com.butao.ulifebiz.view.LoadMoreView;
import com.butao.ulifebiz.view.StarBarView;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/29.
 * 编写人 ：bodong
 * 功能描述 ：消息通知
 */
public class NotificationNewsActivity extends MvpActivity<StoreMessagePresenter> implements StoreMessageView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_news)
    RecyclerView ryNews;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    StoreMessageModel storeMessageModel = new StoreMessageModel();
    List<StoreMessageModel.MessageModel> messageModels = new ArrayList<>();
    CommonRecycleAdapter recycleAdapter;
    int pageNum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_news);
        ButterKnife.bind(this);
        txtTitle.setText("消息通知");
        initRFView();
        pageNum=0;
        mvpPresenter.loadStoreMassages(pageNum);
    }

    @Override
    protected StoreMessagePresenter createPresenter() {
        return new StoreMessagePresenter(this);
    }

    @Override
    public void getStoreMessageSuccess(BaseModel<StoreMessageModel> model) {
        if ("200".equals(model.getCode())) {
            storeMessageModel = model.getData();
            if (storeMessageModel != null) {
                if (!JsonUtil.isEmpty(storeMessageModel.getList())) {
                    if (pageNum == 0) {
                        messageModels = storeMessageModel.getList();
                    } else {
                        messageModels.addAll(storeMessageModel.getList());
                    }
                }
                if (!JsonUtil.isEmpty(messageModels)) {
                    if (pageNum == 0) {
                        initRyView();
                    } else {
                        recycleAdapter.notifyDataSetChanged();
                    }
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
    private void initRyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(NotificationNewsActivity.this);
        ryNews.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<StoreMessageModel.MessageModel>(NotificationNewsActivity.this, R.layout.adapter_notification_new, messageModels) {
            @Override
            protected void convert(ViewHolder holder, StoreMessageModel.MessageModel messageModel, int position) {
                if (messageModel != null) {
                    ExpandableTextView  etvProfile = holder.getView(R.id.etv_profile);
                    if (!TextUtils.isEmpty(messageModel.getRemark())) {
                        etvProfile.setText(messageModel.getRemark());
                    } else {
                        etvProfile.setText("");
                    }
                    if (!TextUtils.isEmpty(messageModel.getTitle())) {
                        holder.setText(R.id.txt_title, messageModel.getTitle());
                    } else {
                        holder.setText(R.id.txt_title, "");
                    }
                    if (!TextUtils.isEmpty(messageModel.getCreatTime())) {
                        holder.setText(R.id.txt_time, messageModel.getCreatTime());
                    } else {
                        holder.setText(R.id.txt_time, "");
                    }
                }
            }
        };
        ryNews.setAdapter(recycleAdapter);
    }

    /**
     * 初始化刷新控件
     */
    public void initRFView() {
        SinaRefreshView headerView = new SinaRefreshView(NotificationNewsActivity.this);
        headerView.setArrowResource(R.mipmap.arrow);
        headerView.setTextColor(0xff745D5C);
        trlView.setHeaderView(headerView);
        LoadMoreView loadingView = new LoadMoreView(NotificationNewsActivity.this);
        trlView.setBottomView(loadingView);
        trlView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageNum = 0;
                mvpPresenter.loadStoreMassages(pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (storeMessageModel != null) {
                    if (storeMessageModel.getList().size() > 9) {
                        pageNum++;
                        mvpPresenter.loadStoreMassages(pageNum);
                    } else {
                        refreshLayout.finishLoadmore();
                        ToastUtils.showShortToast("暂无更多数据");
                    }
                }
            }
        });
    }
    @OnClick(R.id.ll_back)
    public void onClick() {
        finish();
    }
}
