package com.example.jadjaluddin.guia.Traveler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jadjaluddin.guia.Helper.ImageLoadTask;
import com.example.jadjaluddin.guia.Helper.JSONParser;
import com.example.jadjaluddin.guia.Model.Tours;
import com.example.jadjaluddin.guia.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentBookingRequest extends Fragment{

    Tours tour;
    ImageView iv;
    TextView title, description, rate, points, duration, guide;

    public FragmentBookingRequest() {}

    public FragmentBookingRequest(Tours tour) {
        this.tour = tour;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_detials, container, false);
        iv = (ImageView) view.findViewById(R.id.detail_image);
        title = (TextView) view.findViewById(R.id.detail_title);
        description = (TextView) view.findViewById(R.id.detail_description);
        rate = (TextView) view.findViewById(R.id.detail_rate);
        points = (TextView) view.findViewById(R.id.detail_points);
        duration = (TextView) view.findViewById(R.id.detail_duration);
        guide = (TextView) view.findViewById(R.id.detail_guide);

        new ImageLoadTask(tour.main_image, iv);
        title.setText(tour.tour_name);
        description.setText(tour.tour_description);
        rate.setText("Rate: "+tour.tour_rate);
        points.setText("Points Reward: "+tour.points);
        duration.setText("Duration: "+tour.tour_duration+" "+tour.duration_format);

        Button btnBook = (Button) view.findViewById(R.id.detail_book);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("booking_guide_id", tour.tour_guideId));
                params.add(new BasicNameValuePair("booking_tour_id", tour.tour_id));
                params.add(new BasicNameValuePair("booking_user_id", LoggedInTraveler.user_id));
                params.add(new BasicNameValuePair("schedule", "2015-12-12"));

                JSONParser parser = new JSONParser();
                JSONObject obj = parser.makeHttpRequest("http://guia.herokuapp.com/api/v1/book", "POST", params);
                Toast.makeText(getActivity().getApplicationContext(), "CLicked!"+LoggedInTraveler.user_id, Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
