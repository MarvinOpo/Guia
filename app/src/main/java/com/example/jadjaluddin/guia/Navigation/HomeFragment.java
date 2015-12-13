package com.example.jadjaluddin.guia.Navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jadjaluddin.guia.Guide.LoggedInGuide;
import com.example.jadjaluddin.guia.Helper.DBHelper;
import com.example.jadjaluddin.guia.Helper.JSONParser;
import com.example.jadjaluddin.guia.MainActivity;
import com.example.jadjaluddin.guia.Model.PopularDestinations;
import com.example.jadjaluddin.guia.Model.Tours;
import com.example.jadjaluddin.guia.R;
import com.example.jadjaluddin.guia.Helper.RVadapter;
import com.example.jadjaluddin.guia.Traveler.FragmentBookingRequest;
import com.example.jadjaluddin.guia.Traveler.LoggedInTraveler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jadjaluddin on 8/4/2015.
 */
public class HomeFragment extends Fragment {

    String tour_id, tour_name, tour_location, tour_description,
            duration_format, tour_preference, tour_guideId,main_image;
    String[] additional_images;
    int tour_duration, tour_rate, points;
    static AppCompatActivity activity;

    ArrayList<Tours> mList = new ArrayList<Tours>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mList.clear();
//        mList.add(new PopularDestinations(R.drawable.image1, "SANOVA BEACH", "A place where jerks used to hang out"));
//        mList.add(new PopularDestinations(R.drawable.image2, "YUARA POOL", "A paradise for all stupid person"));
        JSONParser parser = new JSONParser();
        JSONArray tours = parser.getJSONFromUrl("http://guia.herokuapp.com/api/v1/tours");

        for(int i = 0; i < tours.length(); i++){
            try {
                JSONObject obj = tours.getJSONObject(i);

                tour_id = obj.getString("_id");
                tour_name = obj.getString("name");
                tour_location = obj.getString("tour_location");
                tour_duration = obj.getInt("duration");
                duration_format = obj.getString("duration_format");
                tour_description = obj.getString("details");
                tour_preference = obj.getString("tour_preference");
                tour_guideId = obj.getString("tour_guide_id");
                tour_rate = obj.getInt("rate");
                main_image = obj.getString("main_image");
                points = obj.getInt("points");

                mList.add(new Tours(tour_id, tour_name, tour_location, tour_description, duration_format,
                        tour_preference, tour_guideId, tour_rate, main_image, tour_duration, null, points));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

        activity = (AppCompatActivity) view.getContext();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.cardList);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        RVadapter adapter = new RVadapter(getActivity().getApplicationContext(),mList, null, null, null);
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

    public static void onCardClick(Tours tour){
        LoggedInTraveler.addedFrag = true;
        FragmentBookingRequest fbr = new FragmentBookingRequest(tour);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.drawer_fragment_container, fbr).addToBackStack(null).commit();
    }
}
