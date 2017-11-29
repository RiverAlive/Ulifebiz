package com.butao.ulifebiz.mvp.fragment.shop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpFragment;
import com.butao.ulifebiz.mvp.model.shop.ShopEvaModel;
import com.butao.ulifebiz.mvp.presenter.ShopReplyPresenter;
import com.butao.ulifebiz.mvp.view.ShopEvaView;
import com.butao.ulifebiz.util.InputMethodUtil;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.LoadMoreView;
import com.butao.ulifebiz.view.SoftKeyboardStateHelper;
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
 * 创建时间 ：2017/9/3.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class CustomerReplyFragment extends MvpFragment<ShopReplyPresenter> implements ShopEvaView {
    @Bind(R.id.ry_evas)
    RecyclerView ryEvas;
    @Bind(R.id.trl_view)
    TwinklingRefreshLayout trlView;
    @Bind(R.id.edt_reply)
    EditText edtReply;
    private String status = "";
    int pageNum = 0;
    ShopEvaModel shopEvaModel = new ShopEvaModel();
    CommonRecycleAdapter recycleAdapter;
    List<ShopEvaModel.ShopEva> shopEvas = new ArrayList<>();
    int select=-1;
    boolean edtflag = false;
    public static CustomerReplyFragment getInstance(String status) {
        CustomerReplyFragment sf = new CustomerReplyFragment();
        sf.status = status;
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_reply, null);
        ButterKnife.bind(this, view);
        initRFView();
        pageNum = 0;
        edtflag=false;
        mvpPresenter.loadShopStoreEvas(pageNum, status);
        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(view.findViewById(R.id.edt_reply));
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {

            }

            @Override
            public void onSoftKeyboardClosed() {
                edtflag=false;
                edtReply.setVisibility(View.GONE);
            }
        });
        edtReply.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId , KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED&&!edtflag) {
                    String remark = edtReply.getText().toString();
                    if(TextUtils.isEmpty(remark)){
                        edtReply.requestFocus();
                        ToastUtils.showShortToast("请输入回复内容");
                        return false;
                    }
                    mvpPresenter.loadStoreReply(shopEvas.get(select).getReviewerId(),remark,cApplication.getStoreName(),shopEvas.get(select).getReviewerName(),shopEvas.get(select).getImgUrl());
                    return true;
                }
                return false;
            }});
        return view;
    }

    @Override
    protected ShopReplyPresenter createPresenter() {
        return new ShopReplyPresenter(this);
    }

    /**
     * 评价列表
     *
     * @param model
     */
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
     * 回复评价
     *
     * @param model
     */
    @Override
    public void getReplySuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            if("1".equals(status)){
                shopEvas.remove(select);
            }else if("2".equals(status)){
                shopEvas.remove(select);
            }else{
                pageNum=0;
                mvpPresenter.loadShopStoreEvas(pageNum, status);
            }
            edtflag=true;
            recycleAdapter.notifyDataSetChanged();
            InputMethodUtil.hideInput(getActivity());
            edtReply.setText("");
            edtReply.setVisibility(View.GONE);
        } else {

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ryEvas.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<ShopEvaModel.ShopEva>(getActivity(), R.layout.adapter_customer_reply, shopEvas) {
            @Override
            protected void convert(ViewHolder holder, ShopEvaModel.ShopEva shopEva, final int position) {
                StarBarView starBarView = holder.getView(R.id.star_view);
                starBarView.setClick(true);
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
                        holder.setText(R.id.txt_branch, shopEva.getScore() + "branch");
                        starBarView.setStarMark(Float.parseFloat(shopEva.getScore()));
                    } else {
                        holder.setText(R.id.txt_branch, "");
                    }
                    holder.setOnClickListener(R.id.txt_reply, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edtflag=false;
                            select = position;
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            edtReply.setVisibility(View.VISIBLE);
                            edtReply.requestFocus();
                        }
                    });
                    if ("3".equals(status)) {
                        RecyclerView recyclerView = holder.getView(R.id.ry_reply);
                        initReplyRyView(recyclerView, position);
                    }
                }
            }
        };
        ryEvas.setAdapter(recycleAdapter);
    }

    private void initReplyRyView(RecyclerView recyclerView, int positionselect) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        CommonRecycleAdapter recycleAdapter = new CommonRecycleAdapter<ShopEvaModel.EvaModel>(getActivity(), R.layout.adapter_customer_right, shopEvas.get(positionselect).getReviewerReplies()) {
            @Override
            protected void convert(ViewHolder holder, ShopEvaModel.EvaModel evaModel, int position) {
                if (evaModel != null) {
                    if (!TextUtils.isEmpty(evaModel.getImgUrl())) {
                        holder.setImageRoundGlide(R.id.img_reviewer, evaModel.getImgUrl());
                    }
                    if (!TextUtils.isEmpty(evaModel.getReviewerName())) {
                        holder.setText(R.id.txt_reviewerName, evaModel.getReviewerName());
                    } else {
                        holder.setText(R.id.txt_reviewerName, "");
                    }
                    if (!TextUtils.isEmpty(evaModel.getRemark())) {
                        holder.setText(R.id.txt_reviewerremark, evaModel.getRemark());
                    } else {
                        holder.setText(R.id.txt_reviewerremark, "");
                    }
                    holder.setOnClickListener(R.id.txt_reply, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        };
        recyclerView.setAdapter(recycleAdapter);
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
                mvpPresenter.loadShopStoreEvas(pageNum, status);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (shopEvaModel != null) {
                    if (shopEvaModel.getList().size() > 9) {
                        pageNum++;
                        mvpPresenter.loadShopStoreEvas(pageNum, status);
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
