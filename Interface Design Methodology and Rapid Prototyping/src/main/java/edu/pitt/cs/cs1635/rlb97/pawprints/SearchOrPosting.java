package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchOrPosting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_or_posting);

        Button search = (Button) findViewById(R.id.searchButton);
        Button posting = (Button) findViewById(R.id.postingButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrPosting.this, PetSearch.class);
                startActivity(intent);
            }
        });

        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrPosting.this, FoundPetOne.class);
                intent.putExtra("lost", true);
                startActivity(intent);
            }
        });
    }
}
