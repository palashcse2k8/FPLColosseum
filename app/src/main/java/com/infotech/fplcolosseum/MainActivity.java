package com.infotech.fplcolosseum;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.homepage.models.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.GameWeekStaticDataModel;
import com.infotech.fplcolosseum.features.homepage.models.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.TeamData;
import com.infotech.fplcolosseum.features.login.views.LoginFragment;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.SharedViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private SharedViewModel sharedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        setupActionBar();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        sharedViewModel.getApiData().observe(this, apiResponse -> {
            // Handle API response in the fragment
            if(apiResponse == null) return;
            if (apiResponse.getStatus() == ApiResponse.Status.SUCCESS) {

                // Update Static data with the data
                prepareData(apiResponse.getData());

            } else {
                Log.d(Constants.LOG_TAG, "Data Loading Error" );
            }
        });

        if(Constants.playerMap.isEmpty()){
            sharedViewModel.getGameWeekStaticData();
        }
//        Fragment fragment = new GameWeekDashboardFragment();
        Fragment fragment = new LoginFragment();
//        Fragment fragment = new HomePageFragment();
//        Fragment fragment = new MyTeamFragment();
        String tag = fragment.getClass().getSimpleName();
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.contentFrame, fragment, tag).commit();

    }

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        assert actionBar != null;
        actionBar.setTitle("Game Week Standings");
    }

    public void prepareData(GameWeekStaticDataModel dataModel){

        //setting team map
        Map<Long, TeamData> teamMap = new HashMap<>();
        for (TeamData data : dataModel.getTeams()) {
            teamMap.put(data.getId(), data);
        }
        Constants.teamMap = teamMap;

        //setting gameWeek map
        Map<Long, GameWeekEvent> gameWeekMap = new HashMap<>();
        for (GameWeekEvent weekEvent : dataModel.getEvents()) {
            gameWeekMap.put(weekEvent.getId(), weekEvent);
        }
        Constants.gameWeekMap = gameWeekMap;

        //setting player type map
        Map<Long, Player_Type> palyerTypeMap = new HashMap<>();
        for (Player_Type type : dataModel.getPlayer_types()) {
            palyerTypeMap.put(type.getId(), type);
        }
        Constants.playerTypeMap = palyerTypeMap;

        //setting player map
        Map<Long, PlayersData> elementMap = new HashMap<>();
        for (PlayersData element : dataModel.getElements()) {
            //adding extra fields here
            element.setTeam_name_full(Objects.requireNonNull(Constants.teamMap.get(element.getTeam())).getName());
            element.setTeam_name_short(Objects.requireNonNull(Constants.teamMap.get(element.getTeam())).getShort_name());
            element.setSingular_name(Objects.requireNonNull(Constants.playerTypeMap.get(element.getElement_type())).getSingular_name());
            element.setSingular_name_short(Objects.requireNonNull(Constants.playerTypeMap.get(element.getElement_type())).getSingular_name_short());
            elementMap.put(element.getId(), element);
        }
        Constants.playerMap = elementMap;
    }

}