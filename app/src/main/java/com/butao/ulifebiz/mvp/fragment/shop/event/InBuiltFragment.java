package com.butao.ulifebiz.mvp.fragment.shop.event;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.butao.ulifebiz.base.MvpLoadFragment;
import com.butao.ulifebiz.mvp.activity.shop.event.LookEventActivity;
import com.butao.ulifebiz.mvp.activity.shop.event.ModifyFirstActivity;
import com.butao.ulifebiz.mvp.model.shop.event.CutBuiltModel;
import com.butao.ulifebiz.mvp.presenter.CutListPresenter;
import com.butao.ulifebiz.mvp.view.CutListView;
import com.butao.ulifebiz.util.JsonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/20.
 * 编写人 ：bodong
 * 功能描述 ：已建活动列表
 */
public class InBuiltFragment extends MvpLoadFragment<CutListPresenter> implements CutListView {
    @Bind(R.id.txt_first)
    TextView txtFirst;
    CutBuiltModel cutBuiltModel = new CutBuiltModel();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_built, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_in_built;
    }

    @Override
    protected void lazyLoad() {
        mvpPresenter.loadCutList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mvpPresenter.loadCutList();
    }

    public void LoadCutList() {
        mvpPresenter.loadCutList();
    }

    @Override
    protected CutListPresenter createPresenter() {
        return new CutListPresenter(this);
    }

    @Override
    public void getCutListSuccess(BaseModel<CutBuiltModel> model) {
        if ("200".equals(model.getCode())) {
            cutBuiltModel = model.getData();
            if(cutBuiltModel!=null){
                if(!TextUtils.isEmpty(cutBuiltModel.getFirstCutPrice())){
                    SpannableString spannableString = new SpannableString("首次购买减免£" + cutBuiltModel.getFirstCutPrice() );
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#f39801")), 6,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    txtFirst.setText(spannableString);
                }else{
                    txtFirst.setText("首次购买减免");
                }
            }
        } else {
            ToastUtils.showLongToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showLongToast(model);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.txt_look_fulldown, R.id.txt_delete_fulldown, R.id.txt_look_daily, R.id.txt_delete_daily,R.id.txt_look_first})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.txt_look_fulldown:
                bundle.putString("Json", JsonUtil.toJson(cutBuiltModel));
                bundle.putString("type", "FullDown");
                bundle.putString("flag", "delete");
                startActivity(getActivity(), LookEventActivity.class,bundle);
                break;
            case R.id.txt_delete_fulldown:
                bundle.putString("Json", JsonUtil.toJson(cutBuiltModel));
                bundle.putString("type", "deleteFullDown");
                startActivity(getActivity(), LookEventActivity.class,bundle);
                break;
            case R.id.txt_look_daily:
                bundle.putString("Json", JsonUtil.toJson(cutBuiltModel));
                bundle.putString("type", "Daily");
                startActivity(getActivity(), LookEventActivity.class,bundle);
                break;
            case R.id.txt_delete_daily:
                bundle.putString("Json", JsonUtil.toJson(cutBuiltModel));
                bundle.putString("type", "DeleteDaily");
                startActivity(getActivity(), LookEventActivity.class,bundle);
                break;
            case R.id.txt_look_first:
                startActivity(getActivity(), ModifyFirstActivity.class);
                break;
        }
    }
}
