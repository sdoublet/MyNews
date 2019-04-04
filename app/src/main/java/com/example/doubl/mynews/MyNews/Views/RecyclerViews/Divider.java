package com.example.doubl.mynews.MyNews.Views.RecyclerViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.doubl.mynews.R;

public class Divider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mOrientation;

    public Divider(Context context, int orientation) {
        this.mDivider = ContextCompat.getDrawable(context, R.drawable.divider);
        if (orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("This item can be used only with a RecyclerView that uses a LinearLayoutManager with a vertical orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c, parent, state);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left, right, top, bottom;
        left=300;
        right= parent.getWidth();
        int count = parent.getChildCount();
        for (int i=0; i<count; i++){
            View current = parent.getChildAt(i);
            top = current.getTop();
            bottom = top+ mDivider.getIntrinsicHeight();
mDivider.setBounds(left, top, right, bottom);
mDivider.draw(c);

        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
