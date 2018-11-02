package com.cricflame.cricflame.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.MainActivity;
import com.cricflame.cricflame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ScoreCardFragment extends Fragment {

    ExpandableListViewAdapter adapter;
    int dbInstance;
    String connectionUrl;
    ExpandableListView expandableListView;
    Innings i1 = new Innings(null, null, null, null, null, null, null);
    Innings i2 = new Innings(null, null, null, null, null, null, null);
    Innings i3 = new Innings(null, null, null, null, null, null, null);
    Innings i4 = new Innings(null, null, null, null, null, null, null);
    String key;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Batsman> team1batsmen;
    private ArrayList<Bowler> team1bowlers;
    private ArrayList<Batsman> team2batsmen;
    private ArrayList<Bowler> team2bowlers;
    private ArrayList<Batsman> team3batsmen;
    private ArrayList<Bowler> team3bowlers;
    private ArrayList<Batsman> team4batsmen;
    private ArrayList<Bowler> team4bowlers;
    //private ArrayList<>team1FallofWicket;
    private String teamname1;
    private String teamname2;
    private String teamname3;
    private String teamname4;
    int type;
    String MatchScoreCaerdId;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getData();
        }
    }

    private class Batsman {
        String balls;
        String fours;
        String outstatus = "";
        String playerName;
        String run;
        String sixes;
        String strikeRate;

        public Batsman(String playerName, String balls, String fours, String run, String sixes) {
            this.playerName = playerName;
            String[] playerNames = playerName.split("\n");
            if (playerNames.length > 1) {
                this.playerName = playerNames[0];
                this.outstatus = playerNames[1];
            }
            this.balls = balls;
            this.fours = fours;
            this.run = run;
            this.sixes = sixes;
            try {
                if (Integer.parseInt(balls) == 0) {
                    throw new Exception();
                }
                this.strikeRate = String.format("%.2f", new Object[]{Double.valueOf((Double.parseDouble(run) / ((double) Integer.parseInt(balls))) * 100.0d)});
            } catch (Exception e) {
                this.strikeRate = "-";
            }
        }

        public Batsman(String playerName, String balls, String fours, String run, String sixes, String strikeRate) {
            this.playerName = playerName;
            String[] playerNames = playerName.split("\n");
            if (playerNames.length > 1) {
                this.playerName = playerNames[0];
                this.outstatus = playerNames[1];
            }
            this.balls = balls;
            this.fours = fours;
            this.run = run;
            this.sixes = sixes;
            this.strikeRate = strikeRate;
        }
    }

    private class Bowler {
        String bowlerName;
        String economyRate;
        String maiden;
        String overs;
        String run;
        String wickets;

        public Bowler(String bowlerName, String maiden, String overs, String run, String wickets) {
            this.bowlerName = bowlerName;
            this.maiden = maiden;
            this.overs = overs;
            this.run = run;
            this.wickets = wickets;
            try {
                double temp = Double.parseDouble(overs);
                int balls = (int) (((double) (((int) temp) * 6)) + ((10.0d * temp) % 10.0d));
                this.economyRate = String.format("%2.2f", new Object[]{Double.valueOf((Double.parseDouble(run) * 6.0d) / ((double) balls))});
            } catch (Exception e) {
                this.economyRate = "-";
            }
        }

        public Bowler(String bowlerName, String maiden, String overs, String run, String wickets, String economyRate) {
            this.bowlerName = bowlerName;
            this.maiden = maiden;
            this.overs = overs;
            this.run = run;
            this.wickets = wickets;
            this.economyRate = economyRate;
        }
    }

    private class ExpandableListViewAdapter extends BaseExpandableListAdapter {
        private static final int extra_total_type = 2;
        private static final int group_AdType = 1;
        private static final int group_scoreType = 0;
        private static final int headingType = 0;
        private static final int playerListType = 1;
        private static final int toBat_fow_type = 3;
        Context f1089c;
        LayoutInflater inflater;
        Innings[] inn = new Innings[4];

        public class ChildViewHolder {
            TextView balls_maidens;
            TextView container_item;
            TextView container_name;
            TextView container_title;
            LinearLayout extra_total_container;
            TextView fours_runs;
            TextView heading;
            TextView outstatus;
            TextView player;
            LinearLayout player_container;
            TextView runs_overs;
            TextView sixes_wickets;
            TextView strikerate_economy;
            LinearLayout toBat_fow_container;
            TextView value;
        }

        public class GroupViewHolder {
            RelativeLayout group_container;
            RelativeLayout parent_group_container;
            TextView team_name;
            TextView team_total;
        }

        public ExpandableListViewAdapter(Context context, Innings i1, Innings i2, Innings i3, Innings i4) {
            this.f1089c = context;
            this.inn[0] = i1;
            this.inn[1] = i2;
            this.inn[2] = i3;
            this.inn[3] = i4;
            this.inflater = (LayoutInflater) this.f1089c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getGroupCount() {
            int i;
            if(type==0){
               i =2;
            }else{
                i =4;
            }
            return i;
        }

        public int getChildrenCount(int groupPosition) {
            if(type==1){
                    if (this.inn[groupPosition].batsmen == null || this.inn[groupPosition].batsmen.size() == 1) {
                        return 0;
                    }
            }else{
                if (groupPosition == 2 || this.inn[groupPosition].batsmen == null || this.inn[groupPosition].batsmen.size() == 1) {
                    return 0;
                }
                }

            return (this.inn[groupPosition].batsmen.size() + this.inn[groupPosition].bowlers.size()) + 2;
        }

        public int getChildType(int groupPosition, int childPosition) {
            if (childPosition == 0 || childPosition == this.inn[groupPosition].batsmen.size() + 1) {
                return 0;
            }
            if ((childPosition <= 0 || childPosition > this.inn[groupPosition].batsmen.size()) && (childPosition <= this.inn[groupPosition].batsmen.size() + 1 || childPosition > (this.inn[groupPosition].batsmen.size() + this.inn[groupPosition].bowlers.size()) + 1)) {
                return -1;
            }
            return 1;
        }

        public int getChildTypeCount() {
            return 2;
        }

        public int getGroupType(int groupPosition) {
            if (groupPosition == 0 || groupPosition == 1) {
                return 0;
            }
            if (groupPosition != 2) {
                return -1;
            }
            return 0;
        }

        public int getGroupTypeCount() {
            int i;
            if(type==0){
                i =2;
            }else{
                i =4;
            }
            return i;
        }

        public Object getGroup(int groupPosition) {
            return null;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        public long getGroupId(int i) {
            return 0;
        }

        public long getChildId(int i, int i1) {
            return 0;
        }

        public boolean hasStableIds() {
            return false;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
            GroupViewHolder groupHolder;
            int groupType = getGroupType(groupPosition);
            if (convertView == null) {
                groupHolder = new GroupViewHolder();
                switch (groupType) {
                    case 0:
                        convertView = this.inflater.inflate(R.layout.score_card_groupitem, viewGroup, false);
                        groupHolder.parent_group_container = (RelativeLayout) convertView.findViewById(R.id.parent_group_container);
                        groupHolder.group_container = (RelativeLayout) convertView.findViewById(R.id.group_container);
                        groupHolder.team_name = (TextView) convertView.findViewById(R.id.team_name);
                        groupHolder.team_total = (TextView) convertView.findViewById(R.id.team_total);
                        break;
                    case -1:
                        convertView = this.inflater.inflate(R.layout.score_card_groupitem, viewGroup, false);
                        groupHolder.parent_group_container = (RelativeLayout) convertView.findViewById(R.id.parent_group_container);
                        groupHolder.group_container = (RelativeLayout) convertView.findViewById(R.id.group_container);
                        groupHolder.team_name = (TextView) convertView.findViewById(R.id.team_name);
                        groupHolder.team_total = (TextView) convertView.findViewById(R.id.team_total);
                        break;
                }
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupViewHolder) convertView.getTag();
            }
            if (groupPosition == 0 || groupPosition == 1) {
                groupHolder.team_name.setText(this.inn[groupPosition].team);
            } else if (groupPosition == 2 || groupPosition == 3) {
                groupHolder.team_name.setText(this.inn[groupPosition].team);
            }
            return convertView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
            ChildViewHolder childHolder;
            int type = getChildType(groupPosition, childPosition);
            if (convertView == null) {
                childHolder = new ChildViewHolder();
                switch (type) {
                    case 0:
                        convertView = this.inflater.inflate(R.layout.heading_score_card, viewGroup, false);
                        childHolder.heading = (TextView) convertView.findViewById(R.id.scorecard_heading);
                        break;
                    case 1:
                        convertView = this.inflater.inflate(R.layout.player_list_scorecard, viewGroup, false);
                        childHolder.player = (TextView) convertView.findViewById(R.id.player_name);
                        childHolder.outstatus = (TextView) convertView.findViewById(R.id.outstatus);
                        childHolder.runs_overs = (TextView) convertView.findViewById(R.id.runs_overs);
                        childHolder.balls_maidens = (TextView) convertView.findViewById(R.id.balls_maidens);
                        childHolder.fours_runs = (TextView) convertView.findViewById(R.id.fours_runs);
                        childHolder.sixes_wickets = (TextView) convertView.findViewById(R.id.sixes_wickets);
                        childHolder.strikerate_economy = (TextView) convertView.findViewById(R.id.strikerate_economy);
                        childHolder.player_container = (LinearLayout) convertView.findViewById(R.id.player_container);
                        break;
                    case 2:
                        convertView = this.inflater.inflate(R.layout.extra_total_scorecard, viewGroup, false);
                        childHolder.extra_total_container = (LinearLayout) convertView.findViewById(R.id.extra_total_container);
                        childHolder.container_name = (TextView) convertView.findViewById(R.id.container_name);
                        childHolder.value = (TextView) convertView.findViewById(R.id.value);
                        break;
                    case 3:
                        convertView = this.inflater.inflate(R.layout.tobat_fow_scorecard, viewGroup, false);
                        childHolder.toBat_fow_container = (LinearLayout) convertView.findViewById(R.id.tobat_fow_container);
                        childHolder.container_title = (TextView) convertView.findViewById(R.id.container_title);
                        childHolder.container_item = (TextView) convertView.findViewById(R.id.container_item);
                        break;
                }
                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildViewHolder) convertView.getTag();
            }
            if (childPosition == 0) {
                childHolder.heading.setText("Batting");
            } else if (childPosition > 0 && childPosition <= this.inn[groupPosition].batsmen.size()) {
                Batsman bat = (Batsman) this.inn[groupPosition].batsmen.get(childPosition - 1);
                childHolder.player.setText(bat.playerName);
                childHolder.runs_overs.setText(bat.run);
                childHolder.balls_maidens.setText(bat.balls);
                childHolder.fours_runs.setText(bat.fours);
                childHolder.sixes_wickets.setText(bat.sixes);
                childHolder.strikerate_economy.setText(bat.strikeRate);
                if (bat.outstatus == "") {
                    childHolder.outstatus.setVisibility(8);
                } else {
                    childHolder.outstatus.setVisibility(0);
                    childHolder.outstatus.setText(bat.outstatus);
                }
                if (childPosition - 1 == 0) {
                    childHolder.player_container.setBackground(getActivity().getResources().getDrawable(R.drawable.ranking_type_back));
                } else if ((childPosition - 1) % 2 != 0 || childPosition == 0) {
                    childHolder.player_container.setBackgroundColor(0);
                } else {
                    childHolder.player_container.setBackgroundColor(Color.parseColor("#7863a260"));
                }
            } else if (childPosition == this.inn[groupPosition].batsmen.size() + 1) {
                childHolder.heading.setText("Bowling");
            } else if (childPosition > this.inn[groupPosition].batsmen.size() + 1 && childPosition <= (this.inn[groupPosition].batsmen.size() + this.inn[groupPosition].bowlers.size()) + 1) {
                Bowler bowler = (Bowler) this.inn[groupPosition].bowlers.get(childPosition - (this.inn[groupPosition].batsmen.size() + 2));
                childHolder.player.setText(bowler.bowlerName);
                childHolder.runs_overs.setText(bowler.overs);
                childHolder.balls_maidens.setText(bowler.maiden);
                childHolder.fours_runs.setText(bowler.run);
                childHolder.sixes_wickets.setText(bowler.wickets);
                childHolder.strikerate_economy.setText(bowler.economyRate);
                childHolder.outstatus.setVisibility(8);
                if (childPosition - (this.inn[groupPosition].batsmen.size() + 2) == 0) {
                    childHolder.player_container.setBackground(getActivity().getResources().getDrawable(R.drawable.ranking_type_back));
                } else if ((childPosition - (this.inn[groupPosition].batsmen.size() + 2)) % 2 != 0 || childPosition - (this.inn[groupPosition].batsmen.size() + 5) == 0) {
                    childHolder.player_container.setBackgroundColor(0);
                } else {
                    childHolder.player_container.setBackgroundColor(Color.parseColor("#7863a260"));
                }
            }
            return convertView;
        }
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

    private class Innings {
        public ArrayList<Batsman> batsmen;
        public ArrayList<Bowler> bowlers;
        public String extra;
        public String fow;
        public String team;
        public String toBat;
        public String total;

        public Innings(ArrayList<Batsman> batsmen, ArrayList<Bowler> bowlers, String team, String extra, String total, String toBat, String fow) {
            this.batsmen = batsmen;
            this.bowlers = bowlers;
            this.team = team;
            this.extra = extra;
            this.total = total;
            this.toBat = toBat;
            this.fow = fow;
        }
    }

    class C14561 implements SwipeRefreshLayout.OnRefreshListener {
        C14561() {
        }

        public void onRefresh() {
            ScoreCardFragment.this.getData();
        }
    }

    class C14572 implements Response.Listener<JSONObject> {
        C14572() {
        }

        public void onResponse(JSONObject response) {
            // ScoreCardFragment.this.parseJsonData(response);
            ScoreCardFragment.this.getJsonData(response);
            ScoreCardFragment.this.swipeRefreshLayout.setRefreshing(false);
        }
    }

    class C14583 implements Response.ErrorListener {
        C14583() {
        }

        public void onErrorResponse(VolleyError error) {
            try {
                Toast.makeText(ScoreCardFragment.this.getActivity(), "Internet Error.", 0);
                ScoreCardFragment.this.swipeRefreshLayout.setRefreshing(false);
            } catch (Exception e) {
            }
        }
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.key = getArguments().getString("key");
        this.type = getArguments().getInt("type");
        this.MatchScoreCaerdId = getArguments().getString("ScoreCardId");
        if (Build.VERSION.SDK_INT >= 21) {
            dbInstance = ThreadLocalRandom.current().nextInt(2, 11);
        } else {
            dbInstance = new Random().nextInt(9) + 2;
        }
        connectionUrl = Global.LIVEMATCH_URL + dbInstance + ".firebaseio.com";
        this.expandableListView = (ExpandableListView) getView().findViewById(R.id.expandableListView);
        this.expandableListView.setChildDivider(null);
        this.expandableListView.setDivider(null);
        this.expandableListView.setGroupIndicator(null);
        this.adapter = new ExpandableListViewAdapter(getActivity(), this.i1, this.i2, this.i3, this.i4);
        this.expandableListView.setAdapter(this.adapter);
        this.swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshlayout);
        this.swipeRefreshLayout.setRefreshing(true);
        this.swipeRefreshLayout.setOnRefreshListener(new C14561());

       // getData();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scrorecard_fragment, container, false);

    }

    private void getData() {
        //Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(this.connectionUrl + "/scorecards/" + this.key + ".json", null, new C14572(), new C14583()));
        //Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.GET,Global.URL +"livematchscore.php?id="+MatchScoreCaerdId, null, new C14572(), new C14583()));
        Volley.newRequestQueue(getActivity()).add(new JsonObjectRequest(Request.Method.GET,Global.SCORE_CARD_URL+MatchScoreCaerdId+"scorecard.json", null, new C14572(), new C14583()));
    }

    private void getJsonData(JSONObject response){
        JSONObject scoreCardObject = response;

        try {
            this.team1batsmen = new ArrayList<>();
            this.team1bowlers = new ArrayList<>();
            this.team2batsmen = new ArrayList<>();
            this.team2bowlers = new ArrayList<>();
            this.team3batsmen = new ArrayList<>();
            this.team3bowlers = new ArrayList<>();
            this.team4batsmen = new ArrayList<>();
            this.team4bowlers = new ArrayList<>();
            JSONObject data = scoreCardObject.getJSONObject("Innings");
            JSONObject inning = data.getJSONObject("1");
            JSONArray players = scoreCardObject.getJSONArray("players");
            JSONObject inning2 = null;
            JSONObject inning3 = null;
            JSONObject inning4 = null;
            if(data.has("2")){
                inning2 = data.getJSONObject("2");
                this.teamname2 = inning2.getString("battingteam")+" - "+inning2.getString("runs")+"/"+inning2.getString("wickets")+" ("+inning2.getString("overs")+")";;
                this.team2bowlers.add(new Bowler("Bowler Name", "M", "O", "R", "W", "ER"));
                this.team2batsmen.add(new Batsman("Batsman Name", "B", "4s", "R", "6s", "SR"));
                JSONArray batsmen2 = inning2.getJSONArray("batsmen");

                for (int i = 0;i<batsmen2.length();i++){
                    String batsmanID = batsmen2.getJSONObject(i).getString("batsmanId");
                    String batsmanName = "";
                    for (int j = 0;j<players.length();j++){
                        if (batsmanID.equalsIgnoreCase(players.getJSONObject(j).getString("id"))){
                            batsmanName = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                            break;
                        }
                    }
                    try {
                        this.team2batsmen.add(new Batsman(batsmanName + "\n"+ batsmen2.getJSONObject(i).getString("outdescription"),batsmen2.getJSONObject(i).getString("ball"),batsmen2.getJSONObject(i).getString("four"),batsmen2.getJSONObject(i).getString("run"),batsmen2.getJSONObject(i).getString("six"),batsmen2.getJSONObject(i).getString("sr")));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }



                if(!inning2.getString("wickets").equalsIgnoreCase("0")){
                    this.team2batsmen.add(new Batsman("Fall Of Wickets", "", "", "", ""));
                    this.team2batsmen.add(new Batsman("Batsman Name", "Score", "", "", "Over"));
                    JSONArray fow2 = inning2.getJSONArray("fow");
                    for (int i = 0;i<fow2.length();i++){
                        String outBatsmanID = fow2.getJSONObject(i).getString("outBatsmanId");
                        String outBatsmanname = "";
                        for (int j = 0;j<players.length();j++){
                            if (outBatsmanID.equalsIgnoreCase(players.getJSONObject(j).getString("id")))
                                outBatsmanname = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                        }
                        this.team2batsmen.add(new Batsman(outBatsmanname,fow2.getJSONObject(i).getString("run")+"-"+fow2.getJSONObject(i).getString("wicketnbr"),"","", fow2.getJSONObject(i).getString("overnbr")));
                    }
                }



                JSONArray bowlers2 = inning2.getJSONArray("bowlers");
                for (int i = 0;i<bowlers2.length();i++){
                    String bowlerID = bowlers2.getJSONObject(i).getString("bowlerId");
                    String bowlerName = "";
                    for (int j = 0;j<players.length();j++){
                        if (bowlerID.equalsIgnoreCase(players.getJSONObject(j).getString("id"))){
                            bowlerName = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                            break;
                        }
                    }
                    this.team2bowlers.add(new Bowler(bowlerName.trim(),bowlers2.getJSONObject(i).getString("maiden"),bowlers2.getJSONObject(i).getString("over"),bowlers2.getJSONObject(i).getString("run"),bowlers2.getJSONObject(i).getString("wicket"),bowlers2.getJSONObject(i).getString("sr")));
                }
                if(inning2.has("extras")){
                    JSONObject extra2 = inning2.getJSONObject("extras");

                    this.team2bowlers.add(new Bowler("Extras -"+ " Total " + extra2.getString("total")+", Byes "+extra2.getString("byes")+", LegByes " + extra2.getString("legByes")+", WideBalls "+extra2.getString("wideBalls")+", NoBalls "+extra2.getString("noBalls")+", Penalty "+extra2.getString("penalty"),"", "", "", ""));
                }


            }

            if(data.has("3")){
                inning3 = data.getJSONObject("3");
                this.teamname3 = inning3.getString("battingteam")+" - "+inning3.getString("runs")+"/"+inning3.getString("wickets")+" ("+inning3.getString("overs")+")";
                this.team3bowlers.add(new Bowler("Bowler Name", "M", "O", "R", "W", "ER"));
                this.team3batsmen.add(new Batsman("Batsman Name", "B", "4s", "R", "6s", "SR"));
                JSONArray batsmen3 = inning3.getJSONArray("batsmen");

                for (int i = 0;i<batsmen3.length();i++){
                    String batsmanID = batsmen3.getJSONObject(i).getString("batsmanId");
                    String batsmanName = "";
                    for (int j = 0;j<players.length();j++){
                        if (batsmanID.equalsIgnoreCase(players.getJSONObject(j).getString("id"))){
                            batsmanName = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                            break;
                        }
                    }
                    try {
                        this.team3batsmen.add(new Batsman(batsmanName + "\n"+ batsmen3.getJSONObject(i).getString("outdescription"),batsmen3.getJSONObject(i).getString("ball"),batsmen3.getJSONObject(i).getString("four"),batsmen3.getJSONObject(i).getString("run"),batsmen3.getJSONObject(i).getString("six"),batsmen3.getJSONObject(i).getString("sr")));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }



                if(!inning3.getString("wickets").equalsIgnoreCase("0")){
                    this.team3batsmen.add(new Batsman("Fall Of Wickets", "", "", "", ""));
                    this.team3batsmen.add(new Batsman("Batsman Name", "Score", "", "", "Over"));
                    JSONArray fow3 = inning3.getJSONArray("fow");
                    for (int i = 0;i<fow3.length();i++){
                        String outBatsmanID = fow3.getJSONObject(i).getString("outBatsmanId");
                        String outBatsmanname = "";
                        for (int j = 0;j<players.length();j++){
                            if (outBatsmanID.equalsIgnoreCase(players.getJSONObject(j).getString("id")))
                                outBatsmanname = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                        }
                        this.team3batsmen.add(new Batsman(outBatsmanname,fow3.getJSONObject(i).getString("run")+"-"+fow3.getJSONObject(i).getString("wicketnbr"),"","", fow3.getJSONObject(i).getString("overnbr")));
                    }
                }



                JSONArray bowlers3 = inning3.getJSONArray("bowlers");
                for (int i = 0;i<bowlers3.length();i++){
                    String bowlerID = bowlers3.getJSONObject(i).getString("bowlerId");
                    String bowlerName = "";
                    for (int j = 0;j<players.length();j++){
                        if (bowlerID.equalsIgnoreCase(players.getJSONObject(j).getString("id"))){
                            bowlerName = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                            break;
                        }
                    }
                    this.team3bowlers.add(new Bowler(bowlerName.trim(),bowlers3.getJSONObject(i).getString("maiden"),bowlers3.getJSONObject(i).getString("over"),bowlers3.getJSONObject(i).getString("run"),bowlers3.getJSONObject(i).getString("wicket"),bowlers3.getJSONObject(i).getString("sr")));
                }
                if(inning3.has("extras")){
                    JSONObject extra3 = inning3.getJSONObject("extras");

                    this.team3bowlers.add(new Bowler("Extras -"+ " Total " + extra3.getString("total")+", Byes "+extra3.getString("byes")+", LegByes " + extra3.getString("legByes")+", WideBalls "+extra3.getString("wideBalls")+", NoBalls "+extra3.getString("noBalls")+", Penalty "+extra3.getString("penalty"),"", "", "", ""));
                }


            }


            if(data.has("4")){
                inning4 = data.getJSONObject("4");
                this.teamname4 = inning4.getString("battingteam")+" - "+inning4.getString("runs")+"/"+inning4.getString("wickets")+" ("+inning4.getString("overs")+")";
                this.team4bowlers.add(new Bowler("Bowler Name", "M", "O", "R", "W", "ER"));
                this.team4batsmen.add(new Batsman("Batsman Name", "B", "4s", "R", "6s", "SR"));
                JSONArray batsmen4 = inning4.getJSONArray("batsmen");

                for (int i = 0;i<batsmen4.length();i++){
                    String batsmanID = batsmen4.getJSONObject(i).getString("batsmanId");
                    String batsmanName = "";
                    for (int j = 0;j<players.length();j++){
                        if (batsmanID.equalsIgnoreCase(players.getJSONObject(j).getString("id"))){
                            batsmanName = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                            break;
                        }
                    }
                    try {
                        this.team4batsmen.add(new Batsman(batsmanName + "\n"+ batsmen4.getJSONObject(i).getString("outdescription"),batsmen4.getJSONObject(i).getString("ball"),batsmen4.getJSONObject(i).getString("four"),batsmen4.getJSONObject(i).getString("run"),batsmen4.getJSONObject(i).getString("six"),batsmen4.getJSONObject(i).getString("sr")));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }



                if(!inning4.getString("wickets").equalsIgnoreCase("0")){
                    this.team4batsmen.add(new Batsman("Fall Of Wickets", "", "", "", ""));
                    this.team4batsmen.add(new Batsman("Batsman Name", "Score", "", "", "Over"));
                    JSONArray fow4 = inning4.getJSONArray("fow");
                    for (int i = 0;i<fow4.length();i++){
                        String outBatsmanID = fow4.getJSONObject(i).getString("outBatsmanId");
                        String outBatsmanname = "";
                        for (int j = 0;j<players.length();j++){
                            if (outBatsmanID.equalsIgnoreCase(players.getJSONObject(j).getString("id")))
                                outBatsmanname = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                        }
                        this.team4batsmen.add(new Batsman(outBatsmanname,fow4.getJSONObject(i).getString("run")+"-"+fow4.getJSONObject(i).getString("wicketnbr"),"","", fow4.getJSONObject(i).getString("overnbr")));
                    }
                }



                JSONArray bowlers4 = inning4.getJSONArray("bowlers");
                for (int i = 0;i<bowlers4.length();i++){
                    String bowlerID = bowlers4.getJSONObject(i).getString("bowlerId");
                    String bowlerName = "";
                    for (int j = 0;j<players.length();j++){
                        if (bowlerID.equalsIgnoreCase(players.getJSONObject(j).getString("id"))){
                            bowlerName = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                            break;
                        }
                    }
                    this.team4bowlers.add(new Bowler(bowlerName.trim(),bowlers4.getJSONObject(i).getString("maiden"),bowlers4.getJSONObject(i).getString("over"),bowlers4.getJSONObject(i).getString("run"),bowlers4.getJSONObject(i).getString("wicket"),bowlers4.getJSONObject(i).getString("sr")));
                }
                if(inning4.has("extras")){
                    JSONObject extra4 = inning4.getJSONObject("extras");

                    this.team4bowlers.add(new Bowler("Extras -"+ " Total " + extra4.getString("total")+", Byes "+extra4.getString("byes")+", LegByes " + extra4.getString("legByes")+", WideBalls "+extra4.getString("wideBalls")+", NoBalls "+extra4.getString("noBalls")+", Penalty "+extra4.getString("penalty"),"", "", "", ""));
                }


            }


            this.teamname1 = inning.getString("battingteam")+" - "+inning.getString("runs")+"/"+inning.getString("wickets")+" ("+inning.getString("overs")+")";
            this.team1bowlers.add(new Bowler("Bowler Name", "M", "O", "R", "W", "ER"));
            this.team1batsmen.add(new Batsman("Batsman Name", "B", "4s", "R", "6s", "SR"));



            JSONArray batsmen = inning.getJSONArray("batsmen");


            for (int i = 0;i<batsmen.length();i++){
                String batsmanID = batsmen.getJSONObject(i).getString("batsmanId");
                String batsmanName = "";
                for (int j = 0;j<players.length();j++){
                    if (batsmanID.equalsIgnoreCase(players.getJSONObject(j).getString("id"))){
                        batsmanName = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                        break;
                    }
                }
                try {
                    this.team1batsmen.add(new Batsman(batsmanName+"\n"+ batsmen.getJSONObject(i).getString("outdescription"),batsmen.getJSONObject(i).getString("ball"),batsmen.getJSONObject(i).getString("four"),batsmen.getJSONObject(i).getString("run"),batsmen.getJSONObject(i).getString("six"),batsmen.getJSONObject(i).getString("sr")));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


            if(!inning.getString("wickets").equalsIgnoreCase("0")){
                this.team1batsmen.add(new Batsman("Fall Of Wickets", "", "", "", ""));
                this.team1batsmen.add(new Batsman("Batsman Name", "Score", "", "", "Over"));
                JSONArray fow = inning.getJSONArray("fow");
                for (int i = 0;i<fow.length();i++){
                    String outBatsmanID = fow.getJSONObject(i).getString("outBatsmanId");
                    String outBatsmanname = "";
                    for (int j = 0;j<players.length();j++){
                        if (outBatsmanID.equalsIgnoreCase(players.getJSONObject(j).getString("id")))
                            outBatsmanname = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                    }
                    this.team1batsmen.add(new Batsman(outBatsmanname, fow.getJSONObject(i).getString("run")+"-"+fow.getJSONObject(i).getString("wicketnbr"),"","", fow.getJSONObject(i).getString("overnbr")));
                }
            }


            JSONArray bowlers = inning.getJSONArray("bowlers");
            for (int i = 0;i<bowlers.length();i++){
                String bowlerID = bowlers.getJSONObject(i).getString("bowlerId");
                String bowlerName = "";
                for (int j = 0;j<players.length();j++){
                    if (bowlerID.equalsIgnoreCase(players.getJSONObject(j).getString("id"))){
                        bowlerName = players.getJSONObject(j).getString("fName")+" "+players.getJSONObject(j).getString("role");
                        break;
                    }
                }
                this.team1bowlers.add(new Bowler(bowlerName.trim(),bowlers.getJSONObject(i).getString("maiden"),bowlers.getJSONObject(i).getString("over"),bowlers.getJSONObject(i).getString("run"),bowlers.getJSONObject(i).getString("wicket"),bowlers.getJSONObject(i).getString("sr")));
            }
            if(inning.has("extras")){
                JSONObject extra = inning.getJSONObject("extras");

                this.team1bowlers.add(new Bowler("Extras -"+ " Total " + extra.getString("total")+", Byes "+extra.getString("byes")+", LegByes " + extra.getString("legByes")+", WideBalls "+extra.getString("wideBalls")+", NoBalls "+extra.getString("noBalls")+", Penalty "+extra.getString("penalty"),"", "", "", ""));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeListView();
    }


    private void parseJsonData(JSONObject response) {
        JSONObject scorecardObject = response;
        try {
            JSONObject bowler;
            JSONObject data = scorecardObject.getJSONObject("data");
            JSONObject Scorecard = data.getJSONObject("scorecard");
            JSONObject team1 = Scorecard.getJSONObject("team1_f");
            this.teamname1 = team1.getString("team_name")+" "+team1.getString("total");
            this.team1batsmen = new ArrayList();
            this.team2batsmen = new ArrayList();
            this.team1bowlers = new ArrayList();
            this.team2bowlers = new ArrayList();
            this.team3batsmen = new ArrayList();
            this.team3bowlers = new ArrayList();
            this.team4batsmen = new ArrayList();
            this.team4bowlers = new ArrayList();

            this.team1bowlers.add(new Bowler("Bowler Name", "M", "O", "R", "W", "ER"));
            this.team1batsmen.add(new Batsman("Batsman Name", "B", "4s", "R", "6s", "SR"));
            JSONObject team2 = Scorecard.getJSONObject("team2_f");
            this.teamname2 = team2.getString("team_name")+" "+team2.getString("total");;
            this.team2batsmen.add(new Batsman("Batsman Name", "B", "4s", "R", "6s", "SR"));
            this.team2bowlers.add(new Bowler("Bowler Name", "M", "O", "R", "W", "ER"));
            try {
                JSONArray bowlers = team1.getJSONArray("bowling");
                for(int i = 0; i <bowlers.length(); i++){
                    this.team1bowlers.add(new Bowler(bowlers.getJSONObject(i).getString("bowler"), bowlers.getJSONObject(i).getString("maiden"), bowlers.getJSONObject(i).getString("over"), bowlers.getJSONObject(i).getString("run"), bowlers.getJSONObject(i).getString("wicket")));
                }
                this.team1bowlers.add(new Bowler("Extras -"+ team1.getString("extras"),"", "", "", ""));
                /*Iterator<String> bowlerKeyIterator = bowlers.keys();
                while (bowlerKeyIterator.hasNext()) {
                    bowler = bowlers.getJSONObject((String) bowlerKeyIterator.next()).getJSONObject("bowler");
                    this.team1bowlers.add(new Bowler(bowler.getString("bowlerName"), bowler.getString("maiden"), bowler.getString("overs"), bowler.getString("run"), bowler.getString("wickets")));
                }*/
            } catch (JSONException e) {
            }
            try {
                //JSONObject players = team1.getJSONObject("players");
                JSONArray Batting = team1.getJSONArray("batting");
                for(int i = 0; i<Batting.length(); i++){
                    this.team1batsmen.add(new Batsman(Batting.getJSONObject(i).getString("batsman")+"\n"+Batting.getJSONObject(i).getString("outdesc"), Batting.getJSONObject(i).getString("ball"), Batting.getJSONObject(i).getString("no_of_4"), Batting.getJSONObject(i).getString("run"), Batting.getJSONObject(i).getString("no_of_6")));
                }
                this.team1batsmen.add(new Batsman("Fall Of Wickets", "", "", "", ""));
                this.team1batsmen.add(new Batsman("Batsman Name", "Score", "", "", "Over"));
                JSONArray FallOfWickets = team1.getJSONArray("fall_of_wic");
                for(int i = 0; i <FallOfWickets.length(); i++){
                    this.team1batsmen.add(new Batsman(FallOfWickets.getJSONObject(i).getString("name"), FallOfWickets.getJSONObject(i).getString("score"),"","", FallOfWickets.getJSONObject(i).getString("over")));
                }
                /*Iterator<String> playerKeyIterator = players.keys();
                while (playerKeyIterator.hasNext()) {
                    JSONObject player = players.getJSONObject((String) playerKeyIterator.next()).getJSONObject("player");
                    this.team1batsmen.add(new Batsman(player.getString("playerName"), player.getString("balls"), player.getString("fours"), player.getString("run"), player.getString("sixes")));
                }*/
            } catch (JSONException e2) {
            }
            try {
                //JSONObject bowlers2 = team2.getJSONObject("bowlers");
                JSONArray bowlers2 = team2.getJSONArray("bowling");
                for(int i = 0; i <bowlers2.length(); i++){
                    this.team2bowlers.add(new Bowler(bowlers2.getJSONObject(i).getString("bowler"), bowlers2.getJSONObject(i).getString("maiden"), bowlers2.getJSONObject(i).getString("over"), bowlers2.getJSONObject(i).getString("run"), bowlers2.getJSONObject(i).getString("wicket")));
                }

                this.team2bowlers.add(new Bowler("Extras -"+ team2.getString("extras"),"", "", "", ""));
               /* Iterator<String> bowlerKeyIterator2 = bowlers2.keys();
                while (bowlerKeyIterator2.hasNext()) {
                    bowler = bowlers2.getJSONObject((String) bowlerKeyIterator2.next()).getJSONObject("bowler");
                    this.team2bowlers.add(new Bowler(bowler.getString("bowlerName"), bowler.getString("maiden"), bowler.getString("overs"), bowler.getString("run"), bowler.getString("wickets")));
                }*/
            } catch (JSONException e3) {
            }
            try {
                JSONArray Batting2 = team2.getJSONArray("batting");
                for(int i = 0; i<Batting2.length(); i++){
                    this.team2batsmen.add(new Batsman(Batting2.getJSONObject(i).getString("batsman")+"\n"+Batting2.getJSONObject(i).getString("outdesc"), Batting2.getJSONObject(i).getString("ball"), Batting2.getJSONObject(i).getString("no_of_4"), Batting2.getJSONObject(i).getString("run"), Batting2.getJSONObject(i).getString("no_of_6")));
                }

                JSONArray FallOfWickets = team2.getJSONArray("fall_of_wic");
                this.team2batsmen.add(new Batsman("Fall Of Wickets", "", "", "", ""));
                this.team2batsmen.add(new Batsman("Batsman Name", "Score", "", "", "Over"));
                for(int i = 0; i <FallOfWickets.length(); i++){
                    this.team2batsmen.add(new Batsman(FallOfWickets.getJSONObject(i).getString("name"), FallOfWickets.getJSONObject(i).getString("score"),"","", FallOfWickets.getJSONObject(i).getString("over")));
                }
               /* JSONObject players2 = team2.getJSONObject("players");
                Iterator<String> playerKeyIterator2 = players2.keys();
                while (playerKeyIterator2.hasNext()) {
                    bowler = players2.getJSONObject((String) playerKeyIterator2.next()).getJSONObject("player");
                    this.team2batsmen.add(new Batsman(bowler.getString("playerName"), bowler.getString("balls"), bowler.getString("fours"), bowler.getString("run"), bowler.getString("sixes")));
                }*/
            } catch (JSONException e4) {
            }
            makeListView();
        } catch (JSONException e5) {
            makeListView();
        }
    }

    private void makeListView() {
        this.i1.batsmen = this.team1batsmen;
        this.i1.bowlers = this.team1bowlers;
        this.i1.team = this.teamname1;
        this.i1.extra = "9";
        this.i1.total = "290-10(50)";
        this.i1.toBat = "Kholi(c),Pandaya,Dhoni(wk)";
        this.i1.fow = "1-19,2-122";
        this.i2.batsmen = this.team2batsmen;
        this.i2.bowlers = this.team2bowlers;
        this.i2.team = this.teamname2;
        this.i2.extra = "10";
        this.i2.total = "170-3(35)";
        this.i2.toBat = "Kholi(c),Pandaya,Dhoni(wk)";
        this.i2.fow = "1-19,2-122";
        this.i3.batsmen = this.team3batsmen;
        this.i3.bowlers = this.team3bowlers;
        this.i3.team = this.teamname3;
        this.i3.extra = "9";
        this.i3.total = "290-10(50)";
        this.i3.toBat = "Kholi(c),Pandaya,Dhoni(wk)";
        this.i3.fow = "1-19,2-122";
        this.i4.batsmen = this.team4batsmen;
        this.i4.bowlers = this.team4bowlers;
        this.i4.team = this.teamname4;
        this.i4.extra = "9";
        this.i4.total = "290-10(50)";
        this.i4.toBat = "Kholi(c),Pandaya,Dhoni(wk)";
        this.i4.fow = "1-19,2-122";
        this.adapter.notifyDataSetChanged();
    }
}