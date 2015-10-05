package com.example.jadjaluddin.guia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jadjaluddin.guia.Guide.Guide1Fragment;
import com.example.jadjaluddin.guia.Traveler.LoggedInTraveler;

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
                //Toast.makeText(getActivity().getApplicationContext(),"On Progress",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), LoggedInTraveler.class);
                intent.putExtra("name", RegisterActivity.name);
                intent.putExtra("bday", RegisterActivity.bday);
                intent.putExtra("gender", RegisterActivity.gender);
                intent.putExtra("age", RegisterActivity.age);
                intent.putExtra("image", RegisterActivity.image);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        mGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guide1Fragment g1f = new Guide1Fragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.new_frag_container, g1f).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
