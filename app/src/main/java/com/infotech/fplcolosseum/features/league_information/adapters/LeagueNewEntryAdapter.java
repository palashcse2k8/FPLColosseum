package com.infotech.fplcolosseum.features.league_information.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.LeagueNewEntryListItemBinding;
import com.infotech.fplcolosseum.databinding.LeagueStandingListItemBinding;
import com.infotech.fplcolosseum.features.league_information.models.LeagueNewEntryResultDataModel;
import com.infotech.fplcolosseum.features.league_information.models.StandingResultDataModel;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;

public class LeagueNewEntryAdapter extends RecyclerView.Adapter<LeagueNewEntryAdapter.LeagueNewEntryViewHolder> {

    private List<LeagueNewEntryResultDataModel> entryList;

    public LeagueNewEntryAdapter(List<LeagueNewEntryResultDataModel> leagueList) {
        if(leagueList != null && !leagueList.isEmpty())
            this.entryList = leagueList;
    }

    @NonNull
    @Override
    public LeagueNewEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new LeagueNewEntryViewHolder(LeagueNewEntryListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueNewEntryViewHolder holder, int position) {
        LeagueNewEntryResultDataModel leagueItem = entryList.get(position);
        holder.bind(leagueItem);
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    static class LeagueNewEntryViewHolder extends RecyclerView.ViewHolder {

        LeagueNewEntryListItemBinding mBinding;

        public LeagueNewEntryViewHolder(@NonNull LeagueNewEntryListItemBinding b) {
            super(b.getRoot());
            this.mBinding = b;
        }

        public void bind(LeagueNewEntryResultDataModel resultDataModel) {

            mBinding.leagueName.setText(resultDataModel.getEntry_name());

            String managerFullName = resultDataModel.getPlayer_first_name() + " " + resultDataModel.getPlayer_last_name();
            mBinding.managerName.setText(managerFullName) ;

            String teamId = "ID : " + resultDataModel.getEntry();
            mBinding.teamID.setText(teamId);

            String joiningTime = CustomUtil.convertUtcToLocalTime(resultDataModel.getJoined_time());
            mBinding.joiningTime.setText(joiningTime);
        }
    }
}
