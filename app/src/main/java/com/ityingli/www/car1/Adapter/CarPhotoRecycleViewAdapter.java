package com.ityingli.www.car1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ityingli.www.car1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class CarPhotoRecycleViewAdapter  extends RecyclerView.Adapter<CarPhotoRecycleViewAdapter.MyHolder>{

    //数据
    private List<String> data;
    //上下文
    Context mcontext;
    LayoutInflater  mLayoutInflater;
    //模拟流式item的高度
    List<Integer> item_height;

    public CarPhotoRecycleViewAdapter(List<String> data, Context context){
        this.data = data;
        this.mcontext = context;
        mLayoutInflater= LayoutInflater.from(mcontext);
        item_height = new ArrayList<>();
        for(int i = 0 ;i<30;i++){
            item_height.add((int)(200+Math.random()*100)); //100-400
        }
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_carphoto_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        // holder.iv.setImageResource(data.get(position));
        //Glide.with(mcontext).load(data.get(position)).into(holder.iv);
        Glide.with(mcontext).load(data.get(position)).crossFade().placeholder(R.drawable.load).error(R.drawable.load_error).into(holder.iv);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = item_height.get(position);
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyHolder extends  RecyclerView.ViewHolder{
        ImageView iv;
        public MyHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.carPhoto_iv);
        }
    }
}
