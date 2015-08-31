package com.example.jadjaluddin.guia;

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

import java.lang.reflect.Array;
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
        LoggedInGuide.toolbar.setTitle("Popular Destination");
        mList.clear();
        mList.add(new PopularDestinations(R.drawable.image1, "SANOVA BEACH", "A place where jerks used to hang out"));
        mList.add(new PopularDestinations(R.drawable.image2, "YUARA POOL", "A paradise for all stupid person"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.filter, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.cardList);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        RVadapter adapter = new RVadapter(mList, null);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoggedInGuide.toolbar.setTitle("Popular Destination");
        LoggedInGuide.doubleBackToExitPressedOnce = false;
    }
}
