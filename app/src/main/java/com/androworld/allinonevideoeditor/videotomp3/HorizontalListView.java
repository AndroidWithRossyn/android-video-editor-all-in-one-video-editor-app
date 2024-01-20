package com.androworld.allinonevideoeditor.videotomp3;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;

import androidx.core.view.ViewCompat;
import androidx.core.widget.EdgeEffectCompat;

import com.androworld.allinonevideoeditor.Styleable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SuppressLint({"ResourceType"})
public class HorizontalListView extends AdapterView<ListAdapter> {
    private View A = null;
    private DataSetObserver a = new DataSetObserver() {
        public void onChanged() {
            HorizontalListView.this.e = true;
            HorizontalListView.this.n = false;
            HorizontalListView.this.f();
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }

        public void onInvalidated() {
            HorizontalListView.this.n = false;
            HorizontalListView.this.f();
            HorizontalListView.this.c();
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }
    };

    public boolean bs = false;
    private OnScrollStateChangedListener.ScrollState cs = OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE;
    private int d;

    public boolean e = false;
    private Runnable f = new Runnable() {
        public void run() {
            HorizontalListView.this.requestLayout();
        }
    };
    private int g;
    private Drawable h = null;
    private int i = 0;
    private EdgeEffectCompat j;
    private EdgeEffectCompat k;

    public GestureDetector l;
    private final a m = new a();
    protected ListAdapter mAdapter;
    protected int mCurrentX;
    protected Scroller mFlingTracker = new Scroller(getContext());
    protected int mNextX;

    public boolean n = false;
    private int o;
    private boolean p = false;

    public int q;
    private int r = Integer.MAX_VALUE;

    public OnClickListener s;
    private OnScrollStateChangedListener t = null;
    private Rect u = new Rect();
    private List<Queue<View>> v = new ArrayList();
    private Integer w = null;
    private int x;
    private RunningOutOfDataListener y = null;
    private int z = 0;

    public interface OnScrollStateChangedListener {

        public enum ScrollState {
            SCROLL_STATE_IDLE,
            SCROLL_STATE_TOUCH_SCROLL,
            SCROLL_STATE_FLING
        }

        void onScrollStateChanged(ScrollState scrollState);
    }

    public interface RunningOutOfDataListener {
        void onRunningOutOfData();
    }

