package com.example.forfoodiesbyfoodies.MainMenuActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.Restaurants;
import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.RestaurantsActivity;
import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.RestaurantsRecyclerAdapter;
import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.ResturantDetailActivity;
import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.activity.AddStreetfoodActivity;
import com.example.forfoodiesbyfoodies.activity.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StreetfoodActivity extends AppCompatActivity implements
        RestaurantsRecyclerAdapter.ItemClickListener {

    Button button;
    private DatabaseReference mDatabase;
    private ArrayList<Restaurants> restaurantsList;
    private RecyclerView mRecyclerView;
    private List<Restaurants> restaurants;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_food);

        button = (Button) findViewById(R.id.AddStreetButtonMain);
    
        restaurantsList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerViewStreetfood);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    
        mDatabase = FirebaseDatabase.getInstance().getReference().child("streetfood");
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddStreetfoodActivity.class));
                finish();
            }
        });
    
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Restaurants restaurants = snap.getValue(Restaurants.class);
                    restaurantsList.add(restaurants);
                }
            
                // setup the adapter
                Collections.sort(restaurantsList, (lhs, rhs) -> lhs.getName().compareTo(rhs.getName()));
                mRecyclerView.setAdapter(new RestaurantsRecyclerAdapter(restaurantsList, StreetfoodActivity.this));
            }
        
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(StreetfoodActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ToMainMenuStreetfood(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    
    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, ResturantDetailActivity.class);
        intent.putExtra("image", restaurantsList.get(position).photo);
        intent.putExtra("description", restaurantsList.get(position).description);
        startActivity(intent);
    }
}