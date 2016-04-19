package com.findmydroid.app.utilities;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.findmydroid.app.R;

/**
 * Created by Ankur Kashyap on 19-04-2016.
 */
public class PlaceListDecorator extends RecyclerView.ItemDecoration {

    private int space;
    public PlaceListDecorator(int space) {
        this.space = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getId() == R.id.places_most_visited || parent.getId() == R.id.places_religious) {
            outRect.left = space;
            outRect.right = space;
        }
        else if (parent.getId() == R.id.places_miscellaneous) {
            outRect.bottom = space;
            outRect.left = space;
            outRect.right = space;
            outRect.top = space;
        }


    }
}
