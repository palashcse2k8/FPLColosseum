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
import com.infotech.fplcolosseum.utilities.Constants;

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
            if (Constants.LoggedInUser.getPlayer().getEntry() == entry_id) {
                transferHistoryApiResultLiveData.addSource(dataRepository.getLatestTransferHistory(entry_id), latestTransfers -> {
                    if (!latestTransfers.getData().isEmpty()) {
                        // Check if the original list is not null
                        if (listApiResponse.getData() != null) {
                            // Add all latest transfers to the existing list
                            listApiResponse.getData().addAll(0, latestTransfers.getData());
                        } else {
                            // If the original list is null, set it to the latest transfers
//                            listApiResponse.setData(latestTransfers.getData());
                        }
                    }
                    dataLoading.setValue(false);
                    transferHistoryApiResultLiveData.setValue(listApiResponse);

                });
            } else {
                dataLoading.setValue(false);
                transferHistoryApiResultLiveData.setValue(listApiResponse);
            }
        });
    }
}
