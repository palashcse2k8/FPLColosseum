package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

public class FootballFieldLayout extends GridLayout {

    Context context;
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLUMNS = 5;
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
        this.context = context;
        setOrientation(HORIZONTAL);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.football_field_layout, this);
    }

    // Add methods to add players dynamically
    public void addPlayer(String playerName, String teamName, int imageResId, int row, int column) {
        PlayerView playerView = new PlayerView(this.context, new PlayersData(), false);
        playerView.setPlayerName(playerName);
        playerView.setTeamName(teamName);
        playerView.setPlayerImage("imageResI");

        // Set the position of the player in the FootballFieldLayout
        playerView.setRow(row);
        playerView.setColumn(column);

        // Set LayoutParams for proper rendering
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        playerView.setLayoutParams(layoutParams);

        addView(playerView);
    }

    public void addPlayerNew(String playerName, String teamName, int imageResId, int row, int column) {
        // Loop to create buttons in each cell

                PlayerView playerView = new PlayerView(this.context, new PlayersData(), false);
                playerView.setPlayerName(playerName);
                playerView.setTeamName(teamName);
                playerView.setPlayerImage("imageResId");

                // Set the position of the player in the FootballFieldLayout
                playerView.setRow(row);
                playerView.setColumn(column);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row, 1, 1f); // row, rowSpan, weight
                params.columnSpec = GridLayout.spec(column, 1, 1f); // col, colSpan, weight
                playerView.setLayoutParams(params);
                addView(playerView);
    }
}
