package com.infotech.fplcolosseum.features.homepage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.LayoutCupListItemBinding;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.LeagueDataModel;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.ArrayList;
import java.util.List;

public class CupListAdapter extends RecyclerView.Adapter<CupListAdapter.CupItemViewHolder> {

    private List<LeagueDataModel> leagueList;

    public CupListAdapter(List<LeagueDataModel> leagueList) {
        if(leagueList != null){
            this.leagueList = leagueList;
        } else {
            this.leagueList = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public CupItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCupListItemBinding view = LayoutCupListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new CupItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CupItemViewHolder holder, int position) {
        LeagueDataModel leagueDataModel = leagueList.get(position);
        holder.bind(leagueDataModel);

        // Handle click to expand/collapse
        holder.itemView.setOnClickListener(v -> {
            CustomUtil.startCupStatusActivity(v.getContext(), leagueDataModel.getId());
        });

    }

    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateCupList(List<LeagueDataModel> newLeagueList) {
        this.leagueList.clear();
        this.leagueList = newLeagueList;
        notifyDataSetChanged();
    }



    static class CupItemViewHolder extends RecyclerView.ViewHolder {

        LayoutCupListItemBinding mBinding;

        public CupItemViewHolder(@NonNull LayoutCupListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(LeagueDataModel leagueDataModel) {
            String cupName = leagueDataModel.getName() + " Cup";
            mBinding.leagueName.setText(cupName);
            mBinding.leagueName.setSelected(true);
        }
    }
}

