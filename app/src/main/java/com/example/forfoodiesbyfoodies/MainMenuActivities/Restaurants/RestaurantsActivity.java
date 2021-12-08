package com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.AddRestaurantActivity;
import com.example.forfoodiesbyfoodies.activity.MainActivity;
import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.Restaurants;
import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.RestaurantsRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    String userID;
    Button AddButon;
    DatabaseReference referenceRes;


    private RecyclerView mRecyclerView;
    private RestaurantsRecyclerAdapter adapter;
    private List<Restaurants> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurants");


        mRecyclerView = findViewById(R.id.recyclerViewRestaurants);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseDatabase.getInstance();
         referenceRes = db.getReference().child("restaurants");


        AddButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddRestaurantActivity.class));
                finish();
            }
        });


        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("admins").child(userID).child("access");
        //Get the string value of access from DB and if = to 3(admins) make admin button visible
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String access = dataSnapshot.getValue().toString();
                    Integer x = Integer.valueOf(access);
                    if (x == 3) {
                        AddButon.setVisibility(View.VISIBLE);
                    } else {
                        AddButon.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


    }


    public void ToMainMenuRestaurants(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


}