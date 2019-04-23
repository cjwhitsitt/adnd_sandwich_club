package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject root = new JSONObject(json);

            JSONObject name = root.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray akaJSON = name.getJSONArray("alsoKnownAs");
            ArrayList<String> akaList = new ArrayList<String>(akaJSON.length());
            for (int i = 0; i < akaJSON.length(); i++) {
                akaList.add(akaJSON.getString(i));
            }

            String origin = root.getString("placeOfOrigin");
            String description = root.getString("description");
            String imageUrlString = root.getString("image");

            JSONArray ingredientsArray = root.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = new ArrayList<String>(ingredientsArray.length());
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }

            Sandwich sandwich = new Sandwich(
                    mainName,
                    akaList,
                    origin,
                    description,
                    imageUrlString,
                    ingredientsList
            );
            return sandwich;

        } catch (JSONException e) {
            return null;
        }
    }
}
