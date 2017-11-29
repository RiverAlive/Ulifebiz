package com.butao.ulifebiz.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;


/**
 * 作者：ht
 * 时间：2016/11/8
 * 描述：拍照或相册
 * <p>
 * 单例模式
 * 1.
 * 调用相册或拍照
 * public void startCamera(boolean isCamera)
 * 2.
 * 剪切图片, uri:图片数据, outputX,outputY:图片大小, isCamera:true拍照,false相册
 * public void setPhotoZoom(Uri uri, int outputX, int outputY, boolean isCamera)
 * 3.
 * 图片本地地址
 * public String getPath()
 * 4.
 * 图片数据Bitmap
 * public Bitmap getBitmap()
 * <p>
 * 返回值:
 * 1:相册
 * 2:拍照
 * 3:剪切完成
 */
public class Camera {

    private static Camera sCamera = null;
    private static Context mContext;
    private static Fragment mFragment = null;

    public static Camera getInstance(Context context) {
        if (sCamera == null) {
            sCamera = new Camera();
        }
        mContext = context;
        return sCamera;
    }

    public static Camera getInstance(Fragment fragment) {
        if (sCamera == null) {
            sCamera = new Camera();
        }
        mFragment = fragment;
        return sCamera;
    }

    private File photoFile;
    private File outFile;
    private String mImgPath;
    /**
     * 拍照
     * isCamera : true拍照,false相册
     */
    public void startCamera(boolean isCamera) {
        if (isCamera) {
            mImgPath = Environment.getExternalStorageDirectory() + "/my_camera/" + TimeUtil.getCharacterAndNumber() + ".jpg";
            photoFile = new File(mImgPath);// 图片储存路径
            if (!photoFile.getParentFile().exists()) {
                photoFile.getParentFile().mkdirs();
            }
            //拍照 2
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            if (mFragment == null) {
                ((Activity) mContext).startActivityForResult(intent, 2);
            } else {
                mFragment.startActivityForResult(intent, 2);
            }
        } else {
            //相册 1
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            if (mFragment == null) {
                ((Activity) mContext).startActivityForResult(intent, 1);
            } else {
                mFragment.startActivityForResult(intent, 1);
            }
        }
    }

    /**
     * 剪切图片
     * isCamera : true拍照,false相册
     * outputX,outputY图片大小
     */
    public void setPhotoZoom(Uri uri, int outputX, int outputY, boolean isCamera) {
        if (isCamera) {
            File temp = new File(mImgPath);
            uri = Uri.fromFile(temp);
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "ture");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
//        intent.putExtra("return-data", false);
//        intent.putExtra("circleCrop", false);
        String mImgOut = Environment.getExternalStorageDirectory() + "/my_camera/" + TimeUtil.getCharacterAndNumber() + ".jpg";
        outFile = new File(mImgOut);// 图片储存路径
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
        //剪切完成 3
        if (mFragment == null) {
            ((Activity) mContext).startActivityForResult(intent, 3);
        } else {
            mFragment.startActivityForResult(intent, 3);
        }

    }

    /**
     * 图片本地地址
     */
    public String getPath() {
        return outFile.getPath();
    }

    /**
     * 图片数据Bitmap
     */
    public Bitmap getBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();

        // 减少内存使用量，有效防止OOM
        {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(outFile.getPath(), options);
            // 屏幕宽
            int Wight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();

            // 缩放比
            /*
            int ratio = options.outWidth / Wight;
            if (ratio <= 0)
                ratio = 1;
            options.inSampleSize = ratio;
            */
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
        }
        // 加载图片,并返回
        return BitmapFactory.decodeFile(outFile.getPath(), options);
    }
}
