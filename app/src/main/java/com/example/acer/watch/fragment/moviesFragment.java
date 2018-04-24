package com.example.acer.watch.fragment;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.watch.R;
import com.example.acer.watch.adapters.VideoUrlInDialogAdapter;
import com.example.acer.watch.adapters.VideoUrlListAdapter;
import com.example.acer.watch.broadcastreceiver.NetWorkChangeReceiver;
import com.example.acer.watch.javabean.VideoNameAndURLBean;
import com.example.acer.watch.javabean.VideoURLBean;
import com.example.acer.watch.javabean.VideoUrlInDialogBean;
import com.example.acer.watch.util.AboutNetWorkUtil;
import com.example.acer.watch.util.myDialog;
import com.example.acer.watch.javabean.searchVideo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by acer on 2018-01-29.
 * 滑动菜单中电影界面的碎片
 */

public class moviesFragment extends Fragment {
//    网络信息类的实例
    private AboutNetWorkUtil netWorkUtil=new AboutNetWorkUtil();
    //    ListView的适配器
    VideoUrlListAdapter adapter;
    //    该集合用于存放视频下载连接
    private List<VideoNameAndURLBean> videoURL=new ArrayList<>();
    //    该集合用于存放VideoUrlist的数据，包含：videoUrl和videoPic
    private List<VideoURLBean> videoURLBeanList = new ArrayList<>();
//    该集合用于存放VideoUrlListInDialog的数据,包含：videoUrl和videoPic
    private List<VideoUrlInDialogBean> videoUrlInDialogBeanList=new ArrayList<>();
    //    搜索按钮
    private Button searchVideo_button;
    //    搜索框
    private EditText searchVideo_editText;
    //    提示文本
    private TextView tiShiFindVideo_textView;
    //    ListView
    private ListView videoList_listView;
    private ListView videoListInDialog_listView;
    //    用于传递数据
    private android.os.Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View moviesView = inflater.inflate(R.layout.movies_layout, container, false);

//          实例化控件
        searchVideo_button = (Button) moviesView.findViewById(R.id.searchVideo_button);
        searchVideo_editText = (EditText) moviesView.findViewById(R.id.searchVideo_editText);
        tiShiFindVideo_textView = (TextView) moviesView.findViewById(R.id.tiShiFindVideo_textView);
        videoList_listView = (ListView) moviesView.findViewById(R.id.videoUrlList_listView);
//        添加相应事件
        searchVideo_editText.addTextChangedListener(new textChangedListener());
        videoList_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                answerVideoUrlListClick(position);
            }
        });
        searchVideo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!netWorkUtil.isNetWorkConnected(getContext())){
                    Toast.makeText(getContext(), "大哥，你没联网。", Toast.LENGTH_SHORT).show();
                }else{
                    searchVideo_editText.clearFocus();
                    hideKeyBoard();
                    final myDialog dialog = new myDialog(getContext());
                    dialog.show();
//            清空视频名字和链接的集合，防止出现多重加载
                    if(videoURL!=null){
                        videoURL.clear();
                    }
//                开始加载ListView
                    initVideoList(searchVideo_editText.getText().toString());
//                使用Handler来接收返回的数据
                    handler = new android.os.Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if (msg.what == 111) {
//                           获取URL集合
                                ArrayList urlList = msg.getData().getParcelableArrayList("url");
                                List<VideoNameAndURLBean> list = (List<VideoNameAndURLBean>) urlList.get(0);
                                if (list == null|| videoURL.size()==0) {
                                    tiShiFindVideo_textView.setText("未找到资源，请换个关键词再试一次。");
                                    dialog.cancel();
                                } else{
                                    tiShiFindVideo_textView.setText("找到" + list.size() + "条相关资源，点击查看详情。");
                                    for (VideoNameAndURLBean nameBean : list) {
                                        List<String> lists=nameBean.getVideoNameList();
                                        for(String videoNames : lists){
                                            videoURLBeanList.add(new VideoURLBean(videoNames,R.drawable.videopic));
                                        }
                                    }
                                    adapter = new VideoUrlListAdapter(getContext(),
                                            R.layout.videourlitem_layout, videoURLBeanList);
                                    videoList_listView.setAdapter(adapter);
                                    dialog.cancel();
                                }
                            }
                        }
                    };
                }
                }
        });
