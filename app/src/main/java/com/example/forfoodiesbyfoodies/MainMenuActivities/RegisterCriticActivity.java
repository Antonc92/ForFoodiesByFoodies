package com.example.forfoodiesbyfoodies.MainMenuActivities;

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

import com.example.forfoodiesbyfoodies.activity.MainActivity;
import com.example.forfoodiesbyfoodies.R;
import com.example.forfoodiesbyfoodies.activity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterCriticActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText mFirstNameCritic, mLastNameCritic, mUserNameCritic, mEmailCritic, mPasswordCritic;
    private ProgressBar progressBarCritic;
    Button mRegisterButtonCritic;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_critic);

        mAuth = FirebaseAuth.getInstance();

        mFirstNameCritic = (EditText) findViewById(R.id.FirstNameRegisterCritic);
        mLastNameCritic = (EditText) findViewById(R.id.LastNameRegisterCritic);
        mUserNameCritic = (EditText) findViewById(R.id.UserNameRegisterCritic);
        mEmailCritic = (EditText) findViewById(R.id.EmailRegisterCritic);
        mPasswordCritic = (EditText) findViewById(R.id.PasswordRegisterCritic);
        progressBarCritic = (ProgressBar) findViewById(R.id.progressBarCritic);
        mRegisterButtonCritic = (Button) findViewById(R.id.SubmitRegisterCritic);
        mRegisterButtonCritic.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.SubmitRegisterCritic:
                registerCritic();
                break;
        }

    }


    public void ToMainMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void registerCritic() {

        String email = mEmailCritic.getText().toString().trim();
        String password = mPasswordCritic.getText().toString().trim();
        String first = mFirstNameCritic.getText().toString().trim();
        String last = mLastNameCritic.getText().toString().trim();
        String username = mUserNameCritic.getText().toString().trim();

        //Critics will have lvl 2 access
        Integer a = 2;
        String access = a.toString();

        if (TextUtils.isEmpty(first)) {
            mFirstNameCritic.setError("First name is required.");
            mFirstNameCritic.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(last)) {
            mLastNameCritic.setError("Last name is required.");
            mLastNameCritic.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            mUserNameCritic.setError("Username is required.");
            mUserNameCritic.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailCritic.setError("Email is required.");
            mEmailCritic.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmailCritic.setError("Please provide valid email.");
            mEmailCritic.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordCritic.setError("Password is required.");
            mPasswordCritic.requestFocus();
            return;
        }
        //Password must be 6 or more characters.
        if (password.length() < 6) {
            mPasswordCritic.setError("Password must be at least 6 characters.");
            mPasswordCritic.requestFocus();
            return;
        }


        progressBarCritic.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user = new User(first, last, username, access, email);
                            FirebaseDatabase.getInstance().getReference("critics")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterCriticActivity.this, "Critic created successfully.", Toast.LENGTH_LONG).show();
                                        progressBarCritic.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(RegisterCriticActivity.this, "Not registered.", Toast.LENGTH_LONG).show();
                                        progressBarCritic.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterCriticActivity.this, "Not registered.", Toast.LENGTH_LONG).show();
                            progressBarCritic.setVisibility(View.GONE);
                        }
                    }
                });


    }
}