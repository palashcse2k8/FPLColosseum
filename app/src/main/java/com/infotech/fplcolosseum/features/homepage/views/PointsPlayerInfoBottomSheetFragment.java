package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentPointsPlayerInfoBottomSheetBinding;
import com.infotech.fplcolosseum.databinding.FragmentTransferPlayerInfoBottomSheetBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerTransferListener;
import com.infotech.fplcolosseum.features.homepage.adapter.PointsAdapter;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.Element;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.Explain;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.player_information.adapter.HistoryAdapterFixed;
import com.infotech.fplcolosseum.features.player_information.views.PlayerFullInformationActivity;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PointsPlayerInfoBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentPointsPlayerInfoBottomSheetBinding binding;
    private PlayersData playerData;
    private OpponentData matchDetails;
    private Element element;

    public static PointsPlayerInfoBottomSheetFragment newInstance(PlayersData playerData, OpponentData fixtureData, Element explain) {
        PointsPlayerInfoBottomSheetFragment fragment = new PointsPlayerInfoBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("player_data", playerData);
        args.putSerializable("matchDetails", fixtureData);
        args.putSerializable("player_explain", explain);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_points_player_info_bottom_sheet, container, false);

        if (getDialog() == null) {
            return null;
        }
        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
//            View bottomSheetInternal = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//            assert bottomSheetInternal != null;
//            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            playerData = (PlayersData) getArguments().getSerializable("player_data");
            matchDetails = (OpponentData) getArguments().getSerializable("matchDetails");
            element = (Element) getArguments().getSerializable("player_explain");
            binding.setPlayer(playerData); // Bind player data to the view
        }

        String price = "€" + (float) playerData.getNow_cost() / 10 + "m";
        String teamName = Objects.requireNonNull(Constants.teamMap.get(playerData.getTeam())).getShort_name();
        String playerType = Objects.requireNonNull(Constants.playerTypeMap.get(playerData.getElement_type())).getSingular_name_short();
        String subtitle = price + " | " + playerType + " | " + teamName;
        binding.details.setText(subtitle);

        String homeTeamName = matchDetails.isHome() ? Constants.teamMap.get(matchDetails.getTeamID()).getName() : Constants.teamMap.get(matchDetails.getOpponentTeamId()).getName();

        String awayTeamName = !matchDetails.isHome() ? Constants.teamMap.get(matchDetails.getTeamID()).getName() : Constants.teamMap.get(matchDetails.getOpponentTeamId()).getName();

        binding.homeTeamName.setText(awayTeamName);
        binding.awayTeamName.setText(homeTeamName);

        String score = !matchDetails.isHome() ? matchDetails.getGoalScored() + " - " + matchDetails.getGoalConceded() : matchDetails.getGoalConceded() + " - " + matchDetails.getGoalScored();
        binding.score.setText(score);

        binding.closeIcon.setOnClickListener(v -> dismiss());

        changeTextViewColors(binding.pointInfoRecyclerViewHeader.pointInfoItemCardView);
        binding.pointInfoRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        binding.pointInfoRecyclerView.setAdapter(new PointsAdapter(this.element.explain.get(0).stats, this.element.stats.total_points));

        // Add a divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        binding.pointInfoRecyclerView.addItemDecoration(dividerItemDecoration);

        binding.fullInfoButton.setOnClickListener(v -> {

            Intent intent = new Intent(requireActivity(), PlayerFullInformationActivity.class);
            intent.putExtra("playerData", this.playerData); // Replace with actual player name
            startActivity(intent);

            dismiss();
        });

        for (long i = Constants.nextGameWeek; i <= 38; i++) {
            View itemView = getLayoutInflater().inflate(R.layout.layout_next_team_item, binding.horizontalNextOpponents, false);
            TextView teamNameTV = itemView.findViewById(R.id.teamName);
            TextView gameWeekNumber = itemView.findViewById(R.id.gameWeekNumber);

            OpponentData matchDetails = Constants.fixtureData.get(i).get(playerData.getTeam());
            String homeOrAway = matchDetails.isHome() ? "H" : "A";
            String opponentTeamName = Objects.requireNonNull(Constants.teamMap.get(matchDetails.getTeamID())).getShort_name();
            String opponentWithHomeAway = opponentTeamName + " (" + homeOrAway + ")";

            teamNameTV.setText(opponentWithHomeAway);

            teamNameTV.setTextColor(CustomUtil.getDifficultyLeveTextColor(matchDetails.getDifficulty()));
            teamNameTV.setBackgroundColor(CustomUtil.getDifficultyLevelColor(matchDetails.getDifficulty()));
            String gwNumber = "GW" + i;
            gameWeekNumber.setText(gwNumber);
            binding.horizontalNextOpponents.addView(itemView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void changeTextViewColors(ViewGroup headerView) {

        // Set the background color of the CardView
        headerView.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.deepGreen)
        );

        // Find all TextViews in the layout and change their color to white
        changeTextViewColorRecursively(headerView);
    }

    private void changeTextViewColorRecursively(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                changeTextViewColorRecursively(child);
            }
        } else if (view instanceof TextView) {
            ((TextView) view).setTextColor(Color.WHITE);
        }
    }
}
