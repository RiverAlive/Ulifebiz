package com.butao.ulifebiz.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.jpush.TagAliasOperatorHelper;
import com.butao.ulifebiz.mvp.activity.shop.product.AddProductActivity;
import com.butao.ulifebiz.mvp.dialog.OnPhotoDialog;
import com.butao.ulifebiz.mvp.dialog.OnphotoListener;
import com.butao.ulifebiz.mvp.dialog.RegisterDetailsDialog;
import com.butao.ulifebiz.mvp.model.BusinessLoginModel;
import com.butao.ulifebiz.mvp.model.shop.product.WareImgModel;
import com.butao.ulifebiz.mvp.presenter.BusinessLoginPresenter;
import com.butao.ulifebiz.mvp.view.BusinessLoginView;
import com.butao.ulifebiz.util.ButtonUtils;
import com.butao.ulifebiz.util.Camera;
import com.butao.ulifebiz.util.RegexUtils;

import java.io.File;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static android.R.attr.action;
import static com.blankj.utilcode.utils.ToastUtils.showShortToast;
import static com.butao.ulifebiz.jpush.TagAliasOperatorHelper.ACTION_SET;
import static com.butao.ulifebiz.jpush.TagAliasOperatorHelper.sequence;

/**
 * 创建时间 ：2017/8/18.
 * 编写人 ：bodong
 * 功能描述 ：登录
 */
public class LoginActivity extends MvpActivity<BusinessLoginPresenter> implements BusinessLoginView {
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_pwd)
    EditText edtPwd;
    String phone = "", pwd = "";
    BaseModel<BusinessLoginModel> baseModel;//数据源
    BusinessLoginModel loginModel;
    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
    private String urlpath, mType = "", storeImgId = "", storeLogoId = ""; // 图片本地路径
    WareImgModel.ImgModel imgModel = new WareImgModel.ImgModel();
    RegisterDetailsDialog detailsDialog;
    Map<String, String> stringMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected BusinessLoginPresenter createPresenter() {
        return new BusinessLoginPresenter(this);
    }

    @Override
    public void getLoginSuccess(BaseModel<BusinessLoginModel> model) {
        if ("200".equals(model.getCode())) {

            baseModel = model;
            loginModel = baseModel.getData();
            if (!TextUtils.isEmpty(loginModel.getStore().getToken())) {
                cApplication.setToken(loginModel.getStore().getToken());
            }
            if (!TextUtils.isEmpty(loginModel.getStore().getStoreId())) {
                cApplication.setStoreId(loginModel.getStore().getStoreId());
            }
            if (!TextUtils.isEmpty(loginModel.getStore().getImg())) {
                cApplication.setLoginImg(loginModel.getStore().getImg());
            }
            if (!TextUtils.isEmpty(loginModel.getStore().getStatus())) {
                cApplication.setStatus(loginModel.getStore().getStatus());
            }
            if (!TextUtils.isEmpty(loginModel.getStore().getStatusName())) {
                cApplication.setStatusName(loginModel.getStore().getStatusName());
            }
            String alias = null;
            int action = -1;
            boolean isAliasAction = false;
            if (!TextUtils.isEmpty(loginModel.getStore().getStoreId())) {
                alias = "store_1_" + loginModel.getStore().getStoreId();
                isAliasAction = true;
                action = ACTION_SET;
                TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                tagAliasBean.action = action;
                sequence++;
                tagAliasBean.alias = alias;
                tagAliasBean.isAliasAction = isAliasAction;
                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
                JPushInterface.resumePush(LoginActivity.this);
            }
            if (!TextUtils.isEmpty(loginModel.getStore().getStoreName())) {
                cApplication.setStoreName(loginModel.getStore().getStoreName());
                cApplication.setLogin(true);
                finish();
                gotoActivity(LoginActivity.this, MainTabActivity.class);
            } else {
                detailsDialog = new RegisterDetailsDialog(LoginActivity.this);
                detailsDialog.dialogShow();
                detailsDialog.setRegisterlistener(registerlistener);
            }
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    @OnClick({R.id.txt_sign, R.id.txt_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_sign:
                phone = edtPhone.getText().toString();
                pwd = edtPwd.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShortToast("您输入的手机号为空，请您重新输入");
                    edtPhone.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showShortToast("您输入的密码为空，请您重新输入");
                    edtPwd.requestFocus();
                    return;
                }
                if (!ButtonUtils.isFastDoubleClick(1000)) {
                    mvpPresenter.loadBusinessLogin(phone, pwd);
                }
                break;
            case R.id.txt_register:
                gotoActivity(LoginActivity.this, RegisterActivity.class);
                break;
        }
    }

    /**
     * 信息补全
     *
     * @param model
     */
    @Override
    public void getStoreInfoSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            cApplication.setStoreName(stringMap.get("storeName"));
            cApplication.setLogin(true);
            finish();
            gotoActivity(LoginActivity.this, MainTabActivity.class);
        } else {
            ToastUtils.showShortToast(model.getData().toString());
        }
    }

    /**
     * 图片上传回调
     *
     * @param model
     */
    @Override
    public void getIMGSuccess(BaseModel<WareImgModel> model) {
        if ("200".equals(model.getCode())) {
            if (model != null) {
                WareImgModel wareImgModel = model.getData();
                imgModel = wareImgModel.getUploadFile();
                if (model.getData() != null && model.getData().getUploadFile() != null && !TextUtils.isEmpty(model.getData().getUploadFile().getPath())) {
                    detailsDialog.setStoreImg(mType, model.getData().getUploadFile().getPath());
                    if ("store".equals(mType)) {
                        storeImgId = model.getData().getUploadFile().getUploadFileId();
                    } else if ("logo".equals(mType)) {
                        storeLogoId = model.getData().getUploadFile().getUploadFileId();
                    }

                }
            }

        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    RegisterDetailsDialog.Registerlistener registerlistener = new RegisterDetailsDialog.Registerlistener() {
        @Override
        public void Register(String type, Map<String, String> map) {
            mType = type;
                if (TextUtils.isEmpty(storeLogoId)) {
                    ToastUtils.showShortToast("请上传店铺logo图");
                    return;
                }
                if (TextUtils.isEmpty(storeImgId)) {
                    ToastUtils.showShortToast("请上传店铺详情图");
                    return;
                }
                stringMap = map;
                map.put("mainImg", storeLogoId);
                map.put("imgs", storeImgId);
                mvpPresenter.loadStoreInfo(stringMap);
            }
    };


    @Override
    public void onActivityResult(int requestCode, int
            resultCode, Intent data) {
        switch (requestCode) {
            case PIC_FROM_CAMERA:
                //相册
                if (data != null) {
                    Camera.getInstance(LoginActivity.this).setPhotoZoom(data.getData(), 600, 600, false);
                }
                break;
            case 2:
                //拍照
                Camera.getInstance(LoginActivity.this).setPhotoZoom(null, 600, 600, true);
                break;
            case 3:
                //剪切完成
                if (data != null) {                    // 获取原图
                    urlpath = Camera.getInstance(LoginActivity.this).getPath();
                    if (!TextUtils.isEmpty(urlpath)) {
                        mType=detailsDialog.type;
                        mvpPresenter.loadIMG(new File(urlpath));
                    } else {
                        showShortToast("剪切失败");
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode,
                data);
    }
}
