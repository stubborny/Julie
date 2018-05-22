package com.bjtu.julie.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Activity.AuthenticateActivity;
import com.bjtu.julie.Activity.FeedBackActivity;
import com.bjtu.julie.Activity.ImpressionActivity;
import com.bjtu.julie.Activity.LoginActivity;
import com.bjtu.julie.Activity.MyDiscountActivity;
import com.bjtu.julie.Activity.MyLikeList;
import com.bjtu.julie.Activity.PublishActivity;
import com.bjtu.julie.Activity.QuestionActivity;
import com.bjtu.julie.Activity.RecieveActivity;
import com.bjtu.julie.Activity.RegActivity;
import com.bjtu.julie.Activity.UserInfoActivity;
import com.bjtu.julie.Activity.WalletActivity;
import com.bjtu.julie.MainActivity;
import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.Model.UserManager;
import com.bjtu.julie.MyApplication;
import com.bjtu.julie.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

import static com.bjtu.julie.Activity.AuthenticateActivity.bitmapToBase64;

public class SettingFragment extends Fragment {
    @BindView(R.id.user1_iv_prehead)
    ImageView user1IvPrehead;
    @BindView(R.id.user1_tv_prename)
    TextView user1TvPrename;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.ll_mypublish)
    LinearLayout llMypublish;
    @BindView(R.id.ll_myrecieve)
    LinearLayout llMyrecieve;
    @BindView(R.id.ll_myimpression)
    LinearLayout llMyimpression;
    @BindView(R.id.ll_mywallet)
    LinearLayout llMywallet;
    @BindView(R.id.ll_ticket)
    LinearLayout llTicket;
    @BindView(R.id.ll_question)
    LinearLayout llQuestion;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.ll_feedback)
    LinearLayout llFeedback;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.change_all)
    FrameLayout changeAll;
    Unbinder unbinder;
    @BindView(R.id.logout)
    LinearLayout logout;
    @BindView(R.id.like)
    LinearLayout like;
    @BindView(R.id.ll_authenticate)
    LinearLayout llAuthenticate;
    @BindView(R.id.scrollView1)
    ScrollView scrollView1;
    private String userpicstring = "haha";

    public ProgressDialog progressDialog;//上传图片时的进度条
    public Handler mHandler;//用于更新进度条
    public Intent tempIntent;//目的是为了解决获取图片路径为null的问题
    //生成当前系统时间的文件用于存储相机拍摄的照片
    public File tempFile = null;//new File(Environment.getExternalStorageDirectory(),getPhotoFileName());
    //存储裁剪后的图片的路径
    public File upFile = null;

    private static final int RC_CAMERA_PERM = 123;
    public static final int PHOTO_CAMERA = 0;//表示从相机获得照片
    public static final int PHOTO_WALL = 1;//表示从相册获得照片
    public static final int PHOTO_STORE = 2;//表示需要存储图片
    public static final int PHOTO_NOT_STORE = 3;//表示不需要存储图片


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.activity_user, container, false);
        unbinder = ButterKnife.bind(this, settingLayout);
        progressDialog = getProgressDialog();//获得进度条
        //更新进度条
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 > 0)
                    progressDialog.setProgress(msg.arg1);//更新进度条
            }
        };
        if (UserManager.getInstance().isLogined()) {
            //已经登陆
            if (UserManager.getInstance().getUser().getNickname().equals("")) {
                user1TvPrename.setText(UserManager.getInstance().getUser().getUsername());
            } else {
                user1TvPrename.setText(UserManager.getInstance().getUser().getNickname());
            }
            logout.setVisibility(View.VISIBLE);
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.load_error)
                    .setLoadingDrawableId(R.mipmap.loading)
                    .setCircular(true)
                    .build();
            x.image().bind(user1IvPrehead, UserManager.getInstance().getUser().getUserpicUrl(), imageOptions);
        } else {
            logout.setVisibility(View.GONE);
            user1TvPrename.setText("登录/注册");
            //未登陆
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.load_error)
                    .setLoadingDrawableId(R.mipmap.loading)
                    .setCircular(true)
                    .build();
            x.image().bind(user1IvPrehead, getResources().getString(R.string.default_head), imageOptions);
        }
        return settingLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_authenticate, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_authenticate:
                if (!UserManager.getInstance().isLogined()) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), AuthenticateActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.logout:
                //Toast.makeText(getContext(), "注销", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(getContext());
                alertdialogbuilder.setTitle("确认要注销么");
                alertdialogbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url = "http://39.107.225.80:8080/julieServer/UpdateOrderServlet";
                        UserManager.getInstance().setUser(null);
                        logout.setVisibility(View.GONE);
                        user1TvPrename.setText("登录/注册");
                        //未登陆
                        ImageOptions imageOptions = new ImageOptions.Builder()
                                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                                .setFailureDrawableId(R.mipmap.load_error)
                                .setLoadingDrawableId(R.mipmap.loading)
                                .setCircular(true)
                                .build();
                        x.image().bind(user1IvPrehead, getResources().getString(R.string.default_head), imageOptions);
                    }
                });
                alertdialogbuilder.setNegativeButton("取消", null);
                AlertDialog alertdialog1 = alertdialogbuilder.create();
                alertdialog1.show();

                break;
        }
    }


    @OnClick(R.id.user1_iv_prehead)
    public void onUser1IvPreheadClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().onBackPressed();
        } else {
            setDialog();
        }
    }

    @OnClick(R.id.user1_tv_prename)
    public void onUser1TvPrenameClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().onBackPressed();
        }
    }

    @OnClick(R.id.ll_info)
    public void onLlInfoClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ll_mypublish)
    public void onLlMypublishClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), PublishActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ll_myrecieve)
    public void onLlMyrecieveClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), RecieveActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.like)
    public void onLikeClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(getContext(), MyLikeList.class);
            startActivity(intent);
        }
    }

    /**
     * 个人印象
     */
    @OnClick(R.id.ll_myimpression)
    public void onLlMyimpressionClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), ImpressionActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 钱包
     */
    @OnClick(R.id.ll_mywallet)
    public void onLlMywalletClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), WalletActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 抵用券
     */
    @OnClick(R.id.ll_ticket)
    public void onLlTicketClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), MyDiscountActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 常见问题
     */
    @OnClick(R.id.ll_question)
    public void onLlQuestionClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), QuestionActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 修改资料
     */
    @OnClick(R.id.ll_setting)
    public void onLlSettingClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 反馈
     */
    @OnClick(R.id.ll_feedback)
    public void onLlFeedbackClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), FeedBackActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 更新
     */
    @OnClick(R.id.ll_update)
    public void onLlUpdateClicked() {
        if (!UserManager.getInstance().isLogined()) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(getContext(), "暂无新版本", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.change_all)
    public void onChangeAllClicked() {
        Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
    }

    private void setDialog() {
        final Dialog mCameraDialog = new Dialog(getContext(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.bottomdialog, null);
        //初始化视图
        root.findViewById(R.id.but_camera).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //打开相机
                if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA)) {
                    tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
                    //调用系统拍照
                    startCamera(mCameraDialog);
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, PHOTO_CAMERA);
                } else {
                    EasyPermissions.requestPermissions(getActivity(), "需要获取系统的拍照的权限！", RC_CAMERA_PERM, Manifest.permission.CAMERA);
                    mCameraDialog.dismiss();
                }


            }
        });
        root.findViewById(R.id.but_photo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Toast.makeText(getContext(), "等会打开相册", Toast.LENGTH_SHORT).show();

                if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //打开系统图库
                    startWall(mCameraDialog);
//                    //打开本地相册
//                    Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    //设定结果返回
//                    startActivityForResult(intent1, PHOTO_WALL);
                } else {
                    EasyPermissions.requestPermissions(getActivity(), "需要获取系统打开内存的权限！", RC_CAMERA_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_WALL);
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        //设置是否返回数据
        intent.putExtra("return-data", true);
        if (flag == true)
            startActivityForResult(intent, PHOTO_STORE);
        else {
            tempIntent = intent;
            try {
                startActivityForResult(tempIntent, PHOTO_NOT_STORE);
                System.out.println("haha");
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
            //user1IvPrehead.setImageBitmap(bitmap);//将图片显示到ImageView上面
            upFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
            savePictureAfterCutToSD(bitmap);
            //上传图片到服务器
            if (flag == false) {
                //需要首先修改tempFile的值
                String path = getSelectPhotoPath(tempIntent);
                System.out.println("path:  " + path);
                tempFile = new File(path);
                //uploadPicture();
                //上传图片
                UploadThread thread = new UploadThread();
                thread.start();
            } else {
                //uploadPicture();
                //上传图片
                UploadThread thread = new UploadThread();
                thread.start();

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
     * 将图片保存到SD卡上面
     *
     * @param bitmap
     */
    public void savePictureAfterCutToSD(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fos = null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//第2个参数表示压缩率，100表示不压缩
        try {
            fos = new FileOutputStream(upFile);
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
        Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
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
        String url = "http://39.107.225.80:8080/julieServer/ChangePicServlet";
        RequestParams params = new RequestParams(url);
        params.addParameter("username", UserManager.getInstance().getUser().getUsername());
        params.addBodyParameter(upFile.getPath().replace("/", ""), upFile);
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
                        UserManager.getInstance().getUser().setUserpicUrl(jb.getString("msg"));
                        Toast.makeText(x.app(), "头像上传成功", Toast.LENGTH_SHORT).show();
                        ImageOptions imageOptions = new ImageOptions.Builder()
                                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                                .setFailureDrawableId(R.mipmap.load_error)
                                .setLoadingDrawableId(R.mipmap.loading)
                                .setCircular(true)
                                .build();
                        x.image().bind(user1IvPrehead, UserManager.getInstance().getUser().getUserpicUrl(), imageOptions);

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
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_SHORT).show();
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
     * 获取下载进度条
     *
     * @return
     */
    public ProgressDialog getProgressDialog() {
        ProgressDialog pg = new ProgressDialog(getContext());
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

