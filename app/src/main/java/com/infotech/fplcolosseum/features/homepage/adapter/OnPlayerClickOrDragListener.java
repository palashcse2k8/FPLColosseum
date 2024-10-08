package com.infotech.fplcolosseum.features.homepage.adapter;

import com.infotech.fplcolosseum.features.homepage.views.PlayerView;

public interface OnPlayerClickOrDragListener {
    void onPlayerDragged(int fromPosition, int toPosition, PlayerView draggedPlayerView, PlayerView dropPlayerView, boolean isSwapData);
    void onClickPlayer(PlayerView view);
}
