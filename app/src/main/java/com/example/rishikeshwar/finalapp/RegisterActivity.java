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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        auth = FirebaseAuth.getInstance();
    }

    public void createUser(View v) {
        if(emailEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Cannot be Blank", Toast.LENGTH_LONG).show();
        } else {
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User created sucessfully!", Toast.LENGTH_SHORT).show();

                                DatabaseReference myRef = database.getReference("users");
                                myRef.child(email.replace('.', ' ')).child("Username").setValue(email.substring(0, email.indexOf('@')));

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
