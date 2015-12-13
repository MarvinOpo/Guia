package com.example.jadjaluddin.guia.Navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jadjaluddin.guia.Guide.LoggedInGuide;
import com.example.jadjaluddin.guia.Helper.JSONParser;
import com.example.jadjaluddin.guia.Helper.RVadapter;
import com.example.jadjaluddin.guia.Model.PendingRequest;
import com.example.jadjaluddin.guia.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment {

    ArrayList<PendingRequest> mList = new ArrayList<PendingRequest>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList.clear();
        if(!LoggedInGuide.guide_id.equals("")) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("booking_guide_id", LoggedInGuide.guide_id));

            JSONParser parser = new JSONParser();
            JSONArray objArray = parser.makeRequestArray("http://guia.herokuapp.com/api/v1/bookings", "POST", params);

            for(int i = 0; i < objArray.length(); i++){
                try {
                    JSONObject req = objArray.getJSONObject(i);
                    if(req.getString("status").equals("pending")) {
                        String booking_id = req.getString("_id");
                        String tour_id = req.getString("booking_tour_id");
                        String user_id = req.getString("booking_user_id");
                        String date = req.getString("schedule");
                        JSONObject tour = parser.getJSONObjectFromUrl("http://guia.herokuapp.com/api/v1/tour/" + tour_id);
                        String tour_name = tour.getString("name");

                        JSONObject user = parser.getJSONObjectFromUrl("http://guia.herokuapp.com/api/v1/user/" + user_id);
                        String user_name = user.getString("name");
                        String user_gender = user.getString("gender");
                        String user_age = user.getString("age");

                        mList.add(new PendingRequest(user_name, user_age, user_gender, tour_name, date, booking_id));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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

        RVadapter adapter = new RVadapter(getActivity().getApplicationContext(), null, null, null, mList);
        rv.setAdapter(adapter);
        return view;
    }
}
