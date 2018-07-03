package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PetSearch extends AppCompatActivity {

    CardView temperamentFilterCard;
    CardView distanceFilterCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_search);

        final RecyclerView recycler = (RecyclerView) findViewById(R.id.searchList);
        recycler.setHasFixedSize(true);

        final EditText searchBarText = (EditText) findViewById(R.id.searchBarText);

        final LinearLayout filterLayout = (LinearLayout) findViewById(R.id.filters);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        boolean filterDistance = false;
        boolean filterTemperament = false;

        final View filterView = LayoutInflater.from(this).inflate(R.layout.filter_card, filterLayout, true);
        final CardView filterCard = (CardView) filterView.findViewById(R.id.filterCard);
        TextView filterText = filterCard.findViewById(R.id.filterText);

        filterText.setText("Add Filter");
        final List<PetInfo> pets = new ArrayList<PetInfo>();
        final ArrayList<PetInfo> lostPets = new ArrayList<PetInfo>();

        final boolean isMissingReports = getIntent().getBooleanExtra("missingReport", false);

        if (!isMissingReports) {
            PetInfo dog = new PetInfo();
            dog.photoId = R.drawable.dog;
            dog.type = "Dog";
            dog.breed = "Labrador Retriever";
            dog.distance = 15;
            dog.time = "Posted about 2 hours ago.";
            dog.color = "Yellow";
            dog.friendly = true;
            dog.latitude = 40.4406;
            dog.longitude = -79.9920;
            dog.comments = "Very friendly dog, saw him walking along the street. I took him to my house, send me a message if he belongs to you!";

            PetInfo cat = new PetInfo();
            cat.photoId = R.drawable.cat;
            cat.type = "Cat";
            cat.breed = "Siamese";
            cat.distance = 25;
            cat.time = "Posted about 2 days ago.";
            cat.color = "Tan/Black";
            cat.friendly = false;
            cat.latitude = 40.4416;
            cat.longitude = -79.9929;
            cat.comments = "Saw this cat wandering around the pizza place downtown.";

            PetInfo cat2 = new PetInfo();
            cat2.photoId = R.drawable.cat2;
            cat2.type = "Cat";
            cat2.breed = "Persian";
            cat2.distance = 50;
            cat2.time = "Posted about 6 hours ago.";
            cat2.color = "White/Yellow";
            cat2.friendly = true;
            cat2.latitude = 40.4411;
            cat2.longitude = -79.9922;
            cat2.comments = "Spotted this friendly kitty on my lawn. Let me know if he's yours!";

            lostPets.add(dog);
            lostPets.add(cat);
            lostPets.add(cat2);
        } else {
            PetInfo dog = new PetInfo();
            dog.photoId = R.drawable.dog2;
            dog.type = "Dog";
            dog.name = "Gracie";
            dog.breed = "Australian Shepard";
            dog.distance = 5;
            dog.time = "Posted about 3 days ago.";
            dog.color = "White/Brown/Black";
            dog.friendly = true;
            dog.latitude = 40.4406;
            dog.longitude = -79.9920;
            dog.reward = 500;
            dog.posting = true;
            dog.comments = "We lost Gracie earlier today. Please let us know if you find her!";

            PetInfo dog2 = new PetInfo();
            dog2.photoId = R.drawable.dog3;
            dog2.type = "Dog";
            dog2.name = "Floopers";
            dog2.breed = "Chihuahua";
            dog2.distance = 15;
            dog2.time = "Posted about 16 hours ago.";
            dog2.color = "White/Brown";
            dog2.friendly = false;
            dog2.latitude = 40.4414;
            dog2.longitude = -79.9936;
            dog2.reward = 1200;
            dog2.posting = true;
            dog2.comments = "Floopers took off into the night at about 11:00PM yesterday. He likes to run to the PetCo... you may find him around there.";

            PetInfo dog3 = new PetInfo();
            dog3.photoId = R.drawable.dog4;
            dog3.type = "Dog";
            dog3.name = "Enrique";
            dog3.breed = "Corgi";
            dog3.distance = 35;
            dog3.time = "Posted about an hour ago.";
            dog3.color = "White/Brown";
            dog3.friendly = true;
            dog3.latitude = 40.4415;
            dog3.longitude = -79.9986;
            dog3.reward = 50000;
            dog3.posting = true;
            dog3.comments = "This foxy dog just ran away during his photoshoot. Help me find him so we can take more pictures ):";

            PetInfo cat = new PetInfo();
            cat.photoId = R.drawable.cat3;
            cat.type = "Cat";
            cat.name = "Miss Meow";
            cat.breed = "Maine Coon";
            cat.distance = 29;
            cat.time = "Posted about 21 hours ago.";
            cat.color = "White/Yellow";
            cat.friendly = true;
            cat.latitude = 40.4411;
            cat.longitude = -79.9922;
            cat.reward = 200;
            cat.posting = true;
            cat.comments = "Spotted this friendly kitty on my lawn. Let me know if he's yours!";

            lostPets.add(dog);
            lostPets.add(dog2);
            lostPets.add(dog3);
            lostPets.add(cat);

            getSupportActionBar().setTitle("Missing Report Search");

            for (int i = 0; i < lostPets.size(); i++) {
                pets.add(lostPets.get(i));
            }

            PetInfoAdapter petInfoAdapter = new PetInfoAdapter(pets);
            recycler.setAdapter(petInfoAdapter);
        }

        filterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(filterCard.getContext());
                builder.setTitle("Add a new filter")
                        .setItems(R.array.filterNames, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final AlertDialog.Builder tempBuilder = new AlertDialog.Builder(filterCard.getContext());
                                switch(which) {
                                    case 0:
                                        tempBuilder.setTitle("Distance from my location")
                                                .setItems(R.array.distances, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        if (distanceFilterCard == null) {
                                                            distanceFilterCard = (CardView) LayoutInflater.from(filterCard.getContext()).inflate(R.layout.filter_card, filterLayout, false);
                                                            distanceFilterCard.setOnClickListener(filterClickListener);
                                                        }
                                                        if (distanceFilterCard.getParent() == null) {
                                                            filterLayout.addView(distanceFilterCard);
                                                        }

                                                        TextView nFilterText = distanceFilterCard.findViewById(R.id.filterText);
                                                        switch(i) {
                                                            case 0:
                                                                nFilterText.setText("0-25 Mi");
                                                                break;
                                                            case 1:
                                                                nFilterText.setText("25-50 Mi");
                                                                break;
                                                            case 2:
                                                                nFilterText.setText("50+ Mi");
                                                                break;
                                                        }

                                                    }
                                                })
                                                .show();
                                        break;
                                    case 1:
                                        tempBuilder.setTitle("Pet Temperament")
                                                .setItems(R.array.temperaments, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        if (temperamentFilterCard == null) {
                                                            temperamentFilterCard = (CardView) LayoutInflater.from(filterCard.getContext()).inflate(R.layout.filter_card, filterLayout, false);
                                                            temperamentFilterCard.setOnClickListener(filterClickListener);
                                                        }

                                                        if (temperamentFilterCard.getParent() == null) {
                                                            filterLayout.addView(temperamentFilterCard);
                                                        }

                                                        TextView nFilterText = temperamentFilterCard.findViewById(R.id.filterText);
                                                        switch(i) {
                                                            case 0:
                                                                nFilterText.setText("Not Friendly");
                                                                break;
                                                            case 1:
                                                                nFilterText.setText("Friendly");
                                                                break;
                                                        }

                                                    }
                                                })
                                                .show();
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });

        searchBarText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] searchTerms = charSequence.toString().toLowerCase().split(" ");
                pets.clear();

                for (int x = 0; x < lostPets.size(); x++) {
                    boolean addPet = true;
                    PetInfo pet = lostPets.get(x);
                    ArrayList<String> petSearchTerms = pet.getSearchTerms();
                    for (int y = 0; y < searchTerms.length; y++) {
                        if (!petSearchTerms.contains(searchTerms[y])) {
                            addPet = false;
                        }
                    }
                    if (charSequence.toString().isEmpty()) {
                        if (isMissingReports) {
                            addPet = true;
                        } else {
                            addPet = false;
                        }
                    }
                    if (addPet) {
                        pets.add(pet);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                PetInfoAdapter petInfoAdapter = new PetInfoAdapter(pets);
                recycler.setAdapter(petInfoAdapter);
            }
        });

    }


    View.OnClickListener filterClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            ((ViewGroup) v.getParent()).removeView(v);
        }
    };

}
