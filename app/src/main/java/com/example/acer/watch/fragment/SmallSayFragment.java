package com.example.acer.watch.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acer.watch.AddSmallSayActivity;
import com.example.acer.watch.R;
import com.example.acer.watch.adapters.smallSayAdapter;
import com.example.acer.watch.javabean.smallSayBean;
import com.example.acer.watch.table.SmallSayTable;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by acer on 2018-01-29.
 * 滑动菜单中小说界面的碎片
 */

public class SmallSayFragment extends Fragment {
    /*
    * 定义一个数组，该数组是用来与RecyclerView绑定的
    * */
    private List<smallSayBean> smallSayBeanList=new ArrayList<>();
    /*
    * 搜素框和搜索按钮和添加小说按钮
    * */
    private EditText searchSmallSay_editText;
    private Button searchSmallSay_button;
    private FloatingActionButton addSmallSay_floatButton;
//    清空小说表按钮
    private Button deleteAll_button;
    /*
    * 声明RecyclerView的适配器
    * */
    private smallSayAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View smallSayView=inflater.inflate(R.layout.smallsay_layout,container,false);
//        实例化搜索控件和添加小说按钮
        searchSmallSay_editText=(EditText)smallSayView.findViewById(R.id.searchSmallSay_editText);
        searchSmallSay_button=(Button)smallSayView.findViewById(R.id.searchSmallSay_button);
        addSmallSay_floatButton=(FloatingActionButton)smallSayView.findViewById(R.id.addSmallSay_floatButton);
        deleteAll_button=(Button)smallSayView.findViewById(R.id.deleteAll_button);
//        为清空表按钮添加动作事件
        deleteAll_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(SmallSayTable.class);
                List<SmallSayTable> smallSayTables=DataSupport.findAll(SmallSayTable.class);
                if(smallSayTables.size()==0){
                    Toast.makeText(getContext(),"成功清空小说表",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        为搜索框添加监听事件，实时监听搜索框内文本变化情况
        searchSmallSay_editText.addTextChangedListener(new textChangedListener());
        searchSmallSay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"现在可以使用按钮", LENGTH_SHORT).show();
            }
        });
//        为添加小说按钮添加动作事件
        addSmallSay_floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                首先获得数据库中的数据总数（为了用小说编号给小说命名，实际添加小说时不需要这一步）
//                List<SmallSayTable> smallSayTables=DataSupport.findAll(SmallSayTable.class);
//                int count=smallSayTables.size()+1;
//                往数据库中添加数据
//                SmallSayTable smallSayTable=new SmallSayTable();
//                smallSayTable.setSmallSayId_table(count);
//                smallSayTable.setSmallSayName(""+count);
//                smallSayTable.setSmallSayImage(R.drawable.sea);
//                smallSayTable.save();
//                freshRecycler();
//                smallSayView.setVerticalScrollbarPosition(smallSayTables.size());
//                刷新RecyclerView，使新添加的小说显示出来
//                SmallSayTable smallSayTable1=DataSupport.findLast(SmallSayTable.class);
//                String name1=smallSayTable1.getSmallSayName();
//                int image1=smallSayTable1.getSmallSayImage();
//                int num=smallSayTable1.getSmallSayId_table();
//                smallSayBeanList.add(0,new smallSayBean(name1,image1));
//                adapter.notifyItemInserted(0);
//                adapter.notifyItemRangeInserted(0,smallSayBeanList.size());
//                调用选择文件的方法
//                chooseAddMethod();
            }
        });
         /*
        *进入软件时，首先加载CardView
        * */
        initSmallSay();
        RecyclerView recyclerView=(RecyclerView)smallSayView.findViewById(R.id.smallSay_RecyclerView);
//        下面的方法用于确定RecyclerView显示的列数~~~~~~~~~~~~(Context,列数)
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),3);
//        将RecyclerView设置为倒序排列（新增的小说会在第一条显示）
//        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
//        给适配器加入数组
        adapter=new smallSayAdapter(smallSayBeanList);
        recyclerView.setAdapter(adapter);
