package com.infotech.fplcolosseum.features.homepage.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.fixture.DateWiseFixtures;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;

public class DateWiseFixtureListAdapter extends RecyclerView.Adapter<DateWiseFixtureListAdapter.DateWiseFixtureViewHolder> implements InnerRecyclerViewItemClickListener{

    private List<DateWiseFixtures> dateWiseFixturesList;

    FragmentActivity activity;
    private RecyclerView recyclerView;

    public DateWiseFixtureListAdapter(List<DateWiseFixtures> dateWiseFixturesList, FragmentActivity activity, RecyclerView outerRecyclerView) {
        this.dateWiseFixturesList = dateWiseFixturesList;
        this.activity = activity;
        this.recyclerView = outerRecyclerView;
    }

    @NonNull
    @Override
    public DateWiseFixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_match_recycler_view_item, parent, false);
        return new DateWiseFixtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateWiseFixtureViewHolder holder, int position) {
        DateWiseFixtures fixtures = dateWiseFixturesList.get(position);
        holder.bind(fixtures);
        holder.fixtureRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.fixtureRecyclerView.setAdapter(new FixtureListAdapter(fixtures.fixtures(), holder.itemView.getContext(), this, position));
    }

    @Override
    public int getItemCount() {
        return dateWiseFixturesList.size();
    }

    public void updateDateList(List<DateWiseFixtures> newPlayersList) {
        this.dateWiseFixturesList = newPlayersList;
        notifyDataSetChanged();
    }

    @Override
    public void onItemExpanded(int outerPosition, int innerPosition) {
        Log.d(Constants.LOG_TAG, outerPosition + " " + innerPosition);
        // Scroll to the expanded item to ensure it's fully visible
        recyclerView.post(() -> recyclerView.smoothScrollToPosition(outerPosition));
    }


    static class DateWiseFixtureViewHolder extends RecyclerView.ViewHolder {

        private final TextView gameWeekFixtureDate;
        RecyclerView fixtureRecyclerView;

        public DateWiseFixtureViewHolder(@NonNull View itemView) {
            super(itemView);
            gameWeekFixtureDate = itemView.findViewById(R.id.gameWeekDate);
            fixtureRecyclerView = itemView.findViewById(R.id.matchResultsRecyclerView);
        }

        public void bind(DateWiseFixtures fixtures) {
//            Log.d(Constants.LOG_TAG, fixtures.getFixtures().get(0).getKickoff_time());
            gameWeekFixtureDate.setText(CustomUtil.convertDateToDayDateMonth(fixtures.fixtures().get(0).getKickoff_time()));

        }
    }
}
