package com.example.joker.newsapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.joker.newsapp.ModelClass.TopHeadlines;
import com.example.joker.newsapp.NewsFragment;

import java.util.ArrayList;

/**
 * Created by joker on 23/12/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TopHeadlines> topHeadlines = new ArrayList<>();
    private Context context;

    public PagerAdapter(Context context, FragmentManager fm,ArrayList<TopHeadlines> topHeadlines) {
        super(fm);
        this.context = context;
        this.topHeadlines = topHeadlines;
    }


    @Override
    public Fragment getItem(int position) {
        NewsFragment fragment = new NewsFragment();
        fragment.setNews(context,topHeadlines.get(position),topHeadlines.size(),position);
        return fragment;
    }

    @Override
    public int getCount() {
        return topHeadlines == null ? 0 : topHeadlines.size();
    }


    public void swapAdapters(ArrayList<TopHeadlines> allRecords) {

        if(allRecords != null && allRecords != topHeadlines){
            topHeadlines = allRecords;
        }
        notifyDataSetChanged();


    }
}
