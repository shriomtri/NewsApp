package com.example.joker.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.joker.newsapp.Utils.HttpHandler;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager ImageViewPager;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private boolean isInternet = false;
    private Toast toast;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences("First_Time_Launched", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);

        ImageViewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(ImageViewPager, true);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3
        };

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        ImageViewPager.setAdapter(myViewPagerAdapter);
        ImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == layouts.length - 1) {
                    btnNext.setText(getString(R.string.start));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(1);
                if (current < layouts.length) {
                    //  to next screen
                    ImageViewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

    }

    private int getItem(int i) {
        return ImageViewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {

        if (toast != null) {
            toast.cancel();
        }

        new CheckNetwork().execute("https://www.google.com");
        toast = Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_LONG);
        toast.show();

    }

    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }
    }

    public class CheckNetwork extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... strings) {
            return HttpHandler.makeServiceCall(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (toast != null) {
                toast.cancel();
            }

            if (s == null || s.length() == 0) {
                toast = Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_SHORT);
                toast.show();
            } else {

                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("activity_executed", true);
                editor.apply();

                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }


        }
    }
}
