package com.example.expensetracker;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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


public class AddTransactionFragment extends Fragment{

    //Adding Transaction Variables
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newTransAmount;
    private Spinner newTransCat, newTransAccount;
    private Button confirmAddTransactionBtn;

    private String choiceCat, choiceAccount;

    //List variables
    private DatabaseReference databaseReferenceCat, databaseReferenceAcc;
    CategoryAdapter categoryAdapter;
    AccountAdapter accountAdapter;
    Spinner spinnerCategory, spinnerAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_transaction, container,false);

        //linking
        spinnerAccount = (Spinner) view.findViewById(R.id.spinnerAccount);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinnerCategory);
        confirmAddTransactionBtn = (Button) view.findViewById(R.id.confirmAddTransactionBtn);

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
                categoryNameList.clear();
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
                }
                accountAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        categoryNameList.add("");
        //Populating Category List
        ArrayAdapter<String> arrayCatAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, categoryNameList );
        arrayCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayCatAdapter);

        spinnerCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choiceCat = adapterView.getItemAtPosition(i).toString();
                System.out.println("CHOICE CAT: "+choiceCat);
                Log.v("item", (String) adapterView.getItemAtPosition(i));
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        accountNameList.add("");
        //Populating Account List
        ArrayAdapter<String> arrayAccAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, accountNameList );
        arrayAccAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccount.setAdapter(arrayAccAdapter);



        spinnerAccount.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choiceAccount = parent.getItemAtPosition(position).toString();
                System.out.println("CHOICE ACCOUNT: "+choiceAccount);
                Log.v("item", (String) parent.getItemAtPosition(position));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // Inflate the layout for this fragment


        //==================================On clicking addTransaction button==================================
        //linking buttons
        dialogBuilder = new AlertDialog.Builder(getActivity());
        newTransAccount = (Spinner) view.findViewById(R.id.spinnerCategory);
        newTransCat = (Spinner) view.findViewById(R.id.spinnerAccount);
        newTransAmount = (EditText) view.findViewById(R.id.newTransAmmount);

        //adding account
        TransactionDAO dao = new TransactionDAO();


        confirmAddTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction transaction = new Transaction(choiceAccount, choiceCat, newTransAmount.getText().toString().trim());
                dao.add(transaction).addOnSuccessListener(suc->{
                    Toast.makeText(getActivity(), "Transaction Added", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(err ->{
                    Toast.makeText(getActivity(), ""+err.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
        return view;
    }

}