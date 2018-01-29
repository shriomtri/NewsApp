package com.example.joker.newsapp;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.joker.newsapp.ViewPagerTransformer.DrawFromBackTransformer;
import com.example.joker.newsapp.ViewPagerTransformer.FlipPageViewTransformer;
import com.example.joker.newsapp.ViewPagerTransformer.StackTransformer;

/**
 * Created by joker on 24/12/17.
 */

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new DrawFromBackTransformer());
    }


    //stack to swap the coordinate of touch event.
    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;

        event.setLocation(swappedX, swappedY);

        return event;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = super.onInterceptTouchEvent(event);
        //If not intercept, touch event should not be swapped.
        //swapTouchEvent(event);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }


}
