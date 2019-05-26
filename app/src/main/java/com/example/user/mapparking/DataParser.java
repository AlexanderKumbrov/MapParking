package com.example.user.mapparking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String ,String> getSinglePlaces (JSONObject placeJson){
        HashMap<String , String>placeMap = new HashMap<>();
        String nameOfPLace = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!placeJson.isNull("name")){
                nameOfPLace = placeJson.getString("name");
            }
            if (!placeJson.isNull("vicinity")){
                vicinity = placeJson.getString("vicinity");
            }
            latitude = placeJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = placeJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = placeJson.getString("reference");

            placeMap.put("place_name", nameOfPLace);
            placeMap.put("vicinity", vicinity);
            placeMap.put("lat", latitude);
            placeMap.put("lng", longitude);
            placeMap.put("reference", reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placeMap;
    }
    private List<HashMap<String , String>> getAllPlaces(JSONArray jsonArray){
        int counter = jsonArray.length();
        List<HashMap<String , String>> placesList = new ArrayList<>();
        HashMap<String , String>placeMap = null;
        for (int i = 0 ; i < counter ; i++){
            try {
                placeMap = getSinglePlaces((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }
    public List<HashMap<String , String>> parse(String JSONData){
        JSONArray jsonArray = null;
        JSONObject  jsonObject;
        try {
            jsonObject = new JSONObject(JSONData);
            jsonArray = jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllPlaces(jsonArray);
    }
}