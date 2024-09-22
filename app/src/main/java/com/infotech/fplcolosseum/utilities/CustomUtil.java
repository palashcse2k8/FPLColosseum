package com.infotech.fplcolosseum.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekStaticDataModel;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.TeamData;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

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

    public static void updateFixtureData(List<MatchDetails> matchDetails) {
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

        for (MatchDetails match : matchDetails) {

            long event = match.getEvent();

            // Creating OpponentData for team_a
            OpponentData teamAData = new OpponentData();
            teamAData.setTeamID(match.getTeam_a());
            teamAData.setDifficulty(match.getTeam_h_difficulty()); // Assuming difficulty is team score for simplicity, adjust as necessary
            teamAData.setKickOffTime(match.getKickoff_time());
            teamAData.setMinutesPlayed(match.getMinutes());
            teamAData.setFinished(match.isFinished());
            teamAData.setGoalConceded(match.getTeam_h_score());
            teamAData.setGoalScored(match.getTeam_a_score());
            teamAData.setHome(true);

            // Creating OpponentData for team_h
            OpponentData teamHData = new OpponentData();
            teamHData.setTeamID(match.getTeam_h());
            teamHData.setDifficulty(match.getTeam_a_difficulty()); // Assuming difficulty is team score for simplicity, adjust as necessary
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


    public static List<PlayersData> deepCopyPlayerList(List<PlayersData> originalList) {
        List<PlayersData> copyList = new ArrayList<>();
        for (PlayersData player : originalList) {
            copyList.add(new PlayersData(player)); // Assuming PlayersData has a copy constructor
        }
        return copyList;
    }

    public static PlayersData deepCopyPlayer(PlayersData original) {
        return new PlayersData(original);
    }

    public static int getDifficultyLevelColor(long number) {
        if (number == 1) {
            return Color.parseColor("#375523");  // lowest value
        } else if (number == 2) {
            return Color.parseColor("#01fc7a");
        } else if (number == 3) {
            return Color.parseColor("#e7e7e7");
        } else if (number == 4) {
            return Color.parseColor("#ff1751");
        } else if (number ==5){
            return Color.parseColor("#80072d"); // high difficulty value
        } else {
            return 0;
        }
    }

    public static int getDifficultyLeveTextColor(long number) {
        if (number == 4 || number == 5) {
            return Color.WHITE;  // Returns white color
        }

        return Color.BLACK;  // Returns black color
    }

    public static void prepareData(GameWeekStaticDataModel dataModel){

        Constants.GameWeekStaticData = dataModel;

        //setting team map
        Map<Long, TeamData> teamMap = new HashMap<>();
        for (TeamData data : dataModel.getTeams()) {
            teamMap.put(data.getId(), data);
        }
        Constants.teamMap = teamMap;

        //setting gameWeek map
        Map<Long, GameWeekEvent> gameWeekMap = new HashMap<>();
        for (GameWeekEvent weekEvent : dataModel.getEvents()) {
            gameWeekMap.put(weekEvent.getId(), weekEvent);
        }
        Constants.gameWeekMap = gameWeekMap;

        //setting player type map
        Map<Long, Player_Type> palyerTypeMap = new HashMap<>();
        for (Player_Type type : dataModel.getPlayer_types()) {
            palyerTypeMap.put(type.getId(), type);
        }
        Constants.playerTypeMap = palyerTypeMap;

        //setting player map
        Map<Long, PlayersData> elementMap = new HashMap<>();
        for (PlayersData element : dataModel.getElements()) {
            //adding extra fields here
            element.setTeam_name_full(Objects.requireNonNull(Constants.teamMap.get(element.getTeam())).getName());
            element.setTeam_name_short(Objects.requireNonNull(Constants.teamMap.get(element.getTeam())).getShort_name());
            element.setSingular_name(Objects.requireNonNull(Constants.playerTypeMap.get(element.getElement_type())).getSingular_name());
            element.setSingular_name_short(Objects.requireNonNull(Constants.playerTypeMap.get(element.getElement_type())).getSingular_name_short());
            elementMap.put(element.getId(), element);
        }
        Constants.playerMap = elementMap;
    }

    public static void printTeamPlayers(List<PlayersData> teamPlayers) {
        for (PlayersData player : teamPlayers) {
            Log.d("FPLC", player.getPosition() + " -> " + player.getWeb_name() + (player.isIs_captain() ? " Captain" : "") + (player.isIs_vice_captain() ? " Vice Captain" : ""));
        }
    }

    public static boolean hasMoreThanThreePlayersFromSameTeam(List<PlayersData> players) {
        // Group players by their team ID and count the number of players in each team
        Map<Long, Long> teamCounts = players.stream()
                .collect(Collectors.groupingBy(PlayersData::getTeam, Collectors.counting()));

        // Check if any team has more than 3 players
        return teamCounts.values().stream().anyMatch(count -> count > 3);
    }

    public static Optional<PlayersData> findPlayerById(List<PlayersData> playerList, Long playerId) {
        return playerList.stream()
                .filter(player -> player.getId() == playerId) // Assuming PlayerData has a getId() method
                .findFirst();
    }

    // Method to get time zone ID from country code
    public static String getTimeZoneId() {
        // Use default Locale time zone ID for the country
        // Here you can use a library like TimeZoneMapper or ICU4J for more accurate results
        return TimeZone.getDefault().getID();
    }

    // Method to convert UTC time to local time
    public static String convertUtcToLocalTime(String utcTimeString) {
        // Get the time zone for the provided country code
        String timeZoneId = getTimeZoneId();

        if (timeZoneId != null) {
            // Parse the UTC time string with the "Z" suffix using ZonedDateTime
            ZonedDateTime utcZonedTime = ZonedDateTime.parse(utcTimeString, DateTimeFormatter.ISO_DATE_TIME);

            // Convert the UTC time to the local time in the target country's time zone
            ZonedDateTime localZonedTime = utcZonedTime.withZoneSameInstant(ZoneId.of(timeZoneId));

            // Format and return the converted local time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd, HH:mm");
            return localZonedTime.format(formatter);
        } else {
            throw new IllegalArgumentException("Time zone not found for country code");
        }
    }

    // Method to convert UTC time to local time
    public static LocalDateTime convertUtcToLocalDateTime(String utcTimeString) {

        String timeZoneId = getTimeZoneId();

        // Parse the UTC time string with the "Z" suffix using ZonedDateTime
        ZonedDateTime utcZonedTime = ZonedDateTime.parse(utcTimeString, DateTimeFormatter.ISO_DATE_TIME);

        // Convert the UTC time to the local time in the target time zone
        ZonedDateTime localZonedTime = utcZonedTime.withZoneSameInstant(ZoneId.of(timeZoneId));

        // Extract and return the LocalDateTime from ZonedDateTime
        return localZonedTime.toLocalDateTime();
    }

    public static String convertedPrice(long currentPrice){
        return "€" + (double) currentPrice / 10 + "m";
    }
}
