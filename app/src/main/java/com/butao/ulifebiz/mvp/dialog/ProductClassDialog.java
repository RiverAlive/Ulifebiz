package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.blankj.utilcode.utils.ScreenUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.view.recyclerview.adapter.CommonRecycleAdapter;
import com.butao.ulifebiz.view.recyclerview.adapter.MultiItemTypeAdapter;
import com.butao.ulifebiz.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/4/28.
 */
public class ProductClassDialog extends PopupWindow {
    private  static Activity activity;
    CommonRecycleAdapter recycleAdapter;
    List<ProdcutClassModel.PClassMosel> categoryList =new ArrayList<>();
    RecyclerView ryClass;
    public ProductClassDialog(final Activity activity,  List<ProdcutClassModel.PClassMosel> categoryList)
    {
        this.activity = activity;
        this.categoryList = categoryList;
        showDialog(activity);
    }
    public void dialogShow()
    {
        if (backdialog != null)
        {
            backdialog.show();
        }
    }
    Dialog backdialog = null;

    private void showDialog(final Activity activity) {
        backdialog = new Dialog(activity, R.style.back_dialog);
        backdialog.setContentView(R.layout.dialog_product_class);
        backdialog.setCanceledOnTouchOutside(true);
        ryClass = (RecyclerView) backdialog.findViewById(R.id.ry_pclass);
        initClass(activity);
    }

    public void initClass(final Activity activity) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ryClass.setLayoutManager(layoutManager);
         recycleAdapter = new CommonRecycleAdapter<ProdcutClassModel.PClassMosel>(activity,R.layout.adapter_product_class,categoryList) {
            @Override
            protected void convert(ViewHolder holder, ProdcutClassModel.PClassMosel pClassMosel, int position) {
                if(!TextUtils.isEmpty(pClassMosel.getName())){
                    holder.setText(R.id.txt_classname,pClassMosel.getName());
                }else{
                    holder.setText(R.id.txt_classname,"");
                }
            }
        };
        ryClass.setAdapter(recycleAdapter);
        initClassClick();
    }
    public void initClassClick(){
        recycleAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                pClasslistener.PclassId(categoryList.get(position));
                backdialog.dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    public interface PClasslistener{
        void PclassId(ProdcutClassModel.PClassMosel pClassMosel);
    }
    public  PClasslistener pClasslistener;

    public void setPClasslistener(PClasslistener pClasslistener) {
        this.pClasslistener = pClasslistener;
    }
}


