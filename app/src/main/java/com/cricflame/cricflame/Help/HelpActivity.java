package com.cricflame.cricflame.Help;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cricflame.cricflame.R;

public class HelpActivity extends AppCompatActivity {

    private ImageView back;
    private Spinner spinner_help;
    String[] help_list = {
      "How Chat Works",
      "How to use Bet-fair",
      "How to use Live-line",
      "How to use Dummy Book",
      "How to use Browse Series",
      "How to use Player Stats",
      "How to control voice feature in Live-line",
      "How Cric-flame Live TV Works"
    };

    String[] description = {
        "HOW CHAT WORKS\n" +
                "\n" +
                "To join chat you need to Register. Recommended option to signin is to click on Sign in with Google. \n" +
                "How to register with email\n" +
                "1. Enter your email address.\n" +
                "2. Enter your password. The password should be more than 8 characters.\n" +
                "3. Confirm your password.\n" +
                "4. Enter Name, Country and phone number.\n" +
                "What is global room?\n" +
                "1. You can chat with any members who are subscribed to cricflame chat.\n" +
                "\n" +
                "USERS\n" +
                "\n" +
                "You can view and chat with all the users who are subscribed to cricflame chat from users section.\n" +
                "\n" +
                "CONVERSATIONS\n" +
                "\n" +
                "All the chats you have made are available in Conversations section. \n" +
                "\n",
            "HOW TO USE BETFAIR\n" +
                    "\n" +
                    "There are 4 sections inside Betfair:\n" +
                    "1. Cricket\n" +
                    "2. Football\n" +
                    "3. Tennis\n" +
                    "4. Horse racing\n" +
                    "\n" +
                    "-->Clicking on any sport shows the match type.\n" +
                    "-->Next page displays the list of tournaments or series names.\n" +
                    "-->After clicking on tournament or series' name you will get match names.\n" +
                    "-->After clicking on match name, you will get many markets.Click on markets to see the odds.\n" +
                    "For any difficulties and suggestion please feedback us.\n" +
                    "\n",
            "HOW LIVE LINE WORKS\n" +
                    "\n" +
                    "Cricflame provides Fastest Liveline among all other apps and is very rich in contents.\n" +
                    "\n"+
                    "1 With the ball by ball voice commentary for scored runs and running session.\n" +
                    "2 Super fast score with current Betfair odds and local market odds.\n" +
                    "3 With remaining target and remaining balls of an innings and session.\n" +
                    "4 Bats'man scorecard and economy of the bowler.\n" +
                    "\n" +
                    "BETFAIR\n" +
                    "IN Betfair section shows current rates of all games and all available markets.\n" +
                    "\n" +
                    "TV ICON" +
                    "\n" +
                    "Clicking on TV icon opens Cricflame TV where you can view live streaming.\n" +
                    "\n" +
                    "SCORECARD" +
                    "\n" +
                    "Detailed Scorecard, Ball-ball commentary with ball by ball bhav and session.\n" +
                    "\n" +
                    "SESSIONS" +
                    "\n" +
                    "IN sessions we show you all sessions open value, lowest value, highest value and what session pass value.\n" +
                    "\n" +
                    "COMMENTARY" +
                    "\n" +
                    "In the commentary, you will see all current match record ball by ball like ball by ball session, score, bhav, favorite team name, overs, and status of each ball.\n" +
                    "\n" +
                    "PITCH REPORT" +
                    "\n" +
                    "In pitch report, you will get 5 main services which will most helpful for predict winning team.\n" +
                    "\n" +
                    "H2H" +
                    "\n" +
                    "In h2h You see a head to head previous 10 to 15 match results. \n" +
                    "according to home or away and the margin of victory.\n" +
                    "\n" +
                    "PITCH REPORT\n" +
                    "IN pitch report section you will get current and previous stats of the ground, like weather, avg. first batting score, highest score etc.\n" +
                    "\n" +
                    "STATS\n" +
                    "In stats, you will get some interesting facts and historical data of two teams or the tournament.\n" +
                    "\n" +
                    "RECORD\n" +
                    "It is mainly for international tournaments, it shows all team and individual records like highest score chase between two teams or highest run scorer and highest wicket-taker.\n" +
                    "\n" +
                    "DO YOU KNOW\n" +
                    "In this section, you see really some interesting about cricket.\n" +
                    "\n" +
                    "ANALYSIS\n" +
                    "In this section, you will see a whole analysis of match and prediction.\n" +
                    "\n" +
                    "GROUND \n" +
                    "In this section, you will see all details about that ground, which match is playing in that ground.\n" +
                    "For any difficulties and suggestion please feedback us.\n" +
                    "\n",
            "HOW TO USE DUMMY BOOK \n" +
                    "\n" +
                    "In this section, we provide two services for making your book \n" +
                    "\n" +
                    "Dummy book for a match. \n" +
                    "Dummy book for a session.\n" +
                    "In dummy book, you have three match options.\n" +
                    " you will be able to maintain your book for three different matches.\n" +
                    "For making a book first you have to add two team names in team name field.\n" +
                    "select the match type between - Test, ODI or T20.\n" +
                    "and start the entry. \n" +
                    "We also gave you the option to add party name or commission. \n" +
                    "\n" +
                    "It doesn't matter that you are a bookie or a punter.\n" +
                    "many punters may be don't know that mostly bookie gave you 1 to 5% commission of your losing bets, suppose if team \"A\" win the match and you lay team \"A\" 5 times than if your bookie gave you 2% commission than it calculates your commission for you.\n" +
                    "it's not necessary to add party name and commission.\n" +
                    "you see all your entries in result section just press\n" +
                    "\"Go to results\" button\"\n" +
                    "When you change team names for any previous match then the data will automatically delected\n" +
                    "For any difficulties and suggestion please feedback us.\n" +
                    "\n",
            "HOW TO USE BROWSE SERIES\n" +
                    "\n" +
                    "Browse series section has 4 tabs \n" +
                    "\n" +
                    "International \n" +
                    "T20\n" +
                    "Domestic\n" +
                    "Women\n" +
                    "\n" +
                    "Click on any series names under any tab and see all match record and full scorecard of any match.\n" +
                    "For any difficulties and suggestion please feedback us.\n" +
                    "\n",
            "HOW TO USE PLAYER STATS\n" +
                    "\n" +
                    "Click on any player name or search the name of any player name in the search bar for seeing any player carrier with their record.\n" +
                    "For any difficulties and suggestion please feedback us.\n" +
                    "\n",
            "HOW TO CONTROL VOICE FEATURE FOR LIVE LINE\n" +
                    "\n" +
                    "We provide voice commentary and session commentary in two languages - \"IN HINDI and IN ENGLISH\"\n" +
                    "The default language is Hindi if you want to change then change it from the main menu of the app.\n" +
                    "Voice commentary work's in the background also if you want to turn it off than simple press loudspeaker button in live line.\n" +
                    "For any difficulties and suggestion please feedback us.\n" +
                    "\n",
            "HOW TO USE CRICFLAME TV\n" +
                    "\n" +
                    "Just click on any channel icon and see live tv. \n" +
                    "For any difficulties and suggestion please feedback us.\n" +
                    "\n"
    };
    private TextView et_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        back= (ImageView) findViewById(com.cricflame.cricflame.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner_help = (Spinner)findViewById(R.id.spinner_help);
        et_description = (TextView)findViewById(R.id.description);
        ArrayAdapter arrayAdapter = new ArrayAdapter(HelpActivity.this,android.R.layout.simple_list_item_1,help_list);
        spinner_help.setAdapter(arrayAdapter);

        spinner_help.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                et_description.setText(description[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
