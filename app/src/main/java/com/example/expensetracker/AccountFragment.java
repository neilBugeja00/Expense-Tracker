package com.example.expensetracker;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newName, newAmount;
    private Button confirmAddAccountBtn;
    private FloatingActionButton addAccount;

    RecyclerView recyclerView;
    AccountAdapter accountAdapter;
    ArrayList<Account> list;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container,false);
        addAccount = view.findViewById(R.id.addButton);

        //==================================Generate Account List==================================
        recyclerView = view.findViewById(R.id.recycler_view_accounts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //connection to DB
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://expensetracker-8ed93-default-rtdb.europe-west1.firebasedatabase.app"); //gets root
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        databaseReference = db.getReference(Account.class.getSimpleName()).child(userId);

        list = new ArrayList<>();
        accountAdapter = new AccountAdapter(getActivity(),list);
        recyclerView.setAdapter(accountAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Account account = dataSnapshot.getValue(Account.class);
                    list.add(account);
                }
                accountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //==================================On clicking addAccount button==================================
        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccountDialog();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void createNewAccountDialog(){
        //linking buttons
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View accountPopupView = getLayoutInflater().inflate(R.layout.account_popup,null);
        newName = (EditText) accountPopupView.findViewById(R.id.newName);
        newAmount = (EditText) accountPopupView.findViewById(R.id.newAmmount);
        confirmAddAccountBtn = (Button) accountPopupView.findViewById(R.id.confirmAddAccountBtn);

        //adding account
        AccountDAO dao = new AccountDAO();

        confirmAddAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = new Account(newName.getText().toString().trim(), newAmount.getText().toString().trim());
                dao.add(account).addOnSuccessListener(suc->{
                    Toast.makeText(getActivity(), "Account Added", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(err ->{
                    Toast.makeText(getActivity(), ""+err.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });

        dialogBuilder.setView(accountPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

    }
}