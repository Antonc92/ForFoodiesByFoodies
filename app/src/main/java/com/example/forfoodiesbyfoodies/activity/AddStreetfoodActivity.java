package com.example.forfoodiesbyfoodies.activity;

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

import com.example.forfoodiesbyfoodies.MainMenuActivities.Restaurants.Restaurants;
import com.example.forfoodiesbyfoodies.MainMenuActivities.StreetfoodActivity;
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

public class AddStreetfoodActivity extends AppCompatActivity {


    private ImageView AddStreetfoodImage;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Button NewStreetfoodBtn;
    private EditText StreetfoodNameNew, StreetfoodDescriptionNew, StreetfoodStreetNew;
    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_street_food);

        AddStreetfoodImage = findViewById(R.id.AddStreetfoodImage);
        NewStreetfoodBtn = findViewById(R.id.AddNewStreetfoodButtonAdd);
        StreetfoodNameNew = findViewById(R.id.StreetfoodNameEditText);
        StreetfoodDescriptionNew = findViewById(R.id.StreetfoodDescriptionEditText);
        StreetfoodStreetNew = findViewById(R.id.NewStreetfoodStreetEditText);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        NewStreetfoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStreetfood();
            }
        });



        AddStreetfoodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });


    }


    private void addStreetfood() {

        String name = StreetfoodNameNew.getText().toString();
        String description = StreetfoodDescriptionNew.getText().toString();
        String street = StreetfoodDescriptionNew.getText().toString();


        if (TextUtils.isEmpty(name)) {
            StreetfoodNameNew.setError("Add restaurant name");
            StreetfoodNameNew.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(description)) {
            StreetfoodDescriptionNew.setError("Add restaurant description");
            StreetfoodDescriptionNew.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(street)) {
            StreetfoodStreetNew.setError("Add restaurant location");
            StreetfoodStreetNew.requestFocus();
            return;
        }





        Restaurants streetfood = new Restaurants(name, description, street, photo);
        FirebaseDatabase.getInstance().getReference("streetfood").push()
                .setValue(streetfood).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AddStreetfoodActivity.this, "Street food added.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddStreetfoodActivity.this, "Streetfood not added", Toast.LENGTH_LONG).show();
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
            AddStreetfoodImage.setImageURI(imageUri);
            uploadPicture();

        }
    }







    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();


        final String randomKey = UUID.randomUUID().toString();
        StorageReference streetfoodImagesRef = storageReference.child("images/" + randomKey);

        streetfoodImagesRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        streetfoodImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                photo = uri.toString();

                            }
                        });

                        pd.dismiss();
                        Toast.makeText(AddStreetfoodActivity.this, "Street food photo uploaded", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddStreetfoodActivity.this, "Failed to upload", Toast.LENGTH_LONG).show();
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





    public void ToMainMenuAddStreetfood(View view) {
        startActivity(new Intent(getApplicationContext(), StreetfoodActivity.class));
        finish();
    }
}