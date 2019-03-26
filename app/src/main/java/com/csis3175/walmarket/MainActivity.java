package com.csis3175.walmarket;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView lblUserName, lblUserEmail, lblUserStore;
    User userLogged;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeVariables();

        fragment = new FindStoreFragment();
        initializeFragment();

    }

    private void initializeFragment() {

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameContent, fragment);
            ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
        }
    }

    private void initializeVariables() {
        NavigationView headView = findViewById(R.id.nav_view);
        lblUserName = headView.getHeaderView(0).findViewById(R.id.lblUserName);
        lblUserEmail = headView.getHeaderView(0).findViewById(R.id.lblUserEmail);
        lblUserStore = headView.getHeaderView(0).findViewById(R.id.lblUserStore);

        userLogged = SessionUtil.getUser(this);
        if (userLogged != null && userLogged.getUserId() > 0) {
            lblUserName.setText(userLogged.getfName() +" "+  userLogged.getlName());
            lblUserEmail.setText(userLogged.getEmail());
        }

        updateStoreInfo();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
            fragment = new FindStoreFragment();
            initializeFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_find_store) {
            fragment = new FindStoreFragment();
            initializeFragment();
        } else if (id == R.id.nav_select_item) {
            if (SessionUtil.isStoreSelected(this)) {
                fragment = new ItemStoreFragment();
                initializeFragment();
            } else
                MessageUtil.addMessage("You must select a store to visit the items!", this);
        } else if (id == R.id.nav_acct_info) {
            fragment = new AccountInformationFragment();
            initializeFragment();

        } else if (id == R.id.nav_view_orders) {
            fragment = new OrderHistoryFragment();
            initializeFragment();

        } else if (id == R.id.nav_share) {
            fragment = new InviteFriendsFragment();
            initializeFragment();
        } else if (id == R.id.nav_sign_out) {
            SessionUtil.destroy(this);
            Intent intent =new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goCart(View view) {
        if (SessionUtil.isStoreSelected(this)) {
            fragment = new CartFragment();
            initializeFragment();
        } else
            MessageUtil.addMessage("You must select a store to visit the cart!", this);
    }

    public void continueFindStore(View view) {
        MessageUtil.addMessage("Continue Find Store no implemented!", this);
    }

    public void goToSelectStore(List<Store> stores, String postalCode, Address address) {
        Toast.makeText(this, "Thats it: " + postalCode, Toast.LENGTH_SHORT).show();
    }

    public void updateStoreInfo() {
        Store store = SessionUtil.getStore(this);
        if (store.getStoreId() > 0)
            lblUserStore.setText(store.getName());
    }
}
