package com.infotech.fplcolosseum.remote;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CustomHeaderInterceptor implements Interceptor {
    private final String deviceId;
    private final String source;
    private final String version;

    public CustomHeaderInterceptor(String deviceId, String source, String version) {
        this.deviceId = deviceId;
        this.source = source;
        this.version = version;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

//        if (Constants.TEST_ENV) {
//
//            String[] apiUrlSplits = originalRequest.url().uri().toString().split("/");
//            String fileName = apiUrlSplits[apiUrlSplits.length - 1];
//
//            File file = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + fileName + ".json");
//
//            if (file.exists()) {
//                String localResponse = JsonUtil.readJsonFromFile(file.getAbsolutePath());
//                if (localResponse != null) {
//                    ResponseBody responseBody = ResponseBody.create(localResponse, MediaType.get("application/json"));
//
//                    return new Response.Builder()
//                            .code(200)
//                            .message("OK")
//                            .request(chain.request())
//                            .protocol(Protocol.HTTP_1_1)
//                            .body(responseBody)
//                            .build();
//                }
//            }
//        }

        Request modifiedRequest = originalRequest.newBuilder()
                .header("deviceId", deviceId)
                .header("source", source)
                .header("version", version)
                .build();

        return chain.proceed(modifiedRequest);
    }
}