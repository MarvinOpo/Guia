package com.example.jadjaluddin.guia.Navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jadjaluddin.guia.Guide.LoggedInGuide;
import com.example.jadjaluddin.guia.R;
import com.example.jadjaluddin.guia.Traveler.LoggedInTraveler;

/**
 * Created by Stephanie on 8/5/2015.
 */
public class SettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            LoggedInGuide.mToolbar.setTitle("Settings");
        }catch (Exception e){
            LoggedInTraveler.mToolbar.setTitle("Settings");
        }
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }
}
