package com.example.acer.watch.javabean;

/**
 * Created by acer on 2018-03-30.
 *
 * 该Bean用于：点击VideoUrlList后弹出的AlertDialog里的ListView
 */

public class VideoUrlInDialogBean {
    private String videoUrl;
    private int picId;
    public VideoUrlInDialogBean(String videoUrl,int picId){
        this.videoUrl=videoUrl;
        this.picId=picId;
    }
    public String getVideoUr(){
        return videoUrl;
    }
    public int getPicId(){
        return picId;
    }
}
