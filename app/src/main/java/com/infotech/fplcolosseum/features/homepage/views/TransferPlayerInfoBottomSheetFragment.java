package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentTransferPlayerInfoBottomSheetBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerTransferListener;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

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

        if (playerData.getSubstitute_number() == 0) {
            binding.btnCancelTransfer.setVisibility(View.GONE);
        }


        binding.fullInfoButton.setOnClickListener(v -> dismiss());
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
