package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentTransferPlayerInfoBottomSheetBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerTransferListener;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.player_information.views.PlayerFullInformationActivity;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class TransferPlayerInfoBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentTransferPlayerInfoBottomSheetBinding binding;
    private PlayersData playerData;
    private PlayerTransferListener playerTransferListener;

    public static TransferPlayerInfoBottomSheetFragment newInstance(PlayersData playerData) {
        TransferPlayerInfoBottomSheetFragment fragment = new TransferPlayerInfoBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("player_data", playerData);
        fragment.setArguments(args);
        return fragment;
    }

    public void setPlayerTransferListener(PlayerTransferListener playerTransferListener) {
        this.playerTransferListener = playerTransferListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transfer_player_info_bottom_sheet, container, false);

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
            binding.setPlayer(playerData); // Bind player data to the view
        }

        String imageUrl = "https://resources.premierleague.com/premierleague/photos/players/110x140/p" + playerData.getCode() + ".png";

        Picasso.get()
                .load(imageUrl)
                .error(R.mipmap.no_image)
                .into(binding.playerImageView);

        if (playerData.getSubstitute_number() == 0) {
            binding.btnCancelTransfer.setVisibility(View.GONE);
        }

        binding.closeIcon.setOnClickListener(v -> dismiss());

        binding.btnTransfer.setOnClickListener(v -> {
            if (playerTransferListener != null) {
                playerTransferListener.onTransferPlayer(playerData);
            }
            dismiss();
        });

        binding.btnCancelTransfer.setOnClickListener(v -> {
            if (playerTransferListener != null) {
                playerTransferListener.onCancelTransfer(playerData);
            }
            dismiss();
        });

        binding.fullInfoButton.setOnClickListener( v -> {

            Intent intent = new Intent(requireActivity(), PlayerFullInformationActivity.class);
            intent.putExtra("playerData", this.playerData); // Replace with actual player name
            startActivity(intent);

            dismiss();
        });

        addNextOpponents();
    }

    public void addNextOpponents(){
        for (long i = Constants.nextGameWeek; i <= 38; i++) {
            View itemView = getLayoutInflater().inflate(R.layout.layout_next_team_item, binding.horizontalNextOpponents, false);
            TextView teamName = itemView.findViewById(R.id.teamName);
            TextView gameWeekNumber = itemView.findViewById(R.id.gameWeekNumber);

            OpponentData matchDetails = Constants.fixtureData.get(i).get(playerData.getTeam());
            String homeOrAway = matchDetails.isHome() ? "H" : "A";
            String opponentTeamName = Objects.requireNonNull(Constants.teamMap.get(matchDetails.getTeamID())).getShort_name();
            String opponentWithHomeAway = opponentTeamName + " (" + homeOrAway + ")";

            teamName.setText(opponentWithHomeAway);

            teamName.setTextColor(CustomUtil.getDifficultyLeveTextColor(matchDetails.getDifficulty()));
            teamName.setBackgroundColor(CustomUtil.getDifficultyLevelColor(matchDetails.getDifficulty()));
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
}
