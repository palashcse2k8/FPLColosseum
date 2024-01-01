package com.infotech.fplcolosseum.features.homepage.viewmodels.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.infotech.fplcolosseum.data.repositories.LoginRepository;
import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.APIServices;
import com.infotech.fplcolosseum.features.login.models.SessionManager;

public class MyTeamViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;
    APIServices apiServices;

    SessionManager sessionManager;

    Application application;
    public MyTeamViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
    }


}
