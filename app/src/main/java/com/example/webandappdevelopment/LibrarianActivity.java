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

import com.google.android.material.navigation.NavigationView;
import com.muddzdev.styleabletoast.StyleableToast;

public class LibrarianActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private TextView navUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_librarian);



        Toolbar toolbar = findViewById(R.id.toolbar_librarian);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_librarian);
        NavigationView navigationView = findViewById(R.id.nav_view_librarian);
        navUserName = navigationView.getHeaderView(0).findViewById(R.id.nav_title_username);
        String userName = SessionObject.getInstance().getLibrarianDTO().getName();
        navUserName.setText(userName);
        navigationView.setNavigationItemSelectedListener(this);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_librarian,
                    new ViewAllBooksLibrarianFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_view_all_books_librarian);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationView navigationView = findViewById(R.id.nav_view_librarian);
        navigationView.setCheckedItem(R.id.nav_view_all_books_librarian);


        switch (item.getItemId()) {

            case R.id.nav_view_all_books_librarian:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_librarian,
                        new ViewAllBooksLibrarianFragment()).commit();
                navigationView.getMenu().getItem(0).setChecked(true);
                navigationView.setCheckedItem(R.id.nav_view_all_books_librarian);
                break;
            case R.id.nav_logout_librarian:
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
