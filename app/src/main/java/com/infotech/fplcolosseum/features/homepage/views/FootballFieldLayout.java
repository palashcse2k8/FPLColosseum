package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class FootballFieldLayout extends LinearLayout {

    public FootballFieldLayout(Context context) {
        super(context);
        initializeViews(context);
    }

    public FootballFieldLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public FootballFieldLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        setOrientation(HORIZONTAL);
    }

    // Add methods to add players dynamically
    public void addPlayer(String playerName, String teamName, int imageResId, int row, int column) {
        PlayerView playerView = new PlayerView(getContext());
        playerView.setPlayerName(playerName);
        playerView.setTeamName(teamName);
        playerView.setPlayerImage(imageResId);

        // Set the position of the player in the FootballFieldLayout
        playerView.setRow(row);
        playerView.setColumn(column);

        addView(playerView);
    }
}
