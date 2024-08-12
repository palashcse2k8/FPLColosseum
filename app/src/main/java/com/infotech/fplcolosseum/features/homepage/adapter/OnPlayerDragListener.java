package com.infotech.fplcolosseum.features.homepage.adapter;

import com.infotech.fplcolosseum.features.homepage.views.PlayerView;

public interface OnPlayerDragListener {
    void onPlayerDragged(int fromPosition, int toPosition, PlayerView draggedPlayerView, PlayerView dropPlayerView);
}
