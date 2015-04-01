package com.modsoussi.niupiaoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapters.CountryListAdapter;
import models.Country;

public class CountryListActivity extends ActionBarActivity{

    private ListView cList;
    private ArrayList<Country> countries = new ArrayList<Country>();
    private final Context context = this;
    private CountryListAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        // Setting action bar title
        getSupportActionBar().setTitle("Country List");

        // Sending GET request to wikipedia
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://en.wikipedia.org/wiki/List_of_countries_and_dependencies_by_area",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String data){
                        // Setting up ListView
                        String cName = "";
                        int dataLength = data.length();
                        int i = 0;
                        int start = data.indexOf("flagicon");
                        while(i<249){
                            start = data.indexOf("title",start);
                            cName = getTitle(data,start);
                            countries.add(new Country(cName,i++,getLandmass(data,start)));
                            start = data.indexOf("flagicon",start);
                        }
                        cList = (ListView)findViewById(R.id.country_list);
                        cAdapter = new CountryListAdapter(context, countries);
                        cList.setAdapter(cAdapter);
                    }

                    private String getTitle(String s, int i){
                        return s.substring(s.indexOf("\"",i)+1,s.indexOf("\"", i + "title=".length()+1));
                    }

                    private float getLandmass(String s, int i){
                        String brToken = "<br />";
                        String spanToken = "</span>";
                        int sIndex = s.indexOf(spanToken,s.indexOf(brToken, i)+ brToken.length())+ spanToken.length();
                        String lMass = s.substring(sIndex,s.indexOf(brToken,sIndex)).replace(",","");
                        if(!lMass.contains("N/A") && !lMass.contains("NA") && !lMass.contains("<tr")){
                            return Float.valueOf(lMass);
                        } else {
                            return 0;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        queue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_country_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
            Collections.sort(countries);
            new AlertDialog.Builder(this)
                    .setTitle("Sort")
                    .setMessage("Choose sort ordering")
                    .setPositiveButton("Ascending",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cAdapter = new CountryListAdapter(context,countries);
                            cList = (ListView)findViewById(R.id.country_list);
                            cList.setAdapter(cAdapter);
                        }
                    })
                    .setNegativeButton("Descending", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Collections.reverse(countries);
                            cList = (ListView)findViewById(R.id.country_list);
                            cList.setAdapter(cAdapter);
                        }
                    })
                    .create()
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onResume(){
        super.onResume();
    }
}
