package com.example.acer.watch.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018-03-08.
 */

public class addSmallSayVIewPagerAdapter extends FragmentPagerAdapter{
/*
* 定义一个数组，用来存放两个Fragment
*
* */
    private ArrayList<Fragment> datas;
//    该方法用于给数组赋值
    public void setDatas(ArrayList<Fragment> datas){
        this.datas=datas;
    }
    public addSmallSayVIewPagerAdapter(FragmentManager fm) {
        super(fm);
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
