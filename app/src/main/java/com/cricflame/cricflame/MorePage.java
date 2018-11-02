package com.cricflame.cricflame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pavneet on 12-Sep-17.
 */

public class MorePage extends AppCompatActivity {
    // Array of strings storing country names
    String[] countries = new String[] {
            "Browse Series","Browse Team","Browse Player","Schedule","Archives","Fantasy Cricket","Photo","Quotes","Ranking","Records","Rate the App","Feedback","Setting","About CricBuzz"
    };

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{
            com.cricflame.cricflame.R.drawable.trophy,
            com.cricflame.cricflame.R.drawable.group,
            com.cricflame.cricflame.R.drawable.profile,
            com.cricflame.cricflame.R.drawable.schedule,
            com.cricflame.cricflame.R.drawable.archi,
            com.cricflame.cricflame.R.drawable.fantasy,
            com.cricflame.cricflame.R.drawable.picture,
            com.cricflame.cricflame.R.drawable.quote,
            com.cricflame.cricflame.R.drawable.rank,
            com.cricflame.cricflame.R.drawable.record,
            com.cricflame.cricflame.R.drawable.rate,
            com.cricflame.cricflame.R.drawable.feedback,
            com.cricflame.cricflame.R.drawable.setting2,
            com.cricflame.cricflame.R.drawable.about
    };

    // Array of strings to store currencies
    // String[] currency = new String[]{
    //       "Browse Series","Browse Team","Browse Player","Schedule","Archives","Fantasy Cricket","Photo","Quotes","Ranking","Records"
    //};


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.cricflame.cricflame.R.layout.more_activity);

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<14;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "" + countries[i]);
            //hm.put("cur","Currency : " + currency[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag","txt"};

        // Ids of views in listview_layout
        int[] to = { com.cricflame.cricflame.R.id.flag, com.cricflame.cricflame.R.id.txt};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, com.cricflame.cricflame.R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(com.cricflame.cricflame.R.id.listview);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
    }
}
