package com.infotech.fplcolosseum.features.gameweek.adapter;
import static com.infotech.fplcolosseum.utilities.ManagerImageLink.managerImagerLinkIDs;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.infotech.fplcolosseum.BuildConfig;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.RowLayoutNewBinding;
import com.infotech.fplcolosseum.features.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.utilities.AppLogger;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private final List<ManagerModel> teams;
    private final Picasso picasso;
    private final Context context;

    public TeamAdapter(Context context, List<ManagerModel> teams) {
        this.teams = teams;
        this.context = context.getApplicationContext(); // Use application context
        // Create a single Picasso instance for the entire adapter
        Picasso.Builder builder = new Picasso.Builder(context);
        picasso = builder
                .indicatorsEnabled(false) // Set to true for debugging
                .loggingEnabled(BuildConfig.DEBUG) // Set to true for debugging
                .build();
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutNewBinding binding = RowLayoutNewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        ManagerModel team = teams.get(position);

        // Set text views
        holder.binding.teamPosition.setText(String.valueOf(position + 1));
        holder.binding.teamName.setText(team.getTeamName());
        holder.binding.managerName.setText(team.getManagerName());
        holder.binding.gameWeekPoint.setText(String.valueOf((int) team.getGameWeekPointsWithoutTransferCost()));
        holder.binding.captainPoint.setText(String.valueOf((int) team.getCaptainGameWeekPoints()));
        holder.binding.vcPoints.setText(String.valueOf((int) team.getViceCaptainGameWeekPoints()));
        holder.binding.bonusPoints.setText(String.valueOf((int) team.getGameWeekBonusPointsXI()));
        holder.binding.benchPoint.setText(String.valueOf((int) team.getGameWeekBenchPoints()));
        holder.binding.goalScored.setText(String.valueOf((int) team.getGoalScored()));
        holder.binding.goalConceds.setText(String.valueOf((int) team.getGoalConceded()));
        holder.binding.bpsPoints.setText(String.valueOf((int) team.getGameWeekBPSPointsXI()));

        // Load image with proper handling
        loadImageForTeam(holder, team);

        // Apply styling for qualified teams
        applyQualifiedStyling(holder, position);
    }

    private void loadImageForTeam(TeamViewHolder holder, ManagerModel team) {
        // Get image ID from map
        String imageId = managerImagerLinkIDs.get(team.getId());
        if (imageId == null) {
            // Handle missing image ID
            picasso.load(R.mipmap.no_image)
                    .into(holder.binding.imageID);
            return;
        }

        String imageURL = "https://drive.google.com/uc?export=view&id=" + imageId;
        AppLogger.d("Loading image for manager " + team.getId() + ": " + imageURL);

        // Cancel any previous request
        picasso.cancelRequest(holder.binding.imageID);

        // Clear previous image
        holder.binding.imageID.setImageDrawable(null);

        // Load new image with placeholder
        picasso.load(imageURL)
                .placeholder(R.drawable.rounded_corner_shape_qaulified) // Add a placeholder
                .error(R.mipmap.no_image)
                .resize(100, 100)
                .centerCrop()
                .priority(Picasso.Priority.HIGH)
                .noFade()
                .tag(team.getId()) // Tag with manager ID for debugging
                .into(holder.binding.imageID);
    }

    private void applyQualifiedStyling(TeamViewHolder holder, int position) {
        if (position < 4) { // for qualified manager teams
            // Change the main background
            holder.itemView.setElevation(20);
            holder.itemView.setBackground(
                    ContextCompat.getDrawable(context, R.drawable.rounded_corner_shape_qaulified)
            );
            holder.binding.gameWeekPointLayout.setBackground(
                    ContextCompat.getDrawable(context, R.drawable.rounded_corner_shape_qaulified)
            );
            holder.binding.gameWeekPointLayout.setElevation(10);

            holder.binding.qualifiedSymbol.setVisibility(View.VISIBLE);
            // Add golden border to the image
            holder.binding.imageID.setStrokeColorResource(R.color.qualified_accent);

            // Change position badge background to gold
            holder.binding.teamPosition.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.qualified_accent))
            );
            holder.binding.teamPosition.setTextColor(Color.BLACK);

            // Make team name more prominent
            holder.binding.teamName.setTextColor(ContextCompat.getColor(context, R.color.qualified_accent));
            holder.binding.teamName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            holder.binding.teamName.setTypeface(null, Typeface.BOLD);

            // Make manager name more prominent
            holder.binding.managerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            holder.binding.managerName.setTypeface(null, Typeface.BOLD);

            // Make game week point more prominent
            holder.binding.gameWeekPoint.setTextColor(ContextCompat.getColor(context, R.color.qualified_accent));
            holder.binding.gameWeekPoint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            holder.binding.gameWeekPoint.setTypeface(null, Typeface.BOLD);

            holder.binding.gameWeekPointText.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public void onViewRecycled(@NonNull TeamViewHolder holder) {
        super.onViewRecycled(holder);
        // Cancel any pending image request when view is recycled
        picasso.cancelRequest(holder.binding.imageID);
        // Clear the image to prevent showing stale images
        holder.binding.imageID.setImageDrawable(null);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        RowLayoutNewBinding binding;

        public TeamViewHolder(RowLayoutNewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
