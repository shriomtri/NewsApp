package com.example.joker.newsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joker.newsapp.Adapter.TopNewListAdapter;
import com.example.joker.newsapp.Database.CRUDHelper;
import com.example.joker.newsapp.Database.NewsContractor;
import com.example.joker.newsapp.Database.SQLHelperClass;
import com.example.joker.newsapp.ModelClass.TopHeadlines;
import com.example.joker.newsapp.Utils.HttpHandler;
import com.example.joker.newsapp.Utils.ParseTopHeadline;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener , LoaderManager.LoaderCallbacks<String> {

    private static final String API_KEY = "e562f69b208f4010850241a1e1a52e31";
    private static final String GOOGLE_SOURCE_IN = "google-news-in";
    private static final int NEWS_LOADER = 121;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toast toast = null;

    ArrayList<TopHeadlines> topHeadlines = new ArrayList<>();

    private TopNewListAdapter topNewListAdapter;
    private RecyclerView recyclerView;

    private LoaderManager loaderManager;
    private SQLiteDatabase database;
    private SQLHelperClass dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("TopHeadLines");



        //get default shared preference instance
        SharedPreferences preferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        loadPreferences(preferences);
        //register the shared preference
        preferences.registerOnSharedPreferenceChangeListener(MainActivity.this);

        loaderManager = getSupportLoaderManager();

        //getting database reference
        dbHelper = new SQLHelperClass(this);
        database = dbHelper.getWritableDatabase();

        topNewListAdapter = new TopNewListAdapter(this);

        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        topNewListAdapter.swapAdapters(CRUDHelper.getAllRecords(database));

        recyclerView.setAdapter(topNewListAdapter);


        makeNetworkCall(GOOGLE_SOURCE_IN);

    }

    //method to handle initiate and restart of
    private void makeNetworkCall(String source){

        Bundle queryBundle = new Bundle();
        queryBundle.putString("SOURCE",source);

        Log.d(TAG , " source " + source);

        Loader<String> newsLoader = loaderManager.getLoader(NEWS_LOADER);

        if(newsLoader == null){
            loaderManager.initLoader(NEWS_LOADER,queryBundle,this).forceLoad();
        }else{
            loaderManager.restartLoader(NEWS_LOADER,queryBundle,this).forceLoad();
        }

    }


    //to load the preferences
    private void loadPreferences(SharedPreferences preferences) {

//        boolean bname = preferences.getBoolean(getString(R.string.key_name), true);
//
//        if (!bname)
//            name.setVisibility(View.INVISIBLE);
//        else
//            name.setVisibility(View.VISIBLE);

    }

    //the create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    //to read clicks on OptionItems
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    //Override methods to detect preferenceChange
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadPreferences(sharedPreferences);
    }


    //methods to make async network calls
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            public String loadInBackground() {

                String source = args.getString("SOURCE");

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("newsapi.org")
                        .appendPath("v2")
                        .appendPath("top-headlines")
                        .appendQueryParameter("sources", source)
                        .appendQueryParameter("apiKey", API_KEY);

                String url = builder.build().toString();
                return HttpHandler.makeServiceCall(url);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String response) {

        if (response == null) {
            showToast("Response is null.");
            return;
        }

        topHeadlines.clear();
        topHeadlines = ParseTopHeadline.parseTopHeadline(response);

        CRUDHelper.dropAllRecord(database);

        CRUDHelper.insertDataToDatabase(database , topHeadlines);

        topNewListAdapter.swapAdapters(CRUDHelper.getAllRecords(database));

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    //method to show the toast
    private void showToast(String s) {

        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT);
        toast.show();
    }

    //method to load news dynamically on user selections
    public void loadNews(View view) {

        int id = view.getId();

        switch (id){
            case R.id.sports_categories_espn:
                makeNetworkCall("espn");
                break;
            case R.id.tech_categories_hacker:
                makeNetworkCall("hacker-news");
                break;
            case R.id.general_categories_abc:
                makeNetworkCall("abc-news");
                break;
            case R.id.health_categories_medical:
                makeNetworkCall("medical-news-today");
                break;
            case R.id.business_categories_cnbc:
                makeNetworkCall("cnbc");
                break;
            case R.id.gaming_categories_ign:
                makeNetworkCall("ign");
                break;
             default:
                 makeNetworkCall(GOOGLE_SOURCE_IN);
        }

    }
}
