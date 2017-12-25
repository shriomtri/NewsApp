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

    private int totalSize = 0;
    private int curPos = 0;

    TextView title, description, source, author, count;
    ImageView imageView;

    public NewsFragment() {
    }

    public void setNews(Context context,TopHeadlines newsModel,int totalSize,int curPos){
        this.topHeadlines = newsModel;
        this.context = context;
        this.totalSize = totalSize;
        this.curPos = curPos;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.news_listitem,container,false);

        title = view.findViewById(R.id.title_textView);
        description = view.findViewById(R.id.description_textView);
        imageView = view.findViewById(R.id.imageView);
        source = view.findViewById(R.id.sourceTextView);
        author = view.findViewById(R.id.authorTextView);
        count = view.findViewById(R.id.countTextView);

        title.setText(topHeadlines.getTitle());
        description.setText(topHeadlines.getDescription());
        Glide.with(context).load(topHeadlines.getImageUrl()).into(imageView);

        String sourceBy = topHeadlines.getSource_name();
        source.setText("Source-" + sourceBy);

        String authorName = topHeadlines.getAuthor();
        if (authorName.equals("null"))
            authorName = "not available";

        author.setText("By - " + authorName);

        count.setText(""+(curPos+1)+" of "+totalSize);

        return view;
    }
}
