package com.infotech.fplcolosseum.utilities;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Patterns;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class CustomUtil {
    public static String getSimpleDate(String trUploadDate) {
        // Define the input and output date formats
        String formattedDate = "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            // Parse the input date string into a Date object
            Date date = inputFormat.parse(trUploadDate);

            // Format the Date object into the desired output format
            formattedDate = outputFormat.format(date);

            // Use the formattedDate as needed (e.g., set it to a TextView)
//            Log.d("formattedDate", formattedDate);// Output: 20-06-2023
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String getSimpleTime(String trUploadDate) {

        String formattedTime = null;
        // Define the input date format
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());

        try {
            // Parse the input date string into a Date object
            Date date = inputFormat.parse(trUploadDate);

            // Get the time portion from the Date object
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            formattedTime = outputFormat.format(date);

            // Use the formattedTime as needed (e.g., set it to a TextView)
//            System.out.println(formattedTime); // Output: 11:24 AM
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public static String getCurrentDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }

    public static String generateTimestamp() {
        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(currentDate);
    }

    public static void showDownloadInfo(Context context, String fileName, int progress) {
        String message = "Downloading " + fileName + ": " + progress + "%";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void shareFile(Context context, String fileName) {

        Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(fileName));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, "Share File"));
    }

//    public static String saveResponseForTestEnv(Response<ResponseBody> response, String fileName) throws IOException {
//        String apiResponse;
//
//        String filePath = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + fileName + ".json").getAbsolutePath();
//        File file = new File(filePath);
//
//        if (!file.exists()) {
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                JsonUtil.saveResponseToJsonFile(response, filePath);
//            }
//        } else {
//            assert response.body() != null;
//            apiResponse = response.body().string().replace("\\", "");
//        }
//
//        return apiResponse;
//    }

    /**
     * Validate the email address using the Patterns class.
     *
     * @param email The email address to be validated.
     * @return True if the email address is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void updateFixtureData(List<MatchDetails> matchDetails){
//
//        Gson gson = new Gson();
//
//        // Example JSON string
////        String jsonString = "[{\"code\":123,\"event\":1,\"finished\":true,\"id\":456,...}, {...}]";
//
//        // Correctly specify the type of the list
//        Type listType = new TypeToken<List<MatchDetails>>() {}.getType();
//
//        // Deserialize JSON string into a List<MatchDetails>
//        List<MatchDetails> matchDetailsList = gson.fromJson((matchDetails, listType);

        for(MatchDetails match: matchDetails) {

            long event = match.getEvent();

            // Creating OpponentData for team_a
            OpponentData teamAData = new OpponentData();
            teamAData.setTeamID(match.getTeam_a());
            teamAData.setDifficulty(match.getTeam_a_difficulty()); // Assuming difficulty is team score for simplicity, adjust as necessary
            teamAData.setKickOffTime(match.getKickoff_time());
            teamAData.setMinutesPlayed(match.getMinutes());
            teamAData.setFinished(match.isFinished());
            teamAData.setGoalConceded(match.getTeam_h_score());
            teamAData.setGoalScored(match.getTeam_a_score());
            teamAData.setHome(true);

            // Creating OpponentData for team_h
            OpponentData teamHData = new OpponentData();
            teamHData.setTeamID(match.getTeam_h());
            teamHData.setDifficulty(match.getTeam_h_difficulty()); // Assuming difficulty is team score for simplicity, adjust as necessary
            teamHData.setKickOffTime(match.getKickoff_time());
            teamHData.setMinutesPlayed(match.getMinutes());
            teamHData.setFinished(match.isFinished());
            teamHData.setGoalConceded(match.getTeam_a_score());
            teamHData.setGoalScored(match.getTeam_h_score());
            teamHData.setHome(false);

            // Update fixtureData for team_a
            Constants.fixtureData.computeIfAbsent(event, k -> new HashMap<>()).put(match.getTeam_a(), teamHData);

            // Update fixtureData for team_h
            Constants.fixtureData.computeIfAbsent(event, k -> new HashMap<>()).put(match.getTeam_h(), teamAData);
        }
    }

}
