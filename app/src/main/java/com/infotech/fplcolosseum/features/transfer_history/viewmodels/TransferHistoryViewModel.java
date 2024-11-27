package com.infotech.fplcolosseum.features.transfer_history.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.transfer_history.models.TransferHistoryModel;

import java.util.List;

public class TransferHistoryViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);

    private final MediatorLiveData<ApiResponse<List<TransferHistoryModel>>> transferHistoryApiResultLiveData;


    public TransferHistoryViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.transferHistoryApiResultLiveData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<List<TransferHistoryModel>>> getTransferHistoryLiveData() {
        return transferHistoryApiResultLiveData;
    }

    public void getTransferHistory(long entry_id) {

        dataLoading.setValue(true);
        // Make API call through the repository
        transferHistoryApiResultLiveData.addSource(dataRepository.getTransferHistory(entry_id), listApiResponse -> {
            dataLoading.setValue(false);
            transferHistoryApiResultLiveData.setValue(listApiResponse);
        });
    }
}
