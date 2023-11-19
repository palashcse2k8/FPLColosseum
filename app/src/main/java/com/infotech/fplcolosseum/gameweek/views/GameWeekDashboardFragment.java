package com.infotech.fplcolosseum.gameweek.views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.GameweekDashboardFragmentBinding;
import com.infotech.fplcolosseum.gameweek.adapter.TeamAdapter;
import com.infotech.fplcolosseum.gameweek.adapter.TeamDataComparator;
import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.viewmodels.GameWeekViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.FileUtility;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EFragment(resName = "gameweek_dashboard_fragment")
public class GameWeekDashboardFragment extends Fragment {

    GameweekDashboardFragmentBinding binding;
    private TeamAdapter adapter;

    private CustomGameWeekDataModel weekDataModel;
    private List<ManagerModel> teams;
    private ProgressDialog progressDialog;

    private GameWeekViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching Game Week data...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        // Initialize ViewModel and other components here
        viewModel = new ViewModelProvider(requireActivity()).get(GameWeekViewModel.class);
        viewModel.deleteAllGameWeekData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GameweekDashboardFragmentBinding.inflate(inflater, container, false);
        binding.setGameWeekViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setUpLiveDataObserver();

        binding.tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIUtils.toast(requireContext(), "Creating pdf file", ToastLevel.INFO);
//                requestPermissions();

            }
        });

        binding.gameWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.d("item selected %s", i);
                if (i > 0)
                    getGameWeekData(Constants.leagues[0], String.valueOf(i));
                else {
                    Logger.d("Game Week Not Selected");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void requestPermissions() {
        // For Android 10 and above, use MediaStore API
        FileUtility fileUtility = new FileUtility(requireContext());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        if (true) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Downloads.DISPLAY_NAME, "test.pdf");
            values.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");
//            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
            values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri currentUri = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                currentUri = requireContext().getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
            } else {

            }

            if (currentUri != null) {
                fileUtility.alterDocument(currentUri);
                UIUtils.toast(requireContext(), "Successfully created PDF", ToastLevel.SUCCESS);
            } else {
                UIUtils.toast(requireContext(), "Failed to create PDF", ToastLevel.ERROR);
            }
        }
    }

    public void getGameWeekData(String leagueID, String gameWeek) {
        try {
//            Logger.d("Getting Game Week Data for leagueID-> " + leagueID + ", gameWeek-> " + gameWeek);
            progressDialog.setTitle("Fetching GameWeek " + gameWeek + " Data");
            progressDialog.show();
            viewModel.gameWeekDataFromAPI(leagueID, gameWeek);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView1;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        teams = new ArrayList<>();
        adapter = new TeamAdapter(teams);
        recyclerView.setAdapter(adapter);
    }

    public void setUpLiveDataObserver() {

        viewModel.leagueGameWeekDataModel().observe(getViewLifecycleOwner(), customGameWeekDataModel -> {
//            Logger.d("leagueGameWeekDataModel changed");
            if (customGameWeekDataModel != null) {
                updateUI(customGameWeekDataModel);
            } else {
                Toast.makeText(getContext(), "Failed to get data!", Toast.LENGTH_SHORT).show();
            }
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUI(CustomGameWeekDataModel weekDataModel) {

//        Logger.d("Updating UI");
        if (weekDataModel != null && !weekDataModel.getTeams().isEmpty()) {
            // Update your RecyclerView and other UI components here using the data
            String gameWeek = " (GW " + (int) weekDataModel.getGameWeek() + ")";
            binding.textviewGameWeek.setText(gameWeek);
            String leagueName = " " + weekDataModel.getLeagueName();
            binding.leagueName.setText(leagueName);
            teams.clear(); // Clear the existing data
            weekDataModel.getTeams().sort(new TeamDataComparator());
            teams.addAll(weekDataModel.getTeams());
            adapter.notifyDataSetChanged();
        } else {
            Logger.d("GameWeek Model is Empty");
        }
    }
}
