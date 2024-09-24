package com.infotech.fplcolosseum.features.homepage.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.LayoutPointsItemBinding;
import com.infotech.fplcolosseum.databinding.LayoutPointsItemFooterBinding;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.ExplainStat;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.List;

// CustomAdapter.java
public class PointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ExplainStat> mData;
    private final long totalPoints ;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public PointsAdapter(List<ExplainStat> data, Long totalPoints) {
        mData = data;
        this.totalPoints = totalPoints;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutPointsItemFooterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return new ItemViewHolder(LayoutPointsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bindData(mData.get(position));

        } else if (holder instanceof FooterViewHolder) {

            ((FooterViewHolder) holder).bindData(totalPoints);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        LayoutPointsItemBinding binding;//Name of the test_list_item.xml in camel case + "Binding"

        public ItemViewHolder(LayoutPointsItemBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bindData(ExplainStat data) {
            binding.identifier.setText(Constants.elementStatMap.get(data.getIdentifier()));
            binding.value.setText(String.valueOf(data.getValue()));
            binding.points.setText(String.valueOf(data.getPoints()));
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        LayoutPointsItemFooterBinding binding;

        FooterViewHolder(LayoutPointsItemFooterBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bindData(long totalPoints) {
            binding.points.setText(String.valueOf(totalPoints));
        }

    }
}
