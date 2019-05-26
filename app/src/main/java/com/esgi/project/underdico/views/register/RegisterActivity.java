package com.esgi.project.underdico.views.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.esgi.project.underdico.R;
import com.esgi.project.underdico.presenters.RegisterPresenter;
import com.esgi.project.underdico.views.imagespinner.ImageSpinnerAdapter;
import com.esgi.project.underdico.views.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterView, AdapterView.OnItemSelectedListener {

    private final String ARG_USERNAME = "username";

    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordConfirmationEditText;
    Spinner flagSpinner;
    Button registerButton;
    String selectedLanguage = "fr";

    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this, getApplicationContext());

        assignViews();
        setListeners();
    }

    @Override
    public void assignViews() {
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmationEditText = findViewById(R.id.passwordConfirmationEditText);
        flagSpinner = findViewById(R.id.flagSpinner);
        flagSpinner.setAdapter(new ImageSpinnerAdapter(this));
        registerButton = findViewById(R.id.registerButton);
    }

    @Override
    public void setListeners() {
        checkUsernameValidityInRealTime();
        checkEmailValidityInRealTime();
        checkPasswordValidityInRealTime();
        checkPasswordsMatchInRealTime();

        registerButton.setOnClickListener(
                view -> presenter.handleRegister(
                        usernameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(), selectedLanguage));

        flagSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void checkUsernameValidityInRealTime() {
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(usernameEditText.getText().length() < 3 || usernameEditText.getText().length() > 16)
                    usernameEditText.setError(getApplicationContext().getString(R.string.username_length_indication));
                else
                    usernameEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public void checkEmailValidityInRealTime() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mail = emailEditText.getText().toString();
                if(!TextUtils.isEmpty(mail) && !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                    emailEditText.setError(getApplicationContext().getString(R.string.invalid_mail));
                else
                    emailEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public void checkPasswordValidityInRealTime() {
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordEditText.getText().length() < 6 || passwordEditText.getText().length() > 32)
                    passwordEditText.setError(getApplicationContext().getString(R.string.password_length_indication));
                else
                    passwordEditText.setError(null);

                if(passwordEditText.getText().toString().equals(passwordConfirmationEditText.getText().toString()))
                    passwordConfirmationEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public void checkPasswordsMatchInRealTime() {
        passwordConfirmationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!passwordEditText.getText().toString().equals(passwordConfirmationEditText.getText().toString()))
                    passwordConfirmationEditText.setError(getApplicationContext().getString(R.string.passwords_do_not_match));
                else
                    passwordConfirmationEditText.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedLanguage = ((Pair<String,Integer>)flagSpinner.getAdapter().getItem(position)).first;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean hasInvalidFields() {
        return usernameEditText.getError() != null || emailEditText.getError() != null || passwordEditText.getError() != null || passwordConfirmationEditText.getError() != null;
    }

    @Override
    public void showRegisterSuccess() {
        Toast.makeText(this,this.getString(R.string.register_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginView(String username) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(ARG_USERNAME, username);
        startActivity(intent);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void askUserToFillFields() {
        Toast.makeText(this,this.getString(R.string.fill_fields), Toast.LENGTH_SHORT).show();
    }
}
