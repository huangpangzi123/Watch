package com.example.acer.watch.javabean;

/**
 * Created by acer on 2018-03-28.
 *
 * videoUrl:表示视频资源连接
 * videoPic:表示视频资源图片（统一的）
 */

public class VideoURLBean {
    private String videoUrl;
    private int videoPic;
    public VideoURLBean(String videoUrl,int videoPic){
        this.videoUrl=videoUrl;
        this.videoPic=videoPic;
    }
    public String getVideoUrl(){
        return videoUrl;
    }
    public int getVideoPic(){
        return videoPic;
    }
}
