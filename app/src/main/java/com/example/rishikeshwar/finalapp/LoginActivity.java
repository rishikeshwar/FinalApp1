package com.example.rishikeshwar.finalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        auth = FirebaseAuth.getInstance();
    }

    public void loginUser(View v) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(email.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Cannot be Blank", Toast.LENGTH_LONG).show();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User Logged In sucessfully!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), LoggedMainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "User failed miserably!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}

