package com.infotech.fplcolosseum.features.homepage.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infotech.fplcolosseum.R;

//public class PlayerView extends LinearLayout {
//
//    private ImageView imageView;
//    private TextView playerNameTextView;
//    private TextView teamNameTextView;
//
//    private int row;
//    private int column;
//
//    public PlayerView(Context context) {
//        super(context);
//        initializeViews(context);
//    }
//
//    public PlayerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initializeViews(context);
//    }
//
//    public PlayerView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initializeViews(context);
//    }
//
//    private void initializeViews(Context context) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.player_template, this);
//
//        imageView = findViewById(R.id.imageView);
//        playerNameTextView = findViewById(R.id.playerNameTextView);
//        teamNameTextView = findViewById(R.id.teamNameTV);
//
//        // Set LayoutParams for PlayerView
////        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
////                LinearLayout.LayoutParams.MATCH_PARENT,
////                LinearLayout.LayoutParams.WRAP_CONTENT);
////        setLayoutParams(layoutParams);
//    }
//
//    // Add methods to set player details (image, name, team name) if needed
//    public void setPlayerImage(int imageResId) {
//        imageView.setImageResource(imageResId);
//    }
//
//    public void setPlayerName(String playerName) {
//        playerNameTextView.setText(playerName);
//    }
//
//    public void setTeamName(String teamName) {
//        teamNameTextView.setText(teamName);
//    }
//
//    // Methods to set player position
//    public void setRow(int row) {
//        this.row = row;
//    }
//
//    public void setColumn(int column) {
//        this.column = column;
//    }
//}

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
        setOnTouchListener(new TouchListener());
        setOnDragListener(new DragListener());
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
        setOnTouchListener(new TouchListener());
        setOnDragListener(new DragListener());
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
        setOnTouchListener(new TouchListener());
        setOnDragListener(new DragListener());
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.player_template, this, true);

        imageView = findViewById(R.id.imageView);
        playerNameTextView = findViewById(R.id.playerNameTextView);
        teamNameTextView = findViewById(R.id.teamNameTV);
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

    private static class TouchListener implements OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Create a drag shadow for the view
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                // Start dragging the view
                view.startDrag(null, shadowBuilder, view, 0);
                // Make the view invisible during the drag
                view.setVisibility(View.VISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private  static class DragListener implements OnDragListener{
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Do something when the drag starts
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // Do something when the dragged item enters the drop area
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    // Do something when the dragged item exits the drop area
                    return true;
                case DragEvent.ACTION_DROP:
                    // Do something when the dragged item is dropped onto the drop area

                    View draggedView = (View) event.getLocalState();
                    View dropTargetView = v;

                    // Get the parent GridLayout
                    GridLayout gridLayout = (GridLayout) dropTargetView.getParent();

                    // Get the layout parameters of the dragged and drop target views
                    GridLayout.LayoutParams draggedParams = (GridLayout.LayoutParams) draggedView.getLayoutParams();
                    GridLayout.LayoutParams dropTargetParams = (GridLayout.LayoutParams) dropTargetView.getLayoutParams();


                    // Remove the views from their parent layouts
                    ViewGroup draggedParent = (ViewGroup) draggedView.getParent();
                    draggedParent.removeView(draggedView);

                    ViewGroup dropTargetParent = (ViewGroup) dropTargetView.getParent();
                    dropTargetParent.removeView(dropTargetView);

                    // Add the views to the opposite layouts
                    draggedParent.addView(dropTargetView, draggedParams);
                    dropTargetParent.addView(draggedView, dropTargetParams);

                    draggedView.setVisibility(View.VISIBLE); // Show the dragged view
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Do something when the drag ends
                    return true;
                default:
                    return false;
            }
        }
    }
}
