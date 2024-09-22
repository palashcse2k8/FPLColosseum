//package com.infotech.fplcolosseum.features.player_information.views;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.infotech.fplcolosseum.features.player_information.adapter.HistoryAdapter;
//
//// CustomRecyclerView.java
//public class CustomRecyclerView extends RecyclerView {
//    private int mFixedColumnWidth;
//
//    public CustomRecyclerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected void onMeasure(int widthSpec, int heightSpec) {
//        super.onMeasure(widthSpec, heightSpec);
//        mFixedColumnWidth = ((HistoryAdapter)getAdapter()).getFixedColumnWidth();
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        if (e.getX() < mFixedColumnWidth) {
//            return false;
//        }
//        return super.onInterceptTouchEvent(e);
//    }
//}
