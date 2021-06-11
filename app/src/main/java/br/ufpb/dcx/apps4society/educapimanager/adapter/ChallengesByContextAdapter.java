package br.ufpb.dcx.apps4society.educapimanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.ufpb.dcx.apps4society.educapimanager.R;
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Challenge;

public class ChallengesByContextAdapter extends RecyclerView.Adapter<ChallengesByContextAdapter.ItemViewHolder>{

    private Context context;
    private List<Challenge> challenges;

    public ChallengesByContextAdapter(android.content.Context context, List<Challenge> challenges) {
        this.context = context;
        this.challenges = challenges;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_challenges_adapter, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);

        if (challenge != null){
            Glide.with(context)
                    .load(challenge.getImageUrl())
                    .into(holder.imageChallenge);

            holder.nameChallenge.setText(challenge.getWord());
        }

    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imageChallenge;
        TextView nameChallenge;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageChallenge = itemView.findViewById(R.id.challenge_image_view);
            nameChallenge = itemView.findViewById(R.id.challenge_word_view);
        }
    }
}
