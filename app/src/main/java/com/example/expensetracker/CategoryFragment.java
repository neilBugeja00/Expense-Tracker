package com.example.expensetracker;

import android.app.AlertDialog;
import android.content.Intent;
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


public class CategoryFragment extends Fragment {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newCatName, newCatAmount;
    private Button confirmAddCatBtn, viewPie;
    private FloatingActionButton addCat;

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> list;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container,false);
        addCat = view.findViewById(R.id.addCatButton);

        //==================================Generate List===================================================
        recyclerView = view.findViewById(R.id.recycler_view_categories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //connection to DB
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://expensetracker-8ed93-default-rtdb.europe-west1.firebasedatabase.app"); //gets root
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        databaseReference = db.getReference(Category.class.getSimpleName()).child(userId);

        list = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(),list);
        recyclerView.setAdapter(categoryAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    list.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //==================================On clicking addCategory button==================================
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewCatDialog();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void createNewCatDialog(){
        //linking buttons
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View catPopupView = getLayoutInflater().inflate(R.layout.category_popup,null);
        newCatName = (EditText) catPopupView.findViewById(R.id.newCatName);
        newCatAmount = (EditText) catPopupView.findViewById(R.id.newCatAmmount);
        confirmAddCatBtn = (Button) catPopupView.findViewById(R.id.confirmAddCatBtn);

        CategoryDAO dao = new CategoryDAO();

        confirmAddCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category category = new Category(newCatName.getText().toString().trim(), newCatAmount.getText().toString().trim());
                dao.add(category).addOnSuccessListener(suc->{
                    Toast.makeText(getActivity(), "Category Added", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(err ->{
                    Toast.makeText(getActivity(), ""+err.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });

        dialogBuilder.setView(catPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }
}