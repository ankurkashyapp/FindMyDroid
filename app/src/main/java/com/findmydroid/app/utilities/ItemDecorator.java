package com.findmydroid.app.utilities;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ankur Kashyap on 10-03-2016.
 */
public class ItemDecorator extends RecyclerView.ItemDecoration {

    private int space;
    public ItemDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemCount = parent.getAdapter().getItemCount()-1;
        int itemPosition = parent.getChildAdapterPosition(view);

        if(itemPosition != itemCount-1 || itemPosition != itemCount)
            outRect.bottom = space;

        if(itemPosition%2==0) {
            outRect.left = space;
            outRect.right = space;
        }
        else {
            outRect.left = space;
        }
        if (itemPosition==0) {
            outRect.left = 0;
            outRect.right = 0;
        }

    }
}
