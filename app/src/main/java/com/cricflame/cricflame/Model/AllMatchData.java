package com.cricflame.cricflame.Model;

import java.io.Serializable;

/**
 * Created by Deepak Sharma on 06/10/2017.
 */

public class AllMatchData implements Serializable{




    public String id, team1_name, team1_score, team1_flag, team2_name, team2_score,
            team2_flag, result, match_date, match_name, team1_over, team2_over, venue;



    private String winLose;
    private String firstBatWin;

    public void setSeries_id(String series_id) {
        this.series_id = series_id;
    }

    private String series_id;

    public String getWinLose() {
        return winLose;
    }

    public void setWinLose(String winLose) {
        this.winLose = winLose;
    }

    public String getFirstBatWin() {
        return firstBatWin;
    }

    public void setFirstBatWin(String firstBatWin) {
        this.firstBatWin = firstBatWin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public String getTeam1_score() {
        return team1_score;
    }

    public void setTeam1_score(String team1_score) {
        this.team1_score = team1_score;
    }

    public String getTeam1_flag() {
        return team1_flag;
    }

    public void setTeam1_flag(String team1_flag) {
        this.team1_flag = team1_flag;
    }

    public String getTeam2_name() {
        return team2_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public String getTeam2_score() {
        return team2_score;
    }

    public void setTeam2_score(String team2_score) {
        this.team2_score = team2_score;
    }

    public String getTeam2_flag() {
        return team2_flag;
    }

    public void setTeam2_flag(String team2_flag) {
        this.team2_flag = team2_flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getTeam1_over() {
        return team1_over;
    }

    public void setTeam1_over(String team1_over) {
        this.team1_over = team1_over;
    }

    public String getTeam2_over() {
        return team2_over;
    }

    public void setTeam2_over(String team2_over) {
        this.team2_over = team2_over;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSeries_id() {
        return series_id;
    }
}
