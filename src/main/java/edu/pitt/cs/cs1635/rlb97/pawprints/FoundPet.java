package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class FoundPet extends AppCompatActivity {
    RadioGroup radioGroup1;
    RadioButton clicked;
    Button submit;
    RadioButton radioCat;
    RadioButton radioDog;
    RadioButton radioOtherType;
    RadioGroup radioGroup2;
    RadioButton radioFriendly;
    RadioButton radioNotFriendly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_pet);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioTypeGroup);
        submit = (Button) findViewById(R.id.submitButton);
        radioCat = (RadioButton) findViewById(R.id.radioCat);
        radioDog = (RadioButton) findViewById(R.id.radioDog);
        radioOtherType = (RadioButton) findViewById(R.id.radioOtherType);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioFriendlyGroup);
        radioFriendly = (RadioButton) findViewById(R.id.radioFriendly);
        radioNotFriendly = (RadioButton) findViewById(R.id.radioNotFriendly);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View v) {
                if (radioGroup1.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(FoundPet.this, "Please select a pet type.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(R.string.success)
                            .setTitle(R.string.successtitle)
                            .setPositiveButton(R.string.ok, dialogueClickListener)
                            .show();
                }
            }
        });

    }

    DialogInterface.OnClickListener dialogueClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent(FoundPet.this, MainActivity.class);
            FoundPet.this.startActivity(intent);
        }
    };
}
