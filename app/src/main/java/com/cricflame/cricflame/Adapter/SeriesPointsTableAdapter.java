package com.cricflame.cricflame.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricflame.cricflame.Model.PointsTableData;
import com.cricflame.cricflame.R;

import java.util.ArrayList;

public class SeriesPointsTableAdapter extends Adapter<ViewHolder> {
    static int GROUP = 0;
    static int INDICATOR = 2;
    static int LOADING = 4;
    static int TEAM = 1;
    ArrayList<PointsTableData> pointsTableDataArrayList;

    public class GroupHolder extends ViewHolder {
        TextView group;

        public GroupHolder(View itemView) {
            super(itemView);
            this.group = (TextView) itemView.findViewById(R.id.points_table_group_textview);
        }
    }

    public class NothingHolder extends ViewHolder {
        public NothingHolder(View itemView) {
            super(itemView);
        }
    }

    public class TeamHolder extends ViewHolder {
        View container;
        TextView cr;
        TextView lost;
        TextView noresult;
        TextView nrr;
        TextView played;
        TextView points;
        TextView teamname;
        TextView won;

        public TeamHolder(View itemView) {
            super(itemView);
            this.container = itemView;
            this.teamname = (TextView) itemView.findViewById(R.id.team_name_in_table);
            this.played = (TextView) itemView.findViewById(R.id.played_matches_in_table);
            this.won = (TextView) itemView.findViewById(R.id.won_matches_in_table);
            this.lost = (TextView) itemView.findViewById(R.id.lost_matches_in_table);
            this.noresult = (TextView) itemView.findViewById(R.id.noresult_matches_in_table);
            this.points = (TextView) itemView.findViewById(R.id.points_in_table);
            this.nrr = (TextView) itemView.findViewById(R.id.nrr_in_table);
            //this.cr = (TextView) itemView.findViewById(R.id.cup_rate_in_table);
        }
    }

    public SeriesPointsTableAdapter(ArrayList<PointsTableData> pointsTableData) {
        this.pointsTableDataArrayList = pointsTableData;
    }

    public int getItemViewType(int position) {
        if (((PointsTableData) this.pointsTableDataArrayList.get(position)).isGroup()) {
            return GROUP;
        }
        if (((PointsTableData) this.pointsTableDataArrayList.get(position)).isBlank()) {
            return INDICATOR;
        }
        if (((PointsTableData) this.pointsTableDataArrayList.get(position)).isLoading()) {
            return LOADING;
        }
        return TEAM;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TEAM) {
            return new TeamHolder(inflater.inflate(R.layout.team_in_points_table, parent, false));
        }
        if (viewType == GROUP) {
            return new GroupHolder(inflater.inflate(R.layout.group_textview, parent, false));
        }
        if (viewType == INDICATOR) {
            return new NothingHolder(inflater.inflate(R.layout.indicator_in_points_table, parent, false));
        }
        return new NothingHolder(inflater.inflate(R.layout.loading_item, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PointsTableData pointsTableData = (PointsTableData) this.pointsTableDataArrayList.get(position);
        if (holder instanceof TeamHolder) {
            if (position % 2 == 0) {
                ((TeamHolder) holder).container.setBackgroundColor(Color.argb(20, 255, 255, 255));
            } else {
                ((TeamHolder) holder).container.setBackgroundColor(Color.argb(0, 255, 255, 255));
            }
            ((TeamHolder) holder).teamname.setText(pointsTableData.getTeamName());
            ((TeamHolder) holder).played.setText(pointsTableData.getP());
            ((TeamHolder) holder).won.setText(pointsTableData.getW());
            ((TeamHolder) holder).lost.setText(pointsTableData.getL());
            ((TeamHolder) holder).noresult.setText(pointsTableData.getNR());
            ((TeamHolder) holder).points.setText(pointsTableData.getPts());
            ((TeamHolder) holder).nrr.setText(pointsTableData.getNRR());
           // ((TeamHolder) holder).cr.setText(pointsTableData.getCuprate());
        } else if (holder instanceof GroupHolder) {
            ((GroupHolder) holder).group.setText(pointsTableData.getGroupName());
        }
    }

    public int getItemCount() {
        return this.pointsTableDataArrayList.size();
    }
}
