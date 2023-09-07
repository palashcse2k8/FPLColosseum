package com.infotech.fplcolosseum.gameweek.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.infotech.fplcolosseum.databinding.GameweekDashboardFragmentBinding;
import com.infotech.fplcolosseum.gameweek.models.LeagueGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.TeamDataModel;
import com.infotech.fplcolosseum.gameweek.viewmodels.TeamAdapter;
import com.infotech.fplcolosseum.remote.APIServices;
import com.infotech.fplcolosseum.remote.RetroClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GameWeekDashboard extends Fragment {

    GameweekDashboardFragmentBinding binding;
    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private List<TeamDataModel> teams;
    private ProgressDialog progressDialog;

    private LeagueGameWeekDataModel leagueGameWeekDataModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait.");
        progressDialog.setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GameweekDashboardFragmentBinding.inflate(inflater, container, false);
        makeApiCall("671887", "116074","1", "1");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.recyclerView1;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize your data (replace with your actual data)
        teams = new ArrayList<>();

        // Sort the data based on your predefined rules
//        Collections.sort(teams, (team1, team2) -> {
//            // Your sorting logic here, e.g., based on points
//            return Integer.compare(team2.getTotalPoints(), team1.getTotalPoints());
//        });

        adapter = new TeamAdapter(teams);
        recyclerView.setAdapter(adapter);
    }



    private void makeApiCall(String leagueID, String entryID, String currentGameweek, String currentPage) {
        progressDialog.show();

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        queryParams.put("entry", entryID);
        queryParams.put("currentweek", currentGameweek);
        queryParams.put("currentPage", currentPage);
        // Add more parameters as needed

        try {

            APIServices apiServices = RetroClass.getAPIService(); // set API
            Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);

            callAPI.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        try {
                            ResponseBody responseBody = response.body();

                            if (responseBody != null) {
                                try {
                                    String json = responseBody.string();
                                    Log.d("apiresponse=>", json);
                                    leagueGameWeekDataModel = new Gson().fromJson(json, LeagueGameWeekDataModel.class);
                                    updateUI();
                                    // Handle the deserialized data
                                } catch (JsonSyntaxException e) {
                                    // Handle JSON syntax exception
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // Handle IOException
                                    e.printStackTrace();
                                }
                            } else {
                                // Handle null response body
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    } else {
                        // Handle an error response
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    UIUtils.toast("API Calling fail", WARNING);
                }
            });
        } catch (
                Exception exception) {
            exception.printStackTrace();
//            UIUtils.toast("API Calling fail", WARNING);
        }
    }


    public void updateUI() {
        if (leagueGameWeekDataModel != null) {
            // Update your RecyclerView and other UI components here using the data
            TextView leagueName = binding.leagueName;
            leagueName.setText(leagueGameWeekDataModel.getLeagueName());
            teams.clear(); // Clear the existing data
            teams.addAll(leagueGameWeekDataModel.getTeamDatas());
            adapter.notifyDataSetChanged();
        } else {
            // Handle the case where gameWeekDataModel is null or the data is not as expected
        }
    }
}
