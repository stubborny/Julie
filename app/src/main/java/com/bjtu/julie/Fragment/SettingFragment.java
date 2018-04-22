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

import com.bjtu.julie.Activity.LoginActivity;
import com.bjtu.julie.Activity.PublishActivity;
import com.bjtu.julie.Activity.RecieveActivity;
import com.bjtu.julie.Activity.UserInfoActivity;
import com.bjtu.julie.Activity.WalletActivity;
import com.bjtu.julie.MainActivity;
import com.bjtu.julie.MyApplication;
import com.bjtu.julie.R;
import com.bjtu.julie.View.ShapeImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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


    //final MyApplication us = (MyApplication) getActivity().getApplication();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.activity_user, container, false);
        unbinder = ButterKnife.bind(this, settingLayout);
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 1) {
            SharedPreferences sp = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String name = sp.getString("name", "null");
            user1TvPrename.setText(name);
            llExit.setVisibility(View.VISIBLE);
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
            Toast.makeText(getContext(), "已经登录", Toast.LENGTH_SHORT).show();
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
            //暂无可用抵用券
            Toast.makeText(getContext(), "暂无可用抵用券", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ll_question)
    public void onLlQuestionClicked() {
        final MyApplication us = (MyApplication) getActivity().getApplication();
        if (us.getStatus() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "更改中……", Toast.LENGTH_SHORT).show();
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

            Toast.makeText(getContext(), "联系开发人员", Toast.LENGTH_SHORT).show();
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
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                String picstring = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                int n = picstring.length();
            }
        }
    }
}

