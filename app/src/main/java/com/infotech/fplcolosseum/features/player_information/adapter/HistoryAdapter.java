package com.infotech.fplcolosseum.features.player_information.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;

import java.util.List;

// CustomAdapter.java
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<List<String>> mData;
    private int mFixedColumnWidth = 0;

    public HistoryAdapter(List<List<String>> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> rowData = mData.get(position);
        holder.fixedColumn.setText(rowData.get(0));

        holder.scrollableColumns.removeAllViews();
        for (int i = 1; i < rowData.size(); i++) {
            TextView textView = new TextView(holder.scrollableColumns.getContext());
            textView.setText(rowData.get(i));
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            textView.setPadding(16, 16, 16, 16);
            holder.scrollableColumns.addView(textView);
        }

        if (mFixedColumnWidth == 0) {
            holder.fixedColumn.post(() -> {
                mFixedColumnWidth = holder.fixedColumn.getWidth();
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getFixedColumnWidth() {
        return mFixedColumnWidth;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fixedColumn;
        HorizontalScrollView scrollView;
        LinearLayout scrollableColumns;

        ViewHolder(View itemView) {
            super(itemView);
            fixedColumn = itemView.findViewById(R.id.fixedColumn);
            scrollView = itemView.findViewById(R.id.scrollView);
            scrollableColumns = itemView.findViewById(R.id.scrollableColumns);
        }
    }
}
