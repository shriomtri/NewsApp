package com.example.joker.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joker.newsapp.Adapter.TopNewListAdapter;
import com.example.joker.newsapp.ModelClass.TopHeadlines;
import com.example.joker.newsapp.Utils.HttpHandler;
import com.example.joker.newsapp.Utils.ParseTopHeadline;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String API_KEY = "e562f69b208f4010850241a1e1a52e31";
    private static final String GOOGLE_SOURCE_IN = "google-news-in";

    private Toast toast = null;

    ArrayList<TopHeadlines> topHeadlines = new ArrayList<>();

    private TopNewListAdapter topNewListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get default shared preference instance
        SharedPreferences preferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        loadPreferences(preferences);
        //register the shared preference
        preferences.registerOnSharedPreferenceChangeListener(MainActivity.this);

        topNewListAdapter = new TopNewListAdapter(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        topNewListAdapter.swapAdapters(topHeadlines);

        recyclerView.setAdapter(topNewListAdapter);

        //calling AsyncTask Top headlines
        new AsyncTopHeadLines().execute();

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


    //Aysnc task to fetch the latestNews i.e Everything
    private class AsyncTopHeadLines extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("newsapi.org")
                    .appendPath("v2")
                    .appendPath("top-headlines")
                    .appendQueryParameter("sources", GOOGLE_SOURCE_IN)
                    .appendQueryParameter("apiKey", API_KEY);

            String url = builder.build().toString();

            return HttpHandler.makeServiceCall(url);
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            if (response.length() < 1) {
                showToast("Response is null.");
                return;
            }

            topHeadlines = ParseTopHeadline.parseTopHeadline(response);
            topNewListAdapter.swapAdapters(topHeadlines);
        }
    }


    //method to show the toast
    private void showToast(String s) {

        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT);
        toast.show();
    }
}
