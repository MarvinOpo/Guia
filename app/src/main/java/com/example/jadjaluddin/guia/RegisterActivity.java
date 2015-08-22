package com.example.jadjaluddin.guia;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class RegisterActivity extends AppCompatActivity {
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_frag_container);

        RegisterFragment rf = new RegisterFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.register_frag_container, rf).commit();
    }
}
