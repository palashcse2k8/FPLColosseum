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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.adapter.OnPlayerClickOrDragListener;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

public class OverlayView extends LinearLayout {
    Context context;
    private TextView label1TextView;
    private TextView label2TextView;
    private TextView label3TextView;
    private TextView info1TextView;
    private TextView info2TextView;
    private TextView info3TextView;
    private boolean isDraggable;

    private int row;
    private int column;

    public OverlayView(Context context, boolean isDraggable) {
        super(context);
        initializeViews(context);
        this.isDraggable = isDraggable;
        this.context = context;
        if (isDraggable) {
//            setOnTouchListener(new TouchListener());
//            setOnDragListener(new DragListener());
        }
    }

    public OverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
        if (isDraggable) {
//            setOnTouchListener(new TouchListener());
//            setOnDragListener(new DragListener());
        }
    }

    public OverlayView(Context context, PlayersData player, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
        if (isDraggable) {
//            setOnTouchListener(new TouchListener());
//            setOnDragListener(new DragListener());
        }
    }

    private void initializeViews(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.overlay_layout, this, true);

        label1TextView = findViewById(R.id.label1);
        label2TextView = findViewById(R.id.label2);
        label3TextView = findViewById(R.id.label3);

        info1TextView = findViewById(R.id.info1);
        info2TextView = findViewById(R.id.info2);
        info3TextView = findViewById(R.id.info3);
    }

    // Add methods to set player details (image, name, team name) if needed

    public void setLabel1TextView(String text) {
        this.label1TextView.setText(text);
    }

    public void setLabel2TextView(String text) {
        this.label2TextView.setText(text);
    }

    public void setLabel3TextView(String text) {
        this.label3TextView.setText(text);
    }

    public void setInfo1TextView(String text) {
        this.info1TextView.setText(text);
    }

    public void setInfo2TextView(String text) {
        this.info2TextView.setText(text);
    }

    public void setInfo3TextView(String text) {
        this.info3TextView.setText(text);
    }
}

class TouchListener implements View.OnTouchListener {
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

class DragListener implements View.OnDragListener {

    OnPlayerClickOrDragListener onPlayerClickOrDragListener;

    public DragListener(OnPlayerClickOrDragListener onPlayerClickOrDragListener) {
        this.onPlayerClickOrDragListener = onPlayerClickOrDragListener;
    }

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