//        添加点击事件
        adapter.setMyItemOnClickListener(new smallSayAdapter.MyItemOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                    smallSayBean mSmallSayBean=smallSayBeanList.get(position);

                    Toast.makeText(getContext(),"你点击了"+mSmallSayBean.getSmallSayName(), LENGTH_SHORT).show();
            }
        });
//        添加长按事件
        adapter.setMyItemOnLongClickListener(new smallSayAdapter.MyItemOnLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {

                    Snackbar.make(view,"删除该小说?",Snackbar.LENGTH_SHORT)
                            .setAction("确定删除", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*
                                    * 逻辑有问题
                                    *
                                    * */
                                    DataSupport.deleteAll(SmallSayTable.class,"smallSayId_table == ?", String.valueOf(position));
//                                    刷新RecycierView，减少一个小说
                                    freshRecycler();
                                    smallSayBeanList.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    adapter.notifyItemRangeRemoved(position,smallSayBeanList.size()-position);
                                }
                            }).show();
            }
        });
        /*
        * 返回给活动一个加载好的碎片，所以return语句永远在最后一句
        * */
        return smallSayView;
    }
    /*
      * 定义一个方法，该方法用于初始化RecyclerView
      * */
    private void initSmallSay(){
//        创建数据库，如果数据库已存在则不执行该语句
        LitePal.getDatabase();
        /*
        * 初始化数据
        * */
            freshRecycler();
    }
    /*
    * 刷新RecyclerView（当新添加/删除一个小说时，就调用该方法，可自动刷新）
    * */
    private void freshRecycler(){
//          首先清除所有RecyclerView，防止RecyclerView出现多倍加载
        smallSayBeanList.clear();
        List<SmallSayTable> smallSayTables= DataSupport.findAll(SmallSayTable.class);
        if(smallSayTables.size()!=0){
//            获取小说的名字和图片
            String name=null;
            int image=0;
//            设置小说的id
            int smallSayID=0;
            for(SmallSayTable smallSayTable: smallSayTables){
//                让ID自增，从而可以使ID与position相同
                smallSayID++;
//                从数据库获取小说的名字和图片
                name=smallSayTable.getSmallSayName();
                image=smallSayTable.getSmallSayImage();
//                将小说的ID更新为与position相同（防止删除或更新时id与position不同出现闪退）
                smallSayTable.setSmallSayId_table(smallSayID);
                smallSayTable.updateAll();
//                加载RecyclerView的数据
                smallSayBeanList.add(new smallSayBean(name,image));
            }
        }
    }
    /*
    * 当用户点击FloatingActionButton时执行该方法
    * 该方法用于提示用户是进行本地添加还是网络添加
    * */
    private void chooseAddMethod(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
        dialog.setTitle("选择添加方式");
        dialog.setMessage("请选择一种添加方式(点击空白处退出选择)");
        dialog.setPositiveButton("本地添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                * 执行选择本地文件的方法
                * */
                Intent intent=new Intent(getContext(), AddSmallSayActivity.class);
                startActivity(intent);
            }
        });
        dialog.setNeutralButton("网络添加", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                * 执行网络添加的方法
                * */
                Toast.makeText(getContext(),"你选择了网络添加",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    /*
    * 该内部类是监听搜索框内文字变化的
    * 无文字则搜索按钮不可用
    * */
    class textChangedListener implements TextWatcher {
//
        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp=s;
        }
        @Override
        public void afterTextChanged(Editable s) {
            if(temp.length()!=0&&temp.toString()!=null){
                searchSmallSay_button.setEnabled(true);
                searchSmallSay_button.setBackground(getResources().getDrawable(R.drawable.shape_cansearch_button));
            }else{
                searchSmallSay_button.setEnabled(false);
                searchSmallSay_button.setBackground(getResources().getDrawable(R.drawable.shape_searchbutton));
            }
        }
    }
}
