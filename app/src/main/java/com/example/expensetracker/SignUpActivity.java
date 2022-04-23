package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    //Variables
    private EditText editName, editEmail, editPassword;
    Button regBtn, callLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialise authentication
        mAuth = FirebaseAuth.getInstance();

        //linking
        editName = findViewById(R.id.regName);
        editEmail = findViewById(R.id.regEmail);
        editPassword = findViewById(R.id.regPassword);

        regBtn = (Button)findViewById(R.id.regButton);
        callLogin = (Button)findViewById(R.id.loginButton);

        UserDAO dao = new UserDAO();

        //Save to firebase on button "regButton" click

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                //checking if variables are empty
                if(name.isEmpty()){
                    editName.setError("Full name required");
                    editName.requestFocus();
                    return;
                }

                if(email.isEmpty()){
                    editEmail.setError("Email required");
                    editEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editEmail.setError("Please provide valid email");
                    editEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    editPassword.setError("Password required");
                    editPassword.requestFocus();
                    return;
                }
                if(password.length()<6){
                    editPassword.setError("Min password length must be 6 characters!");
                    editPassword.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user = new User(name,email);

                                    FirebaseDatabase.getInstance("https://expensetracker-8ed93-default-rtdb.europe-west1.firebasedatabase.app").getReference(User.class.getSimpleName())
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this, "User registered successfully!", Toast.LENGTH_LONG).show();

                                                //redirect to login layout!
                                            }else{
                                                Toast.makeText(SignUpActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(SignUpActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();
                                }
                            }
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