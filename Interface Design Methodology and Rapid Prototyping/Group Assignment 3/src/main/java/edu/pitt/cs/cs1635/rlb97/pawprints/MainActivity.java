package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import edu.pitt.cs.cs1635.rlb97.pawprints.LostPet;
import edu.pitt.cs.cs1635.rlb97.pawprints.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onLoseClick(View view){
        //send intent to lose activity
        Intent intent = new Intent(this, SearchOrPosting.class); // add lose activity
        startActivity(intent);
    }
    public void onFindClick(View view){
        //send intent to find activity
        Intent intent = new Intent(this, FoundPetOne.class); // add find activity here
        startActivity(intent);
    }
    public void onMessageClick(View view){
        //send intent to message activity
        Intent intent = new Intent(this, MessageList.class); // add message activity here
        startActivity(intent);
    }
    public void onPostingClick(View view){
        Intent intent = new Intent(this, PetSearch.class); // add message activity here
        intent.putExtra("missingReport", true);
        startActivity(intent);
    }
}
