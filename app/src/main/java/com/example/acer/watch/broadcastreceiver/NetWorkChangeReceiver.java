package com.example.acer.watch.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by acer on 2018-04-03.
 *
 * 该广播是用来实时监听网络的变化的
 *
 */

public class NetWorkChangeReceiver extends BroadcastReceiver {
    private Context context;
    public NetWorkChangeReceiver(Context context){
        this.context=context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        context=this.context;
        ConnectivityManager cm=(ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=cm.getActiveNetworkInfo();
        if(!info.isAvailable()){
            Toast.makeText(context, "竟然没联网！！！", Toast.LENGTH_SHORT).show();
        }
    }
}
