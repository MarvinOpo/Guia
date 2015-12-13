package com.example.jadjaluddin.guia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jadjaluddin.guia.Guide.GuideAddInfoFragment;
import com.example.jadjaluddin.guia.Guide.LoggedInGuide;
import com.example.jadjaluddin.guia.Helper.DBHelper;
import com.example.jadjaluddin.guia.Helper.JSONParser;
import com.example.jadjaluddin.guia.Traveler.LoggedInTraveler;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class RegisterFragment extends Fragment {
    ImageView mTraveler, mGuide;
    FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mTraveler = (ImageView) view.findViewById(R.id.traveler_logo);
        mGuide = (ImageView) view.findViewById(R.id.guide_logo);

        mTraveler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getActivity().getApplicationContext());
                db.updSetting(RegisterActivity.fb_id, 1, "isTraveler");

                Intent intent = new Intent(getActivity().getApplicationContext(), LoggedInTraveler.class);
                intent.putExtra("fb_id", RegisterActivity.fb_id);
                intent.putExtra("name", RegisterActivity.name);
                intent.putExtra("bday", RegisterActivity.bday);
                intent.putExtra("gender", RegisterActivity.gender);
                intent.putExtra("age", RegisterActivity.age);
                intent.putExtra("image", RegisterActivity.image);
                intent.putExtra("user_id", RegisterActivity.user_id);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        mGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RegisterActivity.guide_id.equals("")) {
                    GuideAddInfoFragment g1f = new GuideAddInfoFragment();
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.new_frag_container, g1f).addToBackStack(null).commit();
                }
                else{
                    try {
                        JSONParser parser = new JSONParser();
                        JSONObject guide = parser.makeHttpRequest("http://guia.herokuapp.com/api/v1/guide/" +
                                RegisterActivity.guide_id, "GET", null);
                        Toast.makeText(getActivity().getApplicationContext(), "Naa ta diri " + RegisterActivity.guide_id, Toast.LENGTH_LONG).show();

                        String contact = guide.getString("contact_number");
                        String email = guide.getString("email_address");
                        String location = guide.getString("city") + ", " +
                                guide.getString("country");

                        Intent intent = new Intent(getActivity().getApplicationContext(), LoggedInGuide.class);
                        intent.putExtra("fb_id", RegisterActivity.fb_id);
                        intent.putExtra("name", RegisterActivity.name);
                        intent.putExtra("bday", RegisterActivity.bday);
                        intent.putExtra("gender", RegisterActivity.gender);
                        intent.putExtra("age", RegisterActivity.age);
                        intent.putExtra("image", RegisterActivity.image);
                        intent.putExtra("location", location);
                        intent.putExtra("contact", contact);
                        intent.putExtra("email", email);
                        intent.putExtra("guide_id", RegisterActivity.guide_id);
                        getActivity().getApplicationContext().startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }
}
