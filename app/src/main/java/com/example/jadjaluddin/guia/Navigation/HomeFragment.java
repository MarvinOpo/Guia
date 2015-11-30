package com.example.jadjaluddin.guia.Navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jadjaluddin.guia.Guide.LoggedInGuide;
import com.example.jadjaluddin.guia.Helper.DBHelper;
import com.example.jadjaluddin.guia.Model.PopularDestinations;
import com.example.jadjaluddin.guia.R;
import com.example.jadjaluddin.guia.Helper.RVadapter;
import com.example.jadjaluddin.guia.Traveler.LoggedInTraveler;

import java.util.ArrayList;

/**
 * Created by jadjaluddin on 8/4/2015.
 */
public class HomeFragment extends Fragment {

    ArrayList<PopularDestinations> mList = new ArrayList<PopularDestinations>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mList.clear();
        mList.add(new PopularDestinations(R.drawable.image1, "SANOVA BEACH", "A place where jerks used to hang out"));
        mList.add(new PopularDestinations(R.drawable.image2, "YUARA POOL", "A paradise for all stupid person"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        try {
            LoggedInGuide.mToolbar.setTitle("Popular Destination");
        }catch (Exception e) {
            LoggedInTraveler.mToolbar.setTitle("Popular Destination");
            inflater.inflate(R.menu.filter, menu);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.cardList);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        RVadapter adapter = new RVadapter(mList, null, null);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            LoggedInGuide.mToolbar.setTitle("Popular Destination");
            LoggedInGuide.doubleBackToExitPressedOnce = false;
        }catch (Exception e){
            LoggedInTraveler.mToolbar.setTitle("Popular Destination");
            LoggedInTraveler.doubleBackToExitPressedOnce = false;
        }
    }
}
