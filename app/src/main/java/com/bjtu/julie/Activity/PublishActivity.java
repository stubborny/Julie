package com.bjtu.julie.Activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.julie.Fragment.FootManFragment;
import com.bjtu.julie.Fragment.MessageFragment;
import com.bjtu.julie.Fragment.MyFootmanFragment;
import com.bjtu.julie.Fragment.MyMessageFragment;
import com.bjtu.julie.Fragment.SettingFragment;
import com.bjtu.julie.R;
import com.bjtu.julie.Service.PollingService;
import com.bjtu.julie.Util.PollingUtils;

import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private MyFootmanFragment myfootmanFragment;
    private MyMessageFragment mymessageFragment;


    /*
     * fragment的布局
     */
    private View myorderLayout;
    private View mymessageLayout;


    /*
     * tab的图标
     */
    private ImageView myfootmanImage;
    private ImageView mymessageImage;


    /*
     * tab的文本
     */
    private TextView myfootmanText;
    private TextView mymessageText;

    private ViewPager viewPager;// 声明一个viewpager对象
    /*
     * 对Fragment进行管理
     */
    private FragmentManager fragmentManager;
    // private FrameLayout frameLayout;
    public static com.bjtu.julie.Activity.PublishActivity PublishActivity;
    private List<Fragment> mFragment = new ArrayList<>();// 声明一个list集合存放Fragment（数据源）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   Log.e("System.out.print", "Start polling service");
    //    PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);

        setContentView(R.layout.activity_publish);
        fragmentManager = getFragmentManager();
        PublishActivity = this;
        initViews(); //初始化界面，并设置四个tab的监听

        Intent intent = getIntent();

        setTabSelection(0);
        viewPager.setCurrentItem(0, false);

    }

    /*
    * 根据传入的index，来设置开启的tab页面
    * @param index
    * index代表对应的下标，0对应消息，1对应联系人，2对应动态，3对应设置
    */
    private void setTabSelection(int index) {
        // TODO Auto-generated method stub
        //清理之前的所有状态
        clearSelection();
//        //开启一个Fragment事务
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        //隐藏所有的fragment，防止有多个界面显示在界面上
//        hideFragments(transaction);
        switch (index) {
            case 0:
                //当点击消息tab时，改变控件的图片和文字颜色
                myfootmanImage.setImageResource(R.mipmap.ic_directions_run_black_24dp);
                myfootmanText.setTextColor(Color.WHITE);
//                //如果messageFragment为空，则创建一个添加到界面上
//                if(cityFragment == null){
//                    cityFragment = new CityFragment();
//                    transaction.add(R.id.content, cityFragment);
//                } else {
//                    //如果messageFragment不为空，则直接将它显示出来
//                    transaction.show(cityFragment);
//                }
                break;
            case 1:
                //当点击联系人tab时，改变控件的图片和文字颜色
                mymessageImage.setImageResource(R.mipmap.ic_textsms_black_24dp);
                mymessageText.setTextColor(Color.WHITE);
//                //如果contactsFragment为空，则创建一个添加到界面上
//                if(cityFragment == null){
//                    guideFragment = new GuideFragment();
//                    transaction.add(R.id.content, guideFragment);
//                } else {
//                    //如果contactsFragment不为空，则直接将它显示出来
//                    transaction.show(guideFragment);
//                }
                break;
        }
//        transaction.commit();
    }

//    /*
//	 * 隐藏所有的fragment
//	 * @param transaction
//	 *     用于对fragment进行操作的事务
//	 */
//    private void hideFragments(FragmentTransaction transaction) {
//        // TODO Auto-generated method stub
//        if(cityFragment != null){
//            transaction.hide(cityFragment);
//        }
//        if(guideFragment != null){
//            transaction.hide(guideFragment);
//        }
//        if(settingFragment != null){
//            transaction.hide(settingFragment);
//        }
//    }

    /*
     * 清理之前的所有状态
     */
    private void clearSelection() {
        // TODO Auto-generated method stub
        myfootmanImage.setImageResource(R.mipmap.ic_directions_run_black_24dp);
        myfootmanText.setTextColor(Color.parseColor("#82858b"));
        mymessageImage.setImageResource(R.mipmap.ic_textsms_black_24dp);
        mymessageText.setTextColor(Color.parseColor("#82858b"));
    }

    /*
 * 初始化界面，并设置四个tab的监听
 */
    private void initViews() {
        // TODO Auto-generated method stub
        // 实例化对象
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myorderLayout = findViewById(R.id.my_footman_layout);
        mymessageLayout = findViewById(R.id.my_message_layout);

        myfootmanImage = (ImageView) findViewById(R.id.my_footman_image);
        mymessageImage = (ImageView) findViewById(R.id.my_message_image);


        myfootmanText = (TextView) findViewById(R.id.my_footman_text);
       mymessageText = (TextView) findViewById(R.id.my_message_text);


        myorderLayout.setOnClickListener(this);
       mymessageLayout.setOnClickListener(this);

        // 设置数据源
       myfootmanFragment = new MyFootmanFragment();
        mymessageFragment = new MyMessageFragment();


        mFragment.clear();
        mFragment.add(myfootmanFragment);
        mFragment.add(mymessageFragment);


        // 设置适配器
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(
                getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragment.get(arg0);
            }
        };

        // 绑定适配器
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(2);
//        mViewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(this);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setTabSelection(position);
//        Log.e("xxx","--------onPageSelected----------position="+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*
     * 点击四个tab时的监听
     * @param v
     *     四个控件的view
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.my_footman_layout:
                //点击消息tab，选中第一个tab
                setTabSelection(0);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.my_message_layout:
                //点击联系人tab，选中第二个tab
                setTabSelection(1);
                viewPager.setCurrentItem(1, false);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Stop polling service
    }
}
