package com.infotech.fplcolosseum.data.sources.network;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomHeaderInterceptor implements Interceptor {
    private final String deviceId;
    private final String source;
    private final String version;

    public CustomHeaderInterceptor(String deviceId, String source, String version) {
        this.deviceId = deviceId;
        this.source = source;
        this.version = version;
    }

//    @NonNull
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request originalRequest = chain.request();
//
////        if (Constants.TEST_ENV) {
////
////            String[] apiUrlSplits = originalRequest.url().uri().toString().split("/");
////            String fileName = apiUrlSplits[apiUrlSplits.length - 1];
////
////            File file = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + fileName + ".json");
////
////            if (file.exists()) {
////                String localResponse = JsonUtil.readJsonFromFile(file.getAbsolutePath());
////                if (localResponse != null) {
////                    ResponseBody responseBody = ResponseBody.create(localResponse, MediaType.get("application/json"));
////
////                    return new Response.Builder()
////                            .code(200)
////                            .message("OK")
////                            .request(chain.request())
////                            .protocol(Protocol.HTTP_1_1)
////                            .body(responseBody)
////                            .build();
////                }
////            }
////        }
//
//        Request modifiedRequest = originalRequest.newBuilder()
//                .header("deviceId", deviceId)
//                .header("source", source)
//                .header("version", version)
//                .build();
//
//        return chain.proceed(modifiedRequest);
//    }

//    @NonNull
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        long t1 = System.nanoTime();
////        Log.d("Network", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
//
//        Response response = chain.proceed(request);
//
//        long t2 = System.nanoTime();
////        Log.d("Network", String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//
//        return response;
//    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //ignoring caching for login api
        if(isApiToExcludeFromCaching(request)){
            request = request.newBuilder()
                    .header("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:103.0) Gecko/20100101 Firefox/103.0")
                    .header("Accept-Language","en-GB,en;q=0.5")
                    .method(request.method(), request.body())
                    .build();
            return chain.proceed(request);
        }

        // Set the cache duration for 1 day
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(1, TimeUnit.DAYS)
                .build();

        request = request.newBuilder()
                .cacheControl(cacheControl)
                .header("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:103.0) Gecko/20100101 Firefox/103.0")
                .header("Accept-Language","en-GB,en;q=0.5")
                .method(request.method(), request.body())
                .build();

        Response response = chain.proceed(request);

//        if (response.cacheResponse() != null) {
//            // This response was served from the cache
//            Logger.d("CACHE", "Response retrieved from cache");
//        } else {
//            // This response was retrieved from the network
//            Logger.d("CACHE", "Response retrieved from network");
//        }

        // Cache the response
        return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build();
    }
    private boolean isApiToExcludeFromCaching(Request request) {
        // Add conditions to identify APIs that should be excluded from caching
        // For example, check the URL, method, or headers
        // Return true if the API should be excluded, false otherwise
        String url = request.url().toString();


        // Example: Exclude caching for login API
        if (url.contains("login") || url.contains("logout")) {
            return true;
        }

        // Add more conditions as needed

        return false;
    }

}