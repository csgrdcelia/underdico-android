package com.esgi.project.underdico;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.expression.ExpressionFragment;
import com.esgi.project.underdico.home.HomeFragment;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.newexpression.NewExpressionFragment;
import com.esgi.project.underdico.user.UserFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView userPicture;
    TextView profilename;
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

        View headerView = navigationView.getHeaderView(0);
        profilename = headerView.findViewById(R.id.tvUsername);
        userPicture = headerView.findViewById(R.id.ivUserPicture);

        displayUserInfos();

        setUserPageListener();

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            launchSearch(query);
        }

        Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        if(existingFragment == null)
            updateFragment(HomeFragment.newInstance());
    }

    private void launchSearch(String query) {
        updateFragment(SearchFragment.newInstance(query));
    }

    private void displayUserInfos() {
        profilename.setText("User1"); //TODO: retrieve real user
        //userPicture.
    }

    private void setUserPageListener() {
        userPicture.setOnClickListener(
                v -> updateFragment(UserFragment.newInstance())
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.random) {
            updateFragment(ExpressionFragment.newInstance(Expression.getRandomExpression()));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_home) {
            updateFragment(HomeFragment.newInstance());
        } else if (id == R.id.menu_top) {
            Toast.makeText(this, "menu_top", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_new) {
            updateFragment(NewExpressionFragment.newInstance());
        } else if (id == R.id.menu_play) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     *  Bind the given fragment into R.id.mainFragment
     */
    private void updateFragment(Fragment fragmentToGive) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragmentToGive);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
