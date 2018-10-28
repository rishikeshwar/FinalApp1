package com.example.rishikeshwar.finalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView welcomeTextView;
    Button logoutButton, allUsersButton;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Map<String, Object> take;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allUsersButton = (Button) findViewById(R.id.allUsersButton);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentFirebaseUser != null) {
            String email = currentFirebaseUser.getEmail();
            welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
            welcomeTextView.setText("Welcome " + currentFirebaseUser.getEmail());
            welcomeTextView.setVisibility(TextView.VISIBLE);

            logoutButton = (Button) findViewById(R.id.logoutButton);
            logoutButton.setVisibility(Button.VISIBLE);

            Toast.makeText(this, "" + currentFirebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
        }

        if(currentFirebaseUser != null) {
            String email = currentFirebaseUser.getEmail();
            if(email.equals("rishikeshwar1@gmail.com")) {
                allUsersButton.setVisibility(Button.VISIBLE);
            } else {
                allUsersButton.setVisibility(Button.INVISIBLE);
            }
        } else {
            allUsersButton.setVisibility(Button.INVISIBLE);
        }

    }

    public void openRegister(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void openLogin(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void gotonext(View view) {
        Intent i = new Intent(getApplicationContext(), LoggedMainActivity.class);
        startActivity(i);
    }

    public void logout(View v) {
        try {
            FirebaseAuth.getInstance().signOut();
            welcomeTextView.setVisibility(TextView.INVISIBLE);
            logoutButton.setVisibility(Button.INVISIBLE);
            allUsersButton.setVisibility(Button.INVISIBLE);

            Toast.makeText(this, "Successfully Logged out", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Coulnt Log Out", Toast.LENGTH_SHORT).show();
        }
    }


}

