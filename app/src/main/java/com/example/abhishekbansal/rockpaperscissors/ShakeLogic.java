package com.example.abhishekbansal.rockpaperscissors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.seismic.ShakeDetector;

public class ShakeLogic extends AppCompatActivity implements ShakeDetector.Listener {

    // global variable to track if light is on/off
    boolean lightOn = false;
    TextView username;
    String[] arOfStrings = {"Rock", "Paper", "Scissor", "Lizard", "Spock"};
    public static final String TAG = "Tejas";
    String option;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        View v = getLayoutInflater().inflate(R.layout.nav_header_home, null);
        username = v.findViewById(R.id.username);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector detector = new ShakeDetector(this);
        detector.start(manager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String email = auth.getCurrentUser().getEmail();
        if (email == null)

        {
            String phone = auth.getCurrentUser().getPhoneNumber();
            username.setText(phone);
            Toast.makeText(getApplicationContext(), phone, Toast.LENGTH_SHORT).show();

        } else {
            username.setText(email);
            Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();

        }


        // this a mandatory function
        // it's required by the ShakeDetector.Listener class
        @Override public void hearShake () {

            // 2. this function AUTOMAGICALLY gets called
            // every time you shake the phone

            // print out a message when the phone shakes
            Log.d("JENELLE", "phone is shaking!!!!");
            Toast.makeText(this, "PHONE IS SHAKING!!!!", Toast.LENGTH_SHORT).show();

            // turn on flashlight when phone shakes!
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

            // TURN ON THE FLASH on your phone!

            try {
                // 1. which camera do you want (Front or back camera?!)
                String cameraID = cameraManager.getCameraIdList()[0];   // BACK CAMERA?

                // 2. turn the flash on
                // turn on

                if (lightOn == false) {
                    cameraManager.setTorchMode(cameraID, true);
                    Log.d("JENELLE", "Turning flash ON!");
                    Toast.makeText(this, "Turning flash ON!", Toast.LENGTH_SHORT).show();

                    lightOn = true;
                } else {
                    cameraManager.setTorchMode(cameraID, false);

                    Log.d("JENELLE", "Turning flash OFF!");
                    Toast.makeText(this, "Turning flash OFF!", Toast.LENGTH_SHORT).show();

                    lightOn = false;
                }

                // turn off
                //cameraManager.setTorchMode(cameraID, false);
            } catch (Exception e) {
                Log.d("JENELLE", "Error while turning on camera flash");
                Toast.makeText(this, "Error while turning on camera flash", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
