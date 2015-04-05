package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class LocalTutorActivity extends Activity {

    private User user;
    private List<User> users;
    ExpandableListAdapterTutor listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_tutor);
        Gson gson = new Gson();
        Intent old_intent = getIntent();
        user = gson.fromJson(old_intent.getStringExtra("User"), User.class);
        Receiver receiver = new Receiver();
        receiver.execute();

        // get the listview
        //expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        //prepareListData();

        //listAdapter = new ExpandableListAdapterTutor(this, listDataHeader, listDataChild);

        // setting list adapter
        //expListView.setAdapter(listAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_local_tutor, menu);
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

    private class Receiver extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost("https://frozen-tor-9289.herokuapp.com/all_users");

            try {
                Gson gson = new Gson();
                StringEntity se = new StringEntity(gson.toJson(user));
                se.setContentType("application/json;charset=UTF-8");
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
                httppostreq.setEntity(se);
                HttpResponse httpresponse = httpclient.execute(httppostreq);
                System.err.println(httpresponse.getStatusLine().getStatusCode());
                if (httpresponse.getStatusLine().getStatusCode() == 200) {
                    InputStream content = httpresponse.getEntity().getContent();
                    Reader reader = new InputStreamReader(content);
                    users = Arrays.asList(gson.fromJson(reader, User[].class));

                    expListView = (ExpandableListView) findViewById(R.id.lvExp);
                    prepareListData();
                    listAdapter = new ExpandableListAdapterTutor(getApplicationContext(), listDataHeader, listDataChild);

                } else {
                    Toast.makeText(getApplicationContext(), "Couldn't Find Tutors", Toast.LENGTH_SHORT).show();
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
        @Override
        protected void onPostExecute(String result) {
            ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header_tutor, expListView, false);
            expListView.addHeaderView(headerView);
            expListView.setAdapter(listAdapter);
        }
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        List<ArrayList<String>> group = new ArrayList<ArrayList<String>>();

        System.err.println(users);
        System.err.println(users.size());
        for (User u : users) {
            listDataHeader.add(u.getName() + " Rating: " + Float.toString(u.getTutor_rating()) + " Price: " + Float.toString(u.getTutor_price())+ "$/hr");
            group.add(new ArrayList<String>());
        }
        // Adding child data
        /*listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");*/
        for (int i = 0; i < group.size();i++) {
            group.get(i).add(users.get(i).getBio());
            listDataChild.put(listDataHeader.get(i), group.get(i));
        }

        // Adding child data
        /*List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");*/

        //listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        //listDataChild.put(listDataHeader.get(1), nowShowing);
        //listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}
