package com.example.expensetracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AccountFragment extends Fragment {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newName, newAmount;
    private Button confirmAddAccountBtn;
    private FloatingActionButton addAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container,false);
        addAccount = view.findViewById(R.id.addButton);

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Fuck me Jerry!", Toast.LENGTH_LONG).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void createNewAccountDialog(){
        //dialogBuilder = new AlertDialog.Builder(this);
        final View accountPopupView = getLayoutInflater().inflate(R.layout.account_popup,null);
        newName = (EditText) accountPopupView.findViewById(R.id.newName);
        newAmount = (EditText) accountPopupView.findViewById(R.id.newAmmount);
        confirmAddAccountBtn = (Button) accountPopupView.findViewById(R.id.confirmAddAccountBtn);

        dialogBuilder.setView(accountPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

    }
}