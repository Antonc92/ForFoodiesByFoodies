package com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddRestaurantActivity extends AppCompatActivity {


    private ImageView AddRestaurantImage;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Button NewRestaurantBtn;
    private EditText RestaurantNameNew, RestaurantDescriptionNew, RestaurantStreetNew;
    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        AddRestaurantImage = findViewById(R.id.AddRestaurantImage);
        NewRestaurantBtn = findViewById(R.id.AddNewRestaurantButtonAdd);
        RestaurantNameNew = findViewById(R.id.RestaurantNameEditText);
        RestaurantDescriptionNew = findViewById(R.id.RestaurantDescriptionEditText);
        RestaurantStreetNew = findViewById(R.id.NewRestaurantStreetEditText);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        NewRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRestaurant();
            }
        });

        AddRestaurantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });


    }

    private void addRestaurant() {

        String name = RestaurantNameNew.getText().toString();
        String description = RestaurantDescriptionNew.getText().toString();
        String street = RestaurantStreetNew.getText().toString();


        if (TextUtils.isEmpty(name)) {
            RestaurantNameNew.setError("Add restaurant name");
            RestaurantNameNew.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            RestaurantDescriptionNew.setError("Add restaurant description");
            RestaurantDescriptionNew.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(street)) {
            RestaurantStreetNew.setError("Add restaurant location");
            RestaurantStreetNew.requestFocus();
            return;
        }





        Restaurants restaurants = new Restaurants(name, description, street, photo);
        FirebaseDatabase.getInstance().getReference("restaurants").push()
                .setValue(restaurants).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AddRestaurantActivity.this, "Register successful.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddRestaurantActivity.this, "Not registered.", Toast.LENGTH_LONG).show();
                }
            }
        });






    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            AddRestaurantImage.setImageURI(imageUri);
            uploadPicture();

        }
    }





    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();


        final String randomKey = UUID.randomUUID().toString();
        StorageReference restaurantImagesRef = storageReference.child("images/" + randomKey);

        restaurantImagesRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        restaurantImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                photo = uri.toString();

                            }
                        });

                        pd.dismiss();
                        Toast.makeText(AddRestaurantActivity.this, "Successful upload.", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddRestaurantActivity.this, "Picture not uploaded.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) progressPercent + "%");
                    }
                });
    }


    public void ToMainMenuAddRestaurant(View view) {
        startActivity(new Intent(getApplicationContext(), RestaurantsActivity.class));
        finish();
    }
}