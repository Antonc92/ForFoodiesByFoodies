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
import android.widget.Toast;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RestaurantsActivity extends AppCompatActivity implements
        RestaurantsRecyclerAdapter.ItemClickListener{
    
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String userID;
    Button AddButon;
    DatabaseReference referenceRes;
    private ArrayList<Restaurants> restaurantsList;


    private RecyclerView mRecyclerView;
    private RestaurantsRecyclerAdapter adapter;
    private List<Restaurants> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);


        restaurantsList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerViewRestaurants);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    
        mDatabase = FirebaseDatabase.getInstance().getReference().child("restaurants");
         
         AddButon = findViewById(R.id.AddRestaurantsButtonMain);


        AddButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddRestaurantActivity.class));
                finish();
            }
        });


//        FirebaseUser user = mAuth.getCurrentUser();
//        userID = user.getUid();
//        reference = FirebaseDatabase.getInstance().getReference().child("admins").child(userID).child("access");
//        //Get the string value of access from DB and if = to 3(admins) make admin button visible
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    String access = dataSnapshot.getValue().toString();
//                    Integer x = Integer.valueOf(access);
//                    if (x == 3) {
//                        AddButon.setVisibility(View.VISIBLE);
//                    } else {
//                        AddButon.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Restaurants restaurants = snap.getValue(Restaurants.class);
                    restaurantsList.add(restaurants);
                }
                
                // setup the adapter
                Collections.sort(restaurantsList, (lhs, rhs) -> lhs.getName().compareTo(rhs.getName()));
                mRecyclerView.setAdapter(new RestaurantsRecyclerAdapter(restaurantsList, RestaurantsActivity.this));
            }
    
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(RestaurantsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
    
    
    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, ResturantDetailActivity.class);
        intent.putExtra("image", restaurantsList.get(position).photo);
        intent.putExtra("description", restaurantsList.get(position).description);
        startActivity(intent);
    }
}