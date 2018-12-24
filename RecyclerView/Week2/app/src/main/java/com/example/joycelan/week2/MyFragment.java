package com.example.joycelan.week2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecycleAdapter mRecyclerAdapter;
    private SwipeRefreshLayout mRefrashLayout;
    private View view;

    //存储item中的数据
    private ArrayList<String> data = new ArrayList<>();
    //自定义recyclerView的适配器
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.frag_recycle,container,false );
        initRecycleView();
        initData();
        return view;
    }

    private void initData(){
        for(int i=0;i<10;i++){
            String item = new String( "数据"+i+'a' );
            data.add( item );
        }

    }
    private void initRecycleView(){
        //获取RecycleView
        mRecyclerView = (RecyclerView) view.findViewById( R.id.my_recycler_view );
        //创建adapter
        mRecyclerAdapter = new RecycleAdapter(getActivity(),data);
        //给RecyclerView设置Adapter
        mRecyclerView.setAdapter( mRecyclerAdapter );
        //创建一个reflashLayout对象
        mRefrashLayout = (SwipeRefreshLayout) view.findViewById( R.id.refresh_layout );
        //监听下拉刷新
        mRefrashLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.remove( 0 );
                mRecyclerAdapter.notifyDataSetChanged();//同步数据
                mRefrashLayout.setRefreshing( false );
            }
        } );
        //设置布局
        mRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity(),LinearLayoutManager.VERTICAL,false ) );
        //设置分割线
        mRecyclerView.addItemDecoration( new DividerItemDecoration( getActivity(),DividerItemDecoration.VERTICAL ) );
        mRecyclerAdapter.setOnItemClickListener( new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position) {
                showDialog();
            }
        } );


        mRecyclerView.addOnScrollListener( new OnVerticalScrollListener(){
           /* @Override
            public void onScrolledToTop(){
                //加一个判断，最后一个不允许删除，同时加一个延时
                mRecyclerAdapter.delItem( mRecyclerAdapter.getItemCount()-1 );
            }*/
            @Override
            public void onLoading(int countItem,int lastItem){
               new Handler().postDelayed( new Runnable() {
                   @Override
                   public void run() {
                       data.add( data.size(),"下拉加载" );
                       mRecyclerAdapter.notifyDataSetChanged();
                   }
               },1000 );
            }
        } );

    }
    public void showDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle( "增加item" );
        dialog.setMessage( "选择增加的位置" );
        dialog.setPositiveButton( "front", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = mRecyclerAdapter.getPosition();
                //mRecyclerAdapter.addItem( pos );
                data.add( pos,"hello,add from front" );
                mRecyclerAdapter.notifyDataSetChanged();
            }
        } );
        dialog.setNegativeButton( "back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pos = mRecyclerAdapter.getPosition();
                //mRecyclerAdapter.addItem( pos+1 );
                data.add( pos+1,"hello,add from back" );
                mRecyclerAdapter.notifyDataSetChanged();
            }
        } );
        dialog.show();

    }

}
