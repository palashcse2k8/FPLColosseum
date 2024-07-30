package com.infotech.fplcolosseum;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.AppBarLayout;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekStaticDataModel;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.TeamData;
import com.infotech.fplcolosseum.features.login.views.LoginFragment;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.SharedViewModel;
import com.infotech.fplcolosseum.utilities.ToolbarChangeListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ToolbarChangeListener {

    private SharedViewModel sharedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setSupportActionBar(findViewById(R.id.toolbar));
//        setupActionBar();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        //request api for updating static data
        sharedViewModel.getApiData().observe(this, apiResponse -> {
            // Handle API response in the fragment
            if(apiResponse == null) return;
            if (apiResponse.getStatus() == ApiResponse.Status.SUCCESS) {

                // Update Static data from api data
                prepareData(apiResponse.getData());

            } else {
                Log.d(Constants.LOG_TAG, "Data Loading Error" );
            }
        });

        if(Constants.playerMap.isEmpty()){
            sharedViewModel.getGameWeekStaticData();
        }
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
        actionBar.setTitle("Fantasy Freakz!");
    }

    public void prepareData(GameWeekStaticDataModel dataModel){

        Constants.GameWeekStaticData = dataModel;

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_options, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
//        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onToolbarChanged(Toolbar newToolbar) {
//        getSupportActionBar().hide();
//        setSupportActionBar(newToolbar);
        Log.d("MainActivity", "onToolbarChanged called with: " + newToolbar);
        Toolbar defaultToolbar = findViewById(R.id.toolbar);
        Log.d("MainActivity", "onToolbarChanged default toolbar with: " + defaultToolbar);

        if (newToolbar != null) {

            // Remove newToolbar from its current parent, if any
            ViewGroup parentViewGroup = (ViewGroup) newToolbar.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeView(newToolbar);
            }

            AppBarLayout appBarLayout = findViewById(R.id.appbarLayout);
            appBarLayout.removeView(defaultToolbar);
            appBarLayout.addView(newToolbar);
            setSupportActionBar(newToolbar);
            Log.d("MainActivity", "setSupportActionBar called with: " + newToolbar);
        } else {
            Log.e("MainActivity", "New toolbar is null");
        }
    }
}