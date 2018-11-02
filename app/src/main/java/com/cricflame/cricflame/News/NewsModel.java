package com.cricflame.cricflame.News;


public class NewsModel {
    private String Date;
    private String Headline;
    private String Image;
    private String Intro;
    private String Source;
    private String para;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }


}
