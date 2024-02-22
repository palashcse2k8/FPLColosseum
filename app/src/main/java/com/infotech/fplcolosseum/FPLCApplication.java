package com.infotech.fplcolosseum;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class FPLCApplication extends Application {

    private Handler handler;
    private Runnable fetchDataRunnable;
    // Define methods for API calls
//    public LiveData<ApiResponse<GameWeekStaticDataModel>> fetchData() {
//        MutableLiveData<ApiResponse<GameWeekStaticDataModel>> data = new MutableLiveData<>();
//        // Make API call using Retrofit or another HTTP client
//        // Update LiveData with the response
//        GameWeekStaticDataRepository repository = new GameWeekStaticDataRepository(this);
//        data.setValue(repository.getGameWeekStaticData().getValue());
//        return data;
//    }
    FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//            .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("FPLColosseum")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return true; // set to true to show debug log
            }
        });

        //manual calling
//        fetchData();
//
//        // Initialize handler and runnable
//        handler = new Handler();
//        fetchDataRunnable = new Runnable() {
//            @Override
//            public void run() {
//                // Fetch data after every 10 minutes
//                fetchData();
//                // Schedule the next execution after 10 minutes
//                handler.postDelayed(this, 10 * 60 * 1000);
//            }
//        };

//         Schedule the first execution
//        handler.post(fetchDataRunnable);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        // Remove the callback to stop the periodic task when the app is closed
//        handler.removeCallbacks(fetchDataRunnable);
//    }
}
