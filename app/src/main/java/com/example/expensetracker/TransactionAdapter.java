package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    Context context;

    ArrayList<Transaction> list;

    public TransactionAdapter(Context context, ArrayList<Transaction> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_transactions,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction transaction = list.get(position);
        holder.transAccount.setText(transaction.getTransAccount());
        holder.transCat.setText(transaction.getTransCategory());
        holder.transAmmount.setText(transaction.getTransAmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transAccount, transCat, transAmmount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            transAccount = itemView.findViewById(R.id.text_view_transAccount);
            transCat = itemView.findViewById(R.id.text_view_transCat);
            transAmmount = itemView.findViewById(R.id.text_view_transAmount);
        }
    }
}
