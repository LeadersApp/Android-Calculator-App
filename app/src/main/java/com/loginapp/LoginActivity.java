package com.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private Button loginBtn, registerBtn;

    private List<User> users = new ArrayList<>(
            Arrays.asList(
                    new User("itay@gmail.com", "123123123"),
                    new User("david@gmail.com", "123123123"),
                    new User("moshe@gmail.com", "123123123")
            )
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create references to UI Views on this screen:
        // Username and password text fields:
        usernameField = findViewById(R.id.editTextUsername);
        passwordField = findViewById(R.id.editTextPassword);

        // Sign-in and register buttons:
        loginBtn = findViewById(R.id.buttonLogin);
        registerBtn = findViewById(R.id.buttonRegister);

        loginBtn.setOnClickListener(this::onLoginBtnClicked);
        registerBtn.setOnClickListener(this::onRegisterBtnClicked);
    }

    public void onLoginBtnClicked(View v) {
        // Login action code goes here...

        ProgressBar pb = findViewById(R.id.progressBarLogin);
        pb.setVisibility(View.VISIBLE);

        List<User> users = getUsersFromDatabase();

        String uName = usernameField.getText().toString();
        String uPass = passwordField.getText().toString();


        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getUsername().equals(uName)) {

                if (users.get(i).isPasswordValid(uPass)) {
                    // Successful login action

                    Toast.makeText(
                            this,
                            "Login successful!",
                            Toast.LENGTH_SHORT)
                    .show();
                    moveToCalculatorActivity();
//                    moveToMainActivity();

                } else {
                    // Password is incorrect!
                    passwordField.setText("");
                    Toast.makeText(
                            this,
                            "Incorrect password!\nPlease try again",
                            Toast.LENGTH_SHORT)
                    .show();
                }
            }
        }
        pb.setVisibility(View.GONE);
    }

    public void onRegisterBtnClicked(View v) {
        // Register action code goes here...
        String uName, uPass;
        uName = usernameField.getText().toString();
        uPass = passwordField.getText().toString();

        User newUser = new User(uName, uPass);

        addNewUser(newUser);
    }

    public void addNewUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                Toast.makeText(
                        this,
                        "Username is already taken!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // If code reaches here - add a new user:
        users.add(user);
    }

    public List<User> getUsersFromDatabase() {
        return users; // return the list of fake users.
    }


    public void moveToMainActivity() {
        Intent mainActivity = new Intent(
                this,
                MainActivity.class
        );
        startActivity(mainActivity);
    }

    public void moveToCalculatorActivity() {
        Intent calcActivity = new Intent(this, CalculatorActivity.class);
        startActivity(calcActivity);
    }

}



/*
    findViewById(R.id.btnLogin).setOnClickListener(this::onLoginBtnClick);


  public void onLoginBtnClick(View v) {
        Intent calculatorActivity = new Intent(
                LoginActivity.this,
                MainActivity.class
        );

        startActivity(calculatorActivity);
    }*/