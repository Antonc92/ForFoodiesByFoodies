package com.example.forfoodiesbyfoodies.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.MainMenuActivities.ViewProfilesActivity;
import com.example.forfoodiesbyfoodies.databinding.ActivityMyProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MyProfileActivity extends AppCompatActivity {


    ActivityMyProfileBinding binding;
    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
    
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if (dataSnapshot != null) {
                        User userModel = dataSnapshot.getValue(User.class);
                        binding.FirstNameEditMy.setText(userModel.first);
                        binding.LastNameEditMy.setText(userModel.last);
                        binding.UserNameEditMy.setText(userModel.username);
                        binding.EmailUserMy.setText(userModel.email);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        
        binding.UpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.PasswordUserMy.getText().toString().isEmpty()) binding.PasswordUserMy.setError("Please set Password");
                else if (binding.FirstNameEditMy.getText().toString().isEmpty()) binding.FirstNameEditMy.setError("Please set first name");
                else if (binding.LastNameEditMy.getText().toString().isEmpty()) binding.LastNameEditMy.setError("Please set last name");
                else if (binding.UserNameEditMy.getText().toString().isEmpty()) binding.UserNameEditMy.setError("Please set username");
                else if (binding.EmailUserMy.getText().toString().isEmpty()) binding.EmailUserMy.setError("Please set email");
                else {
                    databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                
                                String firstName = binding.FirstNameEditMy.getText().toString();
                                String lastName = binding.LastNameEditMy.getText().toString();
                                String userName = binding.UserNameEditMy.getText().toString();
                                String eMail = binding.EmailUserMy.getText().toString();
                                String newPassword = binding.PasswordUserMy.getText().toString();
                
                
                                //Updates user's email in Firebase Auth
                                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                
                                user1.updateEmail(eMail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                    
                                                    //Updates user's password in Firebase Auth
                                                    user1.updatePassword(newPassword)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(MyProfileActivity.this, "Update successful.", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                    
                                                }
                                            }
                                        });
                
                
                                //Update in Realtime Database method
                                updateData(firstName, lastName, userName, eMail);
                            }
                        }
        
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });

    }

    private void updateData(String firstName, String lastName, String userName, String eMail) {





        HashMap User = new HashMap();
        User.put("first", firstName);
        User.put("last", lastName);
        User.put("username", userName);
        User.put("email", eMail);





        databaseReference.child(userID).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){

                    binding.FirstNameEditMy.setText("");
                    binding.LastNameEditMy.setText("");
                    binding.UserNameEditMy.setText("");
                    binding.EmailUserMy.setText("");
                    binding.PasswordUserMy.setText("");
                }
                else {
                    Toast.makeText(MyProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void ToMainMenuMyProfile(View view) {
        startActivity(new Intent(getApplicationContext(), ViewProfilesActivity.class));
        finish();
    }
}