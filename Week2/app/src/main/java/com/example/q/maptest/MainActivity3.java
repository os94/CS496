package com.example.q.maptest;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity3 extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    protected BottomNavigationView navigationView;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
    private Button button2;
    private Button button3;
    private EditText editText;
    String place, placeadd, lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        editText = (EditText) findViewById(R.id.editText);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        // 맵 터치 이벤트 구현 //
        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });*/

        // Button_Lists
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MaplistActivity.class);
                startActivity(i);
            }
        });

        // Button_Register
        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Place registered", Toast.LENGTH_SHORT).show();

                String str = editText.getText().toString();
                List<Address> addressList = null;
                try { // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                }
                catch (IOException e) { //e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Can not find", Toast.LENGTH_SHORT).show();
                }

                Log.d("gggggggggggggggg",str);
                String []splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도

                place = str;
                placeadd = address;
                lat = latitude;
                lng = longitude;
                //Toast.makeText(getApplicationContext(), place, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), placeadd, Toast.LENGTH_SHORT).show();

                new PostTask().execute("http://52.231.69.217:8080/mapupload",place,placeadd,lat,lng);
            }
        });

        // Button_Search
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str = editText.getText().toString();
                List<Address> addressList = null;
                try { // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                }
                catch (IOException e) { //e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "can't find", Toast.LENGTH_SHORT).show();
                }


                //Toast.makeText(getApplicationContext(), addressList.get(0).toString(), Toast.LENGTH_LONG).show();
                String []splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                mOptions2.snippet(address);
                mOptions2.position(point);
                // 마커 추가
                mMap.addMarker(mOptions2);
                // 해당 좌표로 화면 줌
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
            }
        });

        // Add a marker in Sydney and move the camera
        LatLng korea = new LatLng(36.3745, 127.364);
        mMap.addMarker(new MarkerOptions().position(korea).title("Marker in korea"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(korea, 15));
    }

    private class PostTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("place",params[1]);
                jsonObject.accumulate("placeadd",params[2]);
                jsonObject.accumulate("lat",params[3]);
                jsonObject.accumulate("lng",params[4]);
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(params[0]);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "text/html");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();
                    OutputStream outStream = con.getOutputStream();

                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    return buffer.toString();
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if(con!=null){
                        con.disconnect();
                    }
                    try{
                        if(reader!=null){
                            reader.close();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onStart(){
        super.onStart();
        updateNavigationBarState();
    }
    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected void onPause(){
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    @Override
    public void onLowMemory(){
        super.onLowMemory();
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
