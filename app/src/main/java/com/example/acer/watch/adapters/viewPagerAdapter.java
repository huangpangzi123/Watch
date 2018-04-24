package com.example.acer.watch.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by acer on 2018-01-29.
 *
 *    该适配器是ViewPager的适配器
 */

public class viewPagerAdapter extends FragmentPagerAdapter {
    /*
    * 定义一个Fragment数组，用来存放三个碎片
    * */
    private ArrayList<Fragment> datas;
    public viewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
//    将三个碎片加载进ViewPager
    public void setData(ArrayList<Fragment> datas){
        this.datas=datas;
    }
    @Override
    public Fragment getItem(int position) {
        return datas==null ? null:datas.get(position);
    }

    @Override
    public int getCount() {
        return datas==null ? 0:datas.size();
    }
}
