package com.example.acer.watch;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.watch.adapters.viewPagerAdapter;
import com.example.acer.watch.fragment.SmallSayFragment;
import com.example.acer.watch.fragment.moviesFragment;
import com.example.acer.watch.fragment.zhiBoFragment;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
/*
* 声明各个控件变量
* */
    private ViewPager viewPager;
//    定义一个数组，用来存放各个页面
    private ArrayList<View> pagerList;
//    三个引导文本
    private TextView smallSay_textView;
    private TextView movies_textView;
    private TextView zhiBo_textView;
//    当前页面的编号（总共有三页：0，1，2）（初始值为：0）
    private int currIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        * 实例化各个控件
        * */
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        smallSay_textView=(TextView)findViewById(R.id.smallSay_textView);
        movies_textView=(TextView)findViewById(R.id.movies_textView);
        zhiBo_textView=(TextView)findViewById(R.id.zhiBo_textView);
        /*
        * 实例化ViewPager碎片类
        * */
        viewPagerAdapter mViewPagerAdapter=new viewPagerAdapter(getSupportFragmentManager());
//        实例化一个Fragment数组，用来保存三个碎片
        ArrayList<Fragment> datas=new ArrayList<>();
        datas.add(new SmallSayFragment());
        datas.add(new moviesFragment());
        datas.add(new zhiBoFragment());
        mViewPagerAdapter.setData(datas);
        /*
        *给引导词设置点击事件
        * */
        smallSay_textView.setOnClickListener(this);
        movies_textView.setOnClickListener(this);
        zhiBo_textView.setOnClickListener(this);

//        为滑动窗体添加适配器
        viewPager.setAdapter(mViewPagerAdapter);
//        设置初始界面为第一个界面
        viewPager.setCurrentItem(0);
//        为滑动窗体添加切换界面的监听器
        viewPager.addOnPageChangeListener(new myPagerChangedListener());
    }
    /*
    * 该方法监听窗体的动作事件
    * */
    public class myPagerChangedListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            switch (position){
                case 0:
                {

                    smallSay_textView.setBackgroundColor(Color.parseColor("#F5F5DC"));
                    movies_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    zhiBo_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;
                }
                case 1:{
                    smallSay_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    movies_textView.setBackgroundColor(Color.parseColor("#F5F5DC"));
                    zhiBo_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    break;
                }
                case 2:{
                    smallSay_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    movies_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    zhiBo_textView.setBackgroundColor(Color.parseColor("#F5F5DC"));
                    break;
                }
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    /*
    * 该方法监听引导内容的带点击事件
    * */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
//          监听点击了哪个引导文本
            case R.id.smallSay_textView:{
                viewPager.setCurrentItem(0);
                smallSay_textView.setBackgroundColor(Color.parseColor("#F5F5DC"));
                movies_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                zhiBo_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            }
            case R.id.movies_textView:{
                viewPager.setCurrentItem(1);
                smallSay_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                movies_textView.setBackgroundColor(Color.parseColor("#F5F5DC"));
                zhiBo_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            }
            case R.id.zhiBo_textView:{
                viewPager.setCurrentItem(2);
                smallSay_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                movies_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                zhiBo_textView.setBackgroundColor(Color.parseColor("#F5F5DC"));
                break;
            }
        }
    }
}
