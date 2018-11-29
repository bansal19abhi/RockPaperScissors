package com.example.abhishekbansal.rockpaperscissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signuppressed(View view) {
        Log.d("Tejas", "Going to Game Page");

        Intent i = new Intent(this, GameScreen.class);
        startActivity(i);
    }

    public void backpressed(View view) {
        Log.d("Tejas", "Going back to Login Page");

        Intent i = new Intent(this, LoginScreen.class);
        startActivity(i);
    }
}
