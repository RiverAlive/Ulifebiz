package com.butao.ulifebiz.mvp.activity.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.model.OnLineHelp;
import com.butao.ulifebiz.mvp.presenter.OnHelpPresenter;
import com.butao.ulifebiz.mvp.view.OnHlepView;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/8/29.
 * 编写人 ：bodong
 * 功能描述 ：在线帮助
 */
public class OnlineHelpActivity extends MvpActivity<OnHelpPresenter> implements OnHlepView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    CommonRecycleAdapter recycleAdapter;
    @Bind(R.id.ry_question)
    RecyclerView ryQuestion;
    @Bind(R.id.edt_remark)
    EditText edtRemark;
    String remark="";
    OnLineHelp onLineHelp = new OnLineHelp();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_help);
        ButterKnife.bind(this);
        // adapter  adapter_online_help.xml
        txtTitle.setText("在线帮助");
        mvpPresenter.loadHelps();
    }

    @Override
    protected OnHelpPresenter createPresenter() {
        return new OnHelpPresenter(this);
    }

    @Override
    public void getHelpListSuccess(BaseModel<OnLineHelp> model) {
        if ("200".equals(model.getCode())) {
            onLineHelp = model.getData();

            if(onLineHelp!=null&&!JsonUtil.isEmpty(onLineHelp.getHelps())){
                initRyView();
            }
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getHelpSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            ToastUtils.showShortToast("提交成功");
            finish();
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);

    }

    private void initRyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(OnlineHelpActivity.this);
        ryQuestion.setLayoutManager(layoutManager);
        recycleAdapter = new CommonRecycleAdapter<OnLineHelp.HelpModel>(OnlineHelpActivity.this,R.layout.adapter_online_help,onLineHelp.getHelps()) {
            @Override
            protected void convert(ViewHolder holder, OnLineHelp.HelpModel helpModel, final int position) {
                if(helpModel!=null){
                    if(!TextUtils.isEmpty(helpModel.getRemark())){
                        holder.setText(R.id.txt_question,helpModel.getRemark());
                    }else{
                        holder.setText(R.id.txt_question,"");
                    }
                    holder.setOnClickListener(R.id.txt_question, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onLineHelp.getHelps().get(position).setSelect(true);
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        };
        ryQuestion.setAdapter(recycleAdapter);
    }


    @OnClick({R.id.ll_back, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.txt_submit:
                remark = edtRemark.getText().toString();
                if(onLineHelp!=null&&!JsonUtil.isEmpty(onLineHelp.getHelps())){
                    for (int i = 0; i < onLineHelp.getHelps().size(); i++) {
                        if(onLineHelp.getHelps().get(i).isSelect()){
                            remark = remark + onLineHelp.getHelps().get(i).getRemark();
                        }
                    }
                }
                if(TextUtils.isEmpty(remark)){
                    ToastUtils.showShortToast("请输入问题");
                    edtRemark.requestFocus();
                    return;
                }
                mvpPresenter.loadONHelp(remark);
                break;
        }
    }
}
