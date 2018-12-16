package com.example.abhishekbansal.rockpaperscissors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.abhishekbansal.rockpaperscissors.Entities.GameRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class GameLogic extends AppCompatActivity {

    String Rock = "Rock";
    String Paper = "Paper";
    String Scissor = "Scissor";
    String Lizard = "Lizard";
    String Spock = "Spock";
    String docNumber = "";
    final static String TAG = "TestLog";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    GameRoom game;
    String player1Number="";
    String player2Number="";
    String player1Option="";
    String player2Option="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_game_logic);
        Bundle b = getIntent().getExtras();
        docNumber = b.getString("docNumber");
        Log.d(TAG, "DocumentSnapshot ID: " + docNumber);

        db.collection("GameRooms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Map<String, Object> data = document.getData();
                                if (document.getId().equals(docNumber)){
                                    if (document.getData().get("player1Number").equals(user.getPhoneNumber())){
                                        //user is player 1
                                        //game = new GameRoom(user.getPhoneNumber(), data.get("player2Number").toString(),data.get("player1Option").toString(),data.get("player2Option").toString(),data.get("winner").toString());
                                        player1Number= user.getPhoneNumber();
                                        player2Number= data.get("player2Number").toString();
                                        player1Option= data.get("player1Option").toString();
                                        player2Option= data.get("player2Option").toString();
                                    }else {
                                        //user is player 2
                                        //game = new GameRoom(data.get("player1Number").toString(), user.getPhoneNumber(),data.get("player1Option").toString(),data.get("player2Option").toString(),data.get("winner").toString());
                                        player1Number= data.get("player1Number").toString();
                                        player2Number= user.getPhoneNumber();
                                        player1Option= data.get("player1Option").toString();
                                        player2Option= data.get("player2Option").toString();
                                    }
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        gameLogic();

    }

    public void gameLogic(){
        if(player1Option == "Paper" && player2Option == "Paper")

        {
            System.out.println("It's a Tie");
        }
        else if(player1Option == "Rock" && player2Option == "Rock")

        {
            System.out.println("It's a Tie");
        }
        else if(player1Option == "Scissors" && player2Option == "Scissors")

        {
            System.out.println("It's a Tie");
        }
        else if(player1Option == "Lizard" && player2Option == "Lizard")

        {
            System.out.println("It's a Tie");
        }
        else if(player1Option == "Spock" && player2Option == "Spock")

        {
            System.out.println("It's a Tie");
        }
// Rock
        else if(player1Option == "Rock" && player2Option == "Paper")

        {
            System.out.println("Player 2 Wins");
        }
        else if(player1Option == "Rock" && player2Option == "Lizard")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Rock" && player2Option == "Spock")

        {
            System.out.println("Player 2 Wins");
        }
        else if(player1Option == "Rock" && player2Option == "Scissors")

        {
            System.out.println("Player 1 Wins");
        }
// Paper

        else if(player1Option == "Paper" && player2Option == "Rock")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Paper" && player2Option == "Lizard")

        {
            System.out.println("Player 2 Wins");
        }
        else if(player1Option == "Paper" && player2Option == "Spock")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Paper" && player2Option == "Scissors")

        {
            System.out.println("Player 2 Wins");
        }
// Scissors

        else if(player1Option == "Scissors" && player2Option == "Paper")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Scissors" && player2Option == "Rock")

        {
            System.out.println("Player 2 Wins");
        }
        else if(player1Option == "Scissors" && player2Option == "Lizard")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Scissors" && player2Option == "Spock")

        {
            System.out.println("Player 2 Wins");
        }
// Spock

        else if(player1Option == "Spock" && player2Option == "Scissors")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Spock" && player2Option == "Paper")

        {
            System.out.println("Player 2 Wins");
        }
        else if(player1Option == "Spock" && player2Option == "Rock")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Spock" && player2Option == "Lizard")

        {
            System.out.println("Player 2 Wins");
        }
// Lizard

        else if(player1Option == "Lizard" && player2Option == "Spock")

        {
            System.out.println("Player 2 Wins");
        }
        else if(player1Option == "Lizard" && player2Option == "Scissors")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Lizard" && player2Option == "Paper")

        {
            System.out.println("Player 1 Wins");
        }
        else if(player1Option == "Lizard" && player2Option == "Rock")

        {
            System.out.println("Player 2 Wins");
        }
    }
}

/*
// similar

    if(player1 == "Paper" && player2 == "Paper")

    {
        System.out.println("It's a Tie");
    }
    else if(player1 == "Rock" && player2 == "Rock")

    {
        System.out.println("It's a Tie");
    }
    else if(player1 == "Scissors" && player2 == "Scissors")

    {
        System.out.println("It's a Tie");
    }
    else if(player1 == "Lizard" && player2 == "Lizard")

    {
        System.out.println("It's a Tie");
    }
    else if(player1 == "Spock" && player2 == "Spock")

    {
        System.out.println("It's a Tie");
    }
// Rock
    else if(player1 == "Rock" && player2 == "Paper")

    {
        System.out.println("Player 2 Wins");
    }
    else if(player1 == "Rock" && player2 == "Lizard")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Rock" && player2 == "Spock")

    {
        System.out.println("Player 2 Wins");
    }
    else if(player1 == "Rock" && player2 == "Scissors")

    {
        System.out.println("Player 1 Wins");
    }
// Paper

    else if(player1 == "Paper" && player2 == "Rock")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Paper" && player2 == "Lizard")

    {
        System.out.println("Player 2 Wins");
    }
    else if(player1 == "Paper" && player2 == "Spock")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Paper" && player2 == "Scissors")

    {
        System.out.println("Player 2 Wins");
    }
// Scissors

    else if(player1 == "Scissors" && player2 == "Paper")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Scissors" && player2 == "Rock")

    {
        System.out.println("Player 2 Wins");
    }
    else if(player1 == "Scissors" && player2 == "Lizard")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Scissors" && player2 == "Spock")

    {
        System.out.println("Player 2 Wins");
    }
// Spock

    else if(player1 == "Spock" && player2 == "Scissors")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Spock" && player2 == "Paper")

    {
        System.out.println("Player 2 Wins");
    }
    else if(player1 == "Spock" && player2 == "Rock")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Spock" && player2 == "Lizard")

    {
        System.out.println("Player 2 Wins");
    }
// Lizard

    else if(player1 == "Lizard" && player2 == "Spock")

    {
        System.out.println("Player 2 Wins");
    }
    else if(player1 == "Lizard" && player2 == "Scissors")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Lizard" && player2 == "Paper")

    {
        System.out.println("Player 1 Wins");
    }
    else if(player1 == "Lizard" && player2 == "Rock")

    {
        System.out.println("Player 2 Wins");
    }
}*/

//
//    else if (player1 == 'Rock' and player2 == 'Rock'):
//    print "it's a it's a Tie"

//    else if (player1 == 'Scissors' and player2 == 'Paper'):
//    print "Player 1 wins."
//
//    else if (player2 == 'Scissors' and player2 == 'Scissors'):
//    print "it's a Tie"
//
//    else if (player1 == 'Paper' and player2 == 'Paper'):
//    print "it's a Tie"
//
//    else if (player1 == 'Paper' and player2 == 'Scissors'):
//    print "Player 2 wins."
//
//    else if (player1 == 'Rock'and player2 == 'Paper'):
//    print "Player 2 wins."
//
//    else if (player1 == 'Paper' and player2 == 'Rock'):
//    print "Player 2 wins."
//
//    else if (player1 == 'Scissors' and player2 == 'Rock'):
//    print "Player 2 wins."
//// hello
//            else if (player1 == 'Paper' and player2 == 'Rock'):
//    print "Player 2 wins."
//
//            else if (player1 == 'Scissors' and player2 == 'Rock'):
//    print "Player 2 wins."
//
//            else if (player1 == 'Paper' and player2 == 'Rock'):
//    print "Player 2 wins."
//
//            else if (player1 == 'Scissors' and player2 == 'Rock'):
//    print "Player 2 wins."
//
//
//
//            else:
//    print "This is not a valid object selection."

