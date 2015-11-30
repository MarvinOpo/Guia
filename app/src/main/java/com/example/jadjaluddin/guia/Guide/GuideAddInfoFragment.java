package com.example.jadjaluddin.guia.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jadjaluddin.guia.Helper.DBHelper;
import com.example.jadjaluddin.guia.Helper.JSONParser;
import com.example.jadjaluddin.guia.MainActivity;
import com.example.jadjaluddin.guia.R;
import com.example.jadjaluddin.guia.RegisterActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class GuideAddInfoFragment extends Fragment {
    EditText txtContact, txtEmail;
    Spinner spnrLocation;
    Button btnNext, btnBack;
    static String location, contact, email;
    FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_registration, container, false);

        spnrLocation = (Spinner) view.findViewById(R.id.spnrLocation);
        txtContact = (EditText) view.findViewById(R.id.txtContact);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        btnNext = (Button) view.findViewById(R.id.guide1_next);
        btnBack = (Button) view.findViewById(R.id.guide1_back);

        JSONParser parser = new JSONParser();
        JSONArray jArray = parser.getJSONFromUrl("http://10.0.0.13:8080/api/v1/locations");
        //Toast.makeText(getActivity().getApplicationContext(), jArray.toString(), Toast.LENGTH_LONG).show();
        String[] location_list = new String[jArray.length()];

        try {
            for(int i = 0; i<jArray.length(); i++){
                JSONObject obj = jArray.getJSONObject(i);
                //Toast.makeText(getActivity().getApplicationContext(), obj.toString(), Toast.LENGTH_SHORT).show();
                location_list[i] = obj.getString("city") + ", " + obj.getString("country");
            }
        }catch (JSONException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item,location_list);
        spnrLocation.setAdapter(adapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = spnrLocation.getSelectedItem().toString();
                //location = "Cebu, Philippines";
                contact = txtContact.getText().toString();
                email = txtEmail.getText().toString();

                if (contact.equals("")) txtContact.setError("Required!");
                else if (email.equals("")) txtEmail.setError("Required!");
                else {
                    DBHelper db = new DBHelper(getActivity().getApplicationContext());
                    db.updSetting(RegisterActivity.fb_id, 0,"isTraveler");

                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("city", "Cebu"));
                    params.add(new BasicNameValuePair("country", "Philippines"));
                    params.add(new BasicNameValuePair("contact_number", contact));
                    params.add(new BasicNameValuePair("email_address", email));
                    params.add(new BasicNameValuePair("type", "Arts"));

                    JSONParser parser = new JSONParser();
                    JSONObject obj = parser.makeHttpRequest("http://10.0.0.13:8080/api/v1/guide", "POST", params);

                    Intent intent = new Intent(getActivity().getApplicationContext(), LoggedInGuide.class);
                    intent.putExtra("fb_id", RegisterActivity.fb_id);
                    intent.putExtra("guide_id", RegisterActivity.guide_id);
                    intent.putExtra("name", RegisterActivity.name);
                    intent.putExtra("bday", RegisterActivity.bday);
                    intent.putExtra("gender", RegisterActivity.gender);
                    intent.putExtra("age", RegisterActivity.age);
                    intent.putExtra("image", RegisterActivity.image);
                    intent.putExtra("location", location);
                    intent.putExtra("contact", contact);
                    intent.putExtra("email", email);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RegisterActivity.def == 0){
                    MainActivity.manager.logOut();
                    getActivity().finish();
                }
                else getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        return view;
    }
}
