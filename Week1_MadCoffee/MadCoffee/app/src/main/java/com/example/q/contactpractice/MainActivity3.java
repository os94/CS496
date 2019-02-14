package com.example.q.contactpractice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity3 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener/*, GoogleMap.OnMarkerClickListener*/{

    protected BottomNavigationView navigationView;

    private static final String[] needPermissions = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};


    @Override//for permission check
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionToLocationAccepted = true;

        switch (requestCode){
            case PERMISSION_FOR_TAB3:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionToLocationAccepted = false;
                        break;
                    }
                }
                break;
        }

        if (permissionToLocationAccepted == false){
            //앱 안끝내고 대기
            // finish();
        } else {
            getMyLocation();
        }
    }

    //for showing default map
    private static final String TAG = "MainActivity";
    private static final LatLng DEFAULT_LOCATION= new LatLng(36.374259, 127.365544);
    private static final int DEFAULT_ZOOM = 15;
    private static final long INTERVAL_TIME = 5000;
    private static final long FASTEST_INTERVAL_TIME = 2000;
    static final int PERMISSION_FOR_TAB3 = 333;
    private ListView map_chosen;
    private ArrayList<HashMap<String,String>> priceData;

    private MapView mapView;
    private GoogleMap map;
    private TextView inform_text;
    private Location lastKnownLocation;
    private HashMap<String,LatLng> mapPoints = new HashMap<>();
    private HashMap<String,List<String>> mapPointsPrice = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Log.i("hihihi","here");


        //before showing map, permission check
        for (String permission : needPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, needPermissions, PERMISSION_FOR_TAB3);
            }
        }

        Log.i("hihihi","here");
        //맵뷰 상태정보를 번들에 넣었음
        Bundle MapViewBundle = null;
        Log.i("hihihi","here");
        if (savedInstanceState != null) MapViewBundle = savedInstanceState.getBundle(TAG);//액티비티의 상태가 저장되어 있는 경우
        Log.i("hihihi","here");

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(MapViewBundle);
        inform_text=(TextView) findViewById(R.id.map_inform_text);
        map_chosen=(ListView) findViewById(R.id.map_chosen);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                getMyLocation();
                setMapPoints();
                map.setOnMarkerClickListener(marker -> {
                    inform_text.setText("");
                    priceData=new ArrayList<>();
                    List<String> chosenStr=mapPointsPrice.get(marker.getTitle());
                    for(int i = 0; i<chosenStr.size() ; i++){
                        HashMap<String, String> map = new HashMap<String, String>();
                        List<String> oneMenu = Arrays.asList(chosenStr.get(i).split("--"));
                        String menu= oneMenu.get(0);
                        String price= oneMenu.get(1);
                        map.put("menu", menu);
                        map.put("price", price);
                        priceData.add(map);
                    }

                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                            priceData,
                            android.R.layout.simple_list_item_2,
                            new String[]{"menu", "price"},
                            new int[]{android.R.id.text1, android.R.id.text2});
                    map_chosen.setAdapter(adapter);
                    return false;
                });

            }
        });


        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
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
        super.onStart();
        updateNavigationBarState();
    }
    @Override
    protected void onStop(){
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
        super.onPause();
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
            } else if (itemId == R.id.navigation_alarm) {
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
        return R.layout.activity_alarm;
    }

    //@Override
    int getNavigationMenuItemId() {
        return R.id.navigation_alarm;
    }

    private void getMyLocation() {
        //내 위치 사용 전 권한 확인하기
        if (ActivityCompat.checkSelfPermission(MainActivity3.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity3.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setMyLocationEnabled(true);//내 위치 마커 표시
        map.getUiSettings().setMyLocationButtonEnabled(true);//내 위치로 이동하기 마커 표시시

        //구글 플레이 서비스에 fused location provider
        FusedLocationProviderClient fusedLocationProviderClient
                = new FusedLocationProviderClient(this);

        //내 위치 조회요청 후 성공했는지 실패했는지 Task<Location>으로 반환
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        //성공했으면 실행되는 이벤트리스너
        task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {
                //전역변수에 지금 위치값 넣고
                lastKnownLocation = location;


                //지도 내 위치로 이동
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()),DEFAULT_ZOOM));
                //내 위치 자동으로 갱신하기->걸어가면 자동으로 위치 이동
                updateMyLocation();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                //다시 위치 권한 물어보기
                if (ActivityCompat.checkSelfPermission(MainActivity3.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(MainActivity3.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                //기본 위치 N1으로 돌아오기
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM));
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
            }
        });
    }

    private void updateMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL_TIME);
        locationRequest.setFastestInterval(FASTEST_INTERVAL_TIME);

        FusedLocationProviderClient fusedLocationProviderClient
                = new FusedLocationProviderClient(this);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        Location location = locationResult.getLastLocation();

                        map.moveCamera(CameraUpdateFactory.newLatLng(
                                new LatLng(location.getLatitude(), location.getLongitude())));
                    }
                }, null);
    }





    private void setMapPoints(){
        //카페모양 아이콘 지정, 크기 조절
        Bitmap bitmapdraw=(Bitmap) BitmapFactory.decodeResource(getResources(), R.drawable.ic_cafe);
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmapdraw, 60, 60, true);

        //맵에 표시할 포인트들 정하기
        mapPoints = new HashMap<>();
        mapPoints.put("투썸플레이스", new LatLng(36.374140, 127.365396));
        mapPoints.put("카페 그랑", new LatLng(36.373714, 127.358998));
        mapPoints.put("헨델과 그레텔", new LatLng(36.372565, 127.358698));
        mapPoints.put("커피빈", new LatLng(36.367546, 127.360382));
        mapPoints.put("스무디킹", new LatLng(36.365861, 127.361251));
        mapPoints.put("뚜레쥬르", new LatLng(36.370215, 127.363665));
        mapPoints.put("오가다", new LatLng(36.369095, 127.362828));
        mapPoints.put("던킨도너츠", new LatLng(36.368573, 127.364515));
        mapPoints.put("망고식스", new LatLng(36.368289, 127.363482));
        mapPoints.put("드롭탑", new LatLng(36.369954, 127.360039));

        //지도에 카페 장소와 이름, 카페 모양 아이콘까지 추가
        Iterator<String> pointNames = mapPoints.keySet().iterator();
        while(pointNames.hasNext()){
            String key = pointNames.next();
            LatLng value = mapPoints.get(key);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(value);
            markerOptions.title(key);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            map.addMarker(markerOptions);
        }

        //맵에 포인트 클릭하면 나타날 정보 처리를 위해 리스트에 가격 넣어두기
        mapPointsPrice = new HashMap<>();
        mapPointsPrice.put("투썸플레이스", Arrays.asList("연락처--042-350-0871//위치--김병호·김삼열 IT 융합빌딩(N1) 2층//영업시간--평일: 08:00~24:00 / 주말: 08:00~22:00 / 설·추석을 제외한 공휴일 정상 영업//제공메뉴--커피, 샌드위치, 기아치오, 로네펠트티, 아침메뉴(토스트, 잉글리시머핀, 베이글) 등".split("//")));
        mapPointsPrice.put("카페 그랑", Arrays.asList("연락처--042-861-0419//위치--북측 학생식당(카이마루/N11)//영업시간--평일: 08:30~20:00(last order 19:30) / 토요일:10:00~19:30(last order 19:00) / 일요일 및 공휴일 휴무//제공메뉴--커피, 스무디, 샌드위치, 샐러드, 쿠키 등".split("//")));
        mapPointsPrice.put("헨델과 그레텔", Arrays.asList("연락처--042-350-0851//위치--기계공학동(N7) 로비//영업시간--평일: 08:00~24:00 / 주말: 08:00~22:00 / 설·추석을 제외한 공휴일 정상 영업//제공메뉴--커피, 샌드위치, 기아치오, 로네펠트티, 아침메뉴(토스트, 잉글리시머핀, 베이글) 등".split("//")));
        mapPointsPrice.put("커피빈", Arrays.asList("연락처--042-867-3751//위치--인터네셔널센터(W2-1) 1층//영업시간--평일 및 주말:09:00~22:30//제공메뉴--커피, 각종 Tea, 블랜디드, 머핀·베이글·케이크 등 베이커리 메뉴".split("//")));
        mapPointsPrice.put("스무디킹", Arrays.asList("연락처--042-350-0866//위치--응용공학동(W2) 1층//영업시간--평일 및 주말:08:00~23:00//제공메뉴--각종 스무디 및 샌드위치류".split("//")));
        mapPointsPrice.put("뚜레쥬르", Arrays.asList("연락처--042-350-0899//위치--자연과학동 궁리실험관(E6-5) 1층//영업시간--평일 및 주말: 07:00~23:00//제공메뉴--빵, 케이크, 커피, 음료 등".split("//")));
        mapPointsPrice.put("오가다", Arrays.asList("연락처--042-350-0863//위치--학술문화관(E9) 2층//영업시간--평일, 주말, 공휴일 08:30~21:30//제공메뉴--블렌딩티, 쥬스, 스무디, 티 라떼, 커피, 팥빙수, 떡, 디저트류 등".split("//")));
        mapPointsPrice.put("던킨도너츠",Arrays.asList("연락처--042-350-7607//위치--정보전자공학동(E3-2) 1층//영업시간--평일 및 주말: 07:00 ~ 23:00 / 공휴일 정상 영업. 설·추석 휴무//제공메뉴--도너츠, 음료, 핫브레드(오전 11:00 까지 커피 음료를 함께 구매 시 1,000원 할인) 등".split("//")));
        mapPointsPrice.put("망고식스", Arrays.asList("연락처--042-350-0896//위치--KI빌딩(E4) 1층//영업시간--평일: 08:30~21:30 / 주말 및 공휴일: 10:30~18:00 / 방학 기간: 11:30~18:00//제공메뉴--망고쥬스, 커피, 티, 버블티, 디저트 등".split("//")));
        mapPointsPrice.put("드롭탑", Arrays.asList("연락처--042-350-0890//위치--교육지원동(W8) 1층//영업시간--평일: 08:00~22:00 / 공휴일 10:00 ~ 21:00 / 주말 휴무//제공메뉴--커피, 디저트, 쥬스류 등".split("//")));
    }

}
