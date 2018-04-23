package com.bjtu.julie.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Activity.FeedBackActivity;
import com.bjtu.julie.Activity.ForgetActivity;
import com.bjtu.julie.Activity.ImpressionActivity;
import com.bjtu.julie.Activity.LoginActivity;
import com.bjtu.julie.Activity.PublishActivity;
import com.bjtu.julie.Activity.QuestionActivity;
import com.bjtu.julie.Activity.RecieveActivity;
import com.bjtu.julie.Activity.TicketActivity;
import com.bjtu.julie.Activity.UserInfoActivity;
import com.bjtu.julie.Activity.WalletActivity;
import com.bjtu.julie.Adapter.MessageAdaper;
import com.bjtu.julie.FullyLinearLayoutManager;
import com.bjtu.julie.MainActivity;
import com.bjtu.julie.Model.Exchange;
import com.bjtu.julie.Model.UserInfo;
import com.bjtu.julie.MyApplication;
import com.bjtu.julie.R;
import com.bjtu.julie.View.ShapeImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends Fragment {
    @BindView(R.id.user1_iv_prehead)
    ShapeImageView user1IvPrehead;
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
    @BindView(R.id.imageView)
    ImageView imageView;
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

    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.ll_exit)
    LinearLayout llExit;
    private String picUrl = null;
    private  String userpicstring = "haha";

   private String name;
    private List<UserInfo> userinfoList=new ArrayList<>();
    private UserInfo userinfo = new UserInfo(null,null,null,null,null);
    //final MyApplication us = (MyApplication) getActivity().getApplication();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.activity_user, container, false);
        unbinder = ButterKnife.bind(this, settingLayout);
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 1) {
            SharedPreferences sp = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            name = sp.getString("name", "null");
            user1TvPrename.setText(name);
            llExit.setVisibility(View.VISIBLE);
        }
        //下载图片URL
        String url = "http://39.107.225.80:8080//julieServer/ShowPicServlet";
        RequestParams params = new RequestParams(name);
        //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                   picUrl = String.valueOf(jb.getString("picUrl"));

                    //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                    Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                //Toast.makeText(getActivity(),"you clicked button 1",Toast.LENGTH_SHORT).show();
            }
        });

if(picUrl!=null){
    user1IvPrehead.setImageBitmap(getImage(picUrl));
}



        return settingLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.ll_exit)
    public void onViewClicked() {
           final MyApplication us = (MyApplication) getActivity().getApplication();
        us.setStatus(0);
        SharedPreferences  sp = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name","登录/注册");
        edit.apply();
        getActivity().onBackPressed();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }



    @OnClick(R.id.user1_iv_prehead)
    public void onUser1IvPreheadClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().onBackPressed();
        } else {
            setDialog();
        }
    }

    @OnClick(R.id.user1_tv_prename)
    public void onUser1TvPrenameClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "已经登录", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ll_info)
    public void onLlInfoClicked() {
        Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ll_mypublish)
    public void onLlMypublishClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(),PublishActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ll_myrecieve)
    public void onLlMyrecieveClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), RecieveActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ll_myimpression)
    public void onLlMyimpressionClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(),ImpressionActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ll_mywallet)
    public void onLlMywalletClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), WalletActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.imageView)
    public void onImageViewClicked() {
        Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ll_ticket)
    public void onLlTicketClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(),TicketActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ll_question)
    public void onLlQuestionClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(),QuestionActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ll_setting)
    public void onLlSettingClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), UserInfoActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ll_feedback)
    public void onLlFeedbackClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(getContext(), FeedBackActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.ll_update)
    public void onLlUpdateClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
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
                // Toast.makeText(getContext(), "等会打开相机", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.but_photo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Toast.makeText(getContext(), "等会打开相册", Toast.LENGTH_SHORT).show();
                //打开本地相册
                Intent intent1 = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //设定结果返回
                startActivityForResult(intent1, 2);
                mCameraDialog.dismiss();
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

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                String sdStatus = Environment.getExternalStorageState();

                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                String name = "photo.jpg";
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                file.mkdirs(); //创建文件夹
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name;
                FileOutputStream b = null;
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                user1IvPrehead.setImageBitmap(bitmap);
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                //图片太大无法加载的解决办法，压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                user1IvPrehead.setImageBitmap(bitmap);
                c.close();
                //上传
              userpicstring =  bitmapToBase64(bitmap);
                //连接
              String url = "http://39.107.225.80:8080/julieServer/ChangePicServlet";
                RequestParams params = new RequestParams(url);
                params.addParameter("username",user1TvPrename.getText());
                params.addParameter("userpicstring",userpicstring);
                x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {

                    public void onSuccess(String result) {
                        try {
                            if(!TextUtils.isEmpty(result)) {
                                JSONObject jb = new JSONObject(result);
                                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    //请求异常后的回调方法
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                    }

                    //主动调用取消请求的回调方法
                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Bitmap getImage(String path){
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            System.out.println("tdw1");
            if(conn.getResponseCode() == 200){
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

