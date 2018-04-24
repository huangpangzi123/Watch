package com.example.acer.watch.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by acer on 2018-04-03.
 *
 * 该类是用于获取与网络有关的一些信息
 * 包括（网络是否连接，网络类型等）
 *
 * 后续可往里添加更多的关于网络的方法，比如：判WIFI是否可用，获取网络信息
 * */

public class AboutNetWorkUtil {
    /*
    * 判断网络是否连接
    * */
    public static boolean isNetWorkConnected(Context context){
        if(context!=null){
            ConnectivityManager cm=(ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=cm.getActiveNetworkInfo();
            if(info!=null&&info.isAvailable()){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    /*
    * 判断网络类型
    * 0表示：网络无连接
    * 1表示：WIFI连接
    * 2表示：数据连接
    * */
    public static  int getNetWorkInfo(Context context){
        if(context!=null){
            ConnectivityManager cm=(ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=cm.getActiveNetworkInfo();
            if(info==null){
                return 0;
            }else if(info.getType()==ConnectivityManager.TYPE_WIFI){
                return 1;
            }else if(info.getType()==ConnectivityManager.TYPE_MOBILE){
                return 2;
            }
        }
        return 0;
    }
}
