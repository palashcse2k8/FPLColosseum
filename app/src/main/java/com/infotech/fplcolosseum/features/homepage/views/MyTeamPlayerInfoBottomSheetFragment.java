package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentMyTeamPlayerInfoBottomSheetBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerInfoUpdateListener;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.player_information.views.PlayerFullInformationActivity;
import com.infotech.fplcolosseum.utilities.Constants;
import com.squareup.picasso.Picasso;

public class MyTeamPlayerInfoBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentMyTeamPlayerInfoBottomSheetBinding binding;
    private PlayersData playerData;
    private PlayerInfoUpdateListener playerInfoUpdateListener;

    public static MyTeamPlayerInfoBottomSheetFragment newInstance(PlayersData playerData) {
        MyTeamPlayerInfoBottomSheetFragment fragment = new MyTeamPlayerInfoBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("player_data", playerData);
        fragment.setArguments(args);
        return fragment;
    }

    public void setPlayerInfoUpdateListener(PlayerInfoUpdateListener playerInfoUpdateListener) {
        this.playerInfoUpdateListener = playerInfoUpdateListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_team_player_info_bottom_sheet, container, false);

        if (getDialog() == null) {
            return null;
        }
        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            View bottomSheetInternal = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
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

        if (playerData.getPosition() >= 12) {
            binding.btnViceCap.setVisibility(View.GONE);
            binding.btnCap.setVisibility(View.GONE);
        }

        if (playerData.isIs_captain()) {
            binding.btnCap.setVisibility(View.GONE);
        }

        if (playerData.isIs_vice_captain()) {
            binding.btnViceCap.setVisibility(View.GONE);
        }

        String imageUrl = "https://resources.premierleague.com/premierleague/photos/players/110x140/p" + playerData.getCode() + ".png";

        Picasso.get()
                .load(imageUrl)
                .error(R.mipmap.no_image)
                .into(binding.playerImageView);

        Log.d(Constants.LOG_TAG, imageUrl);
        binding.fullInfoButton.setOnClickListener(v -> dismiss());
        binding.closeIcon.setOnClickListener(v -> dismiss());

        binding.btnViceCap.setOnClickListener(v -> {
            if (playerInfoUpdateListener != null) {
                playerInfoUpdateListener.onSetViceCaptain(playerData);
            }
            dismiss();
        });

        binding.btnCap.setOnClickListener(v -> {
            if (playerInfoUpdateListener != null) {
                playerInfoUpdateListener.onSetCaptain(playerData);
            }
            dismiss();
        });

        binding.btnSubstitute.setOnClickListener(v -> {
            if (playerInfoUpdateListener != null) {
                playerInfoUpdateListener.onSwitchPlayer(playerData);
            }
            dismiss();
        });

        binding.fullInfoButton.setOnClickListener( v -> {

            Intent intent = new Intent(requireActivity(), PlayerFullInformationActivity.class);
            intent.putExtra("playerData", this.playerData); // Replace with actual player name
            startActivity(intent);

            dismiss();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
