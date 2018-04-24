package com.example.acer.watch.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.watch.R;
import com.example.acer.watch.javabean.VideoURLBean;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by acer on 2018-03-28.
 *
 * 用于配置VideoList的Adapter
 */
public class VideoUrlListAdapter extends ArrayAdapter<VideoURLBean>{
private int resourceId;
    public VideoUrlListAdapter(@NonNull Context context, @LayoutRes int resource,
                               List<VideoURLBean> objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        获取当前项的VideoUrlBean实例
        VideoURLBean bean=getItem(position);
        View view;
//        当数据很多时，可以提高缓存效率
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,
                    false);
        }else{
            view=convertView;
        }
        ImageView videoPic_imageView=(ImageView)view.findViewById(R.id.videoUrlPic_imageView);
        TextView videoUrl_textView=(TextView)view.findViewById(R.id.videoUrl_textView);
        videoPic_imageView.setImageResource(bean.getVideoPic());
        videoUrl_textView.setText(bean.getVideoUrl());
        return  view;
    }
}
