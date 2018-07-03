package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class FoundPetThree extends AppCompatActivity {

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_pet_three);

        submitButton = (Button) findViewById(R.id.submitButton);

        boolean submitLost = getIntent().getBooleanExtra("lost", false);

        if (!submitLost) {
            LinearLayout reward = findViewById(R.id.reward);
            ((ViewGroup)  reward.getParent()).removeView(reward);
        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage(R.string.success)
                        .setTitle(R.string.successtitle)
                        .setPositiveButton(R.string.ok, dialogueClickListener)
                        .show();
            }
        });
    }

    DialogInterface.OnClickListener dialogueClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent(FoundPetThree.this, MainActivity.class);
            FoundPetThree.this.startActivity(intent);
        }
    };
}
