package com.example.acer.watch;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.watch.adapters.addSmallSayVIewPagerAdapter;
import com.example.acer.watch.fragment.addSmallSayByHandFragment;
import com.example.acer.watch.fragment.addSmallSayByPhoneFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
/*
* 这个活动是本地添加小说的活动界面
* */

public class AddSmallSayActivity extends AppCompatActivity implements View.OnClickListener{
//    声明返回图片
    private ImageView back_imageView;
//    两个引导词
    private TextView byPhone_textView;
    private TextView byHand_textView;
//    声明引导词对应的viewPager
    private ViewPager addSmallSay_viewPager;
//    该变量是ViewPager的页码(总共两页，编号为：0，1)
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsmallsay_layout);
//        实例化各控件
        back_imageView=(ImageView)findViewById(R.id.back_imageView);
        byPhone_textView=(TextView)findViewById(R.id.byPhone_textView);
        byHand_textView=(TextView)findViewById(R.id.byHand_textView);
        addSmallSay_viewPager=(ViewPager)findViewById(R.id.addSmallSay_viewPager);
//        给各个控件设置监听事件
        back_imageView.setOnClickListener(this);
        byHand_textView.setOnClickListener(this);
        byPhone_textView.setOnClickListener(this);
//        引用自定义的Adapter用来管理ViewPager
        addSmallSayVIewPagerAdapter addSmallSayAdapter=new addSmallSayVIewPagerAdapter(getSupportFragmentManager());
//        定义一个数组用来存放两个碎片
        ArrayList<Fragment> datas=new ArrayList<>();
        datas.add(new addSmallSayByPhoneFragment());
        datas.add(new addSmallSayByHandFragment());
//        将数组传递给adapter
        addSmallSayAdapter.setDatas(datas);
//        给ViewPager设置Adapter
        addSmallSay_viewPager.setAdapter(addSmallSayAdapter);
//        给viewPager设置第一个界面
        addSmallSay_viewPager.setCurrentItem(0);
//        给ViewPager添加窗口滑动监听
        addSmallSay_viewPager.setOnPageChangeListener(new myViewPagerChangedListener());
    }
        /*
        * 监听viewPager的窗口滑动的类
        * */
        private class myViewPagerChangedListener implements ViewPager.OnPageChangeListener {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch(position){
                    case 0:{
                        byPhone_textView.setBackgroundColor(Color.parseColor("#473C8B"));
                        byPhone_textView.setTextColor(Color.parseColor("#FFFFFF"));
                        byHand_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        byHand_textView.setTextColor(Color.BLACK);
                        break;
                    }
                    case 1:{
                        byPhone_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        byPhone_textView.setTextColor(Color.BLACK);
                        byHand_textView.setBackgroundColor(Color.parseColor("#473C8B"));
                        byHand_textView.setTextColor(Color.parseColor("#FFFFFF"));
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
//      该方法监听各控件的动作事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imageView:{
                finish();
                break;
            }
            case R.id.byHand_textView:{
                addSmallSay_viewPager.setCurrentItem(1);

                byHand_textView.setBackgroundColor(Color.parseColor("#473C8B"));
                byHand_textView.setTextColor(Color.parseColor("#FFFFFF"));
                byPhone_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                byPhone_textView.setTextColor(Color.BLACK);
                break;
            }
            case R.id.byPhone_textView:{
                addSmallSay_viewPager.setCurrentItem(0);
                byPhone_textView.setBackgroundColor(Color.parseColor("#473C8B"));
                byPhone_textView.setTextColor(Color.parseColor("#FFFFFF"));
                byHand_textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                byHand_textView.setTextColor(Color.BLACK);
                break;
            }
        }
    }
}
