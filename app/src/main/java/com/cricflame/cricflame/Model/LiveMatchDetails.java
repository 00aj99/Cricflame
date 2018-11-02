package com.cricflame.cricflame.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.cricflame.cricflame.LiveLine1.Sessions.Session;

public class LiveMatchDetails implements Comparable<LiveMatchDetails>, Parcelable {
    public static final Creator<LiveMatchDetails> CREATOR = new C09032();
    String ballsdone;
    String ballsdone2;
    String comment;
    String date;
    String flag1;
    String flag2;
    String inning;
    boolean isAd;
    String key;
    String match_number;
    String order;
    String rate;
    String rate2;
    String rate_team;
    String score;
    String score2;
    String series_name;
    String status;
    String t1;
    String t2;
    String target;
    String team1;
    String team2;
    long time;
    String title;
    String total_balls;
    String type;
    String venue;
    String wicket;
    String wicket2;
    String id;
    String ScoreCardId;
    String IsCricBuzz;
    String EventId;
    String market_Id;
    String i1;
    String i2;
    String i3;
    String oddstype;
    String SessionType;
    String IsSuperOver;

    static class C09032 implements Creator<LiveMatchDetails> {
        C09032() {
        }

        public LiveMatchDetails createFromParcel(Parcel in) {
            return new LiveMatchDetails(in);
        }

        public LiveMatchDetails[] newArray(int size) {
            return new LiveMatchDetails[size];
        }
    }


    protected LiveMatchDetails(Parcel in) {
        boolean z = false;
        this.key = "";
        this.ballsdone = "";
        this.ballsdone2 = "";
        this.comment = "";
        this.date = "";
        this.flag1 = "";
        this.flag2 = "";
        this.inning = "";
        this.match_number = "";
        this.rate = "";
        this.rate2 = "";
        this.rate_team = "";
        this.score = "";
        this.score2 = "";
        this.series_name = "";
        this.status = "";
        this.t1 = "";
        this.t2 = "";
        this.team1 = "";
        this.team2 = "";
        this.title = "";
        this.type = "";
        this.venue = "";
        this.wicket = "";
        this.wicket2 = "";
        this.target = "";
        this.total_balls = "";
        this.order = "";
        this.isAd = false;
        this.time = 0;
        this.key = in.readString();
        this.ballsdone = in.readString();
        this.ballsdone2 = in.readString();
        this.comment = in.readString();
        this.date = in.readString();
        this.flag1 = in.readString();
        this.flag2 = in.readString();
        this.inning = in.readString();
        this.match_number = in.readString();
        this.rate = in.readString();
        this.rate2 = in.readString();
        this.rate_team = in.readString();
        this.score = in.readString();
        this.score2 = in.readString();
        this.series_name = in.readString();
        this.status = in.readString();
        this.t1 = in.readString();
        this.t2 = in.readString();
        this.team1 = in.readString();
        this.team2 = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.venue = in.readString();
        this.wicket = in.readString();
        this.wicket2 = in.readString();
        this.target = in.readString();
        this.total_balls = in.readString();
        this.order = in.readString();
        if (in.readByte() != (byte) 0) {
            z = true;
        }
        this.isAd = z;
        this.id = in.readString();
        this.ScoreCardId = in.readString();
        this.IsCricBuzz = in.readString();
        this.EventId = in.readString();
        this.market_Id = in.readString();
        this.i1 = in.readString();
        this.i2 = in.readString();
        this.i3 = in.readString();
        this.oddstype = in.readString();
        this.SessionType = in.readString();
        this.IsSuperOver = in.readString();
    }


