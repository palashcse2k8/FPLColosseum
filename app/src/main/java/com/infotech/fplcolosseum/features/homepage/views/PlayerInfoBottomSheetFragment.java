package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentPlayerInfoBottomsheetBinding;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

import java.io.Serializable;

public class PlayerInfoBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentPlayerInfoBottomsheetBinding binding;
    private PlayersData playerData;

    public static PlayerInfoBottomSheetFragment newInstance(PlayersData playerData) {
        PlayerInfoBottomSheetFragment fragment = new PlayerInfoBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("player_data", (Serializable) playerData);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_info_bottomsheet, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            playerData = (PlayersData) getArguments().getSerializable("player_data");
            binding.setPlayer(playerData); // Bind player data to the view
        }

        binding.fullInfoButton.setOnClickListener(v -> dismiss());
        binding.closeIcon.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
