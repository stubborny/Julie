package com.bjtu.julie.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticateActivity extends AppCompatActivity {

    @BindView(R.id.upload_pic)
    Button uploadPic;
    @BindView(R.id.pic1_show)
    ImageView pic1Show;
    @BindView(R.id.pic2_show)
    ImageView pic2Show;
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 2;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.ly)
    LinearLayout ly;
    private Bitmap b1;
    private Bitmap b2;
    private String bstr = null;
    private String bstr1 = null;
private String str1 = "-1";
    private String str2 = "-2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
        ButterKnife.bind(this);
        int flag = UserManager.getInstance().getUser().getIsAuthentication();
        if(flag == 2){
            ly.setVisibility(View.INVISIBLE);
            tv1.setVisibility(View.INVISIBLE);
            tv2.setVisibility(View.INVISIBLE);
            uploadPic.setText("验证中……");
        }
    }


    private void setDialog(final int num) {
        final Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottomdialog, null);
        //初始化视图
        root.findViewById(R.id.but_camera).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Toast.makeText(getContext(), "等会打开相机", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                int flag = 1 + num;//2---相机1, 3----相机2
                startActivityForResult(intent, flag);
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
                int flag = 3 + num;//4----相册1,5----相册2
                startActivityForResult(intent1, flag);
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
            if (requestCode == 2) {
                String sdStatus = Environment.getExternalStorageState();

                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                String name = "photo.jpg";
                Bundle bundle = data.getExtras();
                b1 = (Bitmap) bundle.get("data");
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                file.mkdirs(); //创建文件夹
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name;
                FileOutputStream b = null;
                try {
                    b = new FileOutputStream(fileName);
                    b1.compress(Bitmap.CompressFormat.JPEG, 100, b);
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

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6;
                b1 = BitmapFactory.decodeFile(fileName, options);
                pic1Show.setImageBitmap(b1);
                tv1.setVisibility(View.VISIBLE);
            } else if (requestCode == 3) {
                String sdStatus = Environment.getExternalStorageState();

                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                String name = "photo.jpg";
                Bundle bundle = data.getExtras();
                b2 = (Bitmap) bundle.get("data");
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                file.mkdirs(); //创建文件夹
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name;
                FileOutputStream b = null;
                try {
                    b = new FileOutputStream(fileName);
                    b2.compress(Bitmap.CompressFormat.JPEG, 100, b);
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
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6;
                b2 = BitmapFactory.decodeFile(fileName, options);
                pic2Show.setImageBitmap(b2);
                tv2.setVisibility(View.VISIBLE);
            } else if (requestCode == 4) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                //图片太大无法加载的解决办法，压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6;
                b1 = BitmapFactory.decodeFile(imagePath, options);
                pic1Show.setImageBitmap(b1);
                tv1.setVisibility(View.VISIBLE);
                //  user1IvPrehead.setImageBitmap(bitmap);
                //   c.close();
                //上传
            } else if (requestCode == 5) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                //图片太大无法加载的解决办法，压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6;
                b2 = BitmapFactory.decodeFile(imagePath, options);
                pic2Show.setImageBitmap(b2);
                tv2.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick(R.id.pic1_show)
    public void onPic1ShowClicked() {
        setDialog(1);
        //显示图片1
    }

    @OnClick(R.id.pic2_show)
    public void onPic2ShowClicked() {
        //显示图片2
        setDialog(2);
    }


    @OnClick(R.id.upload_pic)
    public void onUploadPicClicked() {
        //把图片上传到服务器
        if(uploadPic.getText()=="验证中……"){
            Toast.makeText(this, "正在验证……", Toast.LENGTH_SHORT).show();
            return;
        }
        if (b1 == null || b2 == null) {
            Toast.makeText(this, "别急，你还没上传完图片呢", Toast.LENGTH_SHORT).show();
        }
       else{
            bstr = bitmapToBase64(b1);
            bstr1 = bitmapToBase64(b2);
            upic(bstr,str1);
            upic(bstr1,str2);
            ly.setVisibility(View.INVISIBLE);
            tv1.setVisibility(View.INVISIBLE);
            tv2.setVisibility(View.INVISIBLE);
            uploadPic.setText("验证中……");
        }
    }


    @OnClick(R.id.tv1)
    public void onTv1Clicked() {
        b1 = null;
        pic1Show.setImageBitmap(b1);
        tv1.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.tv2)
    public void onTv2Clicked() {
        b2 = null;
        pic2Show.setImageBitmap(b2);
        tv2.setVisibility(View.INVISIBLE);

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
public void upic(String s,String str){
    String username = UserManager.getInstance().getUser().getUsername();
    //连接
    String url = "http://39.107.225.80:8080/julieServer/UploadAuthPicServlet";
    RequestParams params = new RequestParams(url);
    params.addParameter("username", username);
    params.addParameter("str", str);
    params.addParameter("bstr", s);
    x.http().get(params, new Callback.CommonCallback<String>() {
        public void onSuccess(String result) {
            try {
                JSONObject jb = new JSONObject(result);
                Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
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
