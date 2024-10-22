package com.infotech.fplcolosseum.features.homepage.adapter;

// Interface for communicating with the outer RecyclerView
public interface InnerRecyclerViewItemClickListener {
    void onItemExpanded(int outerPosition, int innerPosition);  // Method to notify outer RecyclerView
}
