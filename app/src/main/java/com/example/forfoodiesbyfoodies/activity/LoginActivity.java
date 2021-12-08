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
import android.widget.TextView;
import android.widget.Toast;

import com.example.forfoodiesbyfoodies.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView NoAccount, ForgotPassword;
    EditText EmailLogin, PasswordLogin;
    Button mLoginButton;
    FirebaseAuth fAuth;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailLogin = (EditText) findViewById(R.id.EmailLogin);
        PasswordLogin = (EditText) findViewById(R.id.PasswordLogin);
        fAuth = FirebaseAuth.getInstance();
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        NoAccount = (TextView) findViewById(R.id.NoAccount);
        NoAccount.setOnClickListener(this);
        mLoginButton = (Button) findViewById(R.id.LoginLogin);
        mLoginButton.setOnClickListener(this);
        ForgotPassword = (TextView) findViewById(R.id.ForgotPassword);
        ForgotPassword.setOnClickListener(this);

        //If already logged in, go to Main
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.NoAccount:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.LoginLogin:
                userLogin();
                break;
            case R.id.ForgotPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void userLogin() {
        String email = EmailLogin.getText().toString().trim();
        String password = PasswordLogin.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            EmailLogin.setError("Email is required.");
            EmailLogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailLogin.setError("Please provide valid email.");
            EmailLogin.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            PasswordLogin.setError("Password is required.");
            PasswordLogin.requestFocus();
            return;
        }
        if (password.length() < 6) {
            PasswordLogin.setError("Password must be at least 6 characters.");
            PasswordLogin.requestFocus();
            return;
        }
        progressBar2.setVisibility(View.VISIBLE);

        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);
                }
            }
        });

    }

}
