package com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forfoodiesbyfoodies.R;
import com.squareup.picasso.Picasso;

public class ResturantDetailActivity extends AppCompatActivity {
	
	private String photoUrl, description;
	private ImageView imageView;
	private TextView descriptionTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resturant_detail);
		
		if (getIntent() != null) {
			photoUrl = getIntent().getStringExtra("image");
			description = getIntent().getStringExtra("description");
		}
		
		initUi();
	}
	
	private void initUi() {
		imageView = findViewById(R.id.resturantImg);
		descriptionTv = findViewById(R.id.descriptionTv);
		
		Picasso.get().load(photoUrl).into(imageView);
		descriptionTv.setText(description);
	}
}