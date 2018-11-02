package com.cricflame.cricflame.Model;

public class PointsTableData {
    private String f1084L;
    private String NR;
    private String NRR;
    private String f1085P;
    private String Pts;
    private String f1086W;
    private String betfair_name;
    private String cuprate;
    private String group_id;
    private String group_name;
    private String id;
    boolean isBlank = false;
    boolean isGroup = false;
    boolean isLoading = false;
    private String series_id;
    private String team_name;

    public boolean isGroup() {
        return this.isGroup;
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public boolean isBlank() {
        return this.isBlank;
    }

    public PointsTableData(boolean loading) {
        if (loading) {
            this.isLoading = true;
        } else {
            this.isBlank = true;
        }
    }

    public PointsTableData(String group_name) {
        this.group_name = group_name;
        this.isGroup = true;
    }

    public PointsTableData(String id, String series_id, String group_id, String team_name, String betfair_name, String p, String w, String l, String NR, String pts, String NRR, String cuprate, String group_name) {
        this.id = id;
        this.series_id = series_id;
        this.group_id = group_id;
        this.team_name = team_name;
        this.betfair_name = betfair_name;
        this.f1085P = p;
        this.f1086W = w;
        this.f1084L = l;
        this.NR = NR;
        this.Pts = pts;
        this.NRR = NRR;
        this.cuprate = cuprate;
        this.group_name = group_name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeriesId() {
        return this.series_id;
    }

    public void setSeriesId(String series_id) {
        this.series_id = series_id;
    }

    public String getGroupId() {
        return this.group_id;
    }

    public void setGroupId(String group_id) {
        this.group_id = group_id;
    }

    public String getTeamName() {
        return this.team_name;
    }

    public void setTeamName(String team_name) {
        this.team_name = team_name;
    }

    public String getBetfairName() {
        return this.betfair_name;
    }

    public void setBetfairName(String betfair_name) {
        this.betfair_name = betfair_name;
    }

    public String getP() {
        return this.f1085P;
    }

    public void setP(String P) {
        this.f1085P = P;
    }

    public String getW() {
        return this.f1086W;
    }

    public void setW(String W) {
        this.f1086W = W;
    }

    public String getL() {
        return this.f1084L;
    }

    public void setL(String L) {
        this.f1084L = L;
    }

    public String getNR() {
        return this.NR;
    }

    public void setNR(String NR) {
        this.NR = NR;
    }

    public String getPts() {
        return this.Pts;
    }

    public void setPts(String Pts) {
        this.Pts = Pts;
    }

    public String getNRR() {
        return this.NRR;
    }

    public void setNRR(String NRR) {
        this.NRR = NRR;
    }

    public String getCuprate() {
        return this.cuprate;
    }

    public void setCuprate(String cuprate) {
        this.cuprate = cuprate;
    }

    public String getGroupName() {
        return this.group_name;
    }

    public void setGroupName(String group_name) {
        this.group_name = group_name;
    }
}
