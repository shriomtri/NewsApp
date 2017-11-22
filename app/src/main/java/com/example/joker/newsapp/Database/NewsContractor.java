package com.example.joker.newsapp.Database;

import android.provider.BaseColumns;

/**
 * Created by joker on 22/11/17.
 */

public class NewsContractor {

    public final class TopHeadline implements BaseColumns {

        public static final String TABLE_NAME = "topheadline";
        public static final String SOURCE_ID = "source_id";
        public static final String SOURCE_NAME = "source_name";
        public static final String AUTHOR = "author";
        public static final String TITLE = "title";
        public static final String DESC = "description";
        public static final String SOURCE_URL = "url";
        public static final String IMAGE_URL = "image_url";
        public static final String PUBLISHED_AT = "publishedAt";

    }

}
