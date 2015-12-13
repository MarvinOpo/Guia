package com.example.jadjaluddin.guia.Traveler;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jadjaluddin.guia.Guide.GuideCalendarFragment;
import com.example.jadjaluddin.guia.Helper.DrawerAdapter;
import com.example.jadjaluddin.guia.Helper.ImageLoadTask;
import com.example.jadjaluddin.guia.MainActivity;
import com.example.jadjaluddin.guia.Model.DrawerItem;
import com.example.jadjaluddin.guia.Navigation.FilterFragment;
import com.example.jadjaluddin.guia.Navigation.HomeFragment;
import com.example.jadjaluddin.guia.Navigation.MessageFragment;
import com.example.jadjaluddin.guia.Navigation.SettingFragment;
import com.example.jadjaluddin.guia.Navigation.TripFragment;
import com.example.jadjaluddin.guia.R;

/**
 * Created by Stephanie Lyn on 8/13/2015.
 */
public class LoggedInTraveler extends AppCompatActivity {

    public static boolean doubleBackToExitPressedOnce = false;
    public static boolean addedFrag = false;
    public static Toolbar mToolbar;
    DrawerLayout mDrawer;
    ListView mDrawerList;
    ActionBarDrawerToggle mToggle;
    String[] titles = {"Home", "Scheduled Tours", "Messages", "Settings", "Logout"};
    int[] icons = {R.drawable.home,
                R.drawable.sched_tours,
                R.drawable.messages,
                R.drawable.settings,
                R.drawable.logout};
    public static String name, bday, gender, age, image, fb_id, user_id;
    FragmentTransaction ft;
    HomeFragment hf = new HomeFragment();
    TripFragment tf = new TripFragment();
    SettingFragment sf = new SettingFragment();
    MessageFragment mf = new MessageFragment();
    FilterFragment ff = new FilterFragment();
    GuideCalendarFragment gcf = new GuideCalendarFragment();
    FragmentBookingRequest fcg = new FragmentBookingRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        try{
            Bundle b = this.getIntent().getExtras();
            fb_id = b.getString("fb_id");
            name = b.getString("name");
            bday = b.getString("bday");
            gender = b.getString("gender");
            age = b.getString("age");
            image = b.getString("image");
            user_id = b.getString("user_id");
        }
        catch(Exception e){}

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.guide_drawer);
        mDrawerList = (ListView) findViewById(R.id.guide_list_drawer);

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.drawer_fragment_container, hf).commit();

        mDrawerList.setDivider(null);
        mDrawerList.addHeaderView(getView());
        DrawerItem[] items = new DrawerItem[5];
        for(int i = 0; i<items.length; i++){
            items[i] = new DrawerItem(icons[i], titles[i]);
        }

        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.drawer_item, items);
        mDrawerList.setAdapter(adapter);

        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addedFrag = false;
                doubleBackToExitPressedOnce = false;
                switch (position) {
                    case 1:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.drawer_fragment_container, hf).commit();
                        mDrawer.closeDrawers();
                        break;
                    case 2:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.drawer_fragment_container, tf).commit();
                        mDrawer.closeDrawers();
                        break;
                    case 3:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.drawer_fragment_container, mf).commit();
                        mDrawer.closeDrawers();
                        break;
                    case 4:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.drawer_fragment_container, sf).commit();
                        mDrawer.closeDrawers();
                        break;
                    case 5:
                        MainActivity.manager.logOut();
                        LoggedInTraveler.this.finish();
                }
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public View getView(){
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.drawer_header, null, false);

        ImageView profImage = (ImageView) view.findViewById(R.id.profile_image);
        TextView profName = (TextView) view.findViewById(R.id.profile_name);
        TextView profDetail = (TextView) view.findViewById(R.id.profile_email);

        new ImageLoadTask(image, profImage).execute();
        profName.setText(name);
        profDetail.setText(gender.substring(0,1).toUpperCase()+gender.substring(1)+", "+age);

        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoggedInTraveler.this, "Traveler Profile is still in progress!", Toast.LENGTH_SHORT).show();
                mDrawer.closeDrawers();
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        doubleBackToExitPressedOnce = false;

        switch(id){
            case R.id.filter:
                mToolbar.setTitle("Filter");
                addedFrag = true;
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.drawer_fragment_container, ff).addToBackStack(null).commit();
                break;
            case R.id.calendar:
                mToolbar.setTitle("Schedules");
                addedFrag = true;
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.drawer_fragment_container, gcf).addToBackStack(null).commit();
                break;
            case R.id.add_trip:
                mToolbar.setTitle("Schedules");
                addedFrag = true;
                ft = getSupportFragmentManager().beginTransaction();
                //ft.replace(R.id.drawer_fragment_container, fcg).addToBackStack(null).commit();
                break;
            case R.id.done:
                addedFrag = false;
                this.getSupportFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            MainActivity.end = true;
            return;
        }
        if (!addedFrag) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        else {
            addedFrag = false;
            super.onBackPressed();
            return;
        }
    }
}
