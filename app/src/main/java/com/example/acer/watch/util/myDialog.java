package com.example.acer.watch.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.acer.watch.R;

/**
 * Created by acer on 2018-03-28.
 * 该类用于实现长时间操作的等待动画
 *
 */
public class myDialog extends ProgressDialog{

    public myDialog(Context context) {
        super(context);
        setCancelable(true);
        setMessage("加载中....");
        setCanceledOnTouchOutside(false);
    }
}