    public LiveMatchDetails(double order) {
        this.key = "";
        this.ballsdone = "";
        this.ballsdone2 = "";
        this.comment = "";
        this.date = "";
        this.flag1 = "";
        this.flag2 = "";
        this.inning = "";
        this.match_number = "";
        this.rate = "";
        this.rate2 = "";
        this.rate_team = "";
        this.score = "";
        this.score2 = "";
        this.series_name = "";
        this.status = "";
        this.t1 = "";
        this.t2 = "";
        this.team1 = "";
        this.team2 = "";
        this.title = "";
        this.type = "";
        this.venue = "";
        this.wicket = "";
        this.wicket2 = "";
        this.target = "";
        this.total_balls = "";
        this.order = "";
        this.isAd = false;
        this.time = 0;
        this.isAd = true;
        this.order = "" + order;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTotal_balls() {
        return this.total_balls;
    }

    public void setTotal_balls(String total_balls) {
        this.total_balls = total_balls;
    }

    public String getMarket_Id() {
        return market_Id;
    }

    public void setMarket_Id(String market_Id) {
        this.market_Id = market_Id;
    }

    public boolean isAd() {
        return this.isAd;
    }

    public String getIsCricBuzz() {
        return IsCricBuzz;
    }

    public String getOddstype() {
        return oddstype;
    }

    public void setOddstype(String oddstype) {
        this.oddstype = oddstype;
    }

    public String getI1() {
        return i1;
    }

    public void setI1(String i1) {
        this.i1 = i1;
    }

    public String getI2() {
        return i2;
    }

    public void setI2(String i2) {
        this.i2 = i2;
    }

    public String getI3() {
        return i3;
    }

    public void setI3(String i3) {
        this.i3 = i3;
    }

    public String getSessionType() {
        return SessionType;
    }

    public void setSessionType(String sessionType) {
        SessionType = sessionType;
    }

    public String getIsSuperOver() {
        return IsSuperOver;
    }

    public void setIsSuperOver(String isSuperOver) {
        IsSuperOver = isSuperOver;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public void setIsCricBuzz(String isCricBuzz) {
        IsCricBuzz = isCricBuzz;
    }

    public LiveMatchDetails(String key, String ballsdone, String ballsdone2, String comment, String date, String flag1, String flag2, String inning, String match_number, String rate, String rate2, String rate_team, String score, String score2, String series_name, String status, String t1, String t2, String team1, String team2, String title, String type, String venue, String wicket, String wicket2, String target, String totalballs, String order, String id, String ScoreCardId, String IsCricBuzz, String EventId, String Market_id,String i1, String i2, String i3, String oddstype, String SessionType, String IsSuperOver) {
        this.key = "";
        this.ballsdone = "";
        this.ballsdone2 = "";
        this.comment = "";
        this.date = "";
        this.flag1 = "";
        this.flag2 = "";
        this.inning = "";
        this.match_number = "";
        this.rate = "";
        this.rate2 = "";
        this.rate_team = "";
        this.score = "";
        this.score2 = "";
        this.series_name = "";
        this.status = "";
        this.t1 = "";
        this.t2 = "";
        this.team1 = "";
        this.team2 = "";
        this.title = "";
        this.type = "";
        this.venue = "";
        this.wicket = "";
        this.wicket2 = "";
        this.target = "";
        this.total_balls = "";
        this.order = "";
        this.isAd = false;
        this.time = 0;
        this.key = key;
        this.ballsdone = ballsdone;
        this.ballsdone2 = ballsdone2;
        this.comment = comment;
        this.date = date;
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.inning = inning;
        this.match_number = match_number;
        this.rate = rate;
        this.rate2 = rate2;
        this.rate_team = rate_team;
        this.score = score;
        this.score2 = score2;
        this.series_name = series_name;
        this.status = status;
        this.t1 = t1;
        this.t2 = t2;
        this.team1 = team1;
        this.team2 = team2;
        this.title = title;
        this.type = type;
        this.venue = venue;
        this.wicket = wicket;
        this.wicket2 = wicket2;
        this.target = target;
        this.total_balls = totalballs;
        this.order = order;
        this.id = id;
        this.ScoreCardId = ScoreCardId;
        this.IsCricBuzz = IsCricBuzz;
        this.EventId = EventId;
        this.market_Id = Market_id;
        this.i1 = i1;
        this.i2 = i2;
        this.i3 = i3;
        this.oddstype = oddstype;
        this.SessionType = SessionType;
        this.IsSuperOver = IsSuperOver;
    }

    public String getBallsdone() {
        return this.ballsdone;
    }

    public void setBallsdone(String ballsdone) {
        this.ballsdone = ballsdone;
    }

    public String getBallsdone2() {
        return this.ballsdone2;
    }

    public void setBallsdone2(String ballsdone2) {
        this.ballsdone2 = ballsdone2;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScoreCardId() {
        return ScoreCardId;
    }

    public void setScoreCardId(String scoreCardId) {
        ScoreCardId = scoreCardId;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlag1() {
        return this.flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return this.flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getInning() {
        return this.inning;
    }

    public void setInning(String inning) {
        this.inning = inning;
    }

    public String getMatch_number() {
        return this.match_number;
    }

    public void setMatch_number(String match_number) {
        this.match_number = match_number;
    }

    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate2() {
        return this.rate2;
    }

    public void setRate2(String rate2) {
        this.rate2 = rate2;
    }

    public String getRate_team() {
        return this.rate_team;
    }

    public void setRate_team(String rate_team) {
        this.rate_team = rate_team;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore2() {
        return this.score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getSeries_name() {
        return this.series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getT1() {
        return this.t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return this.t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getTeam1() {
        return this.team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return this.team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVenue() {
        return this.venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getWicket() {
        return this.wicket;
    }

    public void setWicket(String wicket) {
        this.wicket = wicket;
    }

    public String getWicket2() {
        return this.wicket2;
    }

    public void setWicket2(String wicket2) {
        this.wicket2 = wicket2;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTotalballs(String total_balls) {
        this.total_balls = total_balls;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean equals(Object obj) {
        LiveMatchDetails liveMatchDetails = (LiveMatchDetails) obj;
        if (this.ballsdone.equals(liveMatchDetails.ballsdone) && this.ballsdone2.equals(liveMatchDetails.ballsdone2) && this.comment.equals(liveMatchDetails.comment) && this.date.equals(liveMatchDetails.date) && this.flag1.equals(liveMatchDetails.flag1) && this.flag2.equals(liveMatchDetails.flag2) && this.inning.equals(liveMatchDetails.inning) && this.match_number.equals(liveMatchDetails.match_number) && this.rate.equals(liveMatchDetails.rate) && this.rate2.equals(liveMatchDetails.rate2) && this.rate_team.equals(liveMatchDetails.rate_team) && this.score.equals(liveMatchDetails.score) && this.score2.equals(liveMatchDetails.score2) && this.series_name.equals(liveMatchDetails.series_name) && this.status.equals(liveMatchDetails.status) && this.t1.equals(liveMatchDetails.t1) && this.t2.equals(liveMatchDetails.t2) && this.team1.equals(liveMatchDetails.team1) && this.team2.equals(liveMatchDetails.team2) && this.title.equals(liveMatchDetails.title) && this.type.equals(liveMatchDetails.type) && this.venue.equals(liveMatchDetails.venue) && this.wicket.equals(liveMatchDetails.wicket) && this.wicket2.equals(liveMatchDetails.wicket2) && this.target.equals(liveMatchDetails.target) && this.time == liveMatchDetails.time && this.total_balls.equals(liveMatchDetails.total_balls) && this.order.equals(liveMatchDetails.order)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.isAd + " " + this.key + " " + this.series_name + " " + this.match_number;
    }

    public int compareTo(@NonNull LiveMatchDetails liveMatchDetails) {
        if (Double.parseDouble(this.order) - Double.parseDouble(liveMatchDetails.getOrder()) > 0.0d) {
            return 1;
        }
        return Double.parseDouble(this.order) - Double.parseDouble(liveMatchDetails.getOrder()) < 0.0d ? -1 : 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeString(this.ballsdone);
        parcel.writeString(this.ballsdone2);
        parcel.writeString(this.comment);
        parcel.writeString(this.date);
        parcel.writeString(this.flag1);
        parcel.writeString(this.flag2);
        parcel.writeString(this.inning);
        parcel.writeString(this.match_number);
        parcel.writeString(this.rate);
        parcel.writeString(this.rate2);
        parcel.writeString(this.rate_team);
        parcel.writeString(this.score);
        parcel.writeString(this.score2);
        parcel.writeString(this.series_name);
        parcel.writeString(this.status);
        parcel.writeString(this.t1);
        parcel.writeString(this.t2);
        parcel.writeString(this.team1);
        parcel.writeString(this.team2);
        parcel.writeString(this.title);
        parcel.writeString(this.type);
        parcel.writeString(this.venue);
        parcel.writeString(this.wicket);
        parcel.writeString(this.wicket2);
        parcel.writeString(this.target);
        parcel.writeString(this.total_balls);
        parcel.writeString(this.order);
        parcel.writeByte((byte) (this.isAd ? 1 : 0));
        parcel.writeString(this.id);
        parcel.writeString(this.ScoreCardId);
        parcel.writeString(this.IsCricBuzz);
        parcel.writeString(i1);
        parcel.writeString(i2);
        parcel.writeString(i3);
        parcel.writeString(oddstype);
        parcel.writeString(SessionType);
        parcel.writeString(IsSuperOver);
    }
}
