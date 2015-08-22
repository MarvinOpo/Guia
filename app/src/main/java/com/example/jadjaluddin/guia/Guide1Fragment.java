package com.example.jadjaluddin.guia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jadjaluddin on 8/12/2015.
 */
public class Guide1Fragment extends Fragment {
    EditText txtFname, txtLname;
    Spinner month, day, year;
    ArrayAdapter<String> adapter1, adapter2, adapter3;
    RadioButton rbMale, rbFemale;
    Button btnNext, btnBack;
    FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide1, container, false);

        ArrayList<String> month_list = new ArrayList<String>(), day_list = new ArrayList<String>(), year_list = new ArrayList<String>();

        txtFname = (EditText) view.findViewById(R.id.txtFirstName);
        txtLname = (EditText) view.findViewById(R.id.txtLastName);
        month = (Spinner) view.findViewById(R.id.spnr_month);
        day = (Spinner) view.findViewById(R.id.spnr_day);
        year = (Spinner) view.findViewById(R.id.spnr_year);

        month_list.add("Jan"); month_list.add("Feb"); month_list.add("Mar");
        month_list.add("Apr"); month_list.add("May"); month_list.add("Jun");
        month_list.add("Jul"); month_list.add("Aug"); month_list.add("Sep");
        month_list.add("Oct"); month_list.add("Nov"); month_list.add("Dec");

        for (int i = 1; i <= 31; i++){day_list.add(String.valueOf(i));}
        for (int i = 1995; i >= 1900; i--){year_list.add(String.valueOf(i));}

        adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, month_list);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter1);

        adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, day_list);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter2);

        adapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, year_list);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter3);

        btnNext = (Button) view.findViewById(R.id.guide1_next);
        btnBack = (Button) view.findViewById(R.id.guide1_back);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtFname.getText().toString().equals("")) txtFname.setError("Required");
                else if (txtLname.getText().toString().equals("")) txtLname.setError("Required");
                else {
                    Guide2Fragment g2f = new Guide2Fragment();
                    ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.register_frag_container, g2f).addToBackStack(null).commit();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        return view;
    }
}
