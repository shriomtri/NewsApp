package com.example.joker.newsapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.joker.newsapp.ClickListener;
import com.example.joker.newsapp.Database.NewsContractor;
import com.example.joker.newsapp.R;
import com.example.joker.newsapp.Utils.NewsDataSet;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 27/11/17.
 */

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.Viewholder> {

    private String[] channelName = null;
    private String[] channerType = null;
    private String[] channelId = null;
    private Map<String,Integer> imageCollection = new HashMap<>();
    private Context context;
    ClickListener clickListener;

    private int current_pos = 0;

    public NavAdapter(Context context) {
        this.channelName = NewsDataSet.getNewsChannel();
        this.channerType = NewsDataSet.getCategory();
        this.imageCollection = NewsDataSet.getImageCollection();
        this.channelId = NewsDataSet.getChannelId();
        this.context = context;
        this.clickListener = (ClickListener) context;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return channelId.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView sourceName=null,sourceType=null;
        ImageView sourceImage = null;
        LinearLayout linearLayout = null;

        public Viewholder(View itemView) {
            super(itemView);

            sourceName = itemView.findViewById(R.id.sourceName);
            sourceType = itemView.findViewById(R.id.sourceType);
            sourceImage = itemView.findViewById(R.id.sourceImage);
            linearLayout = itemView.findViewById(R.id.listContainer);

        }

        public void bindData(final int position) {

            this.sourceName.setText(channelName[position]);
            this.sourceType.setText(channerType[position]);

            Glide.with(context)
                    .load(imageCollection.get(channelId[position]))
                    .into(this.sourceImage);

            this.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.DrawerClickListerner(channelId[position]);
                    Log.d("News Clicked ", channelName[position]);

                }
            });



        }
    }
}
