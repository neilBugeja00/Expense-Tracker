package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TestSnipper extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //List variables
    private DatabaseReference databaseReferenceCat, databaseReferenceAcc;
    CategoryAdapter categoryAdapter;
    AccountAdapter accountAdapter;
    Spinner spinnerCategory, spinnerAccount;

    ArrayList<String> categoryNameList = new ArrayList<>();
    ArrayList<String> accountNameList = new ArrayList<>();
    ArrayList<String> strListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_transaction);

        //link spinner
        spinnerAccount = findViewById(R.id.spinnerAccount);

        //===================================DB SHITS==============================================
        //Connect to firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://expensetracker-8ed93-default-rtdb.europe-west1.firebasedatabase.app"); //gets root
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        databaseReferenceCat = db.getReference(Category.class.getSimpleName()).child(userId);
        databaseReferenceAcc = db.getReference(Account.class.getSimpleName()).child(userId);

        //get data from firebase
        ArrayList<Category> categoryList = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(this,categoryList);

        ArrayList<Account> accountList = new ArrayList<>();

        accountAdapter = new AccountAdapter(this,accountList);



        //extracting category data from db
        databaseReferenceCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    categoryList.add(category);
                    categoryNameList.add(category.getCatName());
                }
                categoryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //extracting account data from db
        databaseReferenceAcc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accountList.clear();
                accountNameList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Account account = dataSnapshot.getValue(Account.class);
                    accountList.add(account);
                    accountNameList.add(account.getName());
                    strListArray.add(account.getName());
                }
                accountAdapter.notifyDataSetChanged();
                System.out.println(""+accountNameList.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //==================================testing====================================================

        strListArray.add("");
        accountNameList.add("");


        //===================================setting up spinner=======================================
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, accountNameList );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccount.setAdapter(adapter);

        spinnerAccount.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(),choice,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}