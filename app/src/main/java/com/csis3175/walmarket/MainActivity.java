package com.csis3175.walmarket;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SelectStoreFragment.OnFragmentInteractionListener, FindStoreFragment.OnFragmentInteractionListener {

    TextView lblUserName, lblUserEmail;
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
            ft.commit();
        }
    }

    private void initializeVariables() {
        NavigationView headView = findViewById(R.id.nav_view);
        lblUserName = headView.getHeaderView(0).findViewById(R.id.lblUserName);
        lblUserEmail = headView.getHeaderView(0).findViewById(R.id.lblUserEmail);

        userLogged = SessionUtil.getUser(this);
        if (userLogged != null && userLogged.getUserId() > 0) {
            lblUserName.setText(userLogged.getfName() + userLogged.getlName());
            lblUserEmail.setText(userLogged.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        MessageUtil.addMessage("onBackPressed", this);
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
            MessageUtil.addMessage("You pushed " + id, this);
            fragment = new FindStoreFragment();
            initializeFragment();
        } else if (id == R.id.nav_acct_info) {
            MessageUtil.addMessage("You pushed " + id, this);
            fragment = new SelectStoreFragment();
            initializeFragment();

        } else if (id == R.id.nav_view_orders) {
            MessageUtil.addMessage("You pushed " + id, this);

        } else if (id == R.id.nav_share) {
            MessageUtil.addMessage("You pushed " + id, this);

        } else if (id == R.id.nav_sign_out) {
            MessageUtil.addMessage("You pushed " + id, this);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goCart(View view) {
        MessageUtil.addMessage("Go to Cart Screen no implemented!", this);
    }

    public void continueFindStore(View view) {
        MessageUtil.addMessage("Continue Find Store no implemented!", this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
