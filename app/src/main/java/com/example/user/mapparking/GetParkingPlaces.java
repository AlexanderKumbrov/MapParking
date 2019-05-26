package com.example.user.mapparking;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetParkingPlaces extends AsyncTask<Object , String , String> {
    private String placeData;
    private String url;
    private GoogleMap mMap;
    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String)objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            placeData = downloadUrl.ReadTheUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return placeData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String , String>> placesList = null;
        DataParser dataParser = new DataParser();
        placesList = dataParser.parse(s);
        displayPlaces(placesList);
    }


    private void displayPlaces(List<HashMap<String , String>> placesList){
        for (int i = 0 ; i <placesList.size() ; i ++){

            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String , String> googlePlace = placesList.get(i);
            String namePlace = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            LatLng latLng = new LatLng(lat , lng);
            markerOptions.position(latLng);
            markerOptions.title(namePlace + ":" + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }
    }
}
