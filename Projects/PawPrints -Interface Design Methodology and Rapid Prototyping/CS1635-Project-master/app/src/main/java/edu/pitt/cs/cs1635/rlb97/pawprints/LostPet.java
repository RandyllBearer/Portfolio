package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LostPet extends AppCompatActivity {
    //Initializing all the variables to store the data the user can enter, all initialized at
    // a specific base case


    String petName="unknown";
    String petBreed = "unknown";
    String[] petColors;
    int searchRadius = 100;
    String petType="unknown";


    //all the buttons/radio buttons/etc
    RadioGroup rad, type;
    Button search;
    CheckBox white, black, brown, grey, blonde, other;
    EditText pn, pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pet);
        addListenerOnButton();
    }

    public void addListenerOnButton(){
      rad = (RadioGroup) findViewById(R.id.radius);
      type = (RadioGroup) findViewById(R.id.petTypeGroup);
      search = (Button) findViewById(R.id.search);
      search.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {

              // get selected radio button from radioGroup
              int radID = rad.getCheckedRadioButtonId();
              int typeID = type.getCheckedRadioButtonId();

              // find the radiobuttton by returned id
              RadioButton r = (RadioButton) findViewById(radID);
              RadioButton t = (RadioButton) findViewById(typeID);

              //parse the data of the radius button
              if(r==null){
                  searchRadius = 100;
              }
              else{
                  if(r.getText().toString().equals("25 miles")){
                      searchRadius = 25;
                  }
                  else if(r.getText().toString().equals("50 miles")){
                      searchRadius=50;
                  }
                  else{
                      searchRadius = 100;
                  }
              }
                //parse data of type of pet
              if(t==null){
                  petType=null;
              }
              else if(t.getText().toString().equals("Dog")){
                  petType="dog";
              }
              else if(t.getText().toString().equals("Cat")){
                  petType="cat";
              }
              else{
                  petType="other";
              }

            //getData from checkboxes
              white = (CheckBox)findViewById(R.id.white);
              brown = (CheckBox)findViewById(R.id.brown);
              black = (CheckBox)findViewById(R.id.black);
              grey = (CheckBox)findViewById(R.id.grey);
              blonde = (CheckBox)findViewById(R.id.blonde);
              other = (CheckBox)findViewById(R.id.dbOtherr);



                //get typed in data such as pet name and breed

              pn = (EditText)findViewById(R.id.petName);
              petName = pn.getText().toString();

              pb = (EditText)findViewById(R.id.breed);
              petBreed = pb.getText().toString();

              Log.d("Stats", "radius: "+searchRadius+", Pet Name: "+petName+", Pet Type: "+petType+", Pet Breed:"+petBreed);

              Intent intent = new Intent(v.getContext(), PetList.class);
              if (petType == "dog") {
                  intent.putExtra("isDog", true);
              }

              v.getContext().startActivity(intent);
          }

      });

    }
}
