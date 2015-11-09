package com.example.jadjaluddin.guia.Traveler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jadjaluddin.guia.Helper.DBHelper;
import com.example.jadjaluddin.guia.Helper.RVadapter;
import com.example.jadjaluddin.guia.Model.Course;
import com.example.jadjaluddin.guia.Model.User;
import com.example.jadjaluddin.guia.R;

import java.util.ArrayList;

/**
 * Created by Stephanie Lyn on 10/8/2015.
 */
public class FragmentChooseGuide extends Fragment{
    ArrayList<User> mList = new ArrayList<User>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList.clear();
        mList.add(new User("asdasasdasda", "Asnaui Pangcatan", "08/13/1995", "male", "20", "", "guide", 128, (float) 4.2));
        mList.add(new User("qweqweqweqwe", "Joycen Combista", "08/13/1995", "female", "20", "", "guide", 68, (float) 3.8));
        mList.add(new User("zxczxcxcxzcz", "Claire Magto", "07/06/1995", "female", "20", "", "guide", 186, (float) 4.7));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_search, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.guide_cardList);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        RVadapter adapter = new RVadapter(null, null, mList);
        rv.setAdapter(adapter);

        return view;
    }

    public void nextFrag(User u){
        FragmentCourseBooking fcb = new FragmentCourseBooking(new Course(u, "SANOVA BEACH",
                "asd klajsdkl aj nlaksdn lansd , alsd ans aksdn laknsdlkna sldkn lak ,adslas n.,adn llalmd amsd " +
                        "dasdasdasda lkasnd  asdlk asd ,.asdalsd lkal;m la;msd amd; masd .amsd;lasm"));
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.drawer_fragment_container, fcb).addToBackStack(null).commit();
    }
}
