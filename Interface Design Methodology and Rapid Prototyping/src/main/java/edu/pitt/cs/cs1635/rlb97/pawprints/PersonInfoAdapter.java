package edu.pitt.cs.cs1635.rlb97.pawprints;

/**
 * Created by colton on 3/25/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PersonInfoAdapter extends RecyclerView.Adapter<PersonInfoAdapter.PersonViewHolder> {
    private List<PersonInfo> personList;

    public PersonInfoAdapter(List<PersonInfo> personList) {
        this.personList = personList;
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    @Override
    public void onBindViewHolder(PersonViewHolder petViewHolder, int i) {
        PersonInfo person = personList.get(i);
        petViewHolder.personInfo = person;
        petViewHolder.vName.setText(person.name);
        petViewHolder.vPoints.setText(person.points + " points");
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.personinfo_card_layout, viewGroup, false);

        PersonViewHolder personViewHolder = new PersonViewHolder(itemView);

        return personViewHolder;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        protected PersonInfo personInfo;
        protected TextView vName;
        protected TextView vPoints;

        public PersonViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.personname);
            vPoints = (TextView)  v.findViewById(R.id.points);

            final Context context = v.getContext();

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(context,ViewPet.class);
                    //intent.putExtra("name", personInfo.name);
                    Intent intent = new Intent(context, ChatMessages.class);
                    String fromWhere = "Contacts";
                    String fromWho = vName.getText().toString();
                    intent.putExtra("fromWhere", fromWhere);
                    intent.putExtra("fromWho", fromWho);
                    context.startActivity(intent);

                    //context.startActivity(intent);
                }
            });
        }
    }
}
