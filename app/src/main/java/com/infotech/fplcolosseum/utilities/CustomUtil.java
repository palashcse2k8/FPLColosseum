package com.infotech.fplcolosseum.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Element_Stats;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekStaticDataModel;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.TeamData;
import com.infotech.fplcolosseum.features.league_information.views.LeagueInformationActivity;
import com.infotech.fplcolosseum.features.manager_dashboard.views.MangerDashboardActivity;
import com.infotech.fplcolosseum.features.player_information.views.PlayerFullInformationActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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

        long currentEvent = 0;
        for (MatchDetails match : matchDetails) {

            long event = match.getEvent();
            currentEvent = event;

            // Creating OpponentData for team_a
            OpponentData teamAData = new OpponentData();
            teamAData.setTeamID(match.getTeam_a());
            teamAData.setOpponentTeamId(match.getTeam_h());
            teamAData.setDifficulty(match.getTeam_h_difficulty()); // Assuming difficulty is team score for simplicity, adjust as necessary
            teamAData.setKickOffTime(match.getKickoff_time());
            teamAData.setMinutesPlayed(match.getMinutes());
            teamAData.setFinished(match.isFinished());
            teamAData.setStarted(match.getStarted());
            teamAData.setGoalConceded(match.getTeam_h_score());
            teamAData.setGoalScored(match.getTeam_a_score());
            teamAData.setHome(true);
            teamAData.setStats(match.getStats());

            // Creating OpponentData for team_h
            OpponentData teamHData = new OpponentData();
            teamHData.setTeamID(match.getTeam_h());
            teamHData.setOpponentTeamId(match.getTeam_a());
            teamHData.setDifficulty(match.getTeam_a_difficulty()); // Assuming difficulty is team score for simplicity, adjust as necessary
            teamHData.setKickOffTime(match.getKickoff_time());
            teamHData.setMinutesPlayed(match.getMinutes());
            teamHData.setFinished(match.isFinished());
            teamHData.setStarted(match.getStarted());
            teamHData.setGoalConceded(match.getTeam_a_score());
            teamHData.setGoalScored(match.getTeam_h_score());
            teamHData.setHome(false);
            teamHData.setStats(match.getStats());

            // Update fixtureData for team_a
            Constants.fixtureData.computeIfAbsent(event, k -> new HashMap<>()).put(match.getTeam_a(), teamHData);

            // Update fixtureData for team_h
            Constants.fixtureData.computeIfAbsent(event, k -> new HashMap<>()).put(match.getTeam_h(), teamAData);
        }
        Constants.fixtures.put(currentEvent, matchDetails);
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
            element.setTeam_name_full(Objects.requireNonNull(Constants.teamMap.get(element.getTeam())).getName()); // team name full
            element.setTeam_name_short(Objects.requireNonNull(Constants.teamMap.get(element.getTeam())).getShort_name()); // team name short
            element.setElement_type_full(Objects.requireNonNull(Constants.playerTypeMap.get(element.getElement_type())).getSingular_name()); // player type full
            element.setElement_type_short(Objects.requireNonNull(Constants.playerTypeMap.get(element.getElement_type())).getSingular_name_short()); // player type short
            elementMap.put(element.getId(), element);
        }
        Constants.playerMap = elementMap;

        //setting element stats map
        Map<String, String> elementStatMap = new HashMap<>();
        for (Element_Stats element_stats : dataModel.getElement_stats()) {
            elementStatMap.put(element_stats.getName(), element_stats.getLabel());
        }
        Constants.elementStatMap = elementStatMap;
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

    public static String convertPercent(long value){
        return value + "%";
    }

    public static String convertDateToStringFormat(String inputDate){

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEE d MMM", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        return date.format(outputFormatter);
    }

    public static List<PlayersData> sortPlayersByNewsAdded(List<PlayersData> players) {
        List<PlayersData> sortedPlayers = new ArrayList<>(players);

        sortedPlayers.sort(new Comparator<PlayersData>() {
            @Override
            public int compare(PlayersData p1, PlayersData p2) {
                // Check if news_added is present for both players
                boolean hasNewsP1 = p1.getNews_added() != null && !p1.getNews_added().isEmpty();
                boolean hasNewsP2 = p2.getNews_added() != null && !p2.getNews_added().isEmpty();

                // If both have news, compare dates
                if (hasNewsP1 && hasNewsP2) {
                    LocalDateTime dateP1 = parseDateTime(p1.getNews_added());
                    LocalDateTime dateP2 = parseDateTime(p2.getNews_added());
                    return dateP2.compareTo(dateP1); // Descending order
                }

                // If only one has news, prioritize that one
                if (hasNewsP1) return -1;
                if (hasNewsP2) return 1;

                // If neither has news, maintain original order
                return 0;
            }
        });

        return sortedPlayers;
    }

    private static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public static String getPlayerImageLink(PlayersData player){
        return "https://resources.premierleague.com/premierleague/photos/players/110x140/p" + player.getCode() + ".png";
    }

    public static void updatePlayerImage(ImageView imageView, PlayersData player){
        // Placeholder animation: Fade in the image after it has loaded
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(1000); // 1 second fade-in duration

        Picasso.get()
                .load(getPlayerImageLink(player))
                .error(R.mipmap.no_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Start the fade-in animation when the image is successfully loaded
                        imageView.startAnimation(fadeInAnimation);
                    }

                    @Override
                    public void onError(Exception e) {
//                            Log.e("Image Load Error", "Failed to load image: " + e.getMessage());
                    }
                });
    }

    public static void scrollToItem(final HorizontalScrollView scrollView, final LinearLayout container, final int itemIndex) {
        // Ensure the views are laid out
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                if (container.getChildCount() > itemIndex) {
                    View targetView = container.getChildAt(itemIndex);
                    int targetX = targetView.getLeft();

                    // Calculate the center offset
                    int centerOffset = (scrollView.getWidth() - targetView.getWidth()) / 2;
                    targetX -= centerOffset;

                    // Ensure we don't scroll past the start
                    targetX = Math.max(0, targetX);

                    // Scroll to the target position
                    scrollView.smoothScrollTo(targetX, 0);
                }
            }
        });
    }

    public static void startPlayerFullProfile(FragmentActivity activity, PlayersData playersData){
        Intent intent = new Intent(activity, PlayerFullInformationActivity.class);
        intent.putExtra("playerData", playersData);
        activity.startActivity(intent);
    }

    public static String convertDateToDayDateMonth(String dateString){
        // Define the input format
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set to UTC

        // Define the output format
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE dd MMMM", Locale.ENGLISH);

        try {
            // Parse the input date string to a Date object
            Date date = inputFormat.parse(dateString);

            // Format the date to the desired output format
            return outputFormat.format(date);
              // Output: Saturday 19 October
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String convertDateToDeadLine(String dateString) {
        // Define the input format
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set to UTC

        // Get the current offset from GMT in milliseconds
        int offset = TimeZone.getDefault().getRawOffset();

        // Get the absolute value of the offset and determine the sign
        int absoluteOffset = Math.abs(offset);
        char sign = offset >= 0 ? '+' : '-';

        // Convert the absolute offset to hours and minutes
        int hours = absoluteOffset / 3600000;
        int minutes = (absoluteOffset % 3600000) / 60000;

        // Format the offset string with leading zero for hours
        String offsetString = String.format("%s%02d:%02d", sign, hours, minutes);

        // Define the output format
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE dd MMM HH:mm 'GMT' " + offsetString, Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getDefault()); // Set to the system's default time zone

        try {
            // Parse the input date string to a Date object
            Date date = inputFormat.parse(dateString);

            // Format the date to the desired output format
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getLocalTimeFromUTCDateString(String dateString) {
        // Define the input format
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set to UTC

        // Define the output format for the local time
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault()); // Adjust the format as needed
        outputFormat.setTimeZone(TimeZone.getDefault()); // Set to the system's default time zone

        try {
            // Parse the input date string to a Date object
            Date date = inputFormat.parse(dateString);
            // Format the date to the desired output format
            return outputFormat.format(date); // Return only the time in HH:mm format
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void startLeagueInformationActivity(Context activity, long leagueId){
        Intent intent = new Intent(activity, LeagueInformationActivity.class);
        intent.putExtra(LeagueInformationActivity.LEAGUE_ID, leagueId);
        activity.startActivity(intent);
    }

    public static void startManagerDashboardActivity(Context activity, long managerId){
        Intent intent = new Intent(activity, MangerDashboardActivity.class);
        intent.putExtra(MangerDashboardActivity.ARG_MANAGER_ID, managerId);
        activity.startActivity(intent);
    }
}
