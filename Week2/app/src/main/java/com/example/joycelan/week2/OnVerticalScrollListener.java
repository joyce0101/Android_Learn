package com.example.joycelan.week2;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public abstract class OnVerticalScrollListener extends RecyclerView.OnScrollListener {
    private int countItem;
    private int lastItem;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isScolled = false;//是否可以滑动
    protected abstract void onLoading(int countItem, int lastItem);
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
     /* 测试这三个参数的作用
        if (newState==SCROLL_STATE_IDLE){
            Log.d("test","SCROLL_STATE_IDLE,空闲");
        }
        else if (newState==SCROLL_STATE_DRAGGING){
            Log.d("test","SCROLL_STATE_DRAGGING,拖拽");
        }
        else if (newState==SCROLL_STATE_SETTLING){
            Log.d("test","SCROLL_STATE_SETTLING,固定");
        }
        else{
            Log.d("test","其它");
        }*/
        //拖拽或者惯性滑动时isScolled设置为true
        if (newState == SCROLL_STATE_IDLE) {
            isScolled = true;
        } else {
            isScolled = false;
        }

    }

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy){

    }
    //public void onScrolledToTop(){}

}
