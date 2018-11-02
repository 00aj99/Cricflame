package com.cricflame.cricflame.LiveLine1.PitchReport;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
public class PitchReportPager extends FragmentStatePagerAdapter {

    int tabCount;
    public PitchReportPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PitchTab3 h2h = new PitchTab3();
                return h2h;
            case 1:
                PitchTab2 report = new PitchTab2();
                return report;
            case 2:
                PitchTab1 stats = new PitchTab1();
                return stats;
            case 3:
                PitchTab4 record = new PitchTab4();
                return record;
            case 4:
                PitchTab5 know = new PitchTab5();
                return know;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}