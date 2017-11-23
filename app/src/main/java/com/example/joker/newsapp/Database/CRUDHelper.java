package com.example.joker.newsapp.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.joker.newsapp.ModelClass.TopHeadlines;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by joker on 23/11/17.
 */

public final class CRUDHelper {

    //method to delete all records from database
    public static void dropAllRecord(SQLiteDatabase database) {
        String DELETE_ALL_RECORDS = " DELETE FROM " + NewsContractor.TopHeadline.TABLE_NAME;
        database.execSQL(DELETE_ALL_RECORDS);
    }



    //method to insert the records
    public static void insertDataToDatabase(SQLiteDatabase database , ArrayList<TopHeadlines> topHeadlines){

        Collections.reverse(topHeadlines);

        for (TopHeadlines news : topHeadlines) {

            ContentValues cv = new ContentValues();
            cv.put(NewsContractor.TopHeadline.SOURCE_ID, news.getSource_id());
            cv.put(NewsContractor.TopHeadline.SOURCE_NAME, news.getSource_name());
            cv.put(NewsContractor.TopHeadline.AUTHOR, news.getAuthor());
            cv.put(NewsContractor.TopHeadline.TITLE, news.getTitle());
            cv.put(NewsContractor.TopHeadline.DESC, news.getDescription());
            cv.put(NewsContractor.TopHeadline.SOURCE_URL, news.getUrl());
            cv.put(NewsContractor.TopHeadline.IMAGE_URL, news.getImageUrl());
            cv.put(NewsContractor.TopHeadline.PUBLISHED_AT, news.getPublishedAt());

            database.insert(NewsContractor.TopHeadline.TABLE_NAME, null, cv);
            cv.clear();
        }
    }


    //method that return to all data from Database
    public static ArrayList<TopHeadlines> getAllRecords(SQLiteDatabase database){

        Cursor cursor = getAllData(database);

        ArrayList<TopHeadlines> topHeadlines = new ArrayList<>();

        while (cursor.moveToNext()) {
            TopHeadlines news;
            String source_id = cursor.getString(cursor.getColumnIndex(NewsContractor.TopHeadline.SOURCE_ID));
            String source_name = cursor.getString(cursor.getColumnIndex(NewsContractor.TopHeadline.SOURCE_NAME));
            String aurthor = cursor.getString(cursor.getColumnIndex(NewsContractor.TopHeadline.AUTHOR));
            String title = cursor.getString(cursor.getColumnIndex(NewsContractor.TopHeadline.TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NewsContractor.TopHeadline.DESC));
            String url = cursor.getString(cursor.getColumnIndex(NewsContractor.TopHeadline.SOURCE_URL));
            String image_url = cursor.getString(cursor.getColumnIndex(NewsContractor.TopHeadline.IMAGE_URL));
            String publishDate = cursor.getString(cursor.getColumnIndex(NewsContractor.TopHeadline.PUBLISHED_AT));
            news = new TopHeadlines(source_id, source_name, aurthor, title, description, url, image_url, publishDate);
            topHeadlines.add(news);
        }

        cursor.close();
        return topHeadlines;

    }

    private static Cursor getAllData(SQLiteDatabase database) {

        return database.query(
                NewsContractor.TopHeadline.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NewsContractor.TopHeadline._ID+" DESC ");

    }

}
