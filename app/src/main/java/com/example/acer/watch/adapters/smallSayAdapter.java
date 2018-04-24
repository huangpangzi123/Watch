package com.example.acer.watch.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.acer.watch.R;
import com.example.acer.watch.javabean.smallSayBean;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by acer on 2018-01-29.
 *
 * 该类是展示小说页面的的适配器类
 */

public class smallSayAdapter extends RecyclerView.Adapter<smallSayAdapter.ViewHolder> implements View.OnClickListener,View.OnLongClickListener{
    private Context mContext;
    private List<smallSayBean> mSmallSayList;
//     有参构造方法
    public smallSayAdapter(List<smallSayBean> smallSayList){
        mSmallSayList=smallSayList;
    }

    /*
      * 自定义的ViewHolder，持有Item的所有界面元素
      * */
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView smallSayCardView;
        ImageView smallSayImage;
        TextView smallSayName;
        public ViewHolder(View view){
            super(view);
            smallSayCardView=(CardView)view;
            smallSayImage=(ImageView)view.findViewById(R.id.smallSayImage_imageView);
            smallSayName=(TextView)view.findViewById(R.id.smallSayName_textView);
        }
    }

    /*
    * 自定义一个接口，用来给RecyclerView添加点击事件，并将其声明出来。
    * */
    private MyItemOnClickListener myItemOnClickListener=null;
    public static interface MyItemOnClickListener{
//        自定义的一个方法，用来模仿ListView的点击事件方法.
//        其中两个变量是：1，当前点击的item。2，当前点击的item的编号。
        void onItemClick(View view,int position);
    }
    /*
    * 长按事件监听
    * */
    private MyItemOnLongClickListener myItemOnLongClickListener=null;
    public static interface MyItemOnLongClickListener{
        void onItemLongClick(View view,int position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
//            得到父活动的实例
            mContext=parent.getContext();
        }
        View views= LayoutInflater.from(mContext).inflate(R.layout.smallsay_item,parent,false);
        final ViewHolder holder=new ViewHolder(views);
//        将创建的view添加点击事件
        views.setOnClickListener(this);
        views.setOnLongClickListener((View.OnLongClickListener) this);
//        holder.smallSayCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        holder.smallSayCardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Snackbar.make(v,"删除该小说?",Snackbar.LENGTH_SHORT)
//                        .setAction("确定删除", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                /*
//                                * 这里删除应该是从数据库进行删除，此处直接删除出现闪退
//                                * */
//                            mSmallSayList.remove(holder.getAdapterPosition());
//                           holder.smallSayCardView.removeViewAt(holder.getAdapterPosition());
//                            }
//                        }).show();
//                return false;
//            }
//        });
        return new ViewHolder(views);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        smallSayBean mSmallSayBean=mSmallSayList.get(position);
        holder.smallSayName.setText(mSmallSayBean.getSmallSayName());
//        with(活动或者碎片) load（图片Id或地址）~~~~~~~~~~~~~~~~~~~into（加载进哪里）
        Glide.with(mContext).load(mSmallSayBean.getSmallSayImage()).into(holder.smallSayImage);
//          将position保存在itemView的Tag中，以便点击时获取到编号。
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if(myItemOnClickListener!=null){
//            获取到点击的是哪个item
            myItemOnClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(myItemOnLongClickListener!=null){
            myItemOnLongClickListener.onItemLongClick(v,(int)v.getTag());
        }
        return true;
    }

    //    自定义的该方法用来暴露上方定义的点击事件，可以给myItemOnClickListener赋值
    public void setMyItemOnClickListener(MyItemOnClickListener listener){
        this.myItemOnClickListener=listener;
    }
    public void setMyItemOnLongClickListener(MyItemOnLongClickListener longClickListener){
        this.myItemOnLongClickListener=longClickListener;
    }
    @Override
    public int getItemCount() {
        return mSmallSayList.size();
    }
}
