package com.infotech.fplcolosseum.features.homepage.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.player_information.views.PlayerFullInformationActivity;
import com.infotech.fplcolosseum.features.player_search.views.PlayerSelectionBottomSheetFragment;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.PlayerSortingCriterion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TopTransferPlayerListAdapter extends RecyclerView.Adapter<TopTransferPlayerListAdapter.PlayerViewHolder> {

    private List<PlayersData> playersList;

    String sortingCriterion = PlayerSortingCriterion.TRANSFERS_IN_ROUND.getDisplayName();

    FragmentActivity activity;

    public TopTransferPlayerListAdapter(List<PlayersData> playersList, String sortingCriterion, FragmentActivity activity) {
        this.playersList = playersList;
        this.sortingCriterion = sortingCriterion;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_top_transfer_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        PlayersData player = playersList.get(position);
        holder.bind(player);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(activity, PlayerFullInformationActivity.class);
            intent.putExtra("playerData", player); // Replace with actual player name
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    public void updatePlayersList(List<PlayersData> newPlayersList, String sortingCriterion) {
        this.playersList = newPlayersList;
        this.sortingCriterion = sortingCriterion;
        notifyDataSetChanged();
    }


    class PlayerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView playerImage;
        private final TextView playerName;
        private final TextView playerTypeAndTeam;
        private final TextView playerTotalPoints;
        private final TextView playerPrice;
        private TextView playerSelectedBy;
        private final LinearLayout playerSelectionItemView;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerSelectionItemView = itemView.findViewById(R.id.playerSelectionItemView);
            playerImage = itemView.findViewById(R.id.player_image);
            playerName = itemView.findViewById(R.id.player_name);
            playerTypeAndTeam = itemView.findViewById(R.id.player_position);
            playerTotalPoints = itemView.findViewById(R.id.player_tp);
            playerPrice = itemView.findViewById(R.id.player_price);

        }

        public void bind(PlayersData player) {
            String imageUrl = "https://resources.premierleague.com/premierleague/photos/players/110x140/p" + player.getCode() + ".png";

            // Placeholder animation: Fade in the image after it has loaded
            AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
            fadeInAnimation.setDuration(1000); // 1 second fade-in duration

            Picasso.get()
                    .load(imageUrl)
                    .error(R.mipmap.no_image)
                    .into(playerImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            // Start the fade-in animation when the image is successfully loaded
                            playerImage.startAnimation(fadeInAnimation);
                        }

                        @Override
                        public void onError(Exception e) {
//                            Log.e("Image Load Error", "Failed to load image: " + e.getMessage());
                        }
                    });

            playerName.setText(player.getWeb_name());
            String teamName = Objects.requireNonNull(Constants.teamMap.get(player.getTeam())).getShort_name();
            String playerType = Objects.requireNonNull(Constants.playerTypeMap.get(player.getElement_type())).getSingular_name_short();
            String playerTypeAndTeamString = playerType + " | " + teamName;
            playerTypeAndTeam.setText(playerTypeAndTeamString);

