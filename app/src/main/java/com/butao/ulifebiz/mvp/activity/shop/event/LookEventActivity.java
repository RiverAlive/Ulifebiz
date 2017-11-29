package com.butao.ulifebiz.mvp.activity.shop.event;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.model.shop.event.CutBuiltModel;
import com.butao.ulifebiz.mvp.presenter.LookDeleteEventPresenter;
import com.butao.ulifebiz.mvp.view.LookDeleteEventView;
import com.butao.ulifebiz.util.JsonUtil;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/25.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class LookEventActivity extends MvpActivity<LookDeleteEventPresenter> implements LookDeleteEventView {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.ry_event)
    RecyclerView ryEvent;
    List<CutBuiltModel.DayFullCut> dayFullCuts = new ArrayList<>();
    CutBuiltModel  cutBuiltModel;
    String type="";
    int deleteposition=-1;
    CommonRecycleAdapter recycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_event);
        ButterKnife.bind(this);
        txtTitle.setText("活动详情");
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            cutBuiltModel=JsonUtil.fromJson(bundle.getString("Json"),CutBuiltModel.class);
            type=bundle.getString("type");
            if("FullDown".equals(bundle.getString("type"))||"deleteFullDown".equals(bundle.getString("type"))){
                dayFullCuts=cutBuiltModel.getFullCuts();
            }else if("Daily".equals(bundle.getString("type"))||"DeleteDaily".equals(bundle.getString("type"))){
                dayFullCuts=cutBuiltModel.getDayCuts();
            }
        }
        if(!JsonUtil.isEmpty(dayFullCuts)){
            initEvent();
        }
    }
    private  void initEvent(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LookEventActivity.this);
        ryEvent.setLayoutManager(linearLayoutManager);
         recycleAdapter = new CommonRecycleAdapter<CutBuiltModel.DayFullCut>(LookEventActivity.this,R.layout.adapter_look_event,dayFullCuts) {
            @Override
            protected void convert(ViewHolder holder, final CutBuiltModel.DayFullCut dayFullCut, final int position) {
                if(dayFullCut!=null){
                    String starttime="";
                    String endtime="";
                    if(!TextUtils.isEmpty(dayFullCut.getStartDay())){
                        starttime = dayFullCut.getStartDay();
                    }else{
                        starttime="";
                    }
                    if(!TextUtils.isEmpty(dayFullCut.getStartTime())){
                        starttime = starttime+" "+dayFullCut.getStartTime();
                    }
                    if(!TextUtils.isEmpty(dayFullCut.getEndDay())){
                        endtime = dayFullCut.getEndDay();
                    }else{
                        endtime="";
                    }
                    if(!TextUtils.isEmpty(dayFullCut.getEndTime())){
                        endtime = endtime+" "+dayFullCut.getEndTime();
                    }
                    holder.setText(R.id.txt_event_time,starttime+"\n"+endtime);
                    RecyclerView recyclerView = (RecyclerView)holder.getView(R.id.ry_child);
                    if(!JsonUtil.isEmpty(dayFullCut.getWares())){
                        initDailyevent(recyclerView,dayFullCut.getWares());
                    }else{
                        if(!JsonUtil.isEmpty(dayFullCut.getFullCutPrices())){
                            initFullDown(recyclerView,dayFullCut.getFullCutPrices());
                        }
                    }
                    if("deleteFullDown".equals(type)||"DeleteDaily".equals(type)){
                        holder.setVisible(R.id.txt_delete,true);
                        holder.setOnClickListener(R.id.txt_delete, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteposition =position;
                                if("deleteFullDown".equals(type)){
                                    mvpPresenter.loadLookDeleteEvent(dayFullCut.getId(),"1");
                                }
                                else if("DeleteDaily".equals(type)) {
                                    mvpPresenter.loadLookDeleteEvent(dayFullCut.getId(),"2");
                                }
                            }
                        });
                    }
                }
            }
        };
        ryEvent.setAdapter(recycleAdapter);
    }
    @Override
    protected LookDeleteEventPresenter createPresenter() {
        return new LookDeleteEventPresenter(this);
    }

    @Override
    public void getLookDeleteSuccess(BaseModel model) {
        if("200".equals(model.getCode())){
            if("deleteFullDown".equals(type)){
                dayFullCuts.remove(deleteposition);
                recycleAdapter.notifyDataSetChanged();
            }
            else if("DeleteDaily".equals(type)) {
                dayFullCuts.remove(deleteposition);
                recycleAdapter.notifyDataSetChanged();
            }
            ToastUtils.showShortToast("活动删除成功");
        }else{
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    /**
     * 满减活动
     * @param recyclerView
     * @param fullCuts
     */
    private  void initFullDown(RecyclerView recyclerView, List<CutBuiltModel.FullCut> fullCuts){
        LinearLayoutManager layoutManager = new LinearLayoutManager(LookEventActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        CommonRecycleAdapter recycleAdapter = new CommonRecycleAdapter<CutBuiltModel.FullCut>(LookEventActivity.this,R.layout.adapter_event_fulldown,fullCuts) {
            @Override
            protected void convert(ViewHolder holder, CutBuiltModel.FullCut fullCut, int position) {
                if(fullCut!=null){
                    String price="";
                    if(!TextUtils.isEmpty(fullCut.getFullPrice())){
                        price="满£"+fullCut.getFullPrice();
                    }else{
                        price="满"  ;
                    }
                    if(!TextUtils.isEmpty(fullCut.getCutPrice())){
                        price=price+"减£"+fullCut.getCutPrice();
                    }
                    holder.setText(R.id.txt_full_down,price);
                }
            }
        };
        recyclerView.setAdapter(recycleAdapter);
    }

    /**
     * 天天特价
     * @param recyclerView
     * @param dayCutWares
     */
    private  void initDailyevent(RecyclerView recyclerView, List<CutBuiltModel.DayCutWare> dayCutWares){
        LinearLayoutManager layoutManager = new LinearLayoutManager(LookEventActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        CommonRecycleAdapter recycleAdapter = new CommonRecycleAdapter<CutBuiltModel.DayCutWare>(LookEventActivity.this,R.layout.adapter_event_daily,dayCutWares) {
            @Override
            protected void convert(ViewHolder holder, CutBuiltModel.DayCutWare dayCutWare, int position) {
                if(dayCutWare!=null){
                    if(!TextUtils.isEmpty(dayCutWare.getWareName())){
                        holder.setText(R.id.txt_warename,dayCutWare.getWareName());
                    }else{
                        holder.setText(R.id.txt_warename,"");
                    }
                    if(!TextUtils.isEmpty(dayCutWare.getDiscountPrice())){
                        holder.setText(R.id.txt_discountprice,"£"+dayCutWare.getDiscountPrice());
                    }else{
                        holder.setText(R.id.txt_discountprice,"");
                    }
                    if(!TextUtils.isEmpty(dayCutWare.getWarePrice())){
                        holder.setText(R.id.txt_price,"£"+dayCutWare.getWarePrice());
                    }else{
                        holder.setText(R.id.txt_price,"");
                    }
                    TextView textView =holder.getView(R.id.txt_price);
                    textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线（删除线）
                }
            }
        };
        recyclerView.setAdapter(recycleAdapter);
    }
    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        finish();
    }
}
