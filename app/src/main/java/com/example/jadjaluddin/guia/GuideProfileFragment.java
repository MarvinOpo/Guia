package com.example.jadjaluddin.guia;

import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by jadjaluddin on 8/14/2015.
 */
public class GuideProfileFragment extends Fragment {
    String name = "Edilberto Parrotina";
    String email = "Kenshii_dhaot@gmail";
    String age = "28 years old", location = "Cebu City", specialty = "Trecking", number = "092626010";
    int profile = R.drawable.profile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.guide_calendar, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guide_profile, container, false);

        ImageView profImage = (ImageView) view.findViewById(R.id.main_profile_image);
        TextView profName = (TextView) view.findViewById(R.id.main_profile_name);
        TextView profEmail = (TextView) view.findViewById(R.id.main_profile_email);
        TextView profAge = (TextView) view.findViewById(R.id.main_profile_age);
        TextView profLocation = (TextView) view.findViewById(R.id.location_info);
        TextView profSpecialty = (TextView) view.findViewById(R.id.specialty_info);
        TextView profNumber = (TextView) view.findViewById(R.id.number_info);
        RatingBar rb = (RatingBar) view.findViewById(R.id.rating);


        profImage.setImageResource(profile);
        profName.setText(name);
        profEmail.setText(email);
        profAge.setText(age);
        profLocation.setText(location);
        profSpecialty.setText(specialty);
        profNumber.setText(number);
        rb.setMax(5);
        rb.setNumStars(5);
        rb.setRating(4);

        return view;
    }
}
