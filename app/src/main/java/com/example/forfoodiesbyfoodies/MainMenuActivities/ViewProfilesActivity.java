package com.example.forfoodiesbyfoodies.MainMenuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.activity.MainActivity;
import com.example.forfoodiesbyfoodies.activity.MyProfileActivity;

public class ViewProfilesActivity extends AppCompatActivity {

    Button MyProfileButton, UserProfilesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        MyProfileButton = findViewById(R.id.MyProfileButton);


        MyProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                finish();
            }
        });


        UserProfilesButton = findViewById(R.id.UserProfilesButton);
        UserProfilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AllUsersActivity.class));
                finish();
            }
        });


    }


    public void ToMainMenuProfiles(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }
}