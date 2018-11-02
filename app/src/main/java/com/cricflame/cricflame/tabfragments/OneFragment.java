package com.cricflame.cricflame.tabfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricflame.cricflame.R;
import com.cricflame.cricflame.TourDetailsActivity;


public class OneFragment extends Fragment{

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_one,container,false);
        TextView replace= (TextView) view.findViewById(R.id.textView2);
        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TourDetailsActivity.class));
//                Fragment newFragment = new Browse_Series_Fragment();
//
//                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame_container,newFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
        return view;
        // Inflate the layout for this fragment

    }

}