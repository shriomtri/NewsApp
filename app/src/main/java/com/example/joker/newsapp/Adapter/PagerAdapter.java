package com.example.joker.newsapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.joker.newsapp.ModelClass.TopHeadlines;
import com.example.joker.newsapp.NewsFragment;

import java.util.ArrayList;

/**
 * Created by joker on 23/12/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<TopHeadlines> topHeadlines = new ArrayList<>();
    private Context context;

    public PagerAdapter(FragmentManager fm, ArrayList<TopHeadlines> model, Context context1) {
        super(fm);
        this.topHeadlines = model;
        this.context  = context1;
    }

    @Override
    public Fragment getItem(int position) {
        NewsFragment fragment = new NewsFragment();
        fragment.setNews(context , topHeadlines.get(position));
        return fragment;
    }

    public void swapAdapters(ArrayList<TopHeadlines> headlines) {
        if ((headlines != null) && (topHeadlines != headlines)) {
            topHeadlines = headlines;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return topHeadlines == null ? 0 : topHeadlines.size();
    }


}
