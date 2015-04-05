package com.parse.starter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;


public class SettingsActivity extends Activity {

    private String name;
    private String email;
    private String bio;
    private String password;
    private String cost;
    private boolean tutor;
    private boolean available;
    private SettingsObj settingsobj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUpButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void setUpButton() {
        Button submitButton = (Button) findViewById(R.id.AccountSettingsSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpFields();
                settingsobj = new SettingsObj(email, password, name, bio, tutor, available, Float.parseFloat(cost));
                Submitter submitter = new Submitter();
                submitter.execute();
            }
        });
    }

    public void setUpFields() {

        EditText enter_name = (EditText) findViewById(R.id.AccountSettingsName);
        EditText enter_email = (EditText) findViewById(R.id.AccountSettingsEmail);
        EditText enter_password = (EditText) findViewById(R.id.AccountSettingsPassword);
        EditText enter_bio = (EditText) findViewById(R.id.AccountSettingsBio);
        EditText enter_cost = (EditText) findViewById(R.id.AccountSettingsCost);

        if (enter_name.getText().length() == 0) {
            name = null;
        } else {
            name = enter_name.getText().toString();
        }
        if (enter_password.getText().length() == 0) {
            password = null;
        } else {
            password = enter_password.getText().toString();
        }
        if (enter_bio.getText().length() == 0) {
            bio = null;
        } else {
            bio = enter_bio.getText().toString();
        }
        if (enter_cost.getText().length() == 0) {
            cost = null;
        } else {
            cost = enter_cost.getText().toString();
        }
        email = enter_email.getText().toString();
    }
    public void onTutorSwitchClick(View view) {
        boolean on = ((Switch) view).isChecked();

        if (on) {
            tutor = true;
        } else {
            tutor = false;
        }
    }
    public void onAvailSwitchClick(View view) {
        boolean on = ((Switch) view).isChecked();

        if (on) {
            available = true;
        } else {
            available = false;
        }
    }

    private class Submitter extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost("https://frozen-tor-9289.herokuapp.com/settings");

            try {
                Gson gson = new Gson();
                StringEntity se = new StringEntity(gson.toJson(settingsobj));
                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
                httppostreq.setEntity(se);
                HttpResponse httpresponse = httpclient.execute(httppostreq);
                if (httpresponse.getStatusLine().getStatusCode() == 200) {
                    SettingsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    SettingsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Failed To Save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
