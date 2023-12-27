package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infotech.fplcolosseum.R;

public class PlayerView extends LinearLayout {

    private ImageView imageView;
    private TextView playerNameTextView;
    private TextView teamNameTextView;

    private int row;
    private int column;

    public PlayerView(Context context) {
        super(context);
        initializeViews(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_template, this);

        imageView = findViewById(R.id.imageView);
        playerNameTextView = findViewById(R.id.playerNameTextView);
        teamNameTextView = findViewById(R.id.teamNameTV);

        // Set LayoutParams for PlayerView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
    }

    // Add methods to set player details (image, name, team name) if needed
    public void setPlayerImage(int imageResId) {
        imageView.setImageResource(imageResId);
    }

    public void setPlayerName(String playerName) {
        playerNameTextView.setText(playerName);
    }

    public void setTeamName(String teamName) {
        teamNameTextView.setText(teamName);
    }

    // Methods to set player position
    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
