package com.infotech.fplcolosseum.features.homepage.adapter;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.LayoutLeagueListItemBinding;
import com.infotech.fplcolosseum.databinding.LayoutPointsItemBinding;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.LeagueDataModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchElement;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchStats;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;
import java.util.Objects;

public class ClassicLeagueListAdapter extends RecyclerView.Adapter<ClassicLeagueListAdapter.LeagueItemViewHolder> {

    private List<LeagueDataModel> leagueList;

    public ClassicLeagueListAdapter(List<LeagueDataModel> leagueList) {
        this.leagueList = leagueList;
    }

    @NonNull
    @Override
    public LeagueItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutLeagueListItemBinding view = LayoutLeagueListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new LeagueItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueItemViewHolder holder, int position) {
        LeagueDataModel player = leagueList.get(position);
        holder.bind(player);

        // Handle click to expand/collapse
        holder.itemView.setOnClickListener(v -> {

        });

//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(activity, PlayerFullInformationActivity.class);
//            intent.putExtra("playerData", player); // Replace with actual player name
//            activity.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    public void updatePlayersList(List<LeagueDataModel> newLeagueList) {
        this.leagueList = newLeagueList;
        notifyDataSetChanged();
    }


    static class LeagueItemViewHolder extends RecyclerView.ViewHolder {

        LayoutLeagueListItemBinding mBinding;

        public LeagueItemViewHolder(@NonNull LayoutLeagueListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(LeagueDataModel leagueDataModel) {

            if(leagueDataModel.getEntry_rank() == 0) {
                mBinding.leagueRank.setText("-");
            } else {
                mBinding.leagueRank.setText(String.valueOf(leagueDataModel.getEntry_rank()));
            }

            mBinding.leagueName.setText(leagueDataModel.getName());

            if (leagueDataModel.getEntry_rank() < leagueDataModel.getEntry_last_rank()) {
                mBinding.rankIcon.setImageResource(R.drawable.ic_up_arrow_green);
            } else if (leagueDataModel.getEntry_rank() > leagueDataModel.getEntry_last_rank()) {
                mBinding.rankIcon.setImageResource(R.drawable.ic_down_arrow_red);
            } else if( leagueDataModel.getEntry_rank() == 0){
                mBinding.rankIcon.setImageResource(R.drawable.ic_no_rank);
            } else {
                mBinding.rankIcon.setImageResource(R.drawable.ic_unchange_rank);
            }
        }
    }
}
