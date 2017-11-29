package com.butao.ulifebiz.mvp.dialog.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.butao.ulifebiz.permissiongen.PermissionGen;
import com.butao.ulifebiz.util.RegexUtils;
import com.butao.ulifebiz.util.SysConst;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by User on 2016/6/28.
 */
public class UpdateManager {
    private Context mContext;
    /** 下载中 */
    private  static  final  int DOWNLOAD = 1;
    /** 下载结束 */
    private  static  final int DOWNLOAD_FINISH = 2;
    /** 保存解析的XML信息 */
    HashMap<String,String> mHashMap;
    /** 下载保存路径 */
    private  String mSavePath;
    /** 记录进度条数量 */
    private int progress;
    /** 是否取消更新 */
    private boolean cancelUpdate = false;
    /** 版本名称、版本号 */
    int versionCode;
    String versionName;
    String remark="";
    /** 下载对话框 */
    UpdateDialog dialog;

    private String from="";
    String versions="";
    Version version = new Version();
    OkHttpClient httpClient = new OkHttpClient();
    private Handler mHandle=new Handler(){
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                // 正在下载
                case DOWNLOAD:
                    /** 设置进度条位置 */
                    if(dialog != null)
                        dialog.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    /** 安装文件 */
                    installApk();
                    break;
                case 10001:  // 获取版本信息
                    Version vers= (Version) msg.obj;
                    /** 获取当前软件版本 */
                    getVersionCode(mContext);
                    /** 获取服务器版本信息 */
                    versions=vers.getVersionNo();
                    mHashMap.put("version",versions);
                    remark = vers.getRemark();
                    /** 下载apk地址 */
                    String path=vers.getPath();
                    if (path !=null){
                        String url= SysConst.HTTPSNEW_IP + path;
                        mHashMap.put("url",url);
                    }
                    String name="拾羽商家版";
                    mHashMap.put("name",name);
                    if (version ==null || path ==null){
                        return;
                    }
                    String[] newVersionName = versions.split("\\.");
                    String[] oldVersionName = versionName.split("\\.");

                    /** 比较版本 */
                    if(Integer.parseInt(newVersionName[0]) > Integer.parseInt(oldVersionName[0])) {
                        // 更新
                        showNoticeDialog(versions);
                    } else if(Integer.parseInt(newVersionName[0]) == Integer.parseInt(oldVersionName[0])) {
                        if(Integer.parseInt(newVersionName[1]) > Integer.parseInt(oldVersionName[1])) {
                            // 更新
                            showNoticeDialog(versions);
                        } else if(Integer.parseInt(newVersionName[1]) == Integer.parseInt(oldVersionName[1])) {
                            if(oldVersionName.length>2&&newVersionName.length>2){
                                if(Integer.parseInt(newVersionName[2]) > Integer.parseInt(oldVersionName[2])) {
                                    // 更新
                                    showNoticeDialog(versions);
                                } else {
                                    // 不更新
                                    //Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
                                    if (from.equals("Setting")) {
                                        Toast.makeText(mContext, "当前版本已是最新版本", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }else{
                                if(newVersionName.length>2&&oldVersionName.length<=2){
                                    showNoticeDialog(versions);
                                }
                            }
                        } else {
                            if(Integer.parseInt(newVersionName[1]) > Integer.parseInt(oldVersionName[1])){
                                showNoticeDialog(versions);
                            }
                            // 不更新
                            //Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if(Integer.parseInt(newVersionName[1]) > Integer.parseInt(oldVersionName[1])){
                            showNoticeDialog(versions);
                        }
                        // 不更新
                        //Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
                    }

                    /**   按理serviceCode比较是否最新版本、后台没有serviceCode字段，所有我们versionName来判断是否要更新
                     int serviceCode = Integer.parseInt(versionName);
                     // 版本判断
                     if (serviceCode > versionCode)
                     {
                     showNoticeDialog();
                     } else {
                     Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
                     }
                     */
                    break;
                default:
                    break;
            }
        }
    };
    public UpdateManager(Context context, Activity activity, String from)
    {
        PermissionGen.needPermission(activity, 200, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        this.mContext = context;
        this.from=from;
        mHashMap = new HashMap<String, String>();
        mHashMap.put("name", null);
        mHashMap.put("version", null);
        mHashMap.put("url", null);
    }
    public UpdateManager(Context context, Activity activity)
    {
        if (Build.VERSION.SDK_INT>=23){
            PermissionGen.needPermission(activity, 200, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        this.mContext = context;
        mHashMap = new HashMap<String, String>();
        mHashMap.put("name", null);
        mHashMap.put("version", null);
        mHashMap.put("url", null);
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate()
    {
        Request request = new Request.Builder()
                .url(SysConst.HTTPSNEW_IP + "getVersion.do").addHeader("type","SHANGJIABAN")
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String s = response.body().toString();
                    JSONObject obj = new JSONObject(s);
                    String code = obj.getString("code");
                    if ("200".equals(code)) {
                        String data = obj.getString("data");
                        JSONObject objdata = new JSONObject(data);
                        if(RegexUtils.containsString(data,"versionNo")){
                            String versionNo = objdata.getString("versionNo");
                            version.setVersionNo(versionNo);
                        }
                        if(RegexUtils.containsString(data,"path")){
                            String path = objdata.getString("path");
                            version.setPath(path);
                        }
                        if(RegexUtils.containsString(data,"remark")){
                            String content = objdata.getString("remark");
                            version.setRemark(content);
                        }
//                        version.setData(null);
                        MyThread mthread = new MyThread();
                        mthread.start();
                    } else {
                        String data = obj.getString("data");
                        JSONObject objdata = new JSONObject(data);
                        String error = objdata.getString("error");
//                        version.setData(error);
                        version.setPath(null);
                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }



    /** 获取版本信息 */
    public  class  MyThread extends  Thread {
        @Override
        public void run() {
            Message message=Message.obtain();
            try {
                message.what=10001;
                message.obj=version;
            } catch (Exception e) {
                message.what=-1;
                message.obj=e.getMessage();
            }
            mHandle.sendMessage(message);
        }
    }
    /** 获取软件版本号 */
    private void getVersionCode(Context context)
    {
        try
        {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.bodong.yanruyubiz", 0).versionCode;
            versionName = context.getPackageManager().getPackageInfo("com.bodong.yanruyubiz", 0).versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    /** 显示软件更新对话框 */
    private void showNoticeDialog(String version)
    {
        UpdateDialog backDialog = new UpdateDialog((Activity)mContext, 0,version,remark);
        backDialog.setOnListener(listener);
        backDialog.dialogShow();
    }
    /** 显示软件下载对话框 */
    private void showDownloadDialog(String version)
    {
        // 构造软件下载对话框
        dialog = new UpdateDialog((Activity)mContext, 1,version,remark);
        dialog.setOnListener(listener);
        dialog.dialogShow();

        // 下载文件
        downloadApk();
    }

    UpdateDialog.OnListener listener = new UpdateDialog.OnListener() {
        @Override
        public void quxiao(int type) {
            if(type == 0) {
            } else {
                // 取消更新
                dialog = null;
                cancelUpdate = true;
            }
        }
        @Override
        public void queding() {
            // 显示下载对话框
            showDownloadDialog(versions);
        }
    };

    /** 下载apk文件 */
    private void downloadApk()
    {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /** 下载文件线程  */
    private class downloadApkThread extends Thread
    {
        @Override
        public void run()
        {   try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                String sdpath=Environment.getExternalStorageDirectory() +"/";
                mSavePath=sdpath +"download";
                URL url=new URL(mHashMap.get("url"));
                // 创建连接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                // 获取文件大小
                int length = conn.getContentLength();
                // 创建输入流
                InputStream is = conn.getInputStream();

                File file = new File(mSavePath);
                // 判断文件目录是否存在
                if (!file.exists())
                {
                    file.mkdir();
                }
                File apkFile = new File(mSavePath, mHashMap.get("name"));
                FileOutputStream fos = new FileOutputStream(apkFile);
                int count = 0;
                // 缓存
                byte buf[] = new byte[1024];
                // 写入到文件中
                do
                {
                    int numread = is.read(buf);
                    count += numread;
                    // 计算进度条位置
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandle.sendEmptyMessage(DOWNLOAD);
                    if (numread <= 0)
                    {
                        // 下载完成
                        mHandle.sendEmptyMessage(DOWNLOAD_FINISH);
                        break;
                    }
                    // 写入文件
                    fos.write(buf, 0, numread);
                } while (!cancelUpdate);// 点击取消就停止下载.
                fos.close();
                is.close();
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
            Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
            Log.d("Genxin",e.getMessage());
        }catch (IOException e){
            e.printStackTrace();
            Log.d("Genxin", e.getMessage());
            Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        }
    }

    /** 安装APK文件 */
    private void installApk()
    {
        File apkfile = new File(mSavePath, mHashMap.get("name"));
        if (!apkfile.exists())
        {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

}
