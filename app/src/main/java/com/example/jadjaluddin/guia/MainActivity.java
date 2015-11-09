package com.example.jadjaluddin.guia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jadjaluddin.guia.Helper.DBHelper;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    public static LoginButton loginButton;
    public static boolean end = false;
    public static LoginManager manager;
    String image, name, bday, gender, age;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        Cursor c = db.getSetting();
        if(!c.moveToFirst()) db.defaultSetting();

        c = db.getFilter();
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

                        manager = LoginManager.getInstance();
                        //Toast.makeText(MainActivity.this, "JSON: "+object, Toast.LENGTH_LONG).show();
                        try {

                            //if(user==null) {
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

                                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("bday", bday);
                                intent.putExtra("gender", gender);
                                intent.putExtra("age", age);
                                intent.putExtra("image", image);
                                MainActivity.this.startActivity(intent);
                                //MainActivity.this.finish();
                                pd.dismiss();
//                            }
//                            else{
//                                if(user.type.equals("traveler")){
//                                    Intent intent = new Intent(MainActivity.this, LoggedInTraveler.class);
//                                    intent.putExtra("name", user.name);
//                                    intent.putExtra("bday", user.bday);
//                                    intent.putExtra("gender", user.gender);
//                                    intent.putExtra("age", user.age);
//                                    intent.putExtra("image", user.image);
//                                    MainActivity.this.startActivity(intent);
//                                    //MainActivity.this.finish();
//                                    pd.dismiss();
//                                }
//                                else{
//                                    Intent intent = new Intent(MainActivity.this, LoggedInGuide.class);
//                                    intent.putExtra("name", user.name);
//                                    intent.putExtra("bday", user.bday);
//                                    intent.putExtra("gender", user.gender);
//                                    intent.putExtra("age", user.age);
//                                    intent.putExtra("image", user.image);
//                                    intent.putExtra("location", "Tawason Mandaue City");
//                                    intent.putExtra("contact", "09232360984");
//                                    intent.putExtra("email", "marvz_opo@yahoo.com");
//                                    MainActivity.this.startActivity(intent);
//                                    pd.dismiss();
//                                }
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture,name,birthday,gender,age_range");
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