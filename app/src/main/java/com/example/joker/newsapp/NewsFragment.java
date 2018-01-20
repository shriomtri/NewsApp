package com.example.joker.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.joker.newsapp.ModelClass.TopHeadlines;

import java.io.File;
import java.util.Date;

/**
 * Created by joker on 23/12/17.
 */

public class NewsFragment extends Fragment {

    private TopHeadlines topHeadlines = null;
    private Context context;

    private int totalSize = 0;
    private int curPos = 0;

    TextView title, description, source, publisTextView;
    ImageView imageView;

    public NewsFragment() {
    }

    public void setNews(Context context, TopHeadlines newsModel, int totalSize, int curPos) {
        this.topHeadlines = newsModel;
        this.context = context;
        this.totalSize = totalSize;
        this.curPos = curPos;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.news_listitem, container, false);

        title = view.findViewById(R.id.title_textView);
        description = view.findViewById(R.id.description_textView);
        imageView = view.findViewById(R.id.imageView);
        source = view.findViewById(R.id.sourceTextView);
        publisTextView = view.findViewById(R.id.publishedTimeTextView);


        title.setText(Html.fromHtml(topHeadlines.getTitle().trim()));
        description.setText(Html.fromHtml(topHeadlines.getDescription().trim() + "..."));
        Glide.with(context).load(topHeadlines.getImageUrl()).into(imageView);

        Typeface coustom_font = Typeface.createFromAsset(context.getAssets(), "fonts/JosefinSansLight.ttf");
        Typeface coustom_font_bold = Typeface.createFromAsset(context.getAssets(), "fonts/JosefinSansSemiBold.ttf");

        title.setTypeface(coustom_font_bold);
        description.setTypeface(coustom_font);

        //Source Name
        String sourceBy = topHeadlines.getSource_name().trim();
        source.setText(sourceBy);

//        //Author Name
//        String authorName = topHeadlines.getAuthor().trim();
//        if (authorName.equals("null") || authorName.length() > 20)
//            authorName = "Anonymous";

        String publishedAt = topHeadlines.getPublishedAt().trim();
        String year = publishedAt.substring(0, 4);
        String month = publishedAt.substring(5, 7);
        String day = publishedAt.substring(8, 10);

        String date = getDate(day, Integer.parseInt(month), year);

        if (publishedAt.equals("null")) {
            publisTextView.setText("NewsApp");
        } else {
            publisTextView.setText(date);
        }

        return view;
    }

    //method to return date.
    private String getDate(String day, int i, String year) {

        String month = null;

        switch (i) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
        }

        String date = day+"-"+month+"-"+year;
        return date;

    }

//    private Bitmap getScreenShot(View rootView) {
//
//        View screenView = rootView.getRootView();
//        screenView.setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
//        screenView.setDrawingCacheEnabled(false);
//        return bitmap;
//
//    }
}
