package com.example.joker.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.joker.newsapp.ModelClass.TopHeadlines;

/**
 * Created by joker on 23/12/17.
 */

public class NewsFragment extends Fragment {

    private TopHeadlines topHeadlines = null;
    private Context context;


    TextView title, description, source, author, count;
    ImageView imageView;

    public NewsFragment() {
    }

    public void setNews(Context context,TopHeadlines newsModel){
        this.topHeadlines = newsModel;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_listitem,container,false);

        init(view);

        title.setText(topHeadlines.getTitle());
        description.setText(topHeadlines.getDescription());
        Glide.with(context).load(topHeadlines.getImageUrl()).into(imageView);

        String sourceBy = topHeadlines.getSource_name();
        source.setText("Source-" + sourceBy);

        /*TODO add onclick listener on source textView such that it opens the actual news in webView*/
        String authorName = topHeadlines.getAuthor();
        if (authorName.equals("null"))
            authorName = "not available";

        author.setText("By - " + authorName);

        //String noOfCards = " " + (position + 1) + " of " + topHeadlines.size();
        count.setText("1 of 10");

        return view;
    }

    private void init(View itemView) {

        title = itemView.findViewById(R.id.title_textView);
        description = itemView.findViewById(R.id.description_textView);
        imageView = itemView.findViewById(R.id.imageView);
        source = itemView.findViewById(R.id.sourceTextView);
        author = itemView.findViewById(R.id.authorTextView);
        count = itemView.findViewById(R.id.countTextView);

    }
}
