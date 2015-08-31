package com.example.jadjaluddin.guia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class RegisterFragment extends Fragment {
    ImageView mTraveler, mGuide;
    FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mTraveler = (ImageView) view.findViewById(R.id.traveler_logo);
        mGuide = (ImageView) view.findViewById(R.id.guide_logo);

        mTraveler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"On Progress",Toast.LENGTH_SHORT).show();
            }
        });
        mGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guide1Fragment g1f = new Guide1Fragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.register_frag_container, g1f).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
