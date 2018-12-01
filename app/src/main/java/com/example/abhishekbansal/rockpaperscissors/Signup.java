package com.example.abhishekbansal.rockpaperscissors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Signup extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private EditText mPhoneText;
    private EditText mCodeText;
    private Button mSendButton;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mPhoneText = (EditText) findViewById(R.id.contactTextBox);
        mCodeText = (EditText) findViewById(R.id.verificationTextBox);
        mSendButton = (Button) findViewById(R.id.button2);



        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            mPhoneText.setEnabled(false);
            mSendButton.setEnabled(false);


            String phoneNumber = mPhoneText.getText().toString();


                PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,

                        60,
                        TimeUnit.SECONDS,
                        Signup.this,
                        mCallBacks
                        );


            }
        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };
    }










    public void backpressed(View view) {
        Log.d("Tejas", "Going back to Login Page");

        Intent i = new Intent(this, LoginScreen.class);
        startActivity(i);
    }
}
