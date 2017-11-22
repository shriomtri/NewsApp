package com.example.joker.newsapp.ModelClass;

/**
 * Created by joker on 17/11/17.
 */

public class TopHeadlines {

    private String source_id,
            source_name,
            author,
            title,
            description,
            url,
            imageUrl,
            publishedAt;

    public TopHeadlines(String source_id, String source_name, String author, String title, String description, String url, String imageUrl, String publishedAt) {
        this.source_id = source_id;
        this.source_name = source_name;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }

    public String getSource_id() {
        return source_id;
    }

    public String getSource_name() {
        return source_name;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
