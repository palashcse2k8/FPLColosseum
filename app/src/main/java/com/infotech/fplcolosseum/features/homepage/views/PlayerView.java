package com.infotech.fplcolosseum.features.homepage.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.adapter.OnPlayerDragListener;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;
import com.squareup.picasso.Picasso;

public class PlayerView extends LinearLayout {

    PlayersData player;

    private OnPlayerDragListener dragListener;
    Context context;
    private ImageView imageView;
    private TextView playerNameTextView;
    private TextView teamNameTextView;
    private TextView opponentTeamNameTextView;
    private ImageView imageTopLeft;
    private ImageView imageTopRight;
    private FrameLayout imageBottomLeft;
    private ImageView imageBottomRight;
    private ImageView imageBottomMiddle;
    private TextView changeOfPlayingThisRound;

    private boolean isDraggable;

    private int row;
    private int column;

    private static boolean isDragging = false;
    private static long startClickTime;
    private static final int MAX_CLICK_DURATION = 200;

    public PlayerView(Context context, PlayersData player, boolean isDraggable, OnPlayerDragListener onPlayerDragListener) {
        super(context);
        initializeViews(context);
        this.player = player;
        this.isDraggable = isDraggable;
        this.context = context;
        this.dragListener = onPlayerDragListener;
        if (isDraggable) {
            setOnTouchListener(new TouchListener(onPlayerDragListener));
            setOnDragListener(new DragListener(onPlayerDragListener));
        }
    }

    public PlayerView(Context context, PlayersData player, AttributeSet attrs) {
        super(context, attrs);
        this.player = player;
        initializeViews(context);
        if (isDraggable) {
            setOnTouchListener(new TouchListener(this.dragListener));
            setOnDragListener(new DragListener(this.dragListener));
        }
    }

    public PlayerView(Context context, PlayersData player, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.player = player;
        initializeViews(context);
        if (isDraggable) {
            setOnTouchListener(new TouchListener(this.dragListener));
            setOnDragListener(new DragListener(this.dragListener));
        }
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.player_template, this, true);

        imageView = findViewById(R.id.imageView);
        playerNameTextView = findViewById(R.id.playerNameTextView);
        teamNameTextView = findViewById(R.id.teamNameTV);
        opponentTeamNameTextView = findViewById(R.id.opponentTeamNameTV);
        imageBottomRight = findViewById(R.id.iconBottomRight);
        imageBottomMiddle = findViewById(R.id.iconBottomMiddle);
        imageBottomLeft = findViewById(R.id.iconBottomLeft);
        changeOfPlayingThisRound = findViewById(R.id.changeOfPlayingThisRound);
    }

    // Add methods to set player details (image, name, team name) if needed
    public void setPlayerImage(String imageURL) {

        Picasso.get()
                .load(imageURL)
                .error(R.mipmap.no_image)
                .into(imageView);
    }

    public void setPlayerName(String playerName) {
        playerNameTextView.setText(playerName);
    }

    public void setTeamName(String teamName) {
        teamNameTextView.setText(teamName);
    }

    public void setOpponentTeamName(String teamName) {
        opponentTeamNameTextView.setText(teamName);
    }

    // Methods to set player position
    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setCaptain() {
        imageBottomRight.setVisibility(View.VISIBLE);
        imageBottomRight.setBackgroundResource(R.drawable.alpha_c_circle);
    }

    public void unSetCaptain() {
        imageBottomRight.setVisibility(View.GONE);
//        imageBottomRight.setBackgroundResource(R.drawable.alpha_c_circle);
    }

    public void setViceCaptain() {
        imageBottomRight.setVisibility(View.VISIBLE);
        imageBottomRight.setBackgroundResource(R.drawable.alpha_v_circle);
    }

    public void setDreamTeamPlayer() {
        imageBottomMiddle.setVisibility(View.VISIBLE);
    }

    public void setAvailability(long chanceOfPlayingThisRound) {

        imageBottomLeft.setVisibility(View.VISIBLE);
        changeOfPlayingThisRound.setText(String.valueOf(chanceOfPlayingThisRound));
        GradientDrawable bgDrawable = (GradientDrawable) changeOfPlayingThisRound.getBackground();

        if (chanceOfPlayingThisRound == 25) {
            changeOfPlayingThisRound.setTextColor(Color.parseColor("#000000"));
            bgDrawable.setColor(Color.parseColor("#FF8000"));

            playerNameTextView.setBackgroundColor(Color.parseColor("#FF8000"));
            playerNameTextView.setTextColor(Color.parseColor("#000000"));
        } else if (chanceOfPlayingThisRound == 50) {
            changeOfPlayingThisRound.setTextColor(Color.parseColor("#000000"));
            bgDrawable.setColor(Color.parseColor("#CCCC00"));

            playerNameTextView.setBackgroundColor(Color.parseColor("#CCCC00"));
            playerNameTextView.setTextColor(Color.parseColor("#000000"));
        } else if (chanceOfPlayingThisRound == 75) {
            changeOfPlayingThisRound.setTextColor(Color.parseColor("#000000"));
            bgDrawable.setColor(Color.parseColor("#FFFF00"));

            playerNameTextView.setBackgroundColor(Color.parseColor("#FFFF00"));
            playerNameTextView.setTextColor(Color.parseColor("#000000"));

        } else {
            changeOfPlayingThisRound.setTextColor(Color.parseColor("#FFFFFF"));
            bgDrawable.setColor(Color.parseColor("#FF0000"));

            playerNameTextView.setBackgroundColor(Color.parseColor("#FF0000"));
        }

    }


    public PlayersData getPlayerData() {
        return this.player;
    }

