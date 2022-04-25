package com.example.expensetracker;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TransactionDAO {
    private DatabaseReference databaseReference;

    public TransactionDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://expensetracker-8ed93-default-rtdb.europe-west1.firebasedatabase.app"); //gets root
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        databaseReference = db.getReference(Transaction.class.getSimpleName()).child(userId);


    }

    public Task<Void> add(Transaction transaction){
        return databaseReference.push().setValue(transaction);

    }

}
