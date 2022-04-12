package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    AccountFragment accountFragment = new AccountFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    AddTransactionFragment addTransactionFragment = new AddTransactionFragment();
    ViewTransactionsFragment viewTransactionsFragment = new ViewTransactionsFragment();
    OverviewFragment overviewFragment = new OverviewFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        //sets accountFragment as the home page
        getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.accounts:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
                        return true;
                    case R.id.category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,categoryFragment).commit();
                        return true;
                    case R.id.addTransaction:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,addTransactionFragment).commit();
                        return true;
                    case R.id.viewTransactions:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,viewTransactionsFragment).commit();
                        return true;
                    case R.id.overview:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,overviewFragment).commit();
                        return true;
                    //add the same for list transactions
                }
                return false;
            }
        });
    }
}