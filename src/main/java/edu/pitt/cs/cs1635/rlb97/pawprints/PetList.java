package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PetList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        boolean isDog = getIntent().getBooleanExtra("isDog", false);

        List<PetInfo> pets = new ArrayList<PetInfo>();
        PetInfo dog = new PetInfo();
        dog.photoId = R.drawable.dog;
        dog.type = "Dog";
        dog.breed = "Labrador Retriever";
        dog.distance = 15;
        dog.time = "Posted about 2 hours ago.";
        dog.color = "Yellow";
        dog.friendly = true;
        dog.comments = "Very friendly dog, saw him walking along the street. I took him to my house, send me a message if he belongs to you!";


        PetInfo cat = new PetInfo();
        cat.photoId = R.drawable.cat;
        cat.type = "Cat";
        cat.breed = "Siamese";
        cat.distance = 25;
        cat.time = "Posted about 2 days ago.";
        cat.color = "Tan/Black";
        cat.friendly = false;
        cat.comments = "Saw this cat wandering around the pizza place downtown.";


        if (isDog) {
            pets.add(dog);
        } else {
            pets.add(cat);
        }

        PetInfoAdapter petInfoAdapter = new PetInfoAdapter(pets);
        recycler.setAdapter(petInfoAdapter);
    }
}
