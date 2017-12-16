package com.example.joker.newsapp.Utils;

/**
 * Created by joker on 28/11/17.
 */

public final class NewsDataSet {

    private static String[] NewsChannel = {
            "ABC News", "BBC", "CNN", "The Times of India", "The Hindu", "NBC News", "Google News (India)", "New York Magazine", "Reddit", "Google News (Israel)","Google News (UK)", "Metro",
            "Business Insider", "CNBC", "Financial Times", "The Economist", "The Wall Street Journal",
            "ESPN", "ESPN Cricket Info","Football", "Fox Sports",
            "Crypto Coins News", "Hackr News", "TechCrunch", "TechRadar", "The Next Web",
            "National Geographic", "News Scientist",
            "MTV News", "MTV News(UK)",
            "Buzzfeed", "Daily Mail", "Mashable",
            "IGN", "Polygon",
            "Medical News Today"};


    private static String[] channelId = {
            "abc-news",
            "bbc-news", "cnn", "the-times-of-india", "the-hindu", "nbc-news", "google-news-in", "news-york-magazine",
            "redit-r-all", "google-news-is", "google-news-uk", "metro", "business-insider", "cnbc", "financial-times",
            "the-economist", "the-wall-street-journal", "espn", "espn-circ-info", "football-italia", "fox-sports", "crypto-coins-news",
            "hacker-news", "techcrunch", "rechradar", "the-next-web", "national-geographic", "news-scientist", "mtv-news", "mtv-news-uk",
            "buzzfeed", "daily-mail", "mashable", "ign", "polygon", "medical-news-today"
    };

    private static String[] category = {
            "General", "General", "General", "General", "General", "General", "General", "General", "General", "General", "General", "General",
            "Bussiness", "Bussiness", "Bussiness", "Bussiness", "Bussiness",
            "Sports", "Sports", "Sports", "Sports",
            "Technology", "Technology", "Technology", "Technology", "Technology",
            "Science and Nature", "Science and Nature",
            "Music", "Music",
            "Entertainment", "Entertainment", "Entertainment",
            "Gaming", "Gaming",
            "Health"
    };

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


