package com.example.abhishekbansal.rockpaperscissors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.abhishekbansal.rockpaperscissors.Entities.Player;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Player> playersOnlineList;

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

        for (Player player: playersOnlineList) {
            //TODO create usermarkers
        }
/*
        // Add a marker
        LatLng markerWithImage = new LatLng(39.505424, -9.137466);
        mMap.addMarker(new MarkerOptions()
                .position(markerWithImage)
                .title("Marker in São Martinho")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerWithImage));

        // Add a custom pin to the map
        LatLng coordinate = new LatLng(-5.1841, -37.3478);
        // get the image file (png,jpg,etc) and convert it to a bitmap (bmp) file
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.maplocation);
        img = img.createScaledBitmap(img, 100,100, false);

        BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(img);

        mMap.addMarker(new MarkerOptions()
                .position(coordinate)
                .icon(bd));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 3));
        */
    }
}
