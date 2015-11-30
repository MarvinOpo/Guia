package com.example.jadjaluddin.guia.Guide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jadjaluddin.guia.Helper.JSONParser;
import com.example.jadjaluddin.guia.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kentoy on 11/30/2015.
 */
public class AddItineraryFragment extends Fragment {
    ImageView main_image, add_image;
    EditText title, description, price, duration;
    Spinner duration_format;
    Switch negotiable;
    Button btnNext;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.itinerary_layout1, container, false);

        main_image = (ImageView) view.findViewById(R.id.itinerary_image);
        add_image = (ImageView) view.findViewById(R.id.itinerary_add_image);
        title = (EditText) view.findViewById(R.id.itinerary_title);
        description = (EditText) view.findViewById(R.id.itinerary_title);
        btnNext = (Button) view.findViewById(R.id.itinerary_btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view2 = inflater.inflate(R.layout.itinerary_layout2, null, false);
                price = (EditText) view2.findViewById(R.id.itinerary_price);
                negotiable = (Switch) view2.findViewById(R.id.itinerary_negotiable);
                duration = (EditText) view2.findViewById(R.id.itinerary_duration);
                duration_format = (Spinner) view2.findViewById(R.id.duration_format);

                showAlertDialog(view2);

            }
        });
        return view;
    }

    public void showAlertDialog(final View view2){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_launcher);
        builder.setView(view2);
        builder.setTitle("Pricing");
        builder.setNegativeButton("Back", null);
        builder.setPositiveButton("Done", null);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if EditText is empty disable closing on possitive button
                if (price.getText().toString().trim().isEmpty()) {
                    price.setError("Required");
                } else if (duration.getText().toString().trim().isEmpty()) {
                    duration.setError("Required");
                } else {
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("name", title.getText().toString()));
                    params.add(new BasicNameValuePair("duration", duration.getText().toString()));
                    params.add(new BasicNameValuePair("details", description.getText().toString()));
                    params.add(new BasicNameValuePair("tour_preference", "Arts"));
                    params.add(new BasicNameValuePair("rate", Integer.toString(Integer.parseInt(price.getText().toString()))));
                    params.add(new BasicNameValuePair("tour_guide_id", LoggedInGuide.guide_id));

                    //Toast.makeText(getActivity().getApplicationContext(), LoggedInGuide.guide_id, Toast.LENGTH_LONG).show();
                    JSONParser parser = new JSONParser();
                    JSONObject obj = parser.makeHttpRequest("http://10.0.0.13:8080/api/v1/tour", "POST", params);

                    alertDialog.dismiss();
                }
            }
        });
    }
}
