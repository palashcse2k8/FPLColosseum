package com.infotech.fplcolosseum.features.player_search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayerSelectionViewModel extends ViewModel {
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery.setValue(searchQuery);
    }
}
