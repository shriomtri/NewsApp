package com.example.joker.newsapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.joker.newsapp.ModelClass.TopHeadlines;
import com.example.joker.newsapp.R;

import java.util.ArrayList;

/**
 * Created by joker on 17/11/17.
 */

public class TopNewListAdapter extends RecyclerView.Adapter<TopNewListAdapter.ViewHolder> {

    private ArrayList<TopHeadlines> topHeadlines = new ArrayList<>();
    private Context context;


    public TopNewListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_listitem, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(topHeadlines.get(position).getTitle());
        holder.description.setText(topHeadlines.get(position).getDescription());
        Glide.with(context).load(topHeadlines.get(position).getImageUrl()).into(holder.imageView);

        String source = topHeadlines.get(position).getSource_name();
        holder.source.setText("Source-"+source);

        /*TODO add onclick listener on source textView such that it opens the actual news in webView*/

        String author = topHeadlines.get(position).getAuthor();
        if(author.equals("null"))
            author = "not available";

        holder.author.setText("Author - "+author);

        String noOfCards = " "+(position+1) + " of "+topHeadlines.size();
        holder.count.setText(noOfCards);

    }


    @Override
    public int getItemCount() {
        return topHeadlines == null ? 0 : topHeadlines.size();
    }

    public void swapAdapters(ArrayList<TopHeadlines> headlines) {
        if ((headlines != null) && (topHeadlines != headlines)) {
            topHeadlines = headlines;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description,source,author,count;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_textView);
            description = itemView.findViewById(R.id.description_textView);
            imageView = itemView.findViewById(R.id.imageView);
            source = itemView.findViewById(R.id.sourceTextView);
            author = itemView.findViewById(R.id.authorTextView);
            count = itemView.findViewById(R.id.countTextView);
        }
    }
}
