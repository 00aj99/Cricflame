package com.cricflame.cricflame.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cricflame.cricflame.R;

public class UpcomingMatchesFragment extends Fragment {

    LinearLayout lltRateRecordLyt,lltUpcomingMatchesLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_matches, container, false);
        lltRateRecordLyt = view.findViewById(R.id.llt_rate_record_lyt);
        lltUpcomingMatchesLayout = view.findViewById(R.id.llt_upcoming_matches_layout);
        lltUpcomingMatchesLayout.setVisibility(View.VISIBLE);
        lltRateRecordLyt.setVisibility(View.GONE);
        return view;
    }

}
