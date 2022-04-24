package com.example.expensetracker;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountDAO {

    private DatabaseReference databaseReference;

    public AccountDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://expensetracker-8ed93-default-rtdb.europe-west1.firebasedatabase.app"); //gets root
        databaseReference = db.getReference(Account.class.getSimpleName());
    }

    public Task<Void> add(Account account){
        return databaseReference.push().setValue(account);

    }

}
