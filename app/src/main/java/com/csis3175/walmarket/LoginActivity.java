package com.csis3175.walmarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csis3175.walmarket.util.MessageUtil;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVariables();
    }

    private void initVariables() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        txtEmail.requestFocus();
    }

    public void forgotPassword(View view){
        MessageUtil.addMessage("Forgot Password. Action not Implemented!", this);

    }
    public void signIn(View view){
        MessageUtil.addMessage("Sign In. Action not Implemented!", this);
    }
    public void createAccount(View view){
        MessageUtil.addMessage("Create Account. Action not Implemented!", this);
    }
}
