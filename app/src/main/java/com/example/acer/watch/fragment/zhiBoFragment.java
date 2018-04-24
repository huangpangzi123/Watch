package com.example.acer.watch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.watch.R;

/**
 * Created by acer on 2018-01-29.
 * 滑动菜单中直播界面的碎片
 */

public class zhiBoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View zhiBoView=inflater.inflate(R.layout.zhibo_layout,container,false);
        return zhiBoView;
    }
}
