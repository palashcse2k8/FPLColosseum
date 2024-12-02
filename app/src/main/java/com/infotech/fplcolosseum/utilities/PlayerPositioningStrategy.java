package com.infotech.fplcolosseum.utilities;

import android.util.Log;

import com.infotech.fplcolosseum.features.homepage.models.myteam.PlayerPosition;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerPositioningStrategy {
    public static List<PlayerPosition> getPositionsForFormation(List<PlayersData> players) {
        List<PlayerPosition> positions = new ArrayList<>();

        List<PlayersData> defenders = new ArrayList<>();
        List<PlayersData> midfielders = new ArrayList<>();
        List<PlayersData> forwards = new ArrayList<>();

        for (int i = 1; i < players.size() - 4; i++) {

            PlayersData entry = players.get(i);

            if (entry.getSingular_name_short().equalsIgnoreCase("DEF")) {
                defenders.add(entry);
            } else if (entry.getSingular_name_short().equalsIgnoreCase("MID")) {
                midfielders.add(entry);
            } else if (entry.getSingular_name_short().equalsIgnoreCase("FWD")) {
                forwards.add(entry);
            } else {
                Log.d(Constants.LOG_TAG, "type not defined");
            }
        }

        // Validate team composition
        if (!isValidTeamComposition(defenders, midfielders, forwards)) {
            throw new IllegalArgumentException("Invalid team composition");
        }

        // Add goalkeeper
        positions.add(new PlayerPosition(players.get(0), 0, 2));

        // Position defenders based on formation
        positionDefenders(defenders, positions);

        // Position midfielders based on formation
        positionMidfielders(midfielders, positions);

        // Position forwards based on formation
        positionForwards(forwards, positions);

        positions.add(new PlayerPosition(players.get(11), 4, 0));
        positions.add(new PlayerPosition(players.get(12), 4, 2));
        positions.add(new PlayerPosition(players.get(13), 4, 3));
        positions.add(new PlayerPosition(players.get(14), 4, 4));

        return positions;
    }

    private static boolean isValidTeamComposition(List<PlayersData> defenders,
                                                  List<PlayersData> midfielders,
                                                  List<PlayersData> forwards) {
        return defenders.size() >= 3 && defenders.size() <= 5 &&
                midfielders.size() >= 2 && midfielders.size() <= 5 &&
                !forwards.isEmpty() && forwards.size() <= 3;
    }

    private static void positionDefenders(List<PlayersData> defenders, List<PlayerPosition> positions) {
        // Default to 4 defenders strategy

        int midfielderCount = defenders.size();

       if (midfielderCount == 3) {
            // Triangular midfield setup
            positions.add(new PlayerPosition(defenders.get(0), 1, 1));
            positions.add(new PlayerPosition(defenders.get(1), 1, 2));
            positions.add(new PlayerPosition(defenders.get(2), 1, 3));
        } else if (midfielderCount == 4) {
            // 4-midfielder diamond/box setup
            positions.add(new PlayerPosition(defenders.get(0), 1, 0));
            positions.add(new PlayerPosition(defenders.get(1), 1, 1));
            positions.add(new PlayerPosition(defenders.get(2), 1, 3));
            positions.add(new PlayerPosition(defenders.get(3), 1, 4));
        } else if (midfielderCount == 5) {
            // 5-midfielder advanced positioning
            positions.add(new PlayerPosition(defenders.get(0), 1, 1));
            positions.add(new PlayerPosition(defenders.get(1), 1, 2));
            positions.add(new PlayerPosition(defenders.get(2), 1, 3));
            positions.add(new PlayerPosition(defenders.get(3), 1, 4));
            positions.add(new PlayerPosition(defenders.get(4), 1, 5));
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }
    }

    private static void positionMidfielders(List<PlayersData> midfielders, List<PlayerPosition> positions) {
        // Adaptive midfield positioning based on number of midfielders
        int midfielderCount = midfielders.size();

        if (midfielderCount == 2) {
            // Simple 2-midfielder setup
            positions.add(new PlayerPosition(midfielders.get(0), 2, 1));
            positions.add(new PlayerPosition(midfielders.get(1), 2, 3));
        } else if (midfielderCount == 3) {
            // Triangular midfield setup
            positions.add(new PlayerPosition(midfielders.get(0), 2, 1));
            positions.add(new PlayerPosition(midfielders.get(1), 2, 2));
            positions.add(new PlayerPosition(midfielders.get(2), 2, 3));
        } else if (midfielderCount == 4) {
            // 4-midfielder diamond/box setup
            positions.add(new PlayerPosition(midfielders.get(0), 2, 0));
            positions.add(new PlayerPosition(midfielders.get(1), 2, 1));
            positions.add(new PlayerPosition(midfielders.get(2), 2, 3));
            positions.add(new PlayerPosition(midfielders.get(3), 2, 4));
        } else if (midfielderCount == 5) {
            // 5-midfielder advanced positioning
            positions.add(new PlayerPosition(midfielders.get(0), 2, 1));
            positions.add(new PlayerPosition(midfielders.get(1), 2, 2));
            positions.add(new PlayerPosition(midfielders.get(2), 2, 3));
            positions.add(new PlayerPosition(midfielders.get(3), 2, 2));
            positions.add(new PlayerPosition(midfielders.get(4), 2, 3));
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }
    }

    private static void positionForwards(List<PlayersData> forwards, List<PlayerPosition> positions) {
        int forwardCount = forwards.size();

        if (forwardCount == 1) {
            // Single striker setup
            positions.add(new PlayerPosition(forwards.get(0), 3, 2));
        } else if (forwardCount == 2) {
            // Two-striker partnership
            positions.add(new PlayerPosition(forwards.get(0), 3, 1));
            positions.add(new PlayerPosition(forwards.get(1), 3, 3));
        } else if (forwardCount == 3) {
            // Three-forward attacking line
            positions.add(new PlayerPosition(forwards.get(0), 3, 1));
            positions.add(new PlayerPosition(forwards.get(1), 3, 2));
            positions.add(new PlayerPosition(forwards.get(2), 3, 3));
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }
    }
}

