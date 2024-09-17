package com.infotech.fplcolosseum.features.player_information.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.infotech.fplcolosseum.databinding.FragmentFixturesBinding;
import com.infotech.fplcolosseum.databinding.FragmentMatchesBinding;

public class FixtureFragment extends Fragment {
    FragmentFixturesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFixturesBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
//        Toolbar pointToolBar = requireActivity().findViewById(R.id.pointToolbar);
////        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(pointToolBar);

//        binding.setViewModel(viewModel);
//        binding.setLifecycleOwner(this);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
//        viewModel.getPointsMergedData(Constants.LoggedInUser.getPlayer().getEntry(), Constants.currentGameWeek);
//        selectedChip = (int) Constants.currentGameWeek;
//        viewModel.getTeamCurrentGameWeekAllData(10359552);
//        setRetainInstance(true);
//        setHasOptionsMenu(true);
    }
}
