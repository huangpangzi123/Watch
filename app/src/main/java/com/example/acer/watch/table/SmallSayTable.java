package com.example.acer.watch.table;

import org.litepal.crud.DataSupport;

/**
 * Created by acer on 2018-03-06.
 * 该类用于建立一个数据表，用来存放小说的名字和图片
 *
 */
public class SmallSayTable extends DataSupport{
//    小说的编号
    private int smallSayId_table;
    //    小说的名字
    private String smallSayName_table;
    //    小说的封面图片
    private int smallSayImage_table;
    /*
    * 因为加载小说名字和图片是一起加载的，所以这里将名字和图片一起赋值
    * */
    public void setSmallSayId_table(int id_table){
        this.smallSayId_table=id_table;
    }
    public void setSmallSayName(String name){
        this.smallSayName_table=name;
    }
    public void setSmallSayImage(int image){
        this.smallSayImage_table=image;
    }
    //    获取名字和图片
    public String getSmallSayName(){
        return smallSayName_table;
    }
    public int getSmallSayImage(){
        return smallSayImage_table;
    }
    public int getSmallSayId_table(){
        return smallSayId_table;
    }
}
