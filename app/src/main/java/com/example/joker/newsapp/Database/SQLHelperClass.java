package com.example.joker.newsapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joker on 22/11/17.
 */

public class SQLHelperClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";

    public SQLHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE_TOP_HEADLINE = "CREATE TABLE " + NewsContractor.TopHeadline.TABLE_NAME + " ( "+
                NewsContractor.TopHeadline._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NewsContractor.TopHeadline.SOURCE_ID + " VARCHAR, "+
                NewsContractor.TopHeadline.SOURCE_NAME + " VARCHAR, "+
                NewsContractor.TopHeadline.TITLE + " VARCHAR, "+
                NewsContractor.TopHeadline.DESC + " VARCHAR, "+
                NewsContractor.TopHeadline.SOURCE_URL + " VARCHAR, "+
                NewsContractor.TopHeadline.IMAGE_URL + " VARCHAR, "+
                NewsContractor.TopHeadline.PUBLISHED_AT + " VARCHAR, "+
                NewsContractor.TopHeadline.AUTHOR + " VARCHAR ); ";

        sqLiteDatabase.execSQL(CREATE_TABLE_TOP_HEADLINE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + NewsContractor.TopHeadline.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
