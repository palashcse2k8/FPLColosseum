package com.infotech.fplcolosseum.features.homepage.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
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
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchElement;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchStats;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;
import java.util.Objects;

public class FixtureListAdapter extends RecyclerView.Adapter<FixtureListAdapter.FixtureViewHolder> {

    private List<MatchDetails> matchList;
    private SparseBooleanArray expandState = new SparseBooleanArray(); // Track expand/collapse state

    private int outerRecyclerPosition;
    InnerRecyclerViewItemClickListener listener;
    private
    Context activity;

    public FixtureListAdapter(List<MatchDetails> matchList, Context context, InnerRecyclerViewItemClickListener listener, int outerPosition) {
        this.matchList = matchList;
        this.activity = context;
        this.outerRecyclerPosition = outerPosition;
        this.listener = listener;
        // Initialize all items to collapsed state
        for (int i = 0; i < matchList.size(); i++) {
            expandState.put(i, false);
        }
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
        boolean isExpanded = expandState.get(position);
        holder.bind(player);

        // Set initial visibility of expandable layout
        holder.matchStatLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.expandCollapseIcon.setImageResource(isExpanded ? R.drawable.ic_arrow_up_24x24 : R.drawable.ic_arrow_down_24x24);

        // Handle click to expand/collapse
        holder.itemView.setOnClickListener(v -> {

            boolean currentExpandedState = expandState.get(position);
            expandState.put(position, !currentExpandedState);
            notifyItemChanged(position);
            TransitionManager.beginDelayedTransition(holder.matchStatLayout);
            if (listener != null) {
                listener.onItemExpanded(outerRecyclerPosition, position);
            }
        });

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
        private final TextView matchStatusTV;
        private final LinearLayout matchStatLayout;
        private final ImageView expandCollapseIcon;

        public FixtureViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTeamNameTV = itemView.findViewById(R.id.homeTeamTV);
            awayTeamNameTV = itemView.findViewById(R.id.awayTeamTV);
            scoreTV = itemView.findViewById(R.id.scoreTV);
            matchStatusTV = itemView.findViewById(R.id.matchStatusTV);
            matchStatLayout = itemView.findViewById(R.id.matchStatLayout);
            expandCollapseIcon = itemView.findViewById(R.id.expandCollapseIcon);
        }

        public void bind(MatchDetails matchDetails) {

            String homeTeamName = Objects.requireNonNull(Constants.teamMap.get(matchDetails.getTeam_h())).getName();
            homeTeamNameTV.setText(homeTeamName);

            String awayTeamName = Objects.requireNonNull(Constants.teamMap.get(matchDetails.getTeam_a())).getName();
            awayTeamNameTV.setText(awayTeamName);

            String scoreText;
            if (matchDetails.getFinished()) {
                scoreText = matchDetails.getTeam_h_score() + " - " + matchDetails.getTeam_a_score();
            } else {
                scoreText = CustomUtil.getLocalTimeFromUTCString(matchDetails.getKickoff_time());
            }

            scoreTV.setText(scoreText);

            String matchStatusText;
            if (matchDetails.getStarted() && !matchDetails.getFinished()) {
                matchStatusText = "Live";
            } else if (matchDetails.getFinished() && matchDetails.getStarted()) {
                matchStatusText = "FT";
            } else {
                matchStatusText = "";
            }

            matchStatusTV.setText(matchStatusText);

            createStatView(matchDetails);
        }

        private void createStatView(MatchDetails matchDetails) {

            matchStatLayout.removeAllViews(); // remove all the view before inflating new one

            if (matchDetails.getStats() == null || matchDetails.getStats().isEmpty()) { // if no stat is available or match not started yet

                View satateView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.layout_macth_stat_view_item, null);
                TextView stateIdentifierTitleTV = satateView.findViewById(R.id.statIdentifierTitle);
                stateIdentifierTitleTV.setText("Not played yet");
                matchStatLayout.addView(satateView);
            } else {

                for (MatchStats matchStat : matchDetails.getStats()) {

                    if (matchStat.getA().isEmpty() && matchStat.getH().isEmpty()) {
                        continue;
                    }
                    View satateView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.layout_macth_stat_view_item, null);

                    LinearLayout homeStatContainer = satateView.findViewById(R.id.homeStatContainer);
                    LinearLayout awayStatContainer = satateView.findViewById(R.id.awayStatContainer);
                    TextView stateIdentifierTitleTV = satateView.findViewById(R.id.statIdentifierTitle);

                    stateIdentifierTitleTV.setText(Constants.elementStatMap.get(matchStat.getIdentifier()));

                    for (MatchElement homeElement : matchStat.getH()) {
                        TextView elementText = new TextView(itemView.getContext());

                        // Set the layout parameters for the TextView
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                        // Align the TextView to the right within the LinearLayout
                        params.gravity = Gravity.END; // Align to end/right

                        elementText.setLayoutParams(params);

                        String elementWithCount = Constants.playerMap.get(homeElement.getElement()).getWeb_name() + " (" + homeElement.getValue() + ")";

                        elementText.setText(elementWithCount);
                        elementText.setTypeface(null, Typeface.BOLD);

                        // Add click listener if needed
                        elementText.setOnClickListener(v -> {
                            // Handle click on stat item
                            CustomUtil.startPlayerFullProfile((FragmentActivity) itemView.getContext(), Constants.playerMap.get(homeElement.getElement()));
                        });

                        homeStatContainer.addView(elementText);
                    }

                    for (MatchElement awayElement : matchStat.getA()) {
                        TextView elementText = new TextView(itemView.getContext());

                        String elementWithCount = Constants.playerMap.get(awayElement.getElement()).getWeb_name() + " (" + awayElement.getValue() + ")";

                        elementText.setText(elementWithCount);

                        elementText.setTypeface(null, Typeface.BOLD);

                        // Add click listener if needed
                        elementText.setOnClickListener(v -> {
                            // Handle click on stat item
                            CustomUtil.startPlayerFullProfile((FragmentActivity) itemView.getContext(), Constants.playerMap.get(awayElement.getElement()));
                        });

                        awayStatContainer.addView(elementText);
                    }
                    matchStatLayout.addView(satateView);
                }
            }

        }
    }
}
