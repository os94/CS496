package com.example.q.maptest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MaplistActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    private String TAG = MaplistActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private static String get = "http://52.231.69.217:8080/mapget";
    ArrayList<HashMap<String,String>> maplist;
    private MapView mapView;
    private GoogleMap map;
    private static final LatLng DEFAULT_LOCATION= new LatLng(36.374259, 127.365544);
    private HashMap<String,LatLng> mapPoints = new HashMap<>();
    //private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maplist);

        maplist = new ArrayList<>();
        ArrayList<HashMap<String, String>> mapList;
        Bundle MapViewBundle = null;
        if (savedInstanceState != null) MapViewBundle = savedInstanceState.getBundle(TAG);//액티비티의 상태가 저장되어 있는 경우
        mapView = (MapView) findViewById(R.id.maplist);
        mapView.onCreate(MapViewBundle);

        Intent i = getIntent();
        ListView listView = (ListView) findViewById(R.id.listviewMap);
        lv = (ListView) findViewById(R.id.listviewMap);

        new GetMaps().execute();

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                setMapPoints();
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 10));
            }
        });

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    private class GetMaps extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MaplistActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(get);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray maps = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int i = 0; i < maps.length(); i++) {
                        JSONObject c = maps.getJSONObject(i);

                        String id = c.getString("_id");
                        String place = c.getString("place");
                        String placeadd = c.getString("placeadd");
                        String lat = c.getString("lat");
                        String lng = c.getString("lng");

                        // tmp hash map for single contact
                        HashMap<String, String> map = new HashMap<>();
                        // adding each child node to HashMap key => value
                        map.put("_id", id);
                        map.put("place", place);
                        map.put("placeadd", placeadd);
                        // adding contact to contact list
                        maplist.add(map);

                        LatLng point = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                        mapPoints.put(place, point);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MaplistActivity.this, maplist,
                    R.layout.map_items_listview, new String[]{"place", "placeadd"}, new int[]{R.id.n,
                    R.id.number});
            lv.setAdapter(adapter);
        }
    }

    private void setMapPoints(){
        Iterator<String> pointNames = mapPoints.keySet().iterator();
        while (pointNames.hasNext()) {
            String key = pointNames.next();
            LatLng value = mapPoints.get(key);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(value);
            markerOptions.title(key);
            map.addMarker(markerOptions);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onStart(){
        super.onStart();
        mapView.onStart();
        updateNavigationBarState();
    }
    @Override
    protected void onStop(){
        super.onStop();
        mapView.onStart();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_contact) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (itemId == R.id.navigation_photos) {
                startActivity(new Intent(this, MainActivity2.class));
            } else if (itemId == R.id.navigation_map) {
                startActivity(new Intent(this, MainActivity3.class));
            }
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    //@Override
    int getContentViewId() {
        return R.layout.activity_map;
    }

    //@Override
    int getNavigationMenuItemId() {
        return R.id.navigation_map;
    }

}
