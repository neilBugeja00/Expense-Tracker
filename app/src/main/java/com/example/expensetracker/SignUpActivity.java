package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    //Variables
    TextInputLayout regName, regUsername, regEmail, regPassword;
    Button regBtn, callLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //linking
        EditText editName = findViewById(R.id.regName);
        EditText editUsername = findViewById(R.id.regUsername);
        EditText editEmail = findViewById(R.id.regEmail);
        EditText editPassword = findViewById(R.id.regPassword);

        //regName = findViewById(R.id.regName);
        //regUsername = findViewById(R.id.regUsername);
        //regEmail = findViewById(R.id.regEmail);
        //regPassword = findViewById(R.id.regPassword);

        regBtn = (Button)findViewById(R.id.regButton);
        callLogin = (Button)findViewById(R.id.loginButton);

        UserDAO dao = new UserDAO();

        //Save to firebase on button "regButton" click

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(editName.getText().toString(),editUsername.getText().toString(),editEmail.getText().toString(),editPassword.getText().toString());
                dao.add(user).addOnSuccessListener(suc ->{
                   Toast.makeText(getApplicationContext(),"Record is inserted",Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(err ->{
                    Toast.makeText(getApplicationContext(),""+err.getMessage(),Toast.LENGTH_SHORT).show();
                });
            }
        });

        //Make button "callLogin" move to "LoginActivity.java"

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });


    }
}