//        返回语句永远在最后一句
        return moviesView;
    }

    /*
    * 该内部类是监听搜索框内文字变化的
    * 无文字则搜索按钮不可用
    * */
    class textChangedListener implements TextWatcher {
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length() >= 2 && temp.toString() != null) {
                searchVideo_button.setEnabled(true);
                searchVideo_button.setBackground(getResources().getDrawable(R.drawable.shape_cansearch_button));
            } else {
                searchVideo_button.setEnabled(false);
                searchVideo_button.setBackground(getResources().getDrawable(R.drawable.shape_searchbutton));
            }
        }
    }

    /*
    * 组装VideoUrlList
    * */
    private void initVideoList(final String videoName) {
//        首先清空videoList
        videoURLBeanList.clear();
        final searchVideo searche = new searchVideo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                ArrayList urlList = new ArrayList();
//              执行搜索
                videoURL = searche.doAllMethod(videoName);
                urlList.add(videoURL);
                bundle.putStringArrayList("url", urlList);
                Message message = new Message();
                message.what = 111;
                message.setData(bundle);
//                发送URl集合
                handler.sendMessage(message);
            }
        }).start();
    }
    /*
    * 组装AlertDialog里的VideoUrlList
    * 参数是：搜索界面中的视频列表的连接
    * */
    private void initVideoUrlListInDialog(int position){
        videoUrlInDialogBeanList.clear();
//        获取点击条目所对应的视频连接
        VideoNameAndURLBean beanUrl=videoURL.get(position);
            List<String> tureUrl=beanUrl.getVideoUrlList();
            for(String str : tureUrl){
                VideoUrlInDialogBean bean=new VideoUrlInDialogBean(str,R.drawable.videopic);
                videoUrlInDialogBeanList.add(bean);
            }
    }
//    隐藏软键盘的方法
    private void hideKeyBoard(){
//        获取最顶层的View
        View view=getActivity().getWindow().getDecorView();
        if(view!=null){
            InputMethodManager im=(InputMethodManager)getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
//    videoUrlLIst点击时执行该方法
    private void answerVideoUrlListClick(int position){
        VideoURLBean bean = videoURLBeanList.get(position);
//                重新获取ListView，防止出现子控件与父控件不匹配
        View listInDialogView=LayoutInflater.from(getContext()).inflate(R.layout.listviewindialog_layout,null);
        videoListInDialog_listView=(ListView)listInDialogView.findViewById(R.id.videoUrlListInDialog_listView);
//                实现自动复制
//                ClipboardManager copy = (ClipboardManager) getContext().getSystemService(
//                        Context.CLIPBOARD_SERVICE);
//                设置复制到剪贴板的内容
//                copy.setText(bean.getVideoUrl());
//                初始化ListView
        initVideoUrlListInDialog(position);
//                添加适配器
        VideoUrlInDialogAdapter adapterInDialog=new
                VideoUrlInDialogAdapter(getContext(),
                R.layout.copyvideourl_item,videoUrlInDialogBeanList);
        videoListInDialog_listView.setAdapter(adapterInDialog);
        final AlertDialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                getContext());
        builder.setTitle("提示").
                setMessage("选择一个链接点击即可复制,打开迅雷即可下载")
                .setView(listInDialogView);
        dialog=builder.create();
        dialog.show();
//        ListView的点击事件
        videoListInDialog_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.cancel();
//                实现复制文本
                ClipboardManager cm=(ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                VideoUrlInDialogBean inBean=videoUrlInDialogBeanList.get(position);
                cm.setText(inBean.getVideoUr());
                Toast.makeText(getContext(), "链接已复制到剪贴板，打开迅雷即可自动下载。", Toast.LENGTH_LONG).show();
            }
        });
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        退出时取消广播的注册
//        getActivity().unregisterReceiver(netWorkChangeReceiver);
//    }
}