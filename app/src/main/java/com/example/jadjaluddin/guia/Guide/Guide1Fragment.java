package com.example.jadjaluddin.guia.Guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jadjaluddin.guia.R;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class Guide1Fragment extends Fragment {
    EditText txtLocation, txtContact;
    Button btnNext, btnBack;
    static String location, contact;
    FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_registration1, container, false);

        txtLocation = (EditText) view.findViewById(R.id.txtLocation);
        txtContact = (EditText) view.findViewById(R.id.txtContact);
        btnNext = (Button) view.findViewById(R.id.guide1_next);
        btnBack = (Button) view.findViewById(R.id.guide1_back);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = txtLocation.getText().toString();
                contact = txtContact.getText().toString();
                if(location.equals("")) txtLocation.setError("Required!");
                else if(contact.equals("")) txtContact.setError("Required!");
                else {
                    Guide2Fragment g2f = new Guide2Fragment();
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.new_frag_container, g2f).addToBackStack(null).commit();
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
