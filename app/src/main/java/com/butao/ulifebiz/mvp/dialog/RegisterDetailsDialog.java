package com.butao.ulifebiz.mvp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.CApplication;
import com.butao.ulifebiz.mvp.activity.LoginActivity;
import com.butao.ulifebiz.mvp.activity.shop.product.AddProductActivity;
import com.butao.ulifebiz.util.BitmapUtil;
import com.butao.ulifebiz.util.Camera;

import java.util.HashMap;
import java.util.Map;

/**
 * 填写详细信息
 * Created by bodong on 2017/2/22.
 */
public class RegisterDetailsDialog implements View.OnClickListener {
    private Activity mActivity;
    protected EditText edtStoreName;
    protected EditText edtAddress;
    protected EditText edtPhone;
    protected EditText edtName;
    protected ImageView imgStore;
    protected ImageView imgStoreLogo;
    protected LinearLayout llStore;
    protected LinearLayout llStoreLogo;
    protected TextView txtDatermine;
    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
   public String type="";
    public RegisterDetailsDialog(final Activity activity) {
        this.mActivity = activity;
        showDialog(activity);
    }

    public void dialogShow() {
        if (backdialog != null) {
            backdialog.show();
        }
    }

    Dialog backdialog = null;

    private void showDialog(final Activity activity) {

        backdialog = new Dialog(activity, R.style.back_dialog);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_register_details, null);
        initView(view);
        backdialog.setContentView(view);
        backdialog.setCanceledOnTouchOutside(true);
    }

    private void initView(View rootView) {
        edtStoreName = (EditText) rootView.findViewById(R.id.edt_storeName);
        edtAddress = (EditText) rootView.findViewById(R.id.edt_address);
        edtPhone = (EditText) rootView.findViewById(R.id.edt_phone);
        edtName = (EditText) rootView.findViewById(R.id.edt_name);
        imgStore = (ImageView) rootView.findViewById(R.id.img_store);
        imgStoreLogo = (ImageView) rootView.findViewById(R.id.img_storeLogo);
        llStore = (LinearLayout) rootView.findViewById(R.id.ll_store);
        llStore.setOnClickListener(RegisterDetailsDialog.this);
        llStoreLogo = (LinearLayout) rootView.findViewById(R.id.ll_store_logo);
        llStoreLogo.setOnClickListener(RegisterDetailsDialog.this);
        txtDatermine = (TextView) rootView.findViewById(R.id.txt_Datermine);
        txtDatermine.setOnClickListener(RegisterDetailsDialog.this);
    }

    public void setStoreImg(String type,String imgUrl){
        if("store".equals(type)){
            Glide.with(mActivity).load(imgUrl).placeholder(R.mipmap.tupian_nei).into(imgStore);
        }else  if("logo".equals(type)){
            Glide.with(mActivity).load(imgUrl).placeholder(R.mipmap.tupian_nei).into(imgStoreLogo);
        }
    }
    public void AddImg() {
        PhotoDialog onPhotoDialog = new PhotoDialog(mActivity);
        onPhotoDialog.dialogShow();
       onPhotoDialog.SetOnphotolistener(onphotoListener);
    }

    OnphotoListener onphotoListener = new OnphotoListener() {
        @Override
        public void confirm(int tag) {
            if (String.valueOf(tag) != null) {
                if (tag == PIC_FROM_CAMERA) {
                    //拍照
                    Camera.getInstance(mActivity).startCamera(true);
                } else if (tag == PIC_FROM＿LOCALPHOTO) {
                    //相册
                    Camera.getInstance(mActivity).startCamera(false);
                }
            }
        }
    };
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_store) {
            type="store";
//            registerlistener.Register("store",null);
            AddImg();
        } else if (view.getId() == R.id.ll_store_logo) {
            type="logo";
//            registerlistener.Register("logo",null);
            AddImg();
        } else if (view.getId() == R.id.txt_Datermine) {
            String storename =edtStoreName.getText().toString();
            String phone =edtPhone.getText().toString();
            String name =edtName.getText().toString();
            String address =edtAddress.getText().toString();
            if (TextUtils.isEmpty(storename)) {
                ToastUtils.showShortToast("请您输入店铺名称");
                edtStoreName.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                ToastUtils.showShortToast("请您输入手机号");
                edtPhone.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(address)) {
                ToastUtils.showShortToast("请您输入店铺地址");
                edtAddress.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(name)) {
                ToastUtils.showShortToast("请您输入联系人姓名");
                edtName.requestFocus();
                return;
            }
            Map<String ,String > map = new HashMap<>();
            map.put("token",CApplication.getIntstance().getToken());
            map.put("storeName",storename);
            map.put("managerName",name);
            map.put("address", address);
            map.put("phone", phone);
            registerlistener.Register("submit",map);
        }
    }


    public interface Registerlistener {
        void Register(String type,Map<String,String> map);
    }

    public Registerlistener registerlistener;

    public void setRegisterlistener(Registerlistener registerlistener) {
        this.registerlistener = registerlistener;
    }
}
