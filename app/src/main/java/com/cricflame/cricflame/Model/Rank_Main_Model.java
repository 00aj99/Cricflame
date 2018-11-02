package com.cricflame.cricflame.Model;

import java.util.List;

public class Rank_Main_Model {
    String Header;
    String Type;
    String Gender;
    String Format;
    List<RankingModel> Ranking_List;

    public String getHeader() {
        return Header;
    }

    public String getType() {
        return Type;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public List<RankingModel> getRanking_List() {
        return Ranking_List;
    }

    public void setRanking_List(List<RankingModel> ranking_List) {
        Ranking_List = ranking_List;
    }
}
