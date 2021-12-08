package com.example.forfoodiesbyfoodies.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.forfoodiesbyfoodies.MainMenuActivities.RegisterCriticActivity;
import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.RestaurantsActivity;
import com.example.forfoodiesbyfoodies.MainMenuActivities.StreetfoodActivity;
import com.example.forfoodiesbyfoodies.MainMenuActivities.ViewProfilesActivity;
import com.example.forfoodiesbyfoodies.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private TextView RegCritTextView;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String userID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        RegCritTextView = (TextView) findViewById(R.id.RegCritTextView);

         //Go to Intro activity if not logged in
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), IntroActivity.class));
            finish();
        }



        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("admins").child(userID).child("access");



        //Get the string value of access from DB and if = to 3(admins) make admin button visible
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String access = dataSnapshot.getValue().toString();
                    Integer x = Integer.valueOf(access);
                    if (x == 3) {
                        RegCritTextView.setVisibility(View.VISIBLE);
                    }
                    else{
                        RegCritTextView.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }







    public void LogoutMain(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), IntroActivity.class));
        finish();
    }


    public void RegCritTextView(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterCriticActivity.class));
        finish();

    }

    public void RestaurantsTextView(View view) {
        startActivity(new Intent(getApplicationContext(), RestaurantsActivity.class));
        finish();
    }
    public void StreetfoodTextView(View view) {
        startActivity(new Intent(getApplicationContext(), StreetfoodActivity.class));
        finish();
    }

    public void ProfilesTextView(View view) {
        startActivity(new Intent(getApplicationContext(), ViewProfilesActivity.class));
        finish();
    }
}