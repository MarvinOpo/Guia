package com.example.jadjaluddin.guia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by jadjaluddin on 8/11/2015.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUsername, txtPassword;
    TextView btnRegister;
    Button btnLogin;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;

        switch(id){
            case R.id.btnRegister:
                intent = new Intent(this, RegisterActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btnLogin:
                if(!txtUsername.getText().toString().equals("Admin")) txtUsername.setError("Incorrect Username");
                else if(!txtPassword.getText().toString().equals("admin")) txtPassword.setError("Incorrect Password");
                else {
                    intent = new Intent(this, LoggedInGuide.class);
                    this.startActivity(intent);
                    this.finish();
                }
        }
    }
}
