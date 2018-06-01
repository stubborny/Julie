package com.bjtu.julie.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class AuthenticateActivity extends AppCompatActivity {


    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_btn_ok)
    TextView titleBtnOk;
    @BindView(R.id.upload_img_show)
    ImageView uploadImgShow;
    @BindView(R.id.upload_btn_ok)
    Button uploadBtnOk;
    public ProgressDialog progressDialog;//上传图片时的进度条
    public Handler mHandler;//用于更新进度条
    public Intent tempIntent;//目的是为了解决获取图片路径为null的问题
    //生成当前系统时间的文件用于存储相机拍摄的照片
    public File tempFile = null;//new File(Environment.getExternalStorageDirectory(),getPhotoFileName());
    boolean upFlag;

    private static final int RC_CAMERA_PERM = 123;
    public static final int PHOTO_CAMERA = 0;//表示从相机获得照片
    public static final int PHOTO_WALL = 1;//表示从相册获得照片
    public static final int PHOTO_STORE = 2;//表示需要存储图片
    public static final int PHOTO_NOT_STORE = 3;//表示不需要存储图片
    @BindView(R.id.authentic_dear)
    TextView authenticDear;
    @BindView(R.id.authentic_name)
    EditText authenticName;
    @BindView(R.id.authentic_no)
    EditText authenticNo;
    @BindView(R.id.authentic_layout)
    LinearLayout authenticLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        ButterKnife.bind(this);
        upFlag = false;
        titleBtnOk.setText("");
        titleText.setText("学生认证");
        progressDialog = getProgressDialog();//获得进度条
        //更新进度条
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 > 0)
                    progressDialog.setProgress(msg.arg1);//更新进度条
            }
        };
        int flag = UserManager.getInstance().getUser().getIsAuthentication();
        /**
         * 0表示未认证 1表示已认证 2表示认证中 3表示认证不通过
         */
        if (flag == 0) {
            authenticDear.setText("亲爱的" + UserManager.getInstance().getUser().getNickname() + "，请如实填写下列信息");
        } else if (flag == 1) {
            authenticDear.setText("亲爱的" + UserManager.getInstance().getUser().getNickname() + "，您的认证已通过");
            authenticLayout.setVisibility(View.GONE);
            authenticName.setText(UserManager.getInstance().getUser().getName());
            authenticNo.setText(String.valueOf(UserManager.getInstance().getUser().getNo()));
            authenticName.setEnabled(false);
            authenticNo.setEnabled(false);
        } else if (flag == 2) {
            authenticDear.setText("亲爱的" + UserManager.getInstance().getUser().getNickname() + "，当前状态认证中，请耐心等待");
            authenticLayout.setVisibility(View.GONE);
            authenticName.setText(UserManager.getInstance().getUser().getName());
            authenticNo.setText(String.valueOf(UserManager.getInstance().getUser().getNo()));
            authenticName.setEnabled(false);
            authenticNo.setEnabled(false);
        } else if (flag == 3) {
            authenticDear.setText("亲爱的" + UserManager.getInstance().getUser().getNickname() + "，您的认证未通过，请重新填写信息，并上传照片");
        }

    }

    @OnClick({R.id.upload_img_show, R.id.upload_btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.upload_img_show:
                setDialog();
                break;
            case R.id.upload_btn_ok:
                if (authenticName.getText().equals("")) {
                    Toast.makeText(AuthenticateActivity.this, "请填写姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (authenticNo.getText().equals("")) {
                    Toast.makeText(AuthenticateActivity.this, "请填写学号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (upFlag) {
                    //上传图片
                    UploadThread thread = new UploadThread();
                    thread.start();
                } else {
                    Toast.makeText(AuthenticateActivity.this, "请先选择图片", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private void setDialog() {
        final Dialog mCameraDialog = new Dialog(AuthenticateActivity.this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(AuthenticateActivity.this).inflate(
                R.layout.bottomdialog, null);
        //初始化视图
        root.findViewById(R.id.but_camera).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //打开相机
                if (EasyPermissions.hasPermissions(AuthenticateActivity.this, Manifest.permission.CAMERA)) {
                    tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                    //调用系统拍照
                    startCamera(mCameraDialog);
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, PHOTO_CAMERA);
                } else {
                    EasyPermissions.requestPermissions(AuthenticateActivity.this, "需要获取系统的拍照的权限！", RC_CAMERA_PERM, Manifest.permission.CAMERA);
                    mCameraDialog.dismiss();
                }


            }
        });
        root.findViewById(R.id.but_photo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Toast.makeText(getContext(), "等会打开相册", Toast.LENGTH_SHORT).show();

                if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //打开系统图库
                    startWall(mCameraDialog);
                } else {
                    EasyPermissions.requestPermissions(AuthenticateActivity.this, "需要获取系统打开内存的权限！", RC_CAMERA_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    mCameraDialog.dismiss();

                }


            }
        });

        root.findViewById(R.id.but_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCameraDialog.dismiss();
            }
        });

        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CAMERA:
                //表示从相机获得的照片，需要进行裁剪
                startPhotoCut(Uri.fromFile(tempFile), 300, true);
                break;
            case PHOTO_WALL:
                if (null != data)
                    startPhotoCut(data.getData(), 300, false);
                break;
            case PHOTO_STORE:
                if (null != data) {
                    setPictureToImageView(data, true);
                }
                break;
            case PHOTO_NOT_STORE:
                if (null != data) {
                    setPictureToImageView(data, false);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 将图片裁剪到指定大小
     *
     * @param uri
     * @param size
     * @param flag
     */
    public void startPhotoCut(Uri uri, int size, boolean flag) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);//设置Intent中的view是可以裁剪的
        //设置宽高比
        intent.putExtra("aspectX", 5);
        intent.putExtra("aspectY", 8);
        //设置裁剪图片的宽高
        intent.putExtra("outputX", 225);
        intent.putExtra("outputY", 360);
        //设置是否返回数据
        intent.putExtra("return-data", true);
        if (flag == true)
            startActivityForResult(intent, PHOTO_STORE);
        else {
            tempIntent = intent;
            try {
                startActivityForResult(tempIntent, PHOTO_NOT_STORE);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * 将图片显示到ImageView上面
     *
     * @param data
     * @param flag 表示如果是拍照获得的照片的话则是true，如果是从系统选择的照片的话就是false
     */
    public void setPictureToImageView(Intent data, boolean flag) {
        Bundle bundle = data.getExtras();
        if (null != bundle) {
            Bitmap bitmap = bundle.getParcelable("data");
            uploadImgShow.setImageBitmap(bitmap);//将图片显示到ImageView上面
            //上传图片到服务器
            if (flag == false) {
                //需要首先修改tempFile的值
                String path = getSelectPhotoPath(tempIntent);
                System.out.println("path:  " + path);
                tempFile = new File(path);
                //uploadPicture();
                upFlag = true;
            } else {
                upFlag = true;

            }
            if (flag == true)
                savePictureToSD(bitmap);//保存图片到sd卡上面
        }
    }

    /**
     * 将图片保存到SD卡上面
     *
     * @param bitmap
     */
    public void savePictureToSD(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fos = null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//第2个参数表示压缩率，100表示不压缩
        try {
            fos = new FileOutputStream(tempFile);
            fos.write(baos.toByteArray());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                    baos = null;
                }
                if (null != fos) {
                    fos.close();
                    fos = null;
                }
            } catch (Exception e2) {

            }
        }
    }

    /**
     * 获得选择图片的路径
     *
     * @param data
     * @return
     */
    public String getSelectPhotoPath(Intent data) {
        String path = "";
        Uri uri = data.getData();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getApplication().getContentResolver().query(uri, proj, null, null, null);
        //获得选中图片的索引值
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //将光标移到开头处
        cursor.moveToFirst();
        //根据索引值获得图片地址
        path = cursor.getString(index);
        return path;
    }

    class UploadThread extends Thread {
        @Override
        public void run() {
            uploadPicture();
        }
    }

    /**
     * 上传图片到数据库
     */
    public void uploadPicture() {
        /**
         * 服务器地址，传递两个参数
         */
        String url = "http://39.107.225.80:8080/julieServer/AuthenticateServlet";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("username", UserManager.getInstance().getUser().getUsername());
        params.addBodyParameter("name", authenticName.getText().toString());
        params.addBodyParameter("no", authenticNo.getText().toString());
        params.addBodyParameter(tempFile.getPath().replace("/", ""), tempFile);
        x.http().post(params, new Callback.ProgressCallback<String>() {
            /**
             * 下载暂停时回调方法
             */
            @Override
            public void onWaiting() {
                progressDialog.show();//显示进度条

            }

            /**
             * 下载开始时回调的方法
             */
            @Override
            public void onStarted() {
                progressDialog.show();//显示进度条

            }

            /**
             * 下载进度实时回调的方法
             * @param total 文件总大小
             * @param current 当前已下载大小
             * @param isDownloading 当前下载状态
             */
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int process = 0;
                if (total != 0) {
                    process = (int) (current / (total / 100));
                }
                Message message = new Message();
                message.arg1 = process;
                mHandler.sendMessage(message);
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                    if (jb.getInt("code") == 1) {
                        Toast.makeText(x.app(), "图片上传成功", Toast.LENGTH_SHORT).show();
                        uploadBtnOk.setBackgroundColor(uploadBtnOk.getResources().getColor(R.color.darkgrey));
                        uploadBtnOk.setClickable(false);
                        uploadBtnOk.setText("认证中...");
                        uploadImgShow.setClickable(false);
                        authenticName.setEnabled(false);
                        authenticNo.setEnabled(false);
                        UserManager.getInstance().getUser().setIsAuthentication(2);
                        UserManager.getInstance().getUser().setName(authenticName.getText().toString());
                        UserManager.getInstance().getUser().setNo(Integer.valueOf(authenticName.getText().toString()));
                    } else {
                        Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //隐藏进度条
                progressDialog.dismiss();
                Log.e("System.out.print", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                //隐藏进度条
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 调用相机来照相
     *
     * @param dialog
     */
    public void startCamera(DialogInterface dialog) {
        dialog.dismiss();//首先隐藏选择照片来源的dialog
        //调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2);//调用前置摄像头
        intent.putExtra("autofocus", true);//进行自动对焦操作
        intent.putExtra("fullScreen", false);//设置全屏
        intent.putExtra("showActionIcons", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));//指定调用相机之后所拍照存储到的位置
        startActivityForResult(intent, PHOTO_CAMERA);
    }

    /**
     * 打开系统图库
     *
     * @param dialog
     */
    public void startWall(DialogInterface dialog) {
        dialog.dismiss();//设置隐藏dialog
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_WALL);
    }

    /**
     * 获取下载进度条
     *
     * @return
     */
    public ProgressDialog getProgressDialog() {
        ProgressDialog pg = new ProgressDialog(getApplicationContext());
        pg.setProgress(0);
        pg.setIndeterminate(false);
        pg.setCancelable(false);//表示该进度条不可以被点没
        pg.setTitle("上传进度");
        pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条风格
        pg.setMax(100);//设置进度条的最大值
        return pg;
    }

    /**
     * 生成当前所拍照片的名字(其值为IMG_当前时间.jpg)
     *
     * @return
     */
    public String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());//获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        System.out.println("imagePath:  " + Environment.getExternalStorageDirectory() + "IMG" + format.format(date) + ".jpg");
        return "IMG_" + format.format(date) + ".jpg";
    }

}
