package com.bjtu.julie.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.bjtu.julie.MainActivity;
import com.bjtu.julie.R;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Polling service
 *
 * @Author Carrey
 */
public class PollingService extends Service {

    public static final String ACTION = "com.bjtu.julie.Service.PollingService";

    private Notification mNotification;
    private NotificationManager mManager;
    Notification.Builder mBuilder;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initNotifiManager();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }

    private void initNotifiManager() {


    }

    private void showNotification(int id,String title,String text) {
        //新建一个Notification管理器;
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(this);//新建Notification.Builder对象
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);
        //点击通知后自动清除
        mBuilder.setAutoCancel(true);
        mBuilder.setWhen(System.currentTimeMillis());
        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        mBuilder.setContentIntent(intent);//执行intent
        mNotification = mBuilder.getNotification();

        //第一个参数是id，id不同会有多个通知，相同会覆盖
        mManager.notify(id, mNotification);
    }

    /**
     * Polling thread
     *
     * @Author Ryan
     * @Create 2013-7-13 上午10:18:34
     */
    int count = 0;

    class PollingThread extends Thread {
        @Override
        public void run() {
            Log.e("System.out.print", "Polling...");
            //向服务器发出请求，得到是否显示通知
            String url = "http://39.107.225.80:8080/julieServer/PollServlet";
            RequestParams params = new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                public void onSuccess(String result) {
                    try {
                        JSONObject jb = new JSONObject(result);
                        //Log.i("AAA", String.valueOf(jb.getInt("code"))+jb.getString("msg"));
                        //Toast.makeText(x.app(), jb.getString("msg"), Toast.LENGTH_LONG).show();
                        if(jb.getInt("code")==1){
                            showNotification(0,"有新订单哦",jb.getString("msg"));
                        }
                        Log.e("System.out.print", jb.getString("msg"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                //请求异常后的回调方法
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("System.out.print", ex.getMessage());

                }

                //主动调用取消请求的回调方法
                @Override
                public void onCancelled(CancelledException cex) {
                    Log.e("System.out.print", cex.getMessage());
                }

                @Override
                public void onFinished() {

                }
            });


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("System.out.print", "Service:onDestroy");
    }

}