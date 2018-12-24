package com.example.joycelan.week2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecycleAdapter extends RecyclerView.Adapter <RecycleAdapter.ViewHodler>{
    private ArrayList<String> string;
    private Context context;//设置上下文
    //定义listener接口成员变量
    private OnItemClickListener listener;
    //定义点击位置
    private int position;

    public RecycleAdapter(Context context,ArrayList<String> string){
        this.context = context;
        this.string = string;
    }
    //定义listener接口
    public interface OnItemClickListener{
        void onItemLongClick(View itemView, int position);
    }
    //定义set方法供外部使用listener接口
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item, viewGroup, false);
        //View itemView = View.inflate( context,R.layout.item, );
        return new ViewHodler( itemView );
    }

    //将数据绑定
    @Override
    public void onBindViewHolder(@NonNull final ViewHodler viewHodler, int i) {
        String data = string.get( i );
        viewHodler.textView.setText( data );
        if(listener!=null){
            viewHodler.itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = viewHodler.getLayoutPosition();
                    position = pos;
                    listener.onItemLongClick(viewHodler.itemView,pos);
                    return false;
                }
            } );
        }

    }

    @Override
    public int getItemCount() {
        return string.size();

    }
    //添加item
    public void addItem(int position){
        string.add( position,"数据"+position );
        notifyItemInserted( position );
        notifyItemRangeChanged( position,string.size()-position );//更新数据
    }
    //删除item
    public void delItem(int position){

        notifyItemRemoved( position );
        string.remove( position );
    }


    class ViewHodler extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHodler(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById( R.id.text );
        }
    }

    public int getPosition() {
        return position;
    }
    public ArrayList<String> getData(){
        return string;
    }
    public int getLength(){
        return string.size();
    }
}
