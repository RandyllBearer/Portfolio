package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by colton on 3/25/18.
 */

public class PetInfo {
    protected String name;
    protected String breed;
    protected String type;
    protected double distance;
    protected int photoId;
    protected String time;
    protected String comments;
    protected String color;
    protected boolean friendly;
    protected double latitude;
    protected double longitude;
    protected boolean posting;
    protected double reward;

    public ArrayList<String> getSearchTerms() {
        ArrayList<String> terms = new ArrayList<String>();
        if (name != null && !name.isEmpty()) {
            terms.add(name.toLowerCase());
        }
        if (breed != null && !breed.isEmpty()) {
            String[] breedSplit = breed.split(" ");
            for (int i = 0; i < breedSplit.length; i++) {
                terms.add(breedSplit[i].toLowerCase());
            }

        }
        if (type != null && !type.isEmpty()) {
            terms.add(type.toLowerCase());
        }
        if (color != null & !color.isEmpty()) {
            String[] colors = color.split("/");
            for (int i = 0; i < colors.length; i++) {
                terms.add(colors[i].toLowerCase());
            }
        }
        return terms;
    }
}
