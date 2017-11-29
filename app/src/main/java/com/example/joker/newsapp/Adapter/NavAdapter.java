package com.example.joker.newsapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joker.newsapp.Database.NewsContractor;
import com.example.joker.newsapp.R;

import org.w3c.dom.Text;

/**
 * Created by joker on 27/11/17.
 */

public class NavAdapter extends BaseAdapter{

    private String[] channelName = null;
    private String[] channerType = null;

    public NavAdapter(String[] channelName, String[] channerType) {
        this.channelName = channelName;
        this.channerType = channerType;
    }

    @Override
    public int getCount() {
        return channelName.length;
    }

    @Override
    public Object getItem(int position) {
        return channelName[position];
    }

    @Override
    public long getItemId(int position) {
        return channelName[position].hashCode();
    }

    @Override
    public View getView(int position, View converterView, ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        TextView sourceName = (TextView) v.findViewById(R.id.sourceName);
        TextView sourceType = (TextView) v.findViewById(R.id.sourceType);

        sourceName.setText(channelName[position]);
        sourceType.setText(channerType[position]);

        return  v;

    }
}
