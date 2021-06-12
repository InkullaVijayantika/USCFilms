package com.example.uscfilms;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecyclerDecorator extends RecyclerView.ItemDecoration {
    int top;
    int bottom;
    int left;
    int right;

    public RecyclerDecorator(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state){
        outRect.top = top;
        outRect.bottom = bottom;
        outRect.left = left;
        outRect.right = right;
    }
}
