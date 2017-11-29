package com.butao.ulifebiz.mvp.activity.shop.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.butao.ulifebiz.R;
import com.butao.ulifebiz.base.BaseModel;
import com.butao.ulifebiz.base.BasePresenter;
import com.butao.ulifebiz.base.MvpActivity;
import com.butao.ulifebiz.mvp.dialog.OnPhotoDialog;
import com.butao.ulifebiz.mvp.dialog.OnphotoListener;
import com.butao.ulifebiz.mvp.dialog.ProductClassDialog;
import com.butao.ulifebiz.mvp.model.shop.product.ProdcutClassModel;
import com.butao.ulifebiz.mvp.model.shop.product.ProductModel;
import com.butao.ulifebiz.mvp.model.shop.product.WareImgModel;
import com.butao.ulifebiz.mvp.presenter.AddProductPresenter;
import com.butao.ulifebiz.mvp.view.AddProductView;
import com.butao.ulifebiz.util.Camera;
import com.butao.ulifebiz.util.JsonUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blankj.utilcode.utils.ToastUtils.showShortToast;

/**
 * 创建时间 ：2017/9/16.
 * 编写人 ：bodong
 * 功能描述 ：
 */

public class AddProductActivity extends MvpActivity<AddProductPresenter> implements AddProductView{
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_addimg)
    TextView txtAddimg;
    @Bind(R.id.img_ware)
    ImageView imgWare;
    @Bind(R.id.txt_classname)
    TextView txtClassname;
    @Bind(R.id.edt_warename)
    EditText edtWarename;
    @Bind(R.id.edt_price)
    EditText edtPrice;
    @Bind(R.id.edt_stock)
    EditText edtStock;
    @Bind(R.id.edt_content)
    EditText edtContent;
    @Bind(R.id.txt_right_title)
    TextView txtRightTitle;
    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
    private String urlpath; // 图片本地路径
    WareImgModel.ImgModel imgModel= new WareImgModel.ImgModel();
    ProductClassDialog classDialog;
    String CId="";
    String CName="";
    String ImgId ="";
    String WareId="";
    String type="";
    ProductModel.WareModel wareModel= new ProductModel.WareModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        txtTitle.setText("添加商品");
        txtRightTitle.setText("保存");
        txtRightTitle.setTextColor(getResources().getColor(R.color.royalblue));
        Bundle bundle= getIntent().getExtras();
        type = bundle.getString("type");
        if("edit".equals(type)){
            wareModel = JsonUtil.fromJson(bundle.getString("Json"), ProductModel.WareModel.class);
            CName = bundle.getString("cName");
            CId = bundle.getString("cId");
            if(wareModel!=null){
                if(!TextUtils.isEmpty(CName)){
                    txtClassname.setText(CName);
                }
                if(!TextUtils.isEmpty(wareModel.getName())){
                    edtWarename.setText(wareModel.getName());
                }
                if(!TextUtils.isEmpty(wareModel.getPrice())){
                    edtPrice.setText(wareModel.getPrice());
                }
                if(!TextUtils.isEmpty(wareModel.getKuSum())){
                    edtStock.setText(wareModel.getKuSum());
                }
                if(!TextUtils.isEmpty(wareModel.getMainImg())){
                    ImgId = wareModel.getMainImg();
                }
                if(!TextUtils.isEmpty(wareModel.getImgUrl())){
                    Glide.with(AddProductActivity.this).load(wareModel.getImgUrl()).into(imgWare);
                    txtAddimg.setVisibility(View.GONE);
                    imgWare.setVisibility(View.VISIBLE);
                }else{
                    txtAddimg.setVisibility(View.VISIBLE);
                    imgWare.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected AddProductPresenter createPresenter() {
        return new AddProductPresenter(this);
    }
    /**
     * 商品分类
     * @param model
     */
    @Override
    public void getProductClassSuccess(BaseModel<ProdcutClassModel> model) {
        if ("200".equals(model.getCode())) {
            classDialog = new ProductClassDialog(AddProductActivity.this,model.getData().getCategoryList());
            classDialog.dialogShow();
            classDialog.setPClasslistener(new ProductClassDialog.PClasslistener() {
                @Override
                public void PclassId(ProdcutClassModel.PClassMosel pClassMosel) {
                    txtClassname.setText(pClassMosel.getName());
                    CId = pClassMosel.getId();
                }
            });
        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }
    /**
     * 商品添加修改
     * @param model
     */
    @Override
    public void getAddProductSuccess(BaseModel model) {
        if ("200".equals(model.getCode())) {
            if(!TextUtils.isEmpty(WareId)){
                ToastUtils.showShortToast("修改成功");
            }else{
                ToastUtils.showShortToast("添加成功");
            }
            finish();
        } else {
            ToastUtils.showShortToast(String.valueOf(model.getData()));
        }
    }

    /**
     * 图片上传回调
     * @param model
     */
    @Override
    public void getIMGSuccess(BaseModel<WareImgModel> model) {
        if ("200".equals(model.getCode())) {
            if(model!=null){
                imgModel = model.getData().getUploadFile();
                if(model.getData()!=null&&model.getData().getUploadFile()!=null&&!TextUtils.isEmpty(model.getData().getUploadFile().getPath())){
                    txtAddimg.setVisibility(View.GONE);
                    imgWare.setVisibility(View.VISIBLE);
                    Glide.with(AddProductActivity.this).load(model.getData().getUploadFile().getPath()).into(imgWare);
                    ImgId=model.getData().getUploadFile().getUploadFileId();
                }else{
                    txtAddimg.setVisibility(View.VISIBLE);
                    imgWare.setVisibility(View.GONE);
                }
            }

        } else {
            ToastUtils.showShortToast(model.getData().getError());
        }
    }

    @Override
    public void getFail(String model) {
        ToastUtils.showShortToast(model);
    }

    @OnClick({R.id.ll_back,  R.id.ll_right, R.id.txt_addimg, R.id.img_ware, R.id.txt_selectclass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                if(TextUtils.isEmpty(ImgId)){
                    ToastUtils.showLongToast("请上传商品图片");
                    return;
                }
                if(TextUtils.isEmpty(CId)){
                    ToastUtils.showLongToast("请选择商品分类");
                    return;
                }
                String Warename = edtWarename.getText().toString();
                String Stock = edtStock.getText().toString();
                String Price = edtPrice.getText().toString();
                String Content = edtContent.getText().toString();
                if(TextUtils.isEmpty(Warename)){
                    ToastUtils.showLongToast("请输入商品名称");
                    edtWarename.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(Stock)){
                    ToastUtils.showLongToast("请输入菜品库存");
                    edtStock.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(Price)){
                    ToastUtils.showLongToast("请输入菜品价格");
                    edtPrice.requestFocus();
                    return;
                }
                mvpPresenter.AddWares(WareId,Warename,Price,Stock,CId,Content,ImgId);
                break;
            case R.id.txt_addimg:
                AddImg();
                break;
            case R.id.img_ware:
                AddImg();
                break;
            case R.id.txt_selectclass:
                mvpPresenter.loadProductClass();
                break;
        }
    }
    public void AddImg(){
        OnPhotoDialog onPhotoDialog = new OnPhotoDialog(this);
        onPhotoDialog.SetOnphotolistener(onphotoListener);
        onPhotoDialog.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
    OnphotoListener onphotoListener = new OnphotoListener() {
        @Override
        public void confirm(int tag) {
            if (String.valueOf(tag) != null) {
                if (tag == PIC_FROM_CAMERA) {
                    //拍照
                    Camera.getInstance(AddProductActivity.this).startCamera(true);
                } else if (tag == PIC_FROM＿LOCALPHOTO) {
                    //相册
                    Camera.getInstance(AddProductActivity.this).startCamera(false);
                }
            }
        }
    };
    @Override
    public void onActivityResult(int requestCode, int
            resultCode, Intent data) {
        switch (requestCode) {
            case PIC_FROM_CAMERA:
                //相册
                if (data != null) {
                    Camera.getInstance(AddProductActivity.this).setPhotoZoom(data.getData(), 600, 600, false);
                }
                break;
            case 2:
                //拍照
                Camera.getInstance(AddProductActivity.this).setPhotoZoom(null, 600, 600, true);
                break;
            case 3:
                //剪切完成
                if (data != null) {                    // 获取原图
                    urlpath = Camera.getInstance(AddProductActivity.this).getPath();
                    if (!TextUtils.isEmpty(urlpath)) {
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
