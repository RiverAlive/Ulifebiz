package com.butao.ulifebiz.mvp.activity.shop.event;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.model.shop.event.DailyCutModel;
import com.butao.ulifebiz.util.JsonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建时间 ：2017/9/26.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class AddDailyWareActivity extends MvpActivity {
    @Bind(R.id.txt_warename)
    TextView txtWarename;
    @Bind(R.id.edt_wareprice)
    EditText edtWareprice;
    @Bind(R.id.edt_price)
    EditText edtPrice;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
DailyCutModel.DailyWareModel dailyWareModel = new DailyCutModel.DailyWareModel();
    boolean select = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dailyware);
        ButterKnife.bind(this);
        txtTitle.setText("添加商品");
        txtRightTitle.setText("确定");
        txtRightTitle.setTextColor(getResources().getColor(R.color.royalblue));
        txtRightTitle.setTextSize(18);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.ll_back, R.id.ll_right,R.id.txt_warename})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                setResult(0);
                break;
            case R.id.ll_right:
                String disprice = edtPrice.getText().toString();
                String wareprice = edtWareprice.getText().toString();
                if(dailyWareModel!=null&&select){
                    if(TextUtils.isEmpty(disprice)){
                       ToastUtils.showShortToast("请输入优惠价格");
                        edtPrice.requestFocus();
                        return;
                    }
                    if(TextUtils.isEmpty(wareprice)){
                        ToastUtils.showShortToast("请输入商品原价");
                        edtWareprice.requestFocus();
                        return;
                    }
                    dailyWareModel.setWarePrice(wareprice);
                    dailyWareModel.setDiscountPrice(disprice);
                    Intent intent = new Intent();
                    intent.putExtra("Json", JsonUtil.toJson(dailyWareModel));
                    setResult(20,intent);
                    finish();
                }else{
                    ToastUtils.showShortToast("请选择商品");
                }
                break;
            case R.id.txt_warename:
                Intent intent = new Intent();
                intent.setClass(AddDailyWareActivity.this,ProductSelectActivity.class);
                startActivityForResult(intent, 20);
                break;
        }
    }
    /**
     * 跳转返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，
            case 20://选择折扣回调
                select = true;
                dailyWareModel.setWareId(data.getStringExtra("wareId"));
                dailyWareModel.setWareName(data.getStringExtra("wareName"));
                dailyWareModel.setWarePrice(data.getStringExtra("warePrice"));
                dailyWareModel.setImgUrl(data.getStringExtra("wareImg"));
                txtWarename.setText(data.getStringExtra("wareName"));
                edtWareprice.setText(data.getStringExtra("warePrice"));
                break;
            default:
                break;
        }
    }
}
