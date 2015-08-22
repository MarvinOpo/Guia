package com.example.jadjaluddin.guia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Stephanie Lyn on 8/13/2015.
 */
public class LoggedInGuide extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout guideDrawer;
    ListView guide_drawerList;
    ActionBarDrawerToggle toggle;
    String[] titles = {"Home", "Scheduled Tours", "Messages", "Settings", "Logout"};
    int[] icons = {R.drawable.home,
                R.drawable.sched_tours,
                R.drawable.messages,
                R.drawable.settings,
                R.drawable.logout};

    FragmentTransaction ft;
    HomeFragment hf = new HomeFragment();
    TripFragment tf = new TripFragment();
    SettingFragment sf = new SettingFragment();
    MessageFragment mf = new MessageFragment();
    FilterFragment ff = new FilterFragment();
    GuideProfileFragment gpf = new GuideProfileFragment();
    GuideCalendarFragment gcf = new GuideCalendarFragment();

    String name = "Edilberto Parrotina Jr.";
    String email = "Kenshii_dhaot@gmail.com";
    int profile = R.drawable.profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_guide);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        guideDrawer = (DrawerLayout) findViewById(R.id.guide_drawer);
        guide_drawerList = (ListView) findViewById(R.id.guide_list_drawer);

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.guide_fragment_container, hf).commit();

        guide_drawerList.setDivider(null);
        guide_drawerList.addHeaderView(getView());
        DrawerItem[] items = new DrawerItem[5];
        for(int i = 0; i<items.length; i++){
            items[i] = new DrawerItem(icons[i], titles[i]);
        }

        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.drawer_item, items);
        guide_drawerList.setAdapter(adapter);

        toggle = new ActionBarDrawerToggle(this, guideDrawer, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        guide_drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        toolbar.setTitle("Guia");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.guide_fragment_container, hf).commit();
                        guideDrawer.closeDrawers();
                        break;
                    case 2:
                        toolbar.setTitle("Scheduled Tour");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.guide_fragment_container, tf).commit();
                        guideDrawer.closeDrawers();
                        break;
                    case 3:
                        toolbar.setTitle("Messages");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.guide_fragment_container, mf).commit();
                        guideDrawer.closeDrawers();
                        break;
                    case 4:
                        toolbar.setTitle("Settings");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.guide_fragment_container, sf).commit();
                        guideDrawer.closeDrawers();
                        break;
                    case 5:
                        Intent intent = new Intent(LoggedInGuide.this, LoginActivity.class);
                        LoggedInGuide.this.startActivity(intent);
                        LoggedInGuide.this.finish();
                }
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public View getView(){
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.drawer_header, null, false);

        ImageView profImage = (ImageView) view.findViewById(R.id.profile_image);
        TextView profName = (TextView) view.findViewById(R.id.profile_name);
        TextView profEmail = (TextView) view.findViewById(R.id.profile_email);

        profImage.setImageResource(profile);
        profName.setText(name);
        profEmail.setText(email);

        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setTitle("Profile");
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.guide_fragment_container, gpf).commit();
                guideDrawer.closeDrawers();
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        switch(id){
            case R.id.filter:
                toolbar.setTitle("Filter");
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.guide_fragment_container, ff).commit();
                break;
            case R.id.calendar:
                toolbar.setTitle("Schedules");
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.guide_fragment_container, gcf).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }
}
