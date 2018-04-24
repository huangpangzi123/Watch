package com.example.acer.watch.javabean;
import java.util.List;
/**
 * Created by acer on 2018-04-02.
 *
 * 该Bean类用于存放获取的视频链接和视频名字
 */

public class VideoNameAndURLBean {
    private List<String> videoNameList;
    private List<String> videoUrlList;
    public VideoNameAndURLBean(List<String> videoNameList,
                               List<String> urlList) {
        this.videoNameList=videoNameList;
        this.videoUrlList=urlList;
    }
    public List<String> getVideoNameList() {
        return videoNameList;
    }
    public List<String> getVideoUrlList(){
        return videoUrlList;
    }
}