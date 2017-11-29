package com.butao.ulifebiz.mvp.fragment.shop.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpFragment;
import com.butao.ulifebiz.mvp.model.shop.ShopEvaModel;
import com.butao.ulifebiz.mvp.presenter.ShopEvaPresenter;
import com.butao.ulifebiz.mvp.view.ShopEvaView;
import com.butao.ulifebiz.util.JsonUtil;
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

/**
 * 创建时间 ：2017/9/4.
 * 编写人 ：bodong
 * 功能描述 ：商品评价
 */
public class ShopEvaluateFragment extends MvpFragment<ShopEvaPresenter> implements ShopEvaView {
    @Bind(R.id.ry_evas)
    RecyclerView ryEvas;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    private String status = "";
    int pageNum = 0;
    ShopEvaModel shopEvaModel = new ShopEvaModel();
    CommonRecycleAdapter recycleAdapter;
    List<ShopEvaModel.ShopEva> shopEvas = new ArrayList<>();

    public static ShopEvaluateFragment getInstance(String status) {
        ShopEvaluateFragment sf = new ShopEvaluateFragment();
        sf.status = status;
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_eva, null);
        ButterKnife.bind(this, view);
        initRFView();
        pageNum = 0;
        mvpPresenter.loadShopEvas(pageNum);
        return view;
    }

    @Override
    protected ShopEvaPresenter createPresenter() {
        return new ShopEvaPresenter(this);
    }

    @Override
    public void getShopEvasSuccess(BaseModel<ShopEvaModel> model) {
        if ("200".equals(model.getCode())) {
            shopEvaModel = model.getData();
            if (shopEvaModel != null) {
                if (!JsonUtil.isEmpty(shopEvaModel.getList())) {
                    if (pageNum == 0) {
                        shopEvas = shopEvaModel.getList();
                    } else {
                        shopEvas.addAll(shopEvaModel.getList());
                    }
                }
                if (!JsonUtil.isEmpty(shopEvas)) {
                    if (pageNum == 0) {
                        initRyView();
                    } else {
                        recycleAdapter.notifyDataSetChanged();
                    }
                }
            }
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
        if (pageNum == 0) {
            trlView.finishRefreshing();
        } else {
            trlView.finishLoadmore();
        }
    }

    /**
     * 此界面不用
     * @param model
     */
    @Override
    public void getReplySuccess(BaseModel model) {

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ryEvas.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<ShopEvaModel.ShopEva>(getActivity(), R.layout.adapter_shop_eva, shopEvas) {
            @Override
            protected void convert(ViewHolder holder, ShopEvaModel.ShopEva shopEva, int position) {
                StarBarView starBarView = holder.getView(R.id.star_view);
                starBarView.setClick(true);
                starBarView.setIntegerMark(true);
                if (shopEva != null) {
                    if (!TextUtils.isEmpty(shopEva.getImgUrl())) {
                        holder.setImageRoundGlide(R.id.img_eva, shopEva.getImgUrl());
                    }
                    if (!TextUtils.isEmpty(shopEva.getReviewerName())) {
                        holder.setText(R.id.txt_evaName, shopEva.getReviewerName());
                    } else {
                        holder.setText(R.id.txt_evaName, "");
                    }
                    if (!TextUtils.isEmpty(shopEva.getRemark())) {
                        holder.setText(R.id.txt_evaContent, shopEva.getRemark());
                    } else {
                        holder.setText(R.id.txt_evaContent, "");
                    }
                    if (!TextUtils.isEmpty(shopEva.getCreateTime())) {
                        holder.setText(R.id.txt_evaTime, shopEva.getCreateTime());
                    } else {
                        holder.setText(R.id.txt_evaTime, "");
                    }
                    if (!TextUtils.isEmpty(shopEva.getScore())) {
                        holder.setText(R.id.txt_branch, shopEva.getScore()+"branch");
                        starBarView.setStarMark(Float.parseFloat(shopEva.getScore()));
                    } else {
                        holder.setText(R.id.txt_branch, "");
                    }
                }
            }
        };
        ryEvas.setAdapter(recycleAdapter);
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
                mvpPresenter.loadShopEvas(pageNum);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (shopEvaModel != null) {
                    if (shopEvaModel.getList().size() > 9) {
                        pageNum++;
                        mvpPresenter.loadShopEvas(pageNum);
                    } else {
                        refreshLayout.finishLoadmore();
                        ToastUtils.showShortToast("暂无更多数据");
                    }
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
