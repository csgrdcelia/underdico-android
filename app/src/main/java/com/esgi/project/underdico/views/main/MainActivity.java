package com.esgi.project.underdico.views.main;

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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.presenters.MainPresenter;
import com.esgi.project.underdico.services.ExpressionService;
import com.esgi.project.underdico.utils.ApiInstance;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.channels.GameChannelsFragment;
import com.esgi.project.underdico.views.expression.ExpressionFragment;
import com.esgi.project.underdico.views.home.HomeFragment;
import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.views.login.LoginActivity;
import com.esgi.project.underdico.views.newexpression.NewExpressionFragment;
import com.esgi.project.underdico.views.search.SearchFragment;
import com.esgi.project.underdico.views.user.UserFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    MainPresenter presenter;

    ImageView userPicture;
    TextView profilename;

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, getApplicationContext());

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            launchSearch(query);
        }

        Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        if(existingFragment == null)
            updateFragment(HomeFragment.newInstance());
    }

    @Override
    public void assignViews() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        profilename = headerView.findViewById(R.id.tvUsername);
        userPicture = headerView.findViewById(R.id.ivUserPicture);
        userPicture.setOnClickListener(v -> redirectToUserPage());
    }

    @Override
    public void setListeners() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void displayUserInformation() {
        profilename.setText(Session.getCurrentUser().getUsername());
    }

    private void launchSearch(String query) {
        updateFragment(SearchFragment.newInstance(query));
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
            Toast.makeText(this, "menu_top", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_new) {
            updateFragment(NewExpressionFragment.newInstance());
        } else if (id == R.id.menu_play) {
            updateFragment(GameChannelsFragment.newInstance());
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
    @Override
    public void displayRandomExpression(Expression expression) {
        updateFragment(ExpressionFragment.newInstance(expression));
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void redirectToUserPage() {
        updateFragment(UserFragment.newInstance(Session.getCurrentUser()));
    }

    @Override
    public void refresh() {
        recreate();
    }


}
