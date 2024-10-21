package com.infotech.fplcolosseum.features.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class FixtureListAdapter extends RecyclerView.Adapter<FixtureListAdapter.FixtureViewHolder> {

    private List<MatchDetails> matchList;

    Context activity;

    public FixtureListAdapter(List<MatchDetails> matchList, Context context) {
        this.matchList = matchList;
        this.activity = context;
    }

    @NonNull
    @Override
    public FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_match_result_item, parent, false);
        return new FixtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FixtureViewHolder holder, int position) {
        MatchDetails player = matchList.get(position);
        holder.bind(player);


//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(activity, PlayerFullInformationActivity.class);
//            intent.putExtra("playerData", player); // Replace with actual player name
//            activity.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public void updatePlayersList(List<MatchDetails> newMatchList) {
        this.matchList = newMatchList;
        notifyDataSetChanged();
    }


    static class FixtureViewHolder extends RecyclerView.ViewHolder {

        private final TextView homeTeamNameTV;
        private final TextView awayTeamNameTV;
        private final TextView scoreTV;

        public FixtureViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTeamNameTV = itemView.findViewById(R.id.homeTeamTV);
            awayTeamNameTV = itemView.findViewById(R.id.awayTeamTV);
            scoreTV = itemView.findViewById(R.id.scoreTV);
        }

        public void bind(MatchDetails matchDetails) {

            String homeTeamName = Objects.requireNonNull(Constants.teamMap.get(matchDetails.getTeam_h())).getName();
            homeTeamNameTV.setText(homeTeamName);

            String awayTeamName = Objects.requireNonNull(Constants.teamMap.get(matchDetails.getTeam_a())).getName();
            awayTeamNameTV.setText(awayTeamName);

            String scoreText = "";
            if(matchDetails.getFinished()){
                scoreText = matchDetails.getTeam_h_score() + " - " + matchDetails.getTeam_a_score();
            } else {
                scoreText = CustomUtil.getLocalTimeFromUTCDateString(matchDetails.getKickoff_time());
            }

            scoreTV.setText(scoreText);
        }
    }
}
