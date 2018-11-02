package com.cricflame.cricflame.Model;

import java.io.Serializable;

/**
 * Created by Deepak Sharma on 05/10/2017.
 */

public class TourData implements Serializable {

    String id,league_name,start_date,strat_date,end_date,month,monthId,heading,description,image;
String status,league_type;

String Date;
String SeriesName;
String MatchName;
String Team_One_Name;
String Team_One_Flag;
String Team_Two_Name;
String Team_Two_Flag;
String MatchTime;
String Match_Venue;


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSeriesName() {
        return SeriesName;
    }

    public void setSeriesName(String seriesName) {
        SeriesName = seriesName;
    }

    public String getMatchName() {
        return MatchName;
    }

    public void setMatchName(String matchName) {
        MatchName = matchName;
    }

    public String getTeam_One_Name() {
        return Team_One_Name;
    }

    public void setTeam_One_Name(String team_One_Name) {
        Team_One_Name = team_One_Name;
    }

    public String getTeam_One_Flag() {
        return Team_One_Flag;
    }

    public void setTeam_One_Flag(String team_One_Flag) {
        Team_One_Flag = team_One_Flag;
    }

    public String getTeam_Two_Name() {
        return Team_Two_Name;
    }

    public void setTeam_Two_Name(String team_Two_Name) {
        Team_Two_Name = team_Two_Name;
    }

    public String getTeam_Two_Flag() {
        return Team_Two_Flag;
    }

    public void setTeam_Two_Flag(String team_Two_Flag) {
        Team_Two_Flag = team_Two_Flag;
    }

    public String getMatchTime() {
        return MatchTime;
    }

    public void setMatchTime(String matchTime) {
        MatchTime = matchTime;
    }

    public String getMatch_Venue() {
        return Match_Venue;
    }

    public void setMatch_Venue(String match_Venue) {
        Match_Venue = match_Venue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeague_type() {
        return league_type;
    }

    public void setLeague_type(String league_type) {
        this.league_type = league_type;
    }

    int imageResource;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }



    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getStrat_date() {
        return strat_date;
    }

    public void setStrat_date(String strat_date) {
        this.strat_date = strat_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonthId() {
        return monthId;
    }

    public void setMonthId(String monthId) {
        this.monthId = monthId;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
