package com.infotech.fplcolosseum.features.homepage.adapter;

import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

public interface PlayerTransferListener {
    void onTransferPlayer(PlayersData player);
    void onCancelTransfer(PlayersData player);
}
