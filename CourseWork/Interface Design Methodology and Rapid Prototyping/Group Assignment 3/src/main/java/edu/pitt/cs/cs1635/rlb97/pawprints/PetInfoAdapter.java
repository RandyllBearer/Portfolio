package edu.pitt.cs.cs1635.rlb97.pawprints;

/**
 * Created by colton on 3/25/18.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PetInfoAdapter extends RecyclerView.Adapter<PetInfoAdapter.PetViewHolder> {
    private List<PetInfo> petList;

    public PetInfoAdapter(List<PetInfo> petList) {
        this.petList = petList;
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    @Override
    public void onBindViewHolder(PetViewHolder petViewHolder, int i) {
        PetInfo pet = petList.get(i);
        petViewHolder.petInfo = pet;
        if (pet.name != null) {
            petViewHolder.vBreed.setText(String.format("%s - %s, %s", pet.name, pet.type, pet.breed));
        } else {
            petViewHolder.vBreed.setText(String.format("%s, %s", pet.type, pet.breed));
        }
        petViewHolder.vDistance.setText(String.format("%.2f miles away.", pet.distance));
        if (pet.posting) {
            petViewHolder.vReward.setText(String.format("Reward: $%.2f", pet.reward));
        } else {
            petViewHolder.vReward.setText("");
        }
        petViewHolder.vPhoto.setImageResource(pet.photoId);
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.petinfo_card_layout, viewGroup, false);

        PetViewHolder petViewHolder = new PetViewHolder(itemView);

        return petViewHolder;
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {

        protected PetInfo petInfo;
        protected TextView vBreed;
        protected TextView vDistance;
        protected ImageView vPhoto;
        protected TextView vReward;

        public PetViewHolder(View v) {
            super(v);
            vBreed =  (TextView) v.findViewById(R.id.breed);
            vDistance = (TextView)  v.findViewById(R.id.distance);
            vPhoto = (ImageView)  v.findViewById(R.id.photo);
            vReward = (TextView) v.findViewById(R.id.rewardText);

            final Context context = v.getContext();

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ViewPet.class);
                    intent.putExtra("photoId", petInfo.photoId);
                    intent.putExtra("breed", petInfo.breed);
                    intent.putExtra("type", petInfo.type);
                    intent.putExtra("color", petInfo.color);
                    intent.putExtra("time", petInfo.time);
                    intent.putExtra("distance", petInfo.distance);
                    intent.putExtra("comments", petInfo.comments);
                    intent.putExtra("temperament", petInfo.friendly);
                    intent.putExtra("lat", petInfo.latitude);
                    intent.putExtra("long", petInfo.longitude);
                    intent.putExtra("posting", petInfo.posting);
                    intent.putExtra("reward", petInfo.reward);

                    context.startActivity(intent);
                }
            });
        }
    }
}
