package com.cricflame.cricflame.Model;

public class RankingModel  {
    String type;
    String Flag;
    String Rating;
    String TeamName;
    String FormatType;
    String Gender;
    String PlayerType;

    public String getPlayerType() {
        return PlayerType;
    }

    public void setPlayerType(String playerType) {
        PlayerType = playerType;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getFormatType() {
        return FormatType;
    }

    public void setFormatType(String formatType) {
        FormatType = formatType;
    }
}
