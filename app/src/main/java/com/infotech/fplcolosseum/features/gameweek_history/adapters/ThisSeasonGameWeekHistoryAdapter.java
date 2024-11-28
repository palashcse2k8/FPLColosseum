package com.infotech.fplcolosseum.features.gameweek_history.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.ActivityGameweekHistoryThisSeasonItemBinding;
import com.infotech.fplcolosseum.databinding.ActivityGameweekHistoryThisSeasonItemHeaderBinding;
import com.infotech.fplcolosseum.databinding.NoDataLayoutBinding;
import com.infotech.fplcolosseum.features.gameweek_history.models.CurrentSeasonHistoryModel;

import java.util.List;

public class ThisSeasonGameWeekHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_NO_DATA = 2;

    private List<CurrentSeasonHistoryModel> dataList;
    private String sectionTitle;

    public ThisSeasonGameWeekHistoryAdapter(List<CurrentSeasonHistoryModel> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        // If no data, return 2 (header + no data item)
        // If data exists, return items + header
        return dataList.isEmpty() ? 2 : dataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }

        if (dataList.isEmpty()) {
            return VIEW_TYPE_NO_DATA;
        }

        return VIEW_TYPE_ITEM;
    }

    public void updateAdapterData(List<CurrentSeasonHistoryModel> newData) {

        if (newData == null) return;

        this.dataList.clear();
        this.dataList = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return switch (viewType) {
            case VIEW_TYPE_HEADER ->
                    new HeaderViewHolder(ActivityGameweekHistoryThisSeasonItemHeaderBinding.inflate(inflater, parent, false));
            case VIEW_TYPE_NO_DATA ->
                    new NoDataViewHolder(NoDataLayoutBinding.inflate(inflater, parent, false));
            default ->
                new ItemViewHolder(ActivityGameweekHistoryThisSeasonItemBinding.inflate(inflater, parent, false));
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            // Bind header
            ((HeaderViewHolder) holder).bind();
        } else if (holder instanceof NoDataViewHolder) {
            // Bind no data view
            ((NoDataViewHolder) holder).bind("This Season Not Started Yet!");
        } else if (holder instanceof ItemViewHolder) {
            // Bind regular item
            ((ItemViewHolder) holder).bind(dataList.get(position - 1));
        }
    }

    // ViewHolder classes
    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        ActivityGameweekHistoryThisSeasonItemHeaderBinding mBinding;

        public HeaderViewHolder(@NonNull ActivityGameweekHistoryThisSeasonItemHeaderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind() {
            // Bind item data
        }
    }

    private static class NoDataViewHolder extends RecyclerView.ViewHolder {

        NoDataLayoutBinding mBinding;
        public NoDataViewHolder(@NonNull NoDataLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(String sectionTitle) {
            mBinding.sectionText.setText(sectionTitle);
        }
    }

    // Regular item ViewHolder
    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        ActivityGameweekHistoryThisSeasonItemBinding mBinding;

        public ItemViewHolder(@NonNull ActivityGameweekHistoryThisSeasonItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(CurrentSeasonHistoryModel item) {
            mBinding.gameWeek.setText(String.valueOf(item.getEvent()));
            mBinding.overAllRank.setText(String.valueOf(item.getOverall_rank()));
            mBinding.overAllPoints.setText(String.valueOf(item.getTotal_points()));
            mBinding.gameWeekRank.setText(String.valueOf(item.getRank()));
            mBinding.gameWeekPoints.setText(String.valueOf(item.getPoints()));
            mBinding.pointsOnBench.setText(String.valueOf(item.getPoints_on_bench()));
            mBinding.transferMade.setText(String.valueOf(item.getEvent_transfers()));
            mBinding.transferCost.setText(String.valueOf(item.getEvent_transfers_cost()));
            mBinding.squadValue.setText(String.valueOf(item.getValue()));
        }
    }
}
