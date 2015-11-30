package com.example.jadjaluddin.guia.Navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jadjaluddin.guia.Guide.LoggedInGuide;
import com.example.jadjaluddin.guia.R;
import com.example.jadjaluddin.guia.Traveler.LoggedInTraveler;

/**
 * Created by jadjaluddin on 8/5/2015.
 */
public class TripFragment extends Fragment {
    private FragmentTabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabframe);

        mTabHost.addTab(mTabHost.newTabSpec("upcoming").setIndicator("Upcoming"),
                UpcomingFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("previous").setIndicator("Previous"),
                PreviousFragment.class, null);
        return mTabHost;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.add_tour, menu);

        try{
            LoggedInGuide.mToolbar.setTitle("Scheduled Tour");

        }catch (Exception e){
            LoggedInTraveler.mToolbar.setTitle("Scheduled Tour");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }
}