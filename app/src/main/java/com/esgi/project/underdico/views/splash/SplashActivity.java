package com.esgi.project.underdico.views.splash;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.presenters.SplashPresenter;
import com.esgi.project.underdico.views.login.LoginActivity;
import com.esgi.project.underdico.views.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashView {

    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter = new SplashPresenter(this, getApplicationContext());
    }

    @Override
    public void redirectToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void redirectToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void assignViews() { }

    @Override
    public void setListeners() { }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
