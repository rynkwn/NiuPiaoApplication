package models;

import java.util.Random;

/**
 * Created by modsoussi on 4/1/2015.
 */
public class Creature {

    private String dna;
    private String[] comboArray = {"AA","BA","CA","DA","BD","ACA"};

    public Creature(String dna){
        this.dna = dna;
    }

    public String getDna(){
        return dna;
    }

    public String randomHalfDna(){
        String pDna = getDna();
        Random random = new Random();
        String cDna = "";
        while(cDna.length() < (pDna.length()/2)){
            int i = random.nextInt(pDna.length());
            cDna+= pDna.charAt(i);
        }
        return cDna;
    }

    public int getPoints(){
        int points = 0;

        // i opted for this implementation instead of having a loop to make counting points easier
        int start = 0;
        while(dna.indexOf(comboArray[0],start)!= -1){
            points+=2;
            start = dna.indexOf(comboArray[0],start) + 1;
        }

        start = 0;
        while(dna.indexOf(comboArray[1],start)!= -1){
            points+=3;
            start = dna.indexOf(comboArray[1],start) + 1;
        }

        start = 0;
        while(dna.indexOf(comboArray[2],start)!= -1){
            points+=3;
            start = dna.indexOf(comboArray[2],start)+1;
        }

        start = 0;
        while(dna.indexOf(comboArray[3],start)!= -1){
            points+=2;
            start = dna.indexOf(comboArray[3],start)+1;
        }

        start = 0;
        while(dna.indexOf(comboArray[4],start)!= -1){
            points+=4;
            start = dna.indexOf(comboArray[4],start)+1;
        }

        start = 0;
        while(dna.indexOf(comboArray[5],start)!= -1){
            points+=6;
            start = dna.indexOf(comboArray[5],start)+2;
        }

        return points;
    }
}
