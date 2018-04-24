package com.example.acer.watch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.watch.R;

/**
 * Created by acer on 2018-03-08.
 */

public class addSmallSayByPhoneFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View moviesView=inflater.inflate(R.layout.adsmallsaybyphone_fragmentlayout,container,false);
        return moviesView;
    }
}
