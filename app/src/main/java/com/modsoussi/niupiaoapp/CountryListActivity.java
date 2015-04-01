package com.modsoussi.niupiaoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private ListView cList; // ListView where countries are going to be displayed
    private ArrayList<Country> countries = new ArrayList<Country>(); // countries list
    private final Context context = this;
    private CountryListAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        // Setting action bar title
        getSupportActionBar().setTitle("Country List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Sending GET request to wikipedia
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://en.wikipedia.org/wiki/List_of_countries_and_dependencies_by_area",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String data){
                        // Setting up ListView
                        int i = 0;
                        int start = data.indexOf("flagicon");
                        while(i<257){
                            start = data.indexOf("title",start);
                            String cName = getTitle(data,start);
                            countries.add(new Country(cName,i++,getLandmass(data,start)));
                            start = data.indexOf("flagicon",start);
                        }
                        cList = (ListView)findViewById(R.id.country_list);
                        cAdapter = new CountryListAdapter(context, countries);
                        cList.setAdapter(cAdapter);
                    }

                    // gets country from title attribute
                    private String getTitle(String s, int i){
                        return s.substring(s.indexOf("\"",i)+1,s.indexOf("\"", i + "title=".length()+1));
                    }

                    // gets landmass. I tried to use Jsoup for this but it wasn't parsing correctly, so I decided
                    // to go through it myself. This method finds the first <br /> tag after country name position,
                    // then it finds the first </span> after that, and pulls the number that comes after that </span>.
                    // this is always guaranteed to get the second number in a row which is always the landmass.
                    private float getLandmass(String s, int i){
                        String brToken = "<br />";
                        String spanToken = "</span>";
                        int sIndex = s.indexOf(spanToken,s.indexOf(brToken, i)+ brToken.length())+ spanToken.length();
                        String lMass = s.substring(sIndex,s.indexOf(brToken,sIndex)).replace(",","");
                        if(lMass.contains("&lt;")) {
                            // if lMass has a less than symbol, return the digit after the symbol
                            return Float.valueOf(lMass.substring(lMass.indexOf("&")+4));
                        } else if(!lMass.contains("N/A") && !lMass.contains("NA") && !lMass.contains("<tr")){
                            // to make sure lMass has floats only
                            return Float.valueOf(lMass);
                        } else {
                            // in case landmass is not provided
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

        if (id == R.id.action_sort) {
            new AlertDialog.Builder(this)
                    .setTitle("Sort List")
                    .setMessage("Choose the ordering:")
                    .setPositiveButton("Ascending",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Collections.sort(countries); // sorts in ascending order
                            cAdapter = new CountryListAdapter(context,countries);
                            cList = (ListView)findViewById(R.id.country_list);
                            cList.setAdapter(cAdapter);
                        }
                    })
                    .setNegativeButton("Descending", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Collections.sort(countries); // sorts in ascending order
                            Collections.reverse(countries); // reverses ordering
                            cList = (ListView)findViewById(R.id.country_list);
                            cList.setAdapter(cAdapter);
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // don't do anything
                            return;
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

    public void toScreenThree(View view){
        Intent intent = new Intent(this,GeneticCodeActivity.class);
        startActivity(intent);
    }
}
