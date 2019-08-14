package com.otopba.flick.ui.grid;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

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
    }
}