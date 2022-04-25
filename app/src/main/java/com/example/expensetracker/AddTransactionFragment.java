package com.example.expensetracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddTransactionFragment extends Fragment {

    private DatabaseReference databaseReferenceCat, databaseReferenceAcc;
    CategoryAdapter categoryAdapter;
    AccountAdapter accountAdapter;
    Spinner spinnerCategory, spinnerAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_transaction, container,false);

        spinnerAccount = (Spinner) view.findViewById(R.id.spinnerAccount);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);

        //Connect to firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://expensetracker-8ed93-default-rtdb.europe-west1.firebasedatabase.app"); //gets root
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        databaseReferenceCat = db.getReference(Category.class.getSimpleName()).child(userId);
        databaseReferenceAcc = db.getReference(Account.class.getSimpleName()).child(userId);

        //get data from firebase
        ArrayList<Category> categoryList = new ArrayList<>();
        ArrayList<String> categoryNameList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(),categoryList);

        ArrayList<Account> accountList = new ArrayList<>();
        ArrayList<String> accountNameList = new ArrayList<>();
        accountAdapter = new AccountAdapter(getActivity(),accountList);



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
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Account account = dataSnapshot.getValue(Account.class);
                    accountList.add(account);
                    accountNameList.add(account.getName());
                }
                accountAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Populating Category List
        ArrayAdapter<String> arrayCatAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, categoryNameList );
        arrayCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayCatAdapter);

        //Populating Account List
        ArrayAdapter<String> arrayAccAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, accountNameList );
        arrayAccAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccount.setAdapter(arrayAccAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}