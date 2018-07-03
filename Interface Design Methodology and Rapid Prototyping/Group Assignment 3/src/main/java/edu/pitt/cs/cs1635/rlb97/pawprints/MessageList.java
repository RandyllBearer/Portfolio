package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);

        List<PersonInfo> people = new ArrayList<PersonInfo>();
        PersonInfo someguy = new PersonInfo();
        someguy.name = "Some Guy";
        someguy.points = 1284;

        people.add(someguy);

        PersonInfoAdapter personInfoAdapter = new PersonInfoAdapter(people);
        recycler.setAdapter(personInfoAdapter);
    }
}
