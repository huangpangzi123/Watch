package com.example.acer.watch.javabean;

/**
 * Created by acer on 2018-01-29.
 * 该类是小说小说界面的布局类
 */

public class smallSayBean {
//    小说的名字
    private String smallSayName;
//    小说的封面图片
    private int smallSayImage;
        /*
        * 因为加载小说名字和图片是一起加载的，所以这里将名字和图片一起赋值
        * */
    public smallSayBean(String smallSayName,int smallSayImage){
        this.smallSayImage=smallSayImage;
        this.smallSayName=smallSayName;
    }
//    获取名字和图片
    public String getSmallSayName(){
        return smallSayName;
    }
    public int getSmallSayImage(){
        return smallSayImage;
    }
}
