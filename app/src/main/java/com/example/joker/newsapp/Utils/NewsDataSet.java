package com.example.joker.newsapp.Utils;

import com.example.joker.newsapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joker on 28/11/17.
 */

public final class NewsDataSet {

    private static Map<String, Integer> imageCollection = new HashMap<>();

    private static String[] NewsChannel = {
            "ABC News", "BBC", "CNN", "The Times of India", "The Hindu", "NBC News", "Google News (India)", "Metro",
            "Business Insider", "CNBC", "The Wall Street Journal",
            "ESPN",
            "Crypto Coins News", "Hackr News", "TechCrunch", "TechRadar", "The Next Web",
            "National Geographic",
            "MTV News", "Buzzfeed", "Daily Mail", "Mashable",
            "IGN", "Polygon",
            "Medical News Today"
    };


    private static String[] channelId = {
            "abc-news", "bbc-news", "cnn", "the-times-of-india", "the-hindu", "nbc-news", "google-news-in",
            "metro", "business-insider", "cnbc", "the-wall-street-journal",
            "espn",
            "crypto-coins-news", "hacker-news", "techcrunch", "techradar", "the-next-web",
            "national-geographic",
            "mtv-news", "buzzfeed", "daily-mail", "mashable",
            "ign", "polygon",
            "medical-news-today"
    };

    private static String[] category = {
            "General", "General", "General", "General", "General", "General", "General",
            "Bussiness", "Bussiness", "Bussiness", "Bussiness",
            "Sports",
            "Technology", "Technology", "Technology", "Technology", "Technology",
            "Science and Nature",
            "Music", "Entertainment", "Entertainment", "Entertainment",
            "Comics & Gaming", "Gaming",
            "Health"
    };

    public static Map<String, Integer> getImageCollection() {

        imageCollection.put(channelId[0], R.drawable.abc_news);
        imageCollection.put(channelId[1], R.drawable.bbc_news);
        imageCollection.put(channelId[2], R.drawable.cnn_news);
        imageCollection.put(channelId[3], R.drawable.toi_news);
        imageCollection.put(channelId[4], R.drawable.the_hindu);
        imageCollection.put(channelId[5], R.drawable.nbc_news);
        imageCollection.put(channelId[6], R.drawable.google_news);
        imageCollection.put(channelId[7], R.drawable.metro_news);
        imageCollection.put(channelId[8], R.drawable.business_insider_news);
        imageCollection.put(channelId[9], R.drawable.cnbc_news);
        imageCollection.put(channelId[10], R.drawable.the_wall_street_journal);
        imageCollection.put(channelId[11], R.drawable.espn_news);
        imageCollection.put(channelId[12], R.drawable.cryptocoinsnews);
        imageCollection.put(channelId[13], R.drawable.hackernews);
        imageCollection.put(channelId[14], R.drawable.techcrunch);
        imageCollection.put(channelId[15], R.drawable.techradar);
        imageCollection.put(channelId[16], R.drawable.the_next_web_news);
        imageCollection.put(channelId[17], R.drawable.national_geographic);
        imageCollection.put(channelId[18], R.drawable.mtv_news);
        imageCollection.put(channelId[19], R.drawable.buzzfeeds_news);
        imageCollection.put(channelId[20], R.drawable.daily_mail);
        imageCollection.put(channelId[21], R.drawable.mashable_news);
        imageCollection.put(channelId[22], R.drawable.ign_news);
        imageCollection.put(channelId[23], R.drawable.polygon_news);
        imageCollection.put(channelId[24], R.drawable.medical_today_news);


        return imageCollection;
    }

    public static String[] getNewsChannel() {
        return NewsChannel;
    }

    public static String[] getChannelId() {
        return channelId;
    }

    public static String[] getCategory() {
        return category;
    }
}


