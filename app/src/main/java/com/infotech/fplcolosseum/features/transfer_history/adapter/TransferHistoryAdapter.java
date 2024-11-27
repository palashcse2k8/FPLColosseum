package com.infotech.fplcolosseum.features.transfer_history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.transfer_history.models.TransferHistoryModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TransferHistoryAdapter extends RecyclerView.Adapter<TransferHistoryAdapter.TransferHistoryViewHolder> {
    private final Context context;
    private Map<Integer, List<TransferHistoryModel>> groupedData;
    private List<Integer> eventKeys;

    public TransferHistoryAdapter(Context context, List<TransferHistoryModel> originalData) {
        this.context = context;
        this.groupedData = groupDataByEvent(originalData);
        this.eventKeys = new ArrayList<>(groupedData.keySet());
    }

    public void updateAdapterData(List<TransferHistoryModel> newData) {

        if (newData == null) return;

        this.groupedData.clear();
        this.eventKeys.clear();

        this.groupedData = groupDataByEvent(newData);
        this.eventKeys = new ArrayList<>(groupedData.keySet());
        notifyDataSetChanged();
    }

    private Map<Integer, List<TransferHistoryModel>> groupDataByEvent(List<TransferHistoryModel> data) {
        Map<Integer, List<TransferHistoryModel>> groupedMap = new HashMap<>();
        for (TransferHistoryModel item : data) {
            groupedMap.computeIfAbsent(item.getEvent(), k -> new ArrayList<>()).add(item);
        }
        return groupedMap;
    }

    // ViewHolder class
    public static class TransferHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventTitle;
        LinearLayout containerItems;

        public TransferHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.gameWeekText);
            containerItems = itemView.findViewById(R.id.itemContainer);
        }
    }

    @NonNull
    @Override
    public TransferHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_transfer_history_event_cardview, parent, false);
        return new TransferHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferHistoryViewHolder holder, int position) {
        // Get the event and its items
        int eventKey = eventKeys.get(getItemCount() - (position + 1));
        List<TransferHistoryModel> eventItems = groupedData.get(eventKey);

        // Set event title
        String eventTitle = "GW" + eventKey;
        holder.tvEventTitle.setText(eventTitle);

        // Clear previous items
        holder.containerItems.removeAllViews();

        // Add items to the container
        assert eventItems != null;
        for (int i = 0; i < eventItems.size(); i++) {
            TransferHistoryModel item = eventItems.get(i);
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_transfer_history_event_cardview_item, holder.containerItems, false);


            PlayersData playerInData = Constants.playerMap.get(item.getElement_in());
            PlayersData playerOutData = Constants.playerMap.get(item.getElement_out());

            if (playerInData == null || playerOutData == null) {
                return;
            }

            //set player out information
            TextView tvElementOut = itemView.findViewById(R.id.tout_player_name);
            tvElementOut.setText(playerOutData.getWeb_name());

            TextView tvElementOutPosition = itemView.findViewById(R.id.tout_player_position);
            String positionOutText = Objects.requireNonNull(Constants.teamMap.get(playerOutData.getTeam())).getShort_name() + " | " + CustomUtil.convertedPrice(item.getElement_out_cost());
            tvElementOutPosition.setText(positionOutText);

            ImageView ivPlayerOut = itemView.findViewById(R.id.tout_player_image);

            CustomUtil.updatePlayerImage(ivPlayerOut, playerOutData);

            //set date and time
            TextView tvTransferredDate = itemView.findViewById(R.id.transferDate);
            tvTransferredDate.setText(CustomUtil.getLocalDateFromUTCMicroSecondsString(item.getTime()));
            TextView tvTransferredTime = itemView.findViewById(R.id.transferTime);
            tvTransferredTime.setText(CustomUtil.getLocalTimeFromUTCMicroSecondString(item.getTime()));

            //set player in information
            TextView tvElementIn = itemView.findViewById(R.id.tin_player_name);
            tvElementIn.setText(playerInData.getWeb_name());

            TextView tvElementInPosition = itemView.findViewById(R.id.tin_player_position);
            String positionInText = CustomUtil.convertedPrice(item.getElement_in_cost()) + " | " + Objects.requireNonNull(Constants.teamMap.get(playerInData.getTeam())).getShort_name();
            tvElementInPosition.setText(positionInText);

            ImageView ivPlayerIn = itemView.findViewById(R.id.tin_player_image);

            CustomUtil.updatePlayerImage(ivPlayerIn, playerInData);

            holder.containerItems.addView(itemView);

            // Add divider for all items except the last one
            if (i < eventItems.size() - 1) {
                View divider = new View(context);
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1 // 1 pixel height for the divider
                ));
                divider.setBackgroundColor(ContextCompat.getColor(context, R.color.switch_track_off));

                // Optional: Add some margin to the divider
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) divider.getLayoutParams();
//                params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.marginHorizontal);
//                params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.marginHorizontal);
//                divider.setLayoutParams(params);

                holder.containerItems.addView(divider);
            }
        }
    }

    @Override
    public int getItemCount() {
        return groupedData.size();
    }
}