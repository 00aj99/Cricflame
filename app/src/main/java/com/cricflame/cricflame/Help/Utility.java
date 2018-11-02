package com.cricflame.cricflame.Help;

/**
 * Created by yashlam on 2/5/2018.
 */

public class Utility {
    public static String returnMonth(String month){
        int mon = Integer.parseInt(month);
        switch (mon){
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return null;
        }

    }
}
