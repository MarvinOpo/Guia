package com.example.jadjaluddin.guia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jadjaluddin.guia.Guide.LoggedInGuide;
import com.example.jadjaluddin.guia.Helper.DBHelper;
import com.example.jadjaluddin.guia.Helper.JSONParser;
import com.example.jadjaluddin.guia.Traveler.LoggedInTraveler;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    public static LoginButton loginButton;
    public static boolean end = false;
    public static LoginManager manager;
    String fb_id, image, name, bday, gender, age;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        Cursor c = db.getFilter();
        if(!c.moveToFirst()) db.defaultFilter();

        loginButton = (LoginButton) findViewById(R.id.authButton);
        loginButton.setReadPermissions(Arrays.asList("user_birthday", "email", "user_location"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login attempt cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(MainActivity.this, "Login attempt failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestData(AccessToken token){
        final ProgressDialog pd = ProgressDialog.show(this, "Loading", "Please wait...", true, false);
        GraphRequest request = GraphRequest.newMeRequest(token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        manager = LoginManager.getInstance();
                        //Toast.makeText(MainActivity.this, "JSON: "+object, Toast.LENGTH_LONG).show();
                        try {

                            //if(user==null) {
                            fb_id = object.getString("id");
                            JSONObject pic = object.getJSONObject("picture");
                            JSONObject data = pic.getJSONObject("data");
                            //Toast.makeText(MainActivity.this, "awa ari nisud", Toast.LENGTH_LONG).show();
                            image = data.getString("url");
                            name = object.getString("name");
                            bday = object.getString("birthday");
                            gender = object.getString("gender");

                            JSONObject age_range = object.getJSONObject("age_range");

                            try {
                                age = age_range.getString("max");
                            }
                            catch (Exception e){
                                age = age_range.getString("min");
                            }

                            //setting value for request
                            List<NameValuePair> params = new ArrayList<>();
                            params.add(new BasicNameValuePair("facebook_id", fb_id));
                            params.add(new BasicNameValuePair("name", name));
                            params.add(new BasicNameValuePair("birthday", bday));
                            params.add(new BasicNameValuePair("age", age));
                            params.add(new BasicNameValuePair("gender", gender));
                            params.add(new BasicNameValuePair("profImage", image));

                            JSONParser parser = new JSONParser();
                            JSONObject obj = parser.makeHttpRequest("http://guia.herokuapp.com/api/v1/login", "POST", params);

                            String user_id = obj.getString("_id");
                            String guide_id = obj.getString("guide_id");

                            Cursor c = db.getSettingById(fb_id);
                            if(!c.moveToFirst()) {
                                db.addSetting(fb_id);
                                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                                intent.putExtra("fb_id", fb_id);
                                intent.putExtra("name", name);
                                intent.putExtra("bday", bday);
                                intent.putExtra("gender", gender);
                                intent.putExtra("age", age);
                                intent.putExtra("image", image);
                                intent.putExtra("default", 1);
                                intent.putExtra("guide_id", guide_id);
                                intent.putExtra("user_id", user_id);
                                MainActivity.this.startActivity(intent);

                            }
                            else{
                                //Toast.makeText(getApplicationContext(), String.valueOf(c.getInt(c.getColumnIndex("isTraveler"))), Toast.LENGTH_LONG).show();
                               if(c.getInt(c.getColumnIndex("isTraveler")) == 1){
                                   Intent intent = new Intent(getApplicationContext(), LoggedInTraveler.class);
                                   intent.putExtra("fb_id", fb_id);
                                   intent.putExtra("name", name);
                                   intent.putExtra("bday", bday);
                                   intent.putExtra("gender", gender);
                                   intent.putExtra("age", age);
                                   intent.putExtra("image", image);
                                   intent.putExtra("user_id", user_id);
                                   MainActivity.this.startActivity(intent);
                               }
                               else {
                                   //Toast.makeText(getApplicationContext(), obj.toString(), Toast.LENGTH_LONG).show();

                                   if(guide_id.equals("")){
                                       Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                                       intent.putExtra("fb_id", fb_id);
                                       intent.putExtra("guide_id", guide_id);
                                       intent.putExtra("name", name);
                                       intent.putExtra("bday", bday);
                                       intent.putExtra("gender", gender);
                                       intent.putExtra("age", age);
                                       intent.putExtra("image", image);
                                       intent.putExtra("default", 0);
                                       intent.putExtra("user_id", user_id);
                                       MainActivity.this.startActivity(intent);
                                   }
                                   else{
                                       JSONObject guide = parser.makeHttpRequest("http://guia.herokuapp.com/api/v1/guide/" + guide_id, "GET", null);
                                       //Toast.makeText(getApplicationContext(), "Naa ta diri "+guide_id, Toast.LENGTH_LONG).show();

                                       String contact = guide.getString("contact_number");
                                       String email = guide.getString("email_address");
                                       String location = guide.getString("city")+", "+
                                               guide.getString("country");

                                       Intent intent = new Intent(getApplicationContext(), LoggedInGuide.class);
                                       intent.putExtra("fb_id", fb_id);
                                       intent.putExtra("name", name);
                                       intent.putExtra("bday", bday);
                                       intent.putExtra("gender", gender);
                                       intent.putExtra("age", age);
                                       intent.putExtra("image", image);
                                       intent.putExtra("location", location);
                                       intent.putExtra("contact", contact);
                                       intent.putExtra("email", email);
                                       intent.putExtra("guide_id", guide_id);
                                       MainActivity.this.startActivity(intent);
                                   }
                               }
                            }

                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Wala "+e.toString(),Toast.LENGTH_LONG).show();
                        }
                        pd.dismiss();
                        //MainActivity.this.finish();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,picture,name,birthday,gender,age_range");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (end) {
            end = false;
            this.finish();
        }
        else if(AccessToken.getCurrentAccessToken()!=null){
            requestData(AccessToken.getCurrentAccessToken());
        }
        else{}

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}