//    private static class TouchListener implements OnTouchListener {
//
//        OnPlayerDragListener onPlayerDragListener;
//
//        private static final int MAX_CLICK_DURATION = 200;
//        private static final int DRAG_THRESHOLD = 10; // Minimal movement threshold to distinguish drag
//        private float downX, downY;
//
//
//        public TouchListener(OnPlayerDragListener onPlayerDragListener) {
//            this.onPlayerDragListener = onPlayerDragListener;
//        }
//
//        @SuppressLint("ClickableViewAccessibility")
//        @Override
//        public boolean onTouch(View view, MotionEvent event) {
//
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//
//                Log.d(Constants.LOG_TAG, "Player Clicked! ACTION_DOWN");
//                startClickTime = System.currentTimeMillis();
//
//                return true;
//            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                Log.d(Constants.LOG_TAG, "Player Clicked! ACTION_MOVE");
//                // Create a drag shadow for the view
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//                // Start dragging the view
//                view.startDrag(null, shadowBuilder, view, 0);
//                // Make the view invisible during the drag
//                view.setVisibility(View.VISIBLE);
//                isDragging = true;
//                return true;
//            } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                Log.d(Constants.LOG_TAG, "Player Clicked! ACTION_UP");
//                long clickDuration = System.currentTimeMillis() - startClickTime;
//                if (clickDuration < MAX_CLICK_DURATION && !isDragging) {
//                    onPlayerDragListener.onClickPlayer();
//                    isDragging = false;
//                }
//                return true;
//
//            }
//            return true;
//        }
//    }

    private static class TouchListener implements OnTouchListener {

        OnPlayerDragListener onPlayerDragListener;
        private static final int MAX_CLICK_DURATION = 200;
        private static final int DRAG_THRESHOLD = 10; // Minimal movement threshold to distinguish drag
        private float downX, downY;

        public TouchListener(OnPlayerDragListener onPlayerDragListener) {
            this.onPlayerDragListener = onPlayerDragListener;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    startClickTime = System.currentTimeMillis();
                    isDragging = false;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    if (!isDragging) {
                        float deltaX = Math.abs(event.getX() - downX);
                        float deltaY = Math.abs(event.getY() - downY);
                        if (deltaX > DRAG_THRESHOLD || deltaY > DRAG_THRESHOLD) {
                            // Start dragging
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                            view.startDrag(null, shadowBuilder, view, 0);
                            view.setVisibility(View.VISIBLE);
                            isDragging = true;
                        }
                    }
                    return true;

                case MotionEvent.ACTION_UP:
                    long clickDuration = System.currentTimeMillis() - startClickTime;
                    if (!isDragging && clickDuration < MAX_CLICK_DURATION) {
                        if (onPlayerDragListener != null) {
                            onPlayerDragListener.onClickPlayer((PlayerView) view);
                        }
                    }
                    return true;

                default:
                    return false;
            }
        }
    }

    private static class DragListener implements OnDragListener {
        OnPlayerDragListener onPlayerDragListener;

        public DragListener(OnPlayerDragListener onPlayerDragListener) {
            this.onPlayerDragListener = onPlayerDragListener;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    isDragging = true;
                    return true;

                case DragEvent.ACTION_DROP:
                    isDragging = false;
                    View draggedView = (View) event.getLocalState();
                    View dropTargetView = v;

                    // Handle the logic for swapping the players here
                    if (onPlayerDragListener != null) {
                        int fromPosition = getViewPosition(draggedView);
                        int toPosition = getViewPosition(dropTargetView);

                        PlayerView draggedPlayerView = (PlayerView) draggedView;
                        PlayerView dropTargetPlayerView = (PlayerView) dropTargetView;

                        // Example of drag handling
                        if (draggedPlayerView.getPlayerData().getPosition() == 1 && dropTargetPlayerView.getPlayerData().getPosition() == 12) {
                            onPlayerDragListener.onPlayerDragged(fromPosition, toPosition, draggedPlayerView, dropTargetPlayerView, true);
                        } else if (draggedPlayerView.getPlayerData().getPosition() == 12 && dropTargetPlayerView.getPlayerData().getPosition() == 1) {
                            onPlayerDragListener.onPlayerDragged(fromPosition, toPosition, draggedPlayerView, dropTargetPlayerView, true);
                        } else if (draggedPlayerView.getPlayerData().getPosition() > 12) {
                            if (dropTargetPlayerView.getPlayerData().getPosition() != 1 && dropTargetPlayerView.getPlayerData().getPosition() != 12) {
                                onPlayerDragListener.onPlayerDragged(fromPosition, toPosition, draggedPlayerView, dropTargetPlayerView, dropTargetPlayerView.getPlayerData().getPosition() <= 13);
                            }
                        } else if (draggedPlayerView.getPlayerData().getPosition() > 1 && draggedPlayerView.getPlayerData().getPosition() < 12) {
                            if (dropTargetPlayerView.getPlayerData().getPosition() > 12) {
                                onPlayerDragListener.onPlayerDragged(fromPosition, toPosition, draggedPlayerView, dropTargetPlayerView, true);
                            }
                        }
                    }
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    isDragging = false;
                    return true;

                default:
                    return false;
            }
        }

        private int getViewPosition(View view) {
            Long position = (Long) view.getTag();
            return Math.toIntExact(position != null ? position : -1);
        }
    }


