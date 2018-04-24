package com.example.acer.watch.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.watch.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2018-03-08.
 */

public class addSmallSayByHandFragment extends Fragment {
    /*
    * 声明各个控件
    * */
    private ListView listView;
    private ImageView fileImages_imagesView;
    private TextView fileName_textView;
    private TextView nowPath_textView;
    private FloatingActionButton backFatherPather_floatingButton;
//   该文件变量用于记录当前界面文件的父文件夹
    File currentParent;
//    该数组用于记录当前路径下的所有文件
    File[] currentFiles;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View byHandView=inflater.inflate(R.layout.addsmallsaybyhand_fragmentlayout,container,false);
//        实例化各个控件
        listView=(ListView)byHandView.findViewById(R.id.showFiles_listView);
        fileImages_imagesView=(ImageView)byHandView.findViewById(R.id.fileImages_imageView);
        fileName_textView=(TextView)byHandView.findViewById(R.id.fileName_tetxView);
        nowPath_textView=(TextView)byHandView.findViewById(R.id.nowPath_textView);
        backFatherPather_floatingButton=(FloatingActionButton)byHandView.findViewById(R.id.backFatherPath_floatingButton);
//        获取文件的根目录
//        该方法不一定能准确的获取到根目录，因为手机不同，对存储器的定义也不同
        File rootPath= Environment.getExternalStorageDirectory();
//        判断有没有获取到根目录(如果获取到了根目录就执行if语句)
        if(rootPath.exists()){
            currentParent=rootPath;
//            填充数组
            currentFiles=currentParent.listFiles();
//            将得到的文件和文件夹装载进ListView
            initListView(currentFiles);
        }else{
            nowPath_textView.setText("无法读取到SD卡的内容~~");
        }
//        为ListView添加点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                如果点击了文件则执行相应的操作
                if(currentFiles[position].isFile()){
                    Toast.makeText(getContext(), "你点击了文件", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
//                如果点击的是文件夹则执行相关操作
                File[] tmp=currentFiles[position].listFiles();
//                如果点击的文件夹获取不到内容，则提醒用户
                if(tmp==null||tmp.length==0){
                   nowPath_textView.setText("当前文件夹内未发现文件");
                }else{
//                    获取用户单击的列表项对应的文件夹，把它设为当前的父文件夹
                    currentParent=currentFiles[position];
//                    将该文件夹内所有的内容放到文件数组里
                    currentFiles=tmp;
//                    将文件数组放到更新方法里
                    initListView(currentFiles);
                }
            }
        });
//        为返回上一级目录的按钮注册点击事件
        backFatherPather_floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
//                    如果当前路径不是根目录
                    if(!currentParent.getCanonicalPath().equals(Environment.getExternalStorageDirectory())){
//                        获取上一级目录
                        currentParent=currentParent.getParentFile();
//                        列出上一级目录的所有文件
                        currentFiles=currentParent.listFiles();
//                        更新ListView
                        initListView(currentFiles);
                    }else{
                        Toast.makeText(getActivity(), "当前已经是根目录，无法返回上一级目录。", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        return byHandView;
    }
    /*
    * 该方法用于把文件和文件夹用ListView显示出来
    * */
    private void initListView(File[] files){
//        定义一个List数组，集合元素是Map类型（map类型有键值对应，读取数据方便）
        List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
//        开始对ListView装载数据
        for(int i=0;i<files.length;i++){
            Map<String,Object> nameAndimage=new HashMap<String,Object>();
//          如果当前files[i]是文件夹，就使用文件夹图标，否则使用文件图标
            if(files[i].isDirectory()){
//                键值对应，取数据时方便
                nameAndimage.put("fileImage",R.drawable.folder);
            }else{
                nameAndimage.put("fileImage",R.drawable.file);
            }
            nameAndimage.put("fileName",files[i].getName());
//            把数据装载进ListView
            listItems.add(nameAndimage);
        }
//        配置ListView的adapter
        SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),listItems,
                R.layout.addsmallsay_item,new String[]{"fileImage","fileName"},
                new int[]{R.id.fileImages_imageView,R.id.fileName_tetxView});
//        设置adapter
        listView.setAdapter(simpleAdapter);
        try{
            if(files.length!=0){
//                显示的是文件的“规范路径名”（也是绝对路径名，只不过是规范化之后的路径）
                nowPath_textView.setText("当前路径："+currentParent.getCanonicalPath());
            }else{
                nowPath_textView.setText("当前路径下未发现文件~~~");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
