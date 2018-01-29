package com.example.joker.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.comix.overwatch.HiveProgressView;
import com.example.joker.newsapp.Adapter.NavAdapter;
import com.example.joker.newsapp.Adapter.PagerAdapter;
import com.example.joker.newsapp.Database.CRUDHelper;
import com.example.joker.newsapp.Database.SQLHelperClass;
import com.example.joker.newsapp.ModelClass.TopHeadlines;
import com.example.joker.newsapp.Utils.HttpHandler;
import com.example.joker.newsapp.Utils.NewsDataSet;
import com.example.joker.newsapp.Utils.ParseTopHeadline;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<String> , ClickListener{

    private static final String API_KEY = "ded182f8057546f1b36f4cd3461219d4";
    private static final String THE_TIMES_OF_INDIA = "the-times-of-india";
    private static final int NEWS_LOADER = 121;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toast toast = null;

    ArrayList<TopHeadlines> topHeadlines = new ArrayList<>();

    private FragmentManager fm;

//    private TopNewListAdapter topNewListAdapter;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private LoaderManager loaderManager;
    private SQLiteDatabase database;
    private SQLHelperClass dbHelper;

    //Instace of loadingAnimantion
    private HiveProgressView hiveProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //get default shared preference instance
        SharedPreferences preferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(this);
        loadPreferences(preferences);
        //register the shared preference
        preferences.registerOnSharedPreferenceChangeListener(MainActivity.this);


        loaderManager = getSupportLoaderManager();

        //getting database reference
        dbHelper = new SQLHelperClass(this);
        database = dbHelper.getWritableDatabase();

        //topNewListAdapter = new TopNewListAdapter(this);

        fm = getSupportFragmentManager();
        pagerAdapter = new PagerAdapter(this,fm,CRUDHelper.getAllRecords(database));
        viewPager = findViewById(R.id.newsViewPager);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        topNewListAdapter.swapAdapters(CRUDHelper.getAllRecords(database));

//        recyclerView.setAdapter(topNewListAdapter);

//        //Loading Animation
//        insLoadingView = findViewById(R.id.loading_view);
//        insLoadingView.setVisibility(View.GONE);

        hiveProgressView = findViewById(R.id.hiveProgress);
        hiveProgressView.setVisibility(View.GONE);

        viewPager.setAdapter(pagerAdapter);
        makeNetworkCall(THE_TIMES_OF_INDIA);


        setupNavigationDrawer();


    }

    //setting up navigation drawer
    private void setupNavigationDrawer() {

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        RecyclerView navList  = findViewById(R.id.navList);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        navList.setLayoutManager(staggeredGridLayoutManager);

        NavAdapter navAdapter = new NavAdapter(this);
        navList.setAdapter(navAdapter);


    }

    //method to handle initiate and restart of
    private void makeNetworkCall(String source) {

        Bundle queryBundle = new Bundle();
        queryBundle.putString("SOURCE", source);

        //Log.d(TAG, " source " + source);
        hiveProgressView.setVisibility(View.VISIBLE);

        Loader<String> newsLoader = loaderManager.getLoader(NEWS_LOADER);

        if (newsLoader == null) {
            loaderManager.initLoader(NEWS_LOADER, queryBundle, this).forceLoad();
        } else {
            loaderManager.restartLoader(NEWS_LOADER, queryBundle, this).forceLoad();
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

        hiveProgressView.setVisibility(View.GONE);

        if (response == null) {
            showToast("Check newtork connection.");
            return;
        }


        topHeadlines.clear();
        topHeadlines = ParseTopHeadline.parseTopHeadline(response);

        CRUDHelper.dropAllRecord(database);

        CRUDHelper.insertDataToDatabase(database, topHeadlines);

        pagerAdapter = new PagerAdapter(getApplicationContext(),fm,CRUDHelper.getAllRecords(database));
        //pagerAdapter.swapAdapters(CRUDHelper.getAllRecords(database));
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
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

    @Override
    public void DrawerClickListerner(String source) {

        makeNetworkCall(source);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawers();
        }

    }
}
