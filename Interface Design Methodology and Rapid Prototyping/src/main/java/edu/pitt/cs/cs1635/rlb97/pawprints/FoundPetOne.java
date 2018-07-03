package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class FoundPetOne extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng userLocation;
    private CheckBox locationButton;
    private Button nextButton;
    private boolean submitPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_pet_one);

        Spinner spinner = (Spinner) findViewById(R.id.petTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.petTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        submitPet = getIntent().getBooleanExtra("lost", false);
        TextView directions = (TextView) findViewById(R.id.submitDirections);

        if (submitPet) {
            directions.setText("Make a missing report for a pet you've lost by filling out the following information:");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationButton = (CheckBox) findViewById(R.id.useCurrentLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationButton.isChecked()) {
                    Location location = getLocation();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("User Marker"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                } else {
                    mMap.clear();
                }
            }
        });

        nextButton = (Button) findViewById(R.id.petOneNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoundPetOne.this, FoundPetTwo.class);
                intent.putExtra("lost", submitPet);
                FoundPetOne.this.startActivity(intent);
            }
        });
    }

    public Location getLocation() {
        final Location nLocation = new Location("dummyprovider");
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        nLocation.setLatitude(location.getLatitude());
                        nLocation.setLongitude(location.getLongitude());
                    }
                }
            });
        } catch (SecurityException e) {
            // nothing
        }
        return nLocation;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
