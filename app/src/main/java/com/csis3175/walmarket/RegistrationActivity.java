package com.csis3175.walmarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.csis3175.walmarket.database.UserDbHelper;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.MessageUtil;

public class RegistrationActivity extends AppCompatActivity {

    UserDbHelper userDbHelper;

    EditText txtFName, txtLName, txtEmail, txtAddress, txtPass, txtConfPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initializeVariables();
    }

    private void initializeVariables() {
        userDbHelper = new UserDbHelper(this);

        txtFName = findViewById(R.id.txtFName);
        txtLName = findViewById(R.id.txtLName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        txtPass = findViewById(R.id.txtRegPassword);
        txtConfPass = findViewById(R.id.txtConfirmPassword);

        txtFName.requestFocus();
    }

    public void backSignIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void register(View view) {
        try {
            validateForm();
            User user = new User();
            user.setfName(txtFName.getText().toString());
            user.setlName(txtLName.getText().toString());
            user.setEmail(txtEmail.getText().toString());
            user.setAddress(txtAddress.getText().toString());
            user.setPassword(txtPass.getText().toString());

            User userDb = userDbHelper.getUserByEmail(user.getEmail());
            if (userDb != null)
                throw new Exception("The email address " + user.getEmail() + " is already registered!");

            if(!userDbHelper.addUser(user))
                throw new Exception("Error registering user!");

            startActivity(new Intent(this, RegistrationConfirmActivity.class));
        } catch (Exception e) {
            MessageUtil.addMessage(e.getMessage(), this);
        }
    }

    private void validateForm() throws Exception {
        if (txtFName.getText().toString().isEmpty())
            throw new Exception("Required field First Name");
        if (txtLName.getText().toString().isEmpty())
            throw new Exception("Required field Last Name");
        if (txtEmail.getText().toString().isEmpty())
            throw new Exception("Required field Email");
        if (txtAddress.getText().toString().isEmpty())
            throw new Exception("Required field Address");
        if (txtPass.getText().toString().isEmpty())
            throw new Exception("Required field Password");
        if (txtConfPass.getText().toString().isEmpty())
            throw new Exception("Required field Confirm Password");

        if (!txtPass.getText().toString().equals(txtConfPass.getText().toString()))
            throw new Exception("Passwords are not the same!");
    }
}
