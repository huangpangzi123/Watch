package com.example.acer.watch.adapters;

import android.content.Context;
import android.media.Image;
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
import com.example.acer.watch.javabean.VideoUrlInDialogBean;

import java.util.List;

/**
 * Created by acer on 2018-03-30.
 *
 * 该适配器用于在AlertDialog中的ListView
 */

public class VideoUrlInDialogAdapter extends ArrayAdapter<VideoUrlInDialogBean>{
    private int resourceId;
    public VideoUrlInDialogAdapter(Context context, int resource, List<VideoUrlInDialogBean> objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        获取当前实例
        VideoUrlInDialogBean bean=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext())
                    .inflate(resourceId,parent,false);
        }else{
            view=convertView;
        }
        ImageView videoPicInDialog_imageView=(ImageView)view.findViewById(R.id.videoUrlPicInDialog_imageView);
        TextView videoUrlInDialog_textView=(TextView)view.findViewById(R.id.videoUrlInDialog_textView);
        videoPicInDialog_imageView.setImageResource(bean.getPicId());
        videoUrlInDialog_textView.setText(bean.getVideoUr());
        return view;
    }
}
