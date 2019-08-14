package com.otopba.flick.ui.grid;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    public final int space;
    public final int columnCount;

    public SpacesItemDecoration(int space, int columnCount) {
        this.space = space;
        this.columnCount = columnCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        //TODO: хардкод !!!
        if (columnCount == 2) {
            if (position % 2 == 0) {
                outRect.right = space;
                outRect.left = 0;
            } else {
                outRect.left = space;
                outRect.right = 0;
            }

            if (position < 2) {
                outRect.top = 0;
            } else {
                outRect.top = space;
            }
            outRect.bottom = space;
        } else if (columnCount == 4) {
            outRect.right = 10;
            outRect.left = 10;

            if (position < 4) {
                outRect.top = 0;
            } else {
                outRect.top = space;
            }
            outRect.bottom = space;
        }
    }
}