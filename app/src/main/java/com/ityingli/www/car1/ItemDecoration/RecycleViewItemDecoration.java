package com.ityingli.www.car1.ItemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/5/10.
 */

public class RecycleViewItemDecoration extends RecyclerView.ItemDecoration{
     int itemSpace;
     public  RecycleViewItemDecoration(int space){
        this.itemSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        outRect.left = itemSpace;
        outRect.right = itemSpace;
        outRect.top = itemSpace;
        outRect.bottom = itemSpace;
        if(parent.getChildAdapterPosition(view)==0){
            outRect.top =   0;
        }
    }
}
