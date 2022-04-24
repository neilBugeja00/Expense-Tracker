package com.example.expensetracker;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //Bottom Navigation Variables
    BottomNavigationView bottomNavigationView;

    AccountFragment accountFragment = new AccountFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    AddTransactionFragment addTransactionFragment = new AddTransactionFragment();
    ViewTransactionsFragment viewTransactionsFragment = new ViewTransactionsFragment();
    OverviewFragment overviewFragment = new OverviewFragment();

    //Navigation Drawer Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bottom navigation code
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

        //Navigation Drawer Code
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

}