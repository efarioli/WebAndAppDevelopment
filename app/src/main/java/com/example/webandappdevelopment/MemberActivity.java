package com.example.webandappdevelopment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webandappdevelopment.ModelDTO.MemberDTO;
import com.google.android.material.navigation.NavigationView;
import com.muddzdev.styleabletoast.StyleableToast;

public class MemberActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView navUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navUserName = navigationView.getHeaderView(0).findViewById(R.id.nav_title_username);
        String userName = SessionObject.getInstance().getMemberDTO().getName();
        navUserName.setText(userName);
        navigationView.setNavigationItemSelectedListener(this);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LoanFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_current_loans);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationView navigationView = findViewById(R.id.nav_view);


        switch (item.getItemId()) {
            case R.id.nav_current_loans:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoanFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_current_loans);
                break;
            case R.id.nav_view_all_books:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ViewAllBooksFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_current_loans);
                navigationView.setCheckedItem(R.id.nav_view_all_books);

                break;
            case R.id.nav_loan_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoanHistoryFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_loan_history);
                break;
            case R.id.nav_logout:
                doLogout();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void doLogout() {
        SessionObject sss = SessionObject.getInstance();
        sss.clear();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
