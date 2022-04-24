package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {

    Context context;

    ArrayList<Account> list;

    public AccountAdapter(Context context, ArrayList<Account> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_accounts,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Account account = list.get(position);
        holder.accountName.setText(account.getName());
        holder.accountAmmount.setText(account.getAmmount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView accountName, accountAmmount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            accountName = itemView.findViewById(R.id.text_view_name);
            accountAmmount = itemView.findViewById(R.id.text_view_amount);
        }
    }
}
