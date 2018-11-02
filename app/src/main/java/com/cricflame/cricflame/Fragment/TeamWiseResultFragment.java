package com.cricflame.cricflame.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricflame.cricflame.Adapter.TeamWiseResultAdapter;
import com.cricflame.cricflame.Common.ServiceHandler;
import com.cricflame.cricflame.Global;
import com.cricflame.cricflame.Model.LoadingDialog;
import com.cricflame.cricflame.Model.TeamWiseModel;
import com.cricflame.cricflame.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;


public class TeamWiseResultFragment extends Fragment {

    private ArrayList<TeamWiseModel> teamNames = new ArrayList<TeamWiseModel>();
    private RecyclerView rcvDateWiseResult;
    private TeamWiseResultAdapter teamWiseResultAdapter;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_wise_result, container, false);

        init(view);
        return view;
    }

    private void init(View view){

        teamWiseResultAdapter = new TeamWiseResultAdapter(getActivity(),teamNames,"ALL");
        loadingDialog = new LoadingDialog(getActivity());
        rcvDateWiseResult = (RecyclerView) view.findViewById(R.id.rcv_team_wise_result);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rcvDateWiseResult.setLayoutManager(mLayoutManager);
        rcvDateWiseResult.setAdapter(teamWiseResultAdapter);
        rcvDateWiseResult.setNestedScrollingEnabled(false);
        new GetAllTeams().execute();
    }

    public class GetAllTeams extends AsyncTask<String,Void,String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ServiceHandler sh = new ServiceHandler();
            HashMap<String, String> args1 = new HashMap<>();
            args1.put("type", "result");
            String resultServer = sh.makePostCall(Global.Comman_Url+"teamName", args1);
            try{
                JSONArray TeamArray = new JSONArray(resultServer);

                for(int i = 0; i <TeamArray.length(); i++){
                    TeamWiseModel list = new TeamWiseModel(TeamArray.getJSONObject(i).getString("team1"),TeamArray.getJSONObject(i).getString("team1_flag"));
                    teamNames.add(list);
                }


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(loadingDialog.isShowing())
                loadingDialog.dismiss();
            try{
                teamWiseResultAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
