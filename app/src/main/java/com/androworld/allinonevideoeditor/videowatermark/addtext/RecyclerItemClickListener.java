package com.androworld.allinonevideoeditor.videowatermark.addtext;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener;

public class RecyclerItemClickListener implements OnItemTouchListener {
    GestureDetector a;
    private OnItemClickListener b;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public RecyclerItemClickListener(Context context, OnItemClickListener onItemClickListener) {
        this.b = onItemClickListener;
        this.a = new GestureDetector(context, new SimpleOnGestureListener() {
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (!(findChildViewUnder == null || this.b == null || !this.a.onTouchEvent(motionEvent))) {
            this.b.onItemClick(findChildViewUnder, recyclerView.getChildAdapterPosition(findChildViewUnder));
        }
        return false;
    }
}
