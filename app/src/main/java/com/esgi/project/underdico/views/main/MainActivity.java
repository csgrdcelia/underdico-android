package com.esgi.project.underdico.views.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;

import com.esgi.project.underdico.views.game.GameFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.presenters.MainPresenter;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.privacy.PrivacyFragment;
import com.esgi.project.underdico.views.rooms.RoomListFragment;
import com.esgi.project.underdico.views.expression.ExpressionFragment;
import com.esgi.project.underdico.views.home.HomeFragment;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.login.LoginActivity;
import com.esgi.project.underdico.views.newexpression.NewExpressionFragment;
import com.esgi.project.underdico.views.top.TopFragment;
import com.esgi.project.underdico.views.search.SearchFragment;
import com.esgi.project.underdico.views.user.UserFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    MainPresenter presenter;

    ImageView profilePicture;
    TextView profilename;

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawer;

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, getApplicationContext());

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.addOnBackStackChangedListener(() -> {
            List<Fragment> f = fragmentManager.getFragments();
            currentFragment = f.get(0);
        });

        if(!fromSearchView()) {
            Fragment existingFragment = fragmentManager.findFragmentById(R.id.main_fragment);
            if (existingFragment == null)
                updateFragment(HomeFragment.newInstance());
        }
    }

    private boolean fromSearchView() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            launchSearch(query);
            return true;
        }
        return false;
    }

    @Override
    public void assignViews() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        drawer = findViewById(R.id.drawer_layout);
        profilename = headerView.findViewById(R.id.tvUsername);
        profilePicture = headerView.findViewById(R.id.ivUserPicture);

    }

    @Override
    public void setListeners() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        profilePicture.setOnClickListener(v -> updateFragment(UserFragment.newInstance(Session.getCurrentUser())));
    }

    @Override
    public void displayUserInformation() {
        profilename.setText(Session.getCurrentUser().getUsername());
    }

    @Override
    public void setProfilePicture(Bitmap image) {
        profilePicture.setImageBitmap(image);
    }

    private void launchSearch(String query) {
        updateFragment(SearchFragment.newInstance(query));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if(currentFragment.getClass().equals(GameFragment.class)) {
                GameFragment gameFragment = (GameFragment)currentFragment;
                gameFragment.onStop();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_app_bar, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.random) {
            presenter.callRandomExpression();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_home) {
            updateFragment(HomeFragment.newInstance());
        } else if (id == R.id.menu_top) {
            updateFragment(TopFragment.newInstance());
        } else if (id == R.id.menu_new) {
            updateFragment(NewExpressionFragment.newInstance());
        } else if (id == R.id.menu_play) {
            updateFragment(RoomListFragment.newInstance());
        } else if (id == R.id.menu_logout) {
            Session.clearSharedPreferences(getApplicationContext());
            redirectToLoginPage();
        } else if(id == R.id.menu_privacy) {
            updateFragment(PrivacyFragment.newInstance());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void updateFragment(Fragment fragmentToGive) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, fragmentToGive);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void displayRandomExpression(Expression expression) {
        updateFragment(ExpressionFragment.newInstance(expression));
    }

    @Override
    public void redirectToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void refresh() {
        recreate();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
