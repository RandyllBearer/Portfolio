package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewPet extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet);
        setTitle(R.string.details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageView vPhoto = (ImageView) this.findViewById(R.id.viewPhoto);
        TextView vBreedType = (TextView) this.findViewById(R.id.viewBreedType);
        TextView vTime = (TextView) this.findViewById(R.id.viewTime);
        TextView vColor = (TextView) this.findViewById(R.id.viewColor);
        TextView vTemperament = (TextView) this.findViewById(R.id.viewTemperament);
        TextView vReward = (TextView) this.findViewById(R.id.viewReward);
        TextView vComments = (TextView) this.findViewById(R.id.viewComments);

        int petImage = getIntent().getIntExtra("photoId", 0);
        String petBreed = getIntent().getStringExtra("breed");
        String petType = getIntent().getStringExtra("type");
        String petTime = getIntent().getStringExtra("time");
        String petColor = getIntent().getStringExtra("color");
        boolean petFriendly = getIntent().getBooleanExtra("temperament", false);
        String petComments = getIntent().getStringExtra("comments");
        boolean petLost = getIntent().getBooleanExtra("posting", false);
        double reward = getIntent().getDoubleExtra("reward", 0);

        if (petLost) {
            vReward.setText(String.format("Reward: $%.2f", reward));
        } else {
            vReward.setText("");
        }

        vPhoto.setImageResource(petImage);
        vBreedType.setText(petType + ", " + petBreed);
        vTime.setText(petTime);
        vColor.setText(vColor.getText() + " " + petColor);

        if (petFriendly) {
            vTemperament.setText(vTemperament.getText() + " Friendly");
        } else {
            vTemperament.setText(vTemperament.getText() + " Unfriendly");
        }

        vComments.setText(vComments.getText() + " " + petComments);

        //Button for Contacts
        final ImageView contactLink = (ImageView) findViewById(R.id.contactLink);
        contactLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(contactLink.getContext(), ChatMessages.class);
                String fromWhere = "viewPet";
                String fromWho = "Some Guy";
                intent.putExtra("fromWhere", fromWhere);
                intent.putExtra("fromWho", fromWho);
                startActivity(intent);
            }

        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double latitude = getIntent().getDoubleExtra("lat", 40.4406);
        double longitude = getIntent().getDoubleExtra("long", -79.9959);

        LatLng petLocation = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(petLocation).title("User Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(petLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }
}
