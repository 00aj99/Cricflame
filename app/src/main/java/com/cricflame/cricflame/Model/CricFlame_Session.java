package com.cricflame.cricflame.Model;

import android.support.annotation.NonNull;

public class CricFlame_Session implements Comparable<Object> {
    String Over;
    String Rate_Team;
    String RunScored;
    String Open;
    String Min;
    String Max;

    public String getOver() {
        return Over;
    }

    public void setOver(String over) {
        Over = over;
    }

    public String getRate_Team() {
        return Rate_Team;
    }

    public void setRate_Team(String rate_Team) {
        Rate_Team = rate_Team;
    }

    public String getRunScored() {
        return RunScored;
    }

    public void setRunScored(String runScored) {
        RunScored = runScored;
    }

    public String getOpen() {
        return Open;
    }

    public void setOpen(String open) {
        Open = open;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        CricFlame_Session f = (CricFlame_Session) o;
        return Integer.parseInt(this.Over) - Integer.parseInt(f.Over );
    }
}
