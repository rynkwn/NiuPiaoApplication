package com.modsoussi.niupiaoapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import models.Creature;

/*
 * created by modsoussi
 */

public class GeneticCodeActivity extends ActionBarActivity {

    protected String[] bases = {"A","B","C","D"};
    private Creature one;
    private Creature two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genetic_code);

        // setting action bar title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Optimal DNA");

        // clicking create creature 1 creates a creature with a random dna
        findViewById(R.id.create_creature_one)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dna = "";
                        Random random = new Random();
                        for(int i=0;i<50;i++){
                            dna+= bases[random.nextInt(4)];
                        }
                        one = new Creature(dna);

                        // setting text to textview to creature's points, based on its dna
                        TextView textView = (TextView) findViewById(R.id.creature_one_points);
                        textView.setText("Parent 1 Points: "+one.getPoints());
                    }
                });

        // clicking create creature 2 creates a second creature with a random dna
        findViewById(R.id.create_creature_two)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dna = "";
                        Random random = new Random();
                        for(int i=0;i<50;i++){
                            dna+= bases[random.nextInt(4)];
                        }
                        two = new Creature(dna);

                        // setting text to textview to creature's points, based on its dna
                        TextView textView = (TextView) findViewById(R.id.creature_two_points);
                        textView.setText("Parent 2 Points: " + two.getPoints());
                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_genetic_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // creatures reproducing
    public void reproduce(View view){
        Creature child =  new Creature(one.randomHalfDna()+ two.randomHalfDna());
        TextView childTextView = (TextView)findViewById(R.id.child_points);
        childTextView.setText("Child Points: "+child.getPoints());
    }
}
