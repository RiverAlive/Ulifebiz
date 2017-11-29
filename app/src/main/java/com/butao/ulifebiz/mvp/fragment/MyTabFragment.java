package com.butao.ulifebiz.mvp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.CleanUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.ActivityManager;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpFragment;
import com.butao.ulifebiz.base.MvpLoadFragment;
import com.butao.ulifebiz.mvp.activity.LoginActivity;
import com.butao.ulifebiz.mvp.activity.my.ChanagePwdActivity;
import com.butao.ulifebiz.mvp.activity.my.ContactUsActivity;
import com.butao.ulifebiz.mvp.activity.my.FeedbackActivity;
import com.butao.ulifebiz.mvp.activity.my.NotificationNewsActivity;
import com.butao.ulifebiz.mvp.activity.my.OnlineHelpActivity;
import com.butao.ulifebiz.mvp.dialog.LoginOutDialog;
import com.butao.ulifebiz.mvp.model.shop.BussinessInfo;
import com.butao.ulifebiz.mvp.presenter.MyTabPresenter;
import com.butao.ulifebiz.mvp.view.MyTabView;
import com.butao.ulifebiz.view.GlideCircleTransform;
import com.butao.ulifebiz.view.GlideRoundTransform;
import com.butao.ulifebiz.view.swtich.SwitchButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 创建时间 ：2017/8/20.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class MyTabFragment extends MvpLoadFragment<MyTabPresenter> implements MyTabView {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_newnum)
    TextView txt_newnum;
    @Bind(R.id.img_right)
    ImageView imgRight;
    @Bind(R.id.img_head)
    ImageView imgHead;
    BussinessInfo bussinessInfo;
    @Bind(R.id.sb_sound)
    SwitchButton sbSound;
    boolean sound=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tab, null);
        ButterKnife.bind(this, view);
        txtTitle.setText("个人");
        imgBack.setVisibility(View.GONE);
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.mipmap.email);
        txt_newnum.setVisibility(View.GONE);
        txt_newnum.setText("25");
        if (!TextUtils.isEmpty(cApplication.getLoginImg())) {
            Glide.with(getActivity()).load(cApplication.getLoginImg()).transform(new GlideCircleTransform(getActivity())).placeholder(R.mipmap.head_person).into(imgHead);
        }
        sbSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                sbSound.setChecked(isChecked);
//                if(sound){
//                    sound=false;
//                }else{
                    sbSound.setChecked(isChecked);
                    if(isChecked){
                        mvpPresenter.loadStoreSound("1");
                    }else{
                        mvpPresenter.loadStoreSound("0");
                    }
//                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mvpPresenter.loadBussinessInfo();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my_tab;
    }

    @Override
    protected void lazyLoad() {
        mvpPresenter.loadBussinessInfo();
    }

    @Override
    protected MyTabPresenter createPresenter() {
        return new MyTabPresenter(this);
    }

    @Override
    public void getBussinessInfoSuccess(BaseModel<BussinessInfo> model) {
        if ("200".equals(model.getCode())) {
            bussinessInfo = model.getData();
            if (bussinessInfo != null) {
                sound = true;
                if(bussinessInfo.getIsSound()==1){
                    sbSound.setChecked(true);
                }else{
                    sbSound.setChecked(false);
                }
            }
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getStoreSoundSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("提示音状态修改成功");
        } else {
            ToastUtils.showShortToast(model.getData().toString());
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
    }


    @OnClick({R.id.ll_right, R.id.ll_chagepwd, R.id.ll_help, R.id.ll_feedback, R.id.ll_contact, R.id.txt_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_right:
                startActivity(getActivity(), NotificationNewsActivity.class);
                break;
            case R.id.ll_chagepwd:
                startActivity(getActivity(), ChanagePwdActivity.class);
                break;
            case R.id.ll_help:
                startActivity(getActivity(), OnlineHelpActivity.class);
                break;
            case R.id.ll_feedback:
                startActivity(getActivity(), FeedbackActivity.class);
                break;
            case R.id.ll_contact:
                startActivity(getActivity(), ContactUsActivity.class);
                break;
            case R.id.txt_loginout:
                LoginOutDialog loginOutDialog = new LoginOutDialog(getActivity());
                loginOutDialog.showDialog();
                loginOutDialog.setLoginOutlistener(new LoginOutDialog.LoginOutlistener() {
                    @Override
                    public void LoginOut() {
                        JPushInterface.stopPush(getActivity());
                        cApplication.setLogin(false);
                        cApplication.setToken(null);
                        CleanUtils.cleanInternalSP();
                        CleanUtils.cleanInternalCache();
                        CleanUtils.cleanInternalFiles();
                        SharedPreferences settings = getActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("guide_activity", "false");
                        editor.commit();
                        startActivity(getActivity(), LoginActivity.class);
                        ActivityManager.getInstance().finishOthersActivity(LoginActivity.class);
                    }
                });
                break;
        }
    }
}
