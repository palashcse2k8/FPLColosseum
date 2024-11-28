package com.infotech.fplcolosseum.features.gameweek_history.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.ActivityGameweekHistoryPreviousSeasonItemBinding;
import com.infotech.fplcolosseum.databinding.ActivityGameweekHistoryPreviousSeasonItemHeaderBinding;
import com.infotech.fplcolosseum.databinding.NoDataLayoutBinding;
import com.infotech.fplcolosseum.features.gameweek_history.models.PreviousSeasonHistoryModel;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;

public class PreviousSeasonHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_NO_DATA = 2;

    private List<PreviousSeasonHistoryModel> dataList;
    private String sectionTitle;

    public PreviousSeasonHistoryAdapter(List<PreviousSeasonHistoryModel> dataList) {
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

    public void updateAdapterData(List<PreviousSeasonHistoryModel> newData) {

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
                    new HeaderViewHolder(ActivityGameweekHistoryPreviousSeasonItemHeaderBinding.inflate(inflater, parent, false));
            case VIEW_TYPE_NO_DATA ->
                    new NoDataViewHolder(NoDataLayoutBinding.inflate(inflater, parent, false));
            default ->
                new ItemViewHolder(ActivityGameweekHistoryPreviousSeasonItemBinding.inflate(inflater, parent, false));
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            // Bind header
            ((HeaderViewHolder) holder).bind();
        } else if (holder instanceof NoDataViewHolder) {
            // Bind no data view
            ((NoDataViewHolder) holder).bind("No Previous History!");
        } else if (holder instanceof ItemViewHolder) {
            // Bind regular item
            ((ItemViewHolder) holder).bind(dataList.get(position - 1));
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
        ActivityGameweekHistoryPreviousSeasonItemHeaderBinding mBinding;

        public HeaderViewHolder(@NonNull ActivityGameweekHistoryPreviousSeasonItemHeaderBinding binding) {
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
        ActivityGameweekHistoryPreviousSeasonItemBinding mBinding;

        public ItemViewHolder(@NonNull ActivityGameweekHistoryPreviousSeasonItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(PreviousSeasonHistoryModel item) {
            mBinding.seasonName.setText(item.getSeason_name());
            mBinding.seasonPoints.setText(CustomUtil.formatNumber(item.getTotal_points()));
            mBinding.seasonRank.setText(CustomUtil.formatNumber(item.getRank()));
        }

        public void unbind() {
            mBinding = null;
        }
    }
}
