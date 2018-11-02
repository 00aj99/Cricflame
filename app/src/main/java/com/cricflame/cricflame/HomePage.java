package com.cricflame.cricflame;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class HomePage extends AppCompatActivity {
    ViewPager viewPager;
    ViewPager viewPager1;
    LinearLayout sliderDotspanel;
    private int dotscount;
    LinearLayout sliderDotspanel1;
    private int dotscount1;
    private ImageView[] dots;
    private ImageView[] dots1;

    String[] rank;
    String[] country;
    String[] population;

   // private ListView mainListView ;
    //private ArrayAdapter<String> listAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.activity_main);
//
//   //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        rank = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
//
//        country = new String[]{" ", " ", "  States",
//                "Indonesia", "Brazil", "Pakistan", "Nigeria", "Bangladesh",
//                "Russia", "Japan"};
//
//        population = new String[]{" ", " ",
//                " ", " ", " ", " ",
//                " ", " ", " ", " "};
//
//
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//
//        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
//
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(HomePage.this, rank, country, population);
//
//        viewPager.setAdapter(viewPagerAdapter);
//
//        dotscount = viewPagerAdapter.getCount();
//        dots = new ImageView[dotscount];
//
//        for (int i = 0; i < dotscount; i++) {
//
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            sliderDotspanel.addView(dots[i], params);
//
//        }
//
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for (int i = 0; i < dotscount; i++) {
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//                }
//
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        Timer timer = new Timer();
//        //  timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
//
//
//
//        viewPager1 = (ViewPager) findViewById(R.id.viewPager2);
//        sliderDotspanel1 = (LinearLayout) findViewById(R.id.SliderDots1);
//
//     //  ViewPagerAdapter1 viewPagerAdapter1 = new ViewPagerAdapter1(MainActivity.,videoList);
//
//     // viewPager1.setAdapter(viewPagerAdapter1);
//
//    //    dotscount1 = viewPagerAdapter1.getCount();
//        dots1 = new ImageView[dotscount1];
//
//        for (int i = 0; i < dotscount1; i++) {
//
//            dots1[i] = new ImageView(this);
//            dots1[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(8, 0, 8, 0);
//
//            sliderDotspanel1.addView(dots1[i], params);
//
//        }
//
//        dots1[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for (int i = 0; i < dotscount1; i++) {
//                    dots1[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
//                }
//
//                dots1[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//       // Timer timer = new Timer();
//        //  timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
//
//   //news listview <code>
//
//        // Find the ListView resource.
//    /*    mainListView = (ListView) findViewById( R.id.mainListView );
//
//        // Create and populate a List of planet names.
//        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars"};
//        ArrayList<String> planetList = new ArrayList<String>();
//        planetList.addAll( Arrays.asList(planets) );
//
//        // Create ArrayAdapter using the planet list.
//        listAdapter = new ArrayAdapter<String>(this, R.layout.news_item, planetList);
//
//        // Add more planets. If you passed a String[] instead of a List<String>
//        // into the ArrayAdapter constructor, you must not add more items.
//        // Otherwise an exception will occur.
//        listAdapter.add( "Ceres" );
//        listAdapter.add( "Pluto" );
//        listAdapter.add( "Haumea" );
//        listAdapter.add( "Makemake" );
//        listAdapter.add( "Eris" );
//
//        // Set the ArrayAdapter as the ListView's adapter.
//        //mainListView.setAdapter( listAdapter );
//
//        */
//
//        ListView listview = (ListView) findViewById(R.id.lv);
//        final String item[] = new String[]{"First ODI's 17th September Ind Vs Aus ","Second ODI's 20th September Ind Vs Aus","Third ODI's 22th September Ind Vs Aus","Fourth ODI's 27th September Ind Vs Aus"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1,item);
//
//        listview.setAdapter(adapter);
//
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(HomePage.this, item[i], Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        ListView listview1 = (ListView) findViewById(R.id.lv1);
//        final String item1[] = new String[]{"Dhawan Release from India ODI squad on request ","ComeBack are never easy:Rohit Sharma","Dhoni is Super Finisher","Kohli is Ready for ODI series With Australia"};
//
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1,item1);
//
//        listview1.setAdapter(adapter1);
//
//        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(HomePage.this, item1[i], Toast.LENGTH_SHORT).show();
//            }
//        });
//
    }



    }
/*

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }

*/

