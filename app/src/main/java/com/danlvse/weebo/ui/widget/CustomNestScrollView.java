package com.danlvse.weebo.ui.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.danlvse.weebo.R;

/**
 * Created by zxy on 16/5/28.
 */
public class CustomNestScrollView extends NestedScrollView {
    private int[] location;
    private ViewGroup viewGroup;
    private int mLastX = 0;
    private int mLastY = 0;

    public CustomNestScrollView(Context context) {
        super(context);
    }

    public CustomNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            int delayX = x - mLastX;
            int delayY = y - mLastY;
            if (Math.abs(delayX)>Math.abs(delayY)){
                return false;
            }
                location = new int[2];

            viewGroup = (ViewGroup) this.getChildAt(0);
            viewGroup.getChildAt(4).getLocationOnScreen(location);
            if (location[1] > getResources().getDimension(R.dimen.tab_to_top_size)) {
                return true;
            }
        }
        mLastX = x;
        mLastY = y;
        return super.onInterceptTouchEvent(ev);
    }

}