//    private static class DragListener implements OnDragListener {
//        OnPlayerDragListener onPlayerDragListener;
//
//        public DragListener(OnPlayerDragListener onPlayerDragListener) {
//            this.onPlayerDragListener = onPlayerDragListener;
//        }
//
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//            int action = event.getAction();
//            switch (action) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    // Do something when the drag starts
//                    return true;
//                case DragEvent.ACTION_DRAG_ENTERED:
//                    // Do something when the dragged item enters the drop area
//                    return true;
//                case DragEvent.ACTION_DRAG_EXITED:
//                    // Do something when the dragged item exits the drop area
//                    return true;
//                case DragEvent.ACTION_DROP:
//                    // Do something when the dragged item is dropped onto the drop area
//
//                    View draggedView = (View) event.getLocalState();
//                    View dropTargetView = v;
//
//                    // Get the parent GridLayout
//                    GridLayout gridLayout = (GridLayout) dropTargetView.getParent();
//
//                    // Get the layout parameters of the dragged and drop target views
//                    GridLayout.LayoutParams draggedParams = (GridLayout.LayoutParams) draggedView.getLayoutParams();
//                    GridLayout.LayoutParams dropTargetParams = (GridLayout.LayoutParams) dropTargetView.getLayoutParams();
//
//
//                    // Remove the views from their parent layouts
//                    ViewGroup draggedParent = (ViewGroup) draggedView.getParent();
//                    int fromPosition = getViewPosition(draggedView);
//
//                    ViewGroup dropTargetParent = (ViewGroup) dropTargetView.getParent();
//                    int toPosition = getViewPosition(dropTargetView);
//
//                    // Get the PlayerView instances
//                    PlayerView draggedPlayerView = (PlayerView) draggedView;
//                    PlayerView dropTargetPlayerView = (PlayerView) dropTargetView;
//
//                    // Retrieve the player data
//                    PlayersData draggedPlayerData = draggedPlayerView.getPlayerData();
//                    PlayersData dropTargetPlayerData = dropTargetPlayerView.getPlayerData();
//
//                    if (draggedPlayerData.getPosition() == 1 && dropTargetPlayerData.getPosition() == 12) {
////                        draggedParent.removeView(draggedView);
////                        dropTargetParent.removeView(dropTargetView);
////
////                        // Add the views to the opposite layouts
////                        draggedParent.addView(dropTargetView, draggedParams);
////                        dropTargetParent.addView(draggedView, dropTargetParams);
////
////                        if (dropTargetPlayerView.getPlayerData().isIs_captain()) {
////                            draggedPlayerView.setCaptain();
////                            draggedPlayerView.getPlayerData().setIs_captain(true);
////                            dropTargetPlayerView.unSetCaptain();
////                            dropTargetPlayerView.getPlayerData().setIs_captain(false);
////                        }
////
////                        draggedView.setVisibility(View.VISIBLE); // Show the dragged view
//
//                        if (onPlayerDragListener != null) {
//                            onPlayerDragListener.onPlayerDragged(fromPosition, toPosition, draggedPlayerView, dropTargetPlayerView, true);
//                        }
//
//                        return true;
//                    }
//
//                    if (draggedPlayerData.getPosition() == 12 && dropTargetPlayerData.getPosition() == 1) {
//
//                        if (onPlayerDragListener != null) {
//                            onPlayerDragListener.onPlayerDragged(fromPosition, toPosition, draggedPlayerView, dropTargetPlayerView, true);
//                        }
//
//                        return true;
//                    }
//
//                    if (draggedPlayerData.getPosition() > 12) {
//                        if (dropTargetPlayerData.getPosition() == 1 || dropTargetPlayerData.getPosition() == 12) {
//                            return false;
//                        }
//
//                        if (onPlayerDragListener != null) {
//
//                            onPlayerDragListener.onPlayerDragged(fromPosition, toPosition, draggedPlayerView, dropTargetPlayerView, dropTargetPlayerData.getPosition() <= 13);
//                        }
//
//                        return true;
//                    }
//
//                    if (draggedPlayerData.getPosition() > 1 && draggedPlayerData.getPosition() < 12) {
//                        if (dropTargetPlayerData.getPosition() > 12) {
//
//                            if (onPlayerDragListener != null) {
//                                onPlayerDragListener.onPlayerDragged(fromPosition, toPosition, draggedPlayerView, dropTargetPlayerView, true);
//                            }
//
//                            return true;
//                        }
//                    }
//
//                    return false;
//
//
//                case DragEvent.ACTION_DRAG_ENDED:
//                    // Do something when the drag ends
//                    return true;
//                default:
//                    return false;
//            }
//        }
//
//        private int getViewPosition(View view) {
//            // Implement a method to find the position of the view in the list or grid
//            // For example, if you have tags set on views that correspond to their positions:
//            Long position = (Long) view.getTag();
//            return Math.toIntExact(position != null ? position : -1);
//        }
//
//    }
}
