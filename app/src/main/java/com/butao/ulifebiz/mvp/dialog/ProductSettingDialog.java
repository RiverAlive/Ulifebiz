package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.butao.ulifebiz.R;

/**
 * 创建时间 ：2017/9/5.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class ProductSettingDialog  {
    private Activity mActivity;
    private PopupWindow mPopupWindow;
    View contentView;
    public ProductSettingDialog() {
    }
    ProductListener productListener;
    public void setProductListener(ProductListener productListener) {
        this.productListener = productListener;
    }
    public ProductSettingDialog(Activity activity) {
        mActivity = activity;
        initview();
    }
    private void initview() {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.dialog_product_setting;   // 布局ID
        contentView = LayoutInflater.from(mActivity).inflate(layoutId, null);
        TextView txt_add = (TextView)contentView.findViewById(R.id.txt_add);
        TextView txt_edit = (TextView)contentView.findViewById(R.id.txt_edit);
        txt_add.setText("新建菜品");
        txt_edit.setText("编辑与排序");
        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListener.AddandEdit("product","add");
                mPopupWindow.dismiss();
            }
        });
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListener.AddandEdit("product","edit");
                mPopupWindow.dismiss();
            }
        });
    }


    public void showPopupWindow(View anchorView) {
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, (location[0] + anchorView.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
    }

}