//            playerTotalPoints.setText(String.valueOf(player.getTotal_points()));
            updateCriterionText(playerTotalPoints, player, sortingCriterion);
            playerPrice.setText(CustomUtil.convertedPrice(player.getNow_cost()));
        }

        private void updateCriterionText(TextView tv, PlayersData playersData, String sortingCriterion) {

            PlayerSortingCriterion selectedCriterion = Arrays.stream(PlayerSortingCriterion.values())
                    .filter(criterion -> criterion.getDisplayName().equals(sortingCriterion))
                    .findFirst()
                    .orElse(null);

            if (selectedCriterion == null) {
                Log.d(Constants.LOG_TAG, "Invalid sorting criterion: " + sortingCriterion);
                return;
            }
            switch (selectedCriterion) {
                case TOTAL_POINTS:
                    tv.setText(String.valueOf(playersData.getTotal_points()));
                    break;
                case ROUND_POINTS:
                    tv.setText(String.valueOf(playersData.getEvent_points())); // Adjust according to your model
                    break;
                case TEAM_SELECTED_BY:
                    String selectedByText = playersData.getSelected_by_percent() + "%";
                    tv.setText(selectedByText); // Example field
                    break;
                case MINUTES_PLAYED:
                    tv.setText(String.valueOf(playersData.getMinutes())); // Adjust according to your model
                    break;
                case GOALS_SCORED:
                    tv.setText(String.valueOf(playersData.getGoals_scored()));
                    break;
                case ASSIST:
                    tv.setText(String.valueOf(playersData.getAssists()));
                    break;
                case CLEAN_SHEET:
                    tv.setText(String.valueOf(playersData.getClean_sheets()));
                    break;
                case GOALS_CONCEDED:
                    tv.setText(String.valueOf(playersData.getGoals_conceded()));
                    break;
                case OWN_GOALS:
                    tv.setText(String.valueOf(playersData.getOwn_goals()));
                    break;
                case PENALTY_SAVED:
                    tv.setText(String.valueOf(playersData.getPenalties_saved()));
                    break;
                case PENALTY_MISSED:
                    tv.setText(String.valueOf(playersData.getPenalties_missed()));
                    break;
                case YELLOW_CARDS:
                    tv.setText(String.valueOf(playersData.getYellow_cards()));
                    break;
                case RED_CARDS:
                    tv.setText(String.valueOf(playersData.getRed_cards()));
                    break;
                case SAVES:
                    tv.setText(String.valueOf(playersData.getSaves()));
                    break;
                case BONUS:
                    tv.setText(String.valueOf(playersData.getBonus()));
                    break;
                case BONUS_POINTS_SYSTEM:
                    tv.setText(String.valueOf(playersData.getBps())); // Example field
                    break;
                case INFLUENCE:
                    tv.setText(String.valueOf(playersData.getInfluence()));
                    break;
                case CREATIVITY:
                    tv.setText(String.valueOf(playersData.getCreativity()));
                    break;
                case THREAT:
                    tv.setText(String.valueOf(playersData.getThreat()));
                    break;
                case ICT_INDEX:
                    tv.setText(String.valueOf(playersData.getIct_index())); // Example field
                    break;
                case GAMES_STARTED:
                    tv.setText(String.valueOf(playersData.getStarts()));
                    break;
                case FORM:
                    tv.setText(String.valueOf(playersData.getForm()));
                    break;
                case TIMES_IN_TEAM_OF_THE_WEEK:
                    tv.setText(String.valueOf(playersData.getDreamteam_count())); // Example field
                    break;
                case VALUE_FORM:
                    tv.setText(String.valueOf(playersData.getValue_form()));
                    break;
                case VALUE_SEASON:
                    tv.setText(String.valueOf(playersData.getValue_season()));
                    break;
                case POINTS_PER_MATCH:
                    tv.setText(String.valueOf(playersData.getPoints_per_game()));
                    break;
                case TRANSFERS_IN:
                    tv.setText(String.valueOf(playersData.getTransfers_in()));
                    break;
                case TRANSFERS_OUT:
                    tv.setText(String.valueOf(playersData.getTransfers_out()));
                    break;
                case TRANSFERS_IN_ROUND:
                    tv.setText(String.valueOf(playersData.getTransfers_in_event())); // Example field
                    break;
                case TRANSFERS_OUT_ROUND:
                    tv.setText(String.valueOf(playersData.getTransfers_out_event())); // Example field
                    break;
                case NET_TRANSFERS_IN_ROUND:
                    tv.setText(String.valueOf(playersData.getTransfers_in_event() - playersData.getTransfers_out_event())); // Example field
                    break;
                case NET_TRANSFERS_OUT_ROUND:
                    tv.setText(String.valueOf(playersData.getTransfers_out_event() - playersData.getTransfers_in_event())); // Example field
                    break;
                case PRICE:
                    tv.setText(CustomUtil.convertedPrice(playersData.getNow_cost())); // Example field
                    break;
                case PRICE_RISE:
                    tv.setText(CustomUtil.convertedPrice(playersData.getCost_change_start())); // Example field
                    break;
                case PRICE_FALL:
                    tv.setText(CustomUtil.convertedPrice(playersData.getCost_change_start())); // Example field
                    break;
                case PRICE_RISE_ROUND:
                    tv.setText(CustomUtil.convertedPrice(playersData.getCost_change_event())); // Example field
                    break;
                case PRICE_FALL_ROUND:
                    tv.setText(CustomUtil.convertedPrice(playersData.getCost_change_event())); // Example field
                    break;
                case EXPECTED_GOALS:
                    tv.setText(String.valueOf(playersData.getExpected_goals())); // Example field
                    break;
                case EXPECTED_ASSISTS:
                    tv.setText(String.valueOf(playersData.getExpected_assists())); // Example field
                    break;
                case EXPECTED_GOALS_INVOLVEMENT:
                    tv.setText(String.valueOf(playersData.getExpected_goal_involvements())); // Example field
                    break;
                case EXPECTED_GOALS_CONCEDED:
                    tv.setText(String.valueOf(playersData.getExpected_goals_conceded())); // Example field
                    break;
                default:
                    tv.setText(String.valueOf(playersData.getTotal_points())); // Fallback
                    break;
            }
        }

    }
}
