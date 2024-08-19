package com.infotech.fplcolosseum.features.homepage.adapter;

import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

public interface PlayerInfoUpdateListener {
    void onSetCaptain(PlayersData player);
    void onSetViceCaptain(PlayersData player);
    void onSwitchPlayer(PlayersData player);
}
