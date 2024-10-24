package com.infotech.fplcolosseum.features.league_information.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.LayoutPointsItemFooterBinding;
import com.infotech.fplcolosseum.databinding.LeagueStandingListItemBinding;
import com.infotech.fplcolosseum.features.gameweek.models.custom.PlayerDataModel;
import com.infotech.fplcolosseum.features.homepage.adapter.PointsAdapter;
import com.infotech.fplcolosseum.features.league_information.models.StandingResultDataModel;
import com.infotech.fplcolosseum.features.player_information.models.Fixtures;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;
import java.util.Objects;

public class LeagueStandingAdapter extends RecyclerView.Adapter<LeagueStandingAdapter.LeagueStandingViewHolder> {

    private List<StandingResultDataModel> leagueList;

    public LeagueStandingAdapter(List<StandingResultDataModel> leagueList) {
        this.leagueList = leagueList;
    }

    @NonNull
    @Override
    public LeagueStandingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new LeagueStandingViewHolder(LeagueStandingListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueStandingViewHolder holder, int position) {
        StandingResultDataModel leagueItem = leagueList.get(position);
        holder.bind(leagueItem);
    }

    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    static class LeagueStandingViewHolder extends RecyclerView.ViewHolder {

        LeagueStandingListItemBinding mBinding;

        public LeagueStandingViewHolder(@NonNull LeagueStandingListItemBinding b) {
            super(b.getRoot());
            this.mBinding = b;
        }

        public void bind(StandingResultDataModel resultDataModel) {

            mBinding.rankTV.setText(String.valueOf(resultDataModel.getRank()));

            mBinding.leagueName.setText(resultDataModel.getEntry_name());

            mBinding.managerName.setText(resultDataModel.getPlayer_name());

            mBinding.points.setText(String.valueOf(resultDataModel.getEvent_total()));

            mBinding.totalPoints.setText(String.valueOf(resultDataModel.getTotal()));

            if (resultDataModel.getRank() < resultDataModel.getLast_rank()) {
                mBinding.rankIcon.setImageResource(R.drawable.ic_up_arrow_green);
            } else if (resultDataModel.getRank() > resultDataModel.getLast_rank()) {
                mBinding.rankIcon.setImageResource(R.drawable.ic_down_arrow_red);
            } else {
                mBinding.rankIcon.setImageResource(R.drawable.ic_unchange_rank);
            }
        }
    }
}
