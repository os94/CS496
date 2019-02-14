package com.example.q.contactpractice;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    protected BottomNavigationView navigationView;
    private ArrayList<Bitmap> photoList;
    private ArrayList<Map<String, String>> dataList;
    private ListView mListview;
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListview = (ListView) findViewById(R.id.listview);
        checkPermission();

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            // 권한 없음, 달라고 요청
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else{
            // 권한 있음
            contact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    contact();
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_contact) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(R.anim.hold, R.anim.fade_in);
            } else if (itemId == R.id.navigation_photos) {
                startActivity(new Intent(this, MainActivity2.class));
                overridePendingTransition(R.anim.hold, R.anim.fade_in);
            } else if (itemId == R.id.navigation_alarm) {
                startActivity(new Intent(this, MainActivity3.class));
                overridePendingTransition(R.anim.hold, R.anim.fade_in);
            }
            finish();
        }, 300);
        return true;
    }


    public void contact(){
        dataList = new ArrayList<Map<String, String>>();
        photoList = new ArrayList<Bitmap>();
        Bitmap bitmapdraw=(Bitmap) BitmapFactory.decodeResource(getResources(), R.drawable.basicphoto);
        Cursor c = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

        while (c.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            // 연락처 id 값
            String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            // 연락처 대표 이름
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            map.put("name", name);

            //photo 가져오기
            InputStream photoDataStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));


            Bitmap photo2 = BitmapFactory.decodeStream(photoDataStream);
            if (photo2!=null){
            photoList.add(photo2);
            }else{
                photoList.add(bitmapdraw);
            }

            // ID로 전화 정보 조회
            Cursor phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null, null);

            // 데이터가 있는 경우
            if (phoneCursor.moveToFirst()) {
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                map.put("phone", number);
            }

            phoneCursor.close();
            dataList.add(map);
        }// end while
        c.close();

/*
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                dataList,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "phone"},
                new int[]{android.R.id.text1, android.R.id.text2});
        mListview.setAdapter(adapter);
*/

        //변수 초기화
        ListViewAdapter custom_adapter = new ListViewAdapter();
        //어뎁터 할당
        mListview.setAdapter(custom_adapter);

        //adapter를 통한 값 전달
        for(int i=0; i<dataList.size();i++){
            Map<String, String> onerow= (Map) dataList.get(i);
            custom_adapter.addVO(photoList.get(i),onerow.get("name"),onerow.get("phone"));
        }
//////////////////////////////////////////////////////////////////////
        /*클릭리스너는 어댑터에 넣었어요!
        mListview.setOnItemClickListener((parent, view, position, id) -> {
            String mNum;
            HashMap<String, String> map2;
            map2 = (HashMap<String, String>) dataList.get(position);
            mNum = map2.get("phone");
            String tel = "tel:" + mNum;

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(tel));
            startActivity(intent);
        });*/

    }


    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }
/*
    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();
*/

//    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

//    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_contact;
    }

}
