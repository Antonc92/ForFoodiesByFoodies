package com.example.forfoodiesbyfoodies.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText mFirstName, mLastName, mUserName, mEmail, mPassword;
    private ProgressBar progressBar;
    Button mRegisterButton;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mFirstName = (EditText) findViewById(R.id.FirstNameRegister);
        mLastName = (EditText) findViewById(R.id.LastNameRegister);
        mUserName = (EditText) findViewById(R.id.UserNameRegister);
        mEmail = (EditText) findViewById(R.id.EmailRegister);
        mPassword = (EditText) findViewById(R.id.PasswordRegister);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRegisterButton = (Button) findViewById(R.id.SubmitRegister);
        mRegisterButton.setOnClickListener(this);


        //If already logged in, sent to main page
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }


    public void AlreadyRegistered(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SubmitRegister:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String first = mFirstName.getText().toString().trim();
        String last = mLastName.getText().toString().trim();
        String username = mUserName.getText().toString().trim();

        //Basic users will have lvl 1 access
        Integer a = 1;
        String access = a.toString();

        if (TextUtils.isEmpty(first)) {
            mFirstName.setError("First name is required.");
            mFirstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(last)) {
            mLastName.setError("Last name is required.");
            mLastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            mUserName.setError("Username is required.");
            mUserName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email is required.");
            mEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Please provide valid email.");
            mEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password is required.");
            mPassword.requestFocus();
            return;
        }
        //Password must be 6 or more characters.
        if (password.length() < 6) {
            mPassword.setError("Password must be at least 6 characters.");
            mPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user = new User(first, last, username, email, access);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()){
                                         Toast.makeText(RegisterActivity.this, "Register successful.", Toast.LENGTH_LONG).show();
                                         progressBar.setVisibility(View.GONE);
                                         startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                     }
                                     else{
                                         Toast.makeText(RegisterActivity.this, "Not registered.", Toast.LENGTH_LONG).show();
                                         progressBar.setVisibility(View.GONE);
                                     }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Not registered.", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}