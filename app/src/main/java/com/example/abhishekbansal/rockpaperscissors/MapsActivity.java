package com.example.abhishekbansal.rockpaperscissors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.abhishekbansal.rockpaperscissors.Entities.Player;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Player> playersOnlineList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //TODO get from firebase all users online
        playersOnlineList = new ArrayList<>();


        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //TODO: reorganize the code

        db.collection("players")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {




                                Log.d("--test", document.getId() + " => " + document.getData());
                                GeoPoint geo = (GeoPoint) document.getData().get("location");
                                Player p1 = new Player(document.getId(), geo.getLatitude(), geo.getLongitude(), true);


                                LatLng coordinate = new LatLng(p1.getLat(), p1.getLng());
                                mMap.addMarker(new MarkerOptions()
                                        .position(coordinate)
                                        .title(p1.getPhoneNumber().toString())
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                                playersOnlineList.add(p1);
                            }
                        } else {
                            Log.w("--test2", "Error getting documents.", task.getException());
                        }
                    }
                });
/*

        Log.d("-----", String.valueOf(playersOnlineList.size()));
        for (Player player: playersOnlineList) {
            //TODO create usermarkers
            // Add a marker
            //LatLng markerWithImage = new LatLng(player.getLat(), player.getLng());
            LatLng coordinate = new LatLng(player.getLat(), player.getLng());
            mMap.addMarker(new MarkerOptions()
                    .position(coordinate)
                    .title(player.getPhoneNumber().toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            Log.d("-----", player.toString());


            // Add a marker
            LatLng markerWithImage = new LatLng(39.505424, -9.137466);
            mMap.addMarker(new MarkerOptions()
                    .position(markerWithImage)
                    .title("Marker in São Martinho")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerWithImage));
        }
       */

        //TODO:get current player location
        LatLng coordinate = new LatLng(43.772796, -79.335661);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15));

    }
}
