package com.example.abhishekbansal.rockpaperscissors;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.abhishekbansal.rockpaperscissors.Adapters.GameRoom;
import com.example.abhishekbansal.rockpaperscissors.Adapters.PlayersNearbyAdapter;
import com.example.abhishekbansal.rockpaperscissors.Entities.Player;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindPlayerNearbyActivity extends AppCompatActivity implements PlayersNearbyAdapter.ItemClickListener {

    private ArrayList<Player> playersNearBy;
    PlayersNearbyAdapter selectedPlayerAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    double userLatitude;
    double userLongitude;
    final DocumentReference docRef = db.collection("GameRooms").document();
    final static String TAG = "TestLog";
    MenuItem btnNotifications;
    Context context;

    LocationManager manager;
    LocationListener userLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_player_nearby);
        setTitle("Find NearBy Players");
        context = this;
        this.manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //Setup LocationListeners
        setupLocationListener();

        //Setup permissions
        setupPermissions();

        //fill players array from firebase
        playersNearBy = new ArrayList<Player>();
        db.collection("players")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                //Log.d(TAG, "userLatitude" + " => " + userLongitude);
                                GeoPoint geo = (GeoPoint) document.getData().get("location");
                                Player p1 = new Player(document.getId(), geo.getLatitude(), geo.getLongitude(), true);
                                playersNearBy.add(p1);
                            }
                            //Log.d(TAG, playersNearBy.size() + " => " + playersNearBy.size());
                            updateRecycleView();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        //RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_players);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "--userLatitude--" + " => " + userLatitude);
        Log.d(TAG, "--userLongitude--" + " => " + userLongitude);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Location lastKnownLocation = manager.getLastKnownLocation(manager.GPS_PROVIDER);
        //selectedPlayerAdapter = new PlayersNearbyAdapter(this, playersNearBy, lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        selectedPlayerAdapter = new PlayersNearbyAdapter(this, playersNearBy, userLatitude, userLongitude);
        selectedPlayerAdapter.setClickListener(this);
        recyclerView.setAdapter(selectedPlayerAdapter);

        //decoration divider
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

        //get notifications
        db.collection("GameRooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, "n: " + document.getData().get("player2Number"));
                                if (document.getData().get("player2Number").toString().equals("222")){
                                    //TODO:make it visible

                                    count++;
                                }
                            }
                            if (count > 0){
                                //Log.d(TAG, "visible ");
                                btnNotifications.setVisible(true);
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        //notification listener
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    //Log.d(TAG, "Current data: " + snapshot.getData());
                    //Log.d(TAG, "Current data+n: " + snapshot.getData().get("player2Number"));
                    if (snapshot.getData().get("player2Number").equals("222")){
                        btnNotifications.setVisible(true);
                    }
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    public void setupLocationListener() {
        this.userLocationListener = new LocationListener() {

            // This function gets run when the phone receives a new location
            @Override
            public void onLocationChanged(Location location) {
                //Log.d(TAG, "The user location changed!");
                //Log.d(TAG,"New location: " + location.toString());
                userLatitude = location.getLatitude();
                userLongitude = location.getLongitude();
                Log.d(TAG, "--userLatitude--2222222" + " => " + userLatitude);
                Log.d(TAG, "--userLongitude--2222222" + " => " + userLongitude);
                updateRecycleView();
            }

            // IGNORE THIS FUNCTION!
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            // IGNORE THIS FUNCTION!
            @Override
            public void onProviderEnabled(String provider) {

            }

            // IGNORE THIS FUNCTION!
            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    public void setupPermissions() {
        if (Build.VERSION.SDK_INT < 23) {

            this.manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this.userLocationListener);

        }
        // 5b.  This is for phones AFTER Marshmallow
        else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Show the popup box! ask for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                // Do this code if the user PREVIOUSLY gave us permission to get location.
                // (ie: You already have permission!)
                this.manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this.userLocationListener);

            }

        }
    }

    @Override
    public void onItemClick(View view, int position) {

        //go to game room
        // Add a new document with a generated id.
        GameRoom game = new GameRoom("222",playersNearBy.get(position).getPhoneNumber(),"","","");
        //GameRoom game = new GameRoom("222","222","","","");
        db.collection("GameRooms")
                .add(game)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


        Intent i = new Intent(this, MapsActivity.class);
        //i.putExtra("player", player);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_notification_menu, menu);
        btnNotifications = menu.findItem(R.id.action_notifications);
        btnNotifications.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_notifications) {
            //TODO: go to game
            Toast.makeText(context, "Going to Game room.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateRecycleView() {

        //Log.d(TAG, "playersNearBy.size():  " + playersNearBy.size() + "\n");
        selectedPlayerAdapter.notifyDataSetChanged();
    }
}
