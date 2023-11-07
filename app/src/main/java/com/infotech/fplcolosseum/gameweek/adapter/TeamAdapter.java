package com.infotech.fplcolosseum.gameweek.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.RowLayoutNewBinding;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.models.custom.PlayerDataModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<ManagerModel> teams;

    Context context;
    public TeamAdapter(List<ManagerModel> teams) {
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        RowLayoutNewBinding binding = RowLayoutNewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_layout_new, parent, false);
        return new TeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        ManagerModel team = teams.get(position);
        holder.binding.teamPosition.setText(String.valueOf(position+1));
        holder.binding.teamName.setText(team.getTeamName());
        holder.binding.managerName.setText(team.getManagerName());
        holder.binding.gameWeekPoint.setText(String.valueOf((int)team.getGameWeekPoints()));
        holder.binding.captainPoint.setText(String.valueOf((int)team.getCaptainGameWeekPoints()));
        holder.binding.vcPoints.setText(String.valueOf((int)team.getViceCaptainGameWeekPoints()));
        holder.binding.bonusPoints.setText(String.valueOf((int)team.getGameWeekBonusPointsXI()));
        holder.binding.benchPoint.setText(String.valueOf((int)team.getGameWeekBenchPoints()));
        holder.binding.goalScored.setText(String.valueOf((int)team.getGoalScored()));
        holder.binding.goalConceds.setText(String.valueOf((int)team.getGoalConceded()));
        holder.binding.bpsPoints.setText(String.valueOf((int)team.getGameWeekBPSPointsXI()));

//        String imageUrl = "https://source.unsplash.com/random/200x200?sig=1";
//        Glide.with(context) //1
//                .load(imageUrl)
//                .placeholder(R.drawable.ic_launcher_foreground)
//                .error(R.drawable.ic_launcher_foreground)
//                .skipMemoryCache(true) //2
//                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                .into(holder.binding.imageID);

        String imageURL = "https://awnless-arrangement.000webhostapp.com/images/"+ (int)team.getId()+ ".jpg";
        Picasso.get()
                .load(imageURL)
                .error(R.mipmap.no_image)
                .into(holder.binding.imageID);
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
