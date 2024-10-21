package com.infotech.fplcolosseum.features.homepage.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.fixture.DateWiseFixtures;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.player_information.views.PlayerFullInformationActivity;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DateWiseFixtureListAdapter extends RecyclerView.Adapter<DateWiseFixtureListAdapter.DateWiseFixtureViewHolder> {

    private List<DateWiseFixtures> dateWiseFixturesList;

    FragmentActivity activity;

    public DateWiseFixtureListAdapter(List<DateWiseFixtures> dateWiseFixturesList, FragmentActivity activity) {
        this.dateWiseFixturesList = dateWiseFixturesList;
        this.activity = activity;
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

    }

    @Override
    public int getItemCount() {
        return dateWiseFixturesList.size();
    }

    public void updateDateList(List<DateWiseFixtures> newPlayersList) {
        this.dateWiseFixturesList = newPlayersList;
        notifyDataSetChanged();
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
            gameWeekFixtureDate.setText(CustomUtil.convertDateToDayDateMonth(fixtures.getFixtures().get(0).getKickoff_time()));
            fixtureRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            fixtureRecyclerView.setAdapter(new FixtureListAdapter(fixtures.getFixtures(), itemView.getContext()));
        }
    }
}
