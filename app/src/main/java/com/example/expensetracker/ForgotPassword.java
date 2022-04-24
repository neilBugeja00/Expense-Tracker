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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordBtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById(R.id.email);
        resetPasswordBtn = (Button) findViewById(R.id.btnResetPassword);

        mAuth = FirebaseAuth.getInstance();

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    //method to reset password
    private void resetPassword(){
        String email = emailEditText.getText().toString().trim();

        //validation
        if(email.isEmpty()){
            emailEditText.setError("Email required");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide valid email");
            emailEditText.requestFocus();
            return;
        }

        //reset password
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check email to reset password!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPassword.this, "Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}