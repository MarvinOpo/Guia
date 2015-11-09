package com.example.jadjaluddin.guia.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jadjaluddin.guia.Helper.DBHelper;
import com.example.jadjaluddin.guia.Model.User;
import com.example.jadjaluddin.guia.R;
import com.example.jadjaluddin.guia.RegisterActivity;
import com.facebook.AccessToken;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class Guide2Fragment extends Fragment {
    EditText txtEmail;
    Button btnDone, btnBack;
    static String email;
    FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_registration2, container, false);

        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        btnDone = (Button) view.findViewById(R.id.guide2_done);
        btnBack = (Button) view.findViewById(R.id.guide2_back);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtEmail.getText().toString();
                if(email.equals("")) txtEmail.setError("Required!");
                else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), LoggedInGuide.class);
                    intent.putExtra("name", RegisterActivity.name);
                    intent.putExtra("bday", RegisterActivity.bday);
                    intent.putExtra("gender", RegisterActivity.gender);
                    intent.putExtra("age", RegisterActivity.age);
                    intent.putExtra("image", RegisterActivity.image);
                    intent.putExtra("location", Guide1Fragment.location);
                    intent.putExtra("contact", Guide1Fragment.contact);
                    intent.putExtra("email", email);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        return view;
    }
}
