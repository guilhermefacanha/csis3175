package com.csis3175.walmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csis3175.walmarket.database.UserDbHelper;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.Md5Util;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    UserDbHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVariables();
    }

    private void initVariables() {
        userDbHelper = new UserDbHelper(this);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        txtEmail.requestFocus();
    }

    public void forgotPassword(View view) {
        MessageUtil.addMessage("Forgot Password. Action not Implemented!", this);

    }

    public void signIn(View view) {
        try {
            validateForm();
            User userDb = userDbHelper.getUserByEmail(txtEmail.getText().toString());
            if (userDb == null)
                throw new Exception("User not found with address " + txtEmail.getText().toString() + ", please register!");

            if(!userDb.getPassword().equals(Md5Util.getMd5(txtPassword.getText().toString())))
                throw new Exception("Password invalid!");

            SessionUtil.setUser(userDb,this);

            startActivity(new Intent(this, MainActivity.class));

        } catch (Exception e) {
            MessageUtil.addMessage(e.getMessage(), this);
        }

    }

    private void validateForm() throws Exception {
        if (txtEmail.getText().toString().isEmpty())
            throw new Exception("Required field Email");
        if (txtPassword.getText().toString().isEmpty())
            throw new Exception("Required field Password");
    }

    public void createAccount(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