    private class a extends SimpleOnGestureListener {
        private a() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            return HorizontalListView.this.onDown(motionEvent);
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return HorizontalListView.this.onFling(motionEvent, motionEvent2, f, f2);
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            HorizontalListView.this.a(Boolean.valueOf(true));
            HorizontalListView.this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_TOUCH_SCROLL);
            HorizontalListView.this.f();
            HorizontalListView.this.mNextX += (int) f;
            HorizontalListView.this.i(Math.round(f));
            HorizontalListView.this.requestLayout();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            HorizontalListView.this.f();
            OnItemClickListener onItemClickListener = HorizontalListView.this.getOnItemClickListener();
            int a2 = HorizontalListView.this.c((int) motionEvent.getX(), (int) motionEvent.getY());
            if (a2 >= 0 && !HorizontalListView.this.bs) {
                try {
                    View childAt = HorizontalListView.this.getChildAt(a2);
                    int d = HorizontalListView.this.q + a2;
                    if (onItemClickListener != null) {
                        try {
                            onItemClickListener.onItemClick(HorizontalListView.this, childAt, d, HorizontalListView.this.mAdapter.getItemId(d));
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (HorizontalListView.this.s != null && !HorizontalListView.this.bs) {
                HorizontalListView.this.s.onClick(HorizontalListView.this);
            }
            return false;
        }

        public void onLongPress(MotionEvent motionEvent) {
            HorizontalListView.this.f();
            int a2 = HorizontalListView.this.c((int) motionEvent.getX(), (int) motionEvent.getY());
            if (a2 >= 0 && !HorizontalListView.this.bs) {
                try {
                    View childAt = HorizontalListView.this.getChildAt(a2);
                    OnItemLongClickListener onItemLongClickListener = HorizontalListView.this.getOnItemLongClickListener();
                    if (onItemLongClickListener != null) {
                        try {
                            int d = HorizontalListView.this.q + a2;
                            if (onItemLongClickListener.onItemLongClick(HorizontalListView.this, childAt, d, HorizontalListView.this.mAdapter.getItemId(d))) {
                                try {
                                    HorizontalListView.this.performHapticFeedback(0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    @TargetApi(11)
    private static final class b {
        static {
            if (VERSION.SDK_INT < 11) {
                throw new RuntimeException("Should not get to HoneycombPlus class unless sdk is >= 11!");
            }
        }

        public static void a(Scroller scroller, float f) {
            if (scroller != null) {
                scroller.setFriction(f);
            }
        }
    }

    @TargetApi(14)
    private static final class c {
        static {
            if (VERSION.SDK_INT < 14) {
                throw new RuntimeException("Should not get to IceCreamSandwichPlus class unless sdk is >= 14!");
            }
        }

        public static float a(Scroller scroller) {
            return scroller.getCurrVelocity();
        }
    }


    public void dispatchSetPressed(boolean z2) {
    }

    public HorizontalListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.j = new EdgeEffectCompat(context);
        this.k = new EdgeEffectCompat(context);
        this.l = new GestureDetector(context, this.m);
        a();
        b();
        a(context, attributeSet);
        setWillNotDraw(false);
        if (VERSION.SDK_INT >= 11) {
            b.a(this.mFlingTracker, 0.009f);
        }
    }

    private void a() {
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return HorizontalListView.this.l.onTouchEvent(motionEvent);
            }
        });
    }


    public void a(Boolean bool) {
        if (this.p != bool.booleanValue()) {
            for (View view = this; view.getParent() instanceof View; view = (View) view.getParent()) {
                if ((view.getParent() instanceof ListView) || (view.getParent() instanceof ScrollView)) {
                    view.getParent().requestDisallowInterceptTouchEvent(bool.booleanValue());
                    this.p = bool.booleanValue();
                    return;
                }
            }
        }
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            try {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Styleable.HorizontalListView);
                Drawable drawable = obtainStyledAttributes.getDrawable(1);
                if (drawable != null) {
                    setDivider(drawable);
                }
                int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(3, 0);
                if (dimensionPixelSize != 0) {
                    setDividerWidth(dimensionPixelSize);
                }
                obtainStyledAttributes.recycle();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("BUNDLE_ID_PARENT_STATE", super.onSaveInstanceState());
        bundle.putInt("BUNDLE_ID_CURRENT_X", this.mCurrentX);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.w = Integer.valueOf(bundle.getInt("BUNDLE_ID_CURRENT_X"));
            super.onRestoreInstanceState(bundle.getParcelable("BUNDLE_ID_PARENT_STATE"));
        }
    }

    public void setDivider(Drawable drawable) {
        this.h = drawable;
        if (drawable != null) {
            setDividerWidth(drawable.getIntrinsicWidth());
        } else {
            setDividerWidth(0);
        }
    }

    public void setDividerWidth(int i2) {
        this.i = i2;
        requestLayout();
        invalidate();
    }

    private void b() {
        this.q = -1;
        this.x = -1;
        this.g = 0;
        this.mCurrentX = 0;
        this.mNextX = 0;
        this.r = Integer.MAX_VALUE;
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
    }


    public void c() {
        b();
        removeAllViewsInLayout();
        requestLayout();
    }

    public void setSelection(int i2) {
        this.d = i2;
    }

    public View getSelectedView() {
        return g(this.d);
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.a);
        }
        if (listAdapter != null) {
            this.n = false;
            this.mAdapter = listAdapter;
            this.mAdapter.registerDataSetObserver(this.a);
        }
        a(this.mAdapter.getViewTypeCount());
        c();
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    private void a(int i2) {
        this.v.clear();
        for (int i3 = 0; i3 < i2; i3++) {
            this.v.add(new LinkedList());
        }
    }

    private View b(int i2) {
        int itemViewType = this.mAdapter.getItemViewType(i2);
        if (c(itemViewType)) {
            return (View) ((Queue) this.v.get(itemViewType)).poll();
        }
        return null;
    }

    private void a(int i2, View view) {
        int itemViewType = this.mAdapter.getItemViewType(i2);
        if (c(itemViewType)) {
            ((Queue) this.v.get(itemViewType)).offer(view);
        }
    }

    private boolean c(int i2) {
        return i2 < this.v.size();
    }

    private void a(View view, int i2) {
        addViewInLayout(view, i2, b(view), true);
        a(view);
    }

    private void a(View view) {
        int i2;
        LayoutParams b2 = b(view);
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.o, getPaddingTop() + getPaddingBottom(), b2.height);
        if (b2.width > 0) {
            i2 = MeasureSpec.makeMeasureSpec(b2.width, 1073741824);
        } else {
            i2 = MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(i2, childMeasureSpec);
    }

    private LayoutParams b(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        return layoutParams == null ? new LayoutParams(-2, -1) : layoutParams;
    }


    @SuppressLint({"WrongCall"})
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.mAdapter != null) {
            invalidate();
            if (this.e) {
                int i6 = this.mCurrentX;
                b();
                removeAllViewsInLayout();
                this.mNextX = i6;
                this.e = false;
            }
            if (this.w != null) {
                this.mNextX = this.w.intValue();
                this.w = null;
            }
            if (this.mFlingTracker.computeScrollOffset()) {
                this.mNextX = this.mFlingTracker.getCurrX();
            }
            if (this.mNextX < 0) {
                this.mNextX = 0;
                if (this.j.isFinished()) {
                    this.j.onAbsorb((int) d());
                }
                this.mFlingTracker.forceFinished(true);
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            } else if (this.mNextX > this.r) {
                this.mNextX = this.r;
                if (this.k.isFinished()) {
                    this.k.onAbsorb((int) d());
                }
                this.mFlingTracker.forceFinished(true);
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
            int i7 = this.mCurrentX - this.mNextX;
            e(i7);
            d(i7);
            f(i7);
            this.mCurrentX = this.mNextX;
            if (e()) {
                onLayout(z2, i2, i3, i4, i5);
            } else if (!this.mFlingTracker.isFinished()) {
                ViewCompat.postOnAnimation(this, this.f);
            } else if (this.cs == OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING) {
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
        }
    }


    public float getLeftFadingEdgeStrength() {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (this.mCurrentX == 0) {
            return 0.0f;
        }
        if (this.mCurrentX < horizontalFadingEdgeLength) {
            return ((float) this.mCurrentX) / ((float) horizontalFadingEdgeLength);
        }
        return 1.0f;
    }


    public float getRightFadingEdgeStrength() {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (this.mCurrentX == this.r) {
            return 0.0f;
        }
        if (this.r - this.mCurrentX < horizontalFadingEdgeLength) {
            return ((float) (this.r - this.mCurrentX)) / ((float) horizontalFadingEdgeLength);
        }
        return 1.0f;
    }

    private float d() {
        if (VERSION.SDK_INT >= 14) {
            return c.a(this.mFlingTracker);
        }
        return 30.0f;
    }


    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        this.o = i3;
    }

    private boolean e() {
        if (!h(this.x)) {
            return false;
        }
        View rightmostChild = getRightmostChild();
        if (rightmostChild == null) {
            return false;
        }
        int i2 = this.r;
        this.r = (this.mCurrentX + (rightmostChild.getRight() - getPaddingLeft())) - getRenderWidth();
        if (this.r < 0) {
            this.r = 0;
        }
        if (this.r != i2) {
            return true;
        }
        return false;
    }

    private void d(int i2) {
        View rightmostChild = getRightmostChild();
        int i3 = 0;
        a(rightmostChild != null ? rightmostChild.getRight() : 0, i2);
        View leftmostChild = getLeftmostChild();
        if (leftmostChild != null) {
            i3 = leftmostChild.getLeft();
        }
        b(i3, i2);
    }

    private void e(int i2) {
        int i3;
        View leftmostChild = getLeftmostChild();
        while (leftmostChild != null && leftmostChild.getRight() + i2 <= 0) {
            int i4 = this.g;
            if (h(this.q)) {
                i3 = leftmostChild.getMeasuredWidth();
            } else {
                i3 = this.i + leftmostChild.getMeasuredWidth();
            }
            this.g = i3 + i4;
            a(this.q, leftmostChild);
            removeViewInLayout(leftmostChild);
            this.q++;
            leftmostChild = getLeftmostChild();
        }
        View rightmostChild = getRightmostChild();
        while (rightmostChild != null && rightmostChild.getLeft() + i2 >= getWidth()) {
            a(this.x, rightmostChild);
            removeViewInLayout(rightmostChild);
            this.x--;
            rightmostChild = getRightmostChild();
        }
    }

    private void a(int i2, int i3) {
        while (i2 + i3 + this.i < getWidth() && this.x + 1 < this.mAdapter.getCount()) {
            this.x++;
            if (this.q < 0) {
                this.q = this.x;
            }
            View view = this.mAdapter.getView(this.x, b(this.x), this);
            a(view, -1);
            i2 += (this.x == 0 ? 0 : this.i) + view.getMeasuredWidth();
            h();
        }
    }

    private void b(int i2, int i3) {
        int i4;
        int i5;
        while ((i2 + i3) - this.i > 0 && this.q >= 1) {
            this.q--;
            View view = this.mAdapter.getView(this.q, b(this.q), this);
            a(view, 0);
            if (this.q == 0) {
                i4 = view.getMeasuredWidth();
            } else {
                i4 = this.i + view.getMeasuredWidth();
            }
            i2 -= i4;
            int i6 = this.g;
            if (i2 + i3 == 0) {
                i5 = view.getMeasuredWidth();
            } else {
                i5 = view.getMeasuredWidth() + this.i;
            }
            this.g = i6 - i5;
        }
    }

    private void f(int i2) {
        int childCount = getChildCount();
        if (childCount > 0) {
            this.g += i2;
            int i3 = this.g;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                int paddingLeft = getPaddingLeft() + i3;
                int paddingTop = getPaddingTop();
                childAt.layout(paddingLeft, paddingTop, childAt.getMeasuredWidth() + paddingLeft, childAt.getMeasuredHeight() + paddingTop);
                i3 += childAt.getMeasuredWidth() + this.i;
            }
        }
    }

    private View getLeftmostChild() {
        return getChildAt(0);
    }

    private View getRightmostChild() {
        return getChildAt(getChildCount() - 1);
    }

    private View g(int i2) {
        if (i2 < this.q || i2 > this.x) {
            return null;
        }
        return getChildAt(i2 - this.q);
    }


    public int c(int i2, int i3) {
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            getChildAt(i4).getHitRect(this.u);
            if (this.u.contains(i2, i3)) {
                return i4;
            }
        }
        return -1;
    }

    private boolean h(int i2) {
        return i2 == this.mAdapter.getCount() + -1;
    }

    private int getRenderHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private int getRenderWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void scrollTo(int i2) {
        this.mFlingTracker.startScroll(this.mNextX, 0, i2 - this.mNextX, 0);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        requestLayout();
    }

    public int getFirstVisiblePosition() {
        return this.q;
    }

    public int getLastVisiblePosition() {
        return this.x;
    }

    private void a(Canvas canvas) {
        if (this.j != null && !this.j.isFinished() && i()) {
            int save = canvas.save();
            int height = getHeight();
            canvas.rotate(-90.0f, 0.0f, 0.0f);
            canvas.translate((float) ((-height) + getPaddingBottom()), 0.0f);
            this.j.setSize(getRenderHeight(), getRenderWidth());
            if (this.j.draw(canvas)) {
                invalidate();
            }
            canvas.restoreToCount(save);
        } else if (this.k != null && !this.k.isFinished() && i()) {
            int save2 = canvas.save();
            int width = getWidth();
            canvas.rotate(90.0f, 0.0f, 0.0f);
            canvas.translate((float) getPaddingTop(), (float) (-width));
            this.k.setSize(getRenderHeight(), getRenderWidth());
            if (this.k.draw(canvas)) {
                invalidate();
            }
            canvas.restoreToCount(save2);
        }
    }

    private void b(Canvas canvas) {
        int childCount = getChildCount();
        Rect rect = this.u;
        this.u.top = getPaddingTop();
        this.u.bottom = this.u.top + getRenderHeight();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (i2 != childCount - 1 || !h(this.x)) {
                View childAt = getChildAt(i2);
                rect.left = childAt.getRight();
                rect.right = childAt.getRight() + this.i;
                if (rect.left < getPaddingLeft()) {
                    rect.left = getPaddingLeft();
                }
                if (rect.right > getWidth() - getPaddingRight()) {
                    rect.right = getWidth() - getPaddingRight();
                }
                a(canvas, rect);
                if (i2 == 0 && childAt.getLeft() > getPaddingLeft()) {
                    rect.left = getPaddingLeft();
                    rect.right = childAt.getLeft();
                    a(canvas, rect);
                }
            }
        }
    }

    private void a(Canvas canvas, Rect rect) {
        if (this.h != null) {
            this.h.setBounds(rect);
            this.h.draw(canvas);
        }
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        b(canvas);
    }


    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        a(canvas);
    }


    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        this.mFlingTracker.fling(this.mNextX, 0, (int) (-f2), 0, 0, this.r, 0, 0);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        requestLayout();
        return true;
    }


    public boolean onDown(MotionEvent motionEvent) {
        this.bs = !this.mFlingTracker.isFinished();
        this.mFlingTracker.forceFinished(true);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
        f();
        if (!this.bs) {
            int c2 = c((int) motionEvent.getX(), (int) motionEvent.getY());
            if (c2 >= 0) {
                this.A = getChildAt(c2);
                if (this.A != null) {
                    this.A.setPressed(true);
                    refreshDrawableState();
                }
            }
        }
        return true;
    }


    public void f() {
        if (this.A != null) {
            this.A.setPressed(false);
            refreshDrawableState();
            this.A = null;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.mFlingTracker == null || this.mFlingTracker.isFinished()) {
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
            a(Boolean.valueOf(false));
            g();
        } else if (motionEvent.getAction() == 3) {
            f();
            g();
            a(Boolean.valueOf(false));
        }
        return super.onTouchEvent(motionEvent);
    }

    private void g() {
        if (this.j != null) {
            this.j.onRelease();
        }
        if (this.k != null) {
            this.k.onRelease();
        }
    }

    private void h() {
        if (this.y != null && this.mAdapter != null && this.mAdapter.getCount() - (this.x + 1) < this.z && !this.n) {
            this.n = true;
            this.y.onRunningOutOfData();
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.s = onClickListener;
    }


    public void setCurrentScrollState(OnScrollStateChangedListener.ScrollState scrollState) {
        if (!(this.cs == scrollState || this.t == null)) {
            this.t.onScrollStateChanged(scrollState);
        }
        this.cs = scrollState;
    }


    public void i(int i2) {
        if (!(this.j == null || this.k == null)) {
            try {
                int i3 = this.mCurrentX + i2;
                if (this.mFlingTracker != null && !this.mFlingTracker.isFinished()) {
                    return;
                }
                if (i3 < 0) {
                    try {
                        this.j.onPull(((float) Math.abs(i2)) / ((float) getRenderWidth()));
                        if (!this.k.isFinished()) {
                            try {
                                this.k.onRelease();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else if (i3 > this.r) {
                    try {
                        this.k.onPull(((float) Math.abs(i2)) / ((float) getRenderWidth()));
                        if (!this.j.isFinished()) {
                            try {
                                this.j.onRelease();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    private boolean i() {
        return this.mAdapter != null && !this.mAdapter.isEmpty() && this.r > 0;
    }
}
