package com.example.jadjaluddin.guia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class RegisterActivity extends AppCompatActivity {
    static String name, bday, gender, age, image;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_frag_container);

        try{
            Bundle b = this.getIntent().getExtras();
            name = b.getString("name");
            bday = b.getString("bday");
            gender = b.getString("gender");
            age = b.getString("age");
            image = b.getString("image");
        }
        catch(Exception e){}

        RegisterFragment rf = new RegisterFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.register_frag_container, rf).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.manager.logOut();
    }
}
