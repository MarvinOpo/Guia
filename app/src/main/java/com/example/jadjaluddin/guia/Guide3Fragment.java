package com.example.jadjaluddin.guia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class Guide3Fragment extends Fragment {
    Button btnNext, btnBack;
    FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide3, container, false);

        btnNext = (Button) view.findViewById(R.id.guide3_next);
        btnBack = (Button) view.findViewById(R.id.guide3_back);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guide4Fragment g4f = new Guide4Fragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.register_frag_container, g4f).addToBackStack(null).commit();
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
