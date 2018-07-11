package com.example.q.maptest;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    protected BottomNavigationView navigationView;
    private GridView mGridView;
    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 1;
    Button button;
    Button button1;
    ProgressDialog pd;
    GridView gv;
    private Uri imageUri;
    private ArrayList<String> img;
    private static final int REQUEST_OPEN_RESULT_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //checkPermission();

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);


        //gv= (GridView) findViewById(R.id.gridView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_OPEN_RESULT_CODE);


            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new JsonTask().execute("http://52.231.69.217:8080/iget");

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == REQUEST_OPEN_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                imageUri = resultData.getData();
                try {
                    String imagePath = uri.toString();
                    Bitmap bitmap = getBitmapFromUri(uri);
                    BitmapToString(bitmap);
                    /*BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap orgImage = BitmapFactory.decodeFile(imagePath, options);
                    Bitmap resize = Bitmap.createScaledBitmap(orgImage, 100, 100, true);*/
                    //new PostTask().execute("http://52.231.69.217:8080/iupload",BitmapToString(bitmap));
                    new PostTask().execute("http://52.231.69.217:8080/iupload", BitmapToString(resizeBitmapImage(bitmap, 480)));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*Glide.with(this)
                        .load(uri)
                        .into(imageView);*/
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return bitmap;
    }

    public static Bitmap getResizedBitmap(Resources resources, int id, int size, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        Bitmap src = BitmapFactory.decodeResource(resources, id, options);
        Bitmap resized = Bitmap.createScaledBitmap(src, width, height, true);
        return resized;
    }

    public static String BitmapToString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (NullPointerException e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private class PostTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("image", params[1]);
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
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    public Bitmap resizeBitmapImage(Bitmap source, int maxResolution) {
        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth = width;
        int newHeight = height;
        float rate = 0.0f;

        if (width > height) {
            if (maxResolution < width) {
                rate = maxResolution / (float) width;
                newHeight = (int) (height * rate);
                newWidth = maxResolution;
            }
        } else {
            if (maxResolution < height) {
                rate = maxResolution / (float) height;
                newWidth = (int) (width * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }


    private Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity2.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            img = new ArrayList<String>();
            try {
                JSONArray response = new JSONArray(result);
                for (int i = 0; i < response.length(); i++) {
                    JSONObject imageobject = response.getJSONObject(i);
                    String im = imageobject.getString("image");
                    img.add(im);
                }
                gv = (GridView) findViewById(R.id.gridView);
                gv.setAdapter(new ImageAdapterGridView(MainActivity2.this,img));



                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.full_image, img);
                //gv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    /*rivate void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            // requestPermissions => call onRequestPermissionsResult
        } else {
            gallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    gallery();
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request
        }
    }*/



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
        return R.layout.activity_photo;
    }

    //@Override
    int getNavigationMenuItemId() {
        return R.id.navigation_photos;
    }

}
