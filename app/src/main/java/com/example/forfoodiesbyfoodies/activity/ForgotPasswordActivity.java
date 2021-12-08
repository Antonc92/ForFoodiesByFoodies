package com.example.forfoodiesbyfoodies.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText EmailReset;
    private Button ResetEmailButton;
    private ProgressBar progressBar3;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EmailReset = (EditText) findViewById(R.id.EmailReset);
        ResetEmailButton = (Button) findViewById(R.id.ResetEmailButton);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        ResetEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    public void ToIntro(View view) {
        startActivity(new Intent(getApplicationContext(), IntroActivity.class));
        finish();
    }

    private void resetPassword() {

        String email = EmailReset.getText().toString().trim();

        if (email.isEmpty()){
            EmailReset.setError("Email is required.");
            EmailReset.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EmailReset.setError("Please provide a valid email.");
            EmailReset.requestFocus();
            return;
        }

        progressBar3.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset password.", Toast.LENGTH_LONG).show();
                    progressBar3.setVisibility(View.GONE);
                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(ForgotPasswordActivity.this, "Resetting password failed.", Toast.LENGTH_LONG).show();
                    progressBar3.setVisibility(View.GONE);
                }
            }
        });

    }
}