package com.infotech.fplcolosseum.features.gameweek_history.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.ActivityGameweekHistoryThisSeasonItemBinding;
import com.infotech.fplcolosseum.databinding.ActivityGameweekHistoryThisSeasonItemHeaderBinding;
import com.infotech.fplcolosseum.databinding.NoDataLayoutBinding;
import com.infotech.fplcolosseum.features.gameweek_history.models.CurrentSeasonHistoryModel;
import com.infotech.fplcolosseum.utilities.CustomUtil;

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

            CurrentSeasonHistoryModel currentItem = dataList.get(position - 1);
            CurrentSeasonHistoryModel nextItem = (position < dataList.size()) ? dataList.get(position) : null;

            ((ItemViewHolder) holder).bind(currentItem, nextItem);
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        if (holder instanceof HeaderViewHolder) {
            // Bind header
            ((HeaderViewHolder) holder).unbind();
        } else if (holder instanceof NoDataViewHolder) {
            // Bind no data view
            ((NoDataViewHolder) holder).unbind();
        } else if (holder instanceof ItemViewHolder) {
            // Bind regular item
            ((ItemViewHolder) holder).unbind();
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

        public void unbind() {
            mBinding = null;
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

        public void unbind() {
            mBinding = null;
        }
    }

    // Regular item ViewHolder
    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        ActivityGameweekHistoryThisSeasonItemBinding mBinding;

        public ItemViewHolder(@NonNull ActivityGameweekHistoryThisSeasonItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(CurrentSeasonHistoryModel currentItem, CurrentSeasonHistoryModel nextItem) {
            mBinding.gameWeek.setText(CustomUtil.formatNumber(currentItem.getEvent()));
            mBinding.overAllRank.setText(CustomUtil.formatNumber(currentItem.getOverall_rank()));
            mBinding.overAllPoints.setText(CustomUtil.formatNumber(currentItem.getTotal_points()));
            mBinding.gameWeekRank.setText(CustomUtil.formatNumber(currentItem.getRank()));
            mBinding.gameWeekPoints.setText(CustomUtil.formatNumber(currentItem.getPoints()));
            mBinding.pointsOnBench.setText(CustomUtil.formatNumber(currentItem.getPoints_on_bench()));
            mBinding.transferMade.setText(CustomUtil.formatNumber(currentItem.getEvent_transfers()));
            mBinding.transferCost.setText(CustomUtil.formatNumber(currentItem.getEvent_transfers_cost()));
            mBinding.squadValue.setText(CustomUtil.convertedPrice(currentItem.getValue()));

            updateRankIcon(currentItem, nextItem);
        }

        public void updateRankIcon(CurrentSeasonHistoryModel currentItem, CurrentSeasonHistoryModel nextItem){
            // Rank change comparison
            if (nextItem != null) {
                if (currentItem.getOverall_rank() < nextItem.getOverall_rank()) {
                    mBinding.rankChange.setImageResource(R.drawable.ic_up_arrow_green);
                } else if (currentItem.getOverall_rank() > nextItem.getOverall_rank()) {
                    mBinding.rankChange.setImageResource(R.drawable.ic_down_arrow_red);
                } else {
                    mBinding.rankChange.setImageResource(R.drawable.ic_unchange_rank);
                }
            } else {
                // For the last item, show no change
                mBinding.rankChange.setImageResource(R.drawable.ic_unchange_rank);
            }
        }

        public void unbind() {
            mBinding = null;
        }
    }
}
