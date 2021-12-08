package com.example.forfoodiesbyfoodies.MainMenuActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.activity.AddStreetfoodActivity;
import com.example.forfoodiesbyfoodies.activity.MainActivity;

public class StreetfoodActivity extends AppCompatActivity {

    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_food);

        button = (Button) findViewById(R.id.AddStreetButtonMain);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddStreetfoodActivity.class));
                finish();
            }
        });

    }

    public void ToMainMenuStreetfood(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}