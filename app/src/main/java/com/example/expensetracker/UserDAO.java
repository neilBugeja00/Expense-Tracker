package com.example.expensetracker;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO {

    private DatabaseReference databaseReference;

    public UserDAO(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://expensetracker-8ed93-default-rtdb.europe-west1.firebasedatabase.app"); //gets root
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    public Task<Void> add(User user){
        return databaseReference.push().setValue(user);

    }

}
