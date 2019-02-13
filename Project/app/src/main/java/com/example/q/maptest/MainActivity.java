package com.example.q.maptest;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    CallbackManager callbackManager;
    protected BottomNavigationView navigationView;
    public static final String url = "";
    private Button postbutton;
    private Button getbutton;
    TextView txtJson;
    private ListView listviewContact;
    ProgressDialog pd;
    ArrayList<String> StoreContacts;
    ArrayList<String> StoreContacts2;
    ArrayAdapter<String> arrayAdapter;
    Cursor cursor;
    String name, phonenumber;
    EditText editSearch;
    public static final int RequestPermissionCode = 1;
    List<Document> documents = new ArrayList<Document>();
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private static String get = "http://52.231.69.217:8080/get";
    ArrayList<HashMap<String,String>> contactlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        //ConnectServer connectServer = new ConnectServer();
        //connectServer.requestGet(url, "search Key에 들어갈 변수");

        contactlist = new ArrayList<>();
        getbutton = (Button) findViewById(R.id.button1);
        postbutton = (Button) findViewById(R.id.button);
        lv = (ListView) findViewById(R.id.listviewContacts);
        //txtJson = (TextView) findViewById(R.id.tvJsonItem);
        StoreContacts = new ArrayList<String>();
        StoreContacts2 = new ArrayList<String>();

        ArrayList<HashMap<String, String>> contactList;
        contactList = new ArrayList<>();

        getbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Contact download", Toast.LENGTH_SHORT).show();
                //new JsonTask().execute("http://52.231.69.217:8080/get");
                new GetContacts().execute();
            }
        });

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Contact backup", Toast.LENGTH_SHORT).show();
                    GetContactsIntoArrayList();
                    //listviewContact.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                    //        R.layout.contact_items_listview, R.id.textView, StoreContacts));
                    //postbutton.setEnabled(false);
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                        Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 123);
                }
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Canceled", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_LONG).show();
            }
        });

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean checkandRequestPermissions() {
        int permissioncallPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        List<String> listPermissionNeeded = new ArrayList<>();
        if (permissioncallPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), 1);
            return false;
        }
        return true;
    }

    public void GetContactsIntoArrayList() {
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //StoreContacts.add(new String(Character.toChars(0x1F92A)) + new String(Character.toChars(0x1F37B)) + new String(Character.toChars(0x1F4DE)) + "    " + name);
                //StoreContacts2.add(phonenumber);
                new PostTask().execute("http://52.231.69.217:8080/upload",name,phonenumber);
                //POST("http://52.231.69.217:8080/upload",name,phonenumber);
            }
            cursor.close();
        }
    }

    private class PostTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name",params[1]);
                jsonObject.accumulate("number",params[2]);
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

    /*private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
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
                    buffer.append(line+"\n");
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
            if (pd.isShowing()){
                pd.dismiss();
            }
            txtJson.setText(result);
        }
    }*/

    private class GetContacts extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
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
                    JSONArray contacts = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("_id");
                        String name = c.getString("name");
                        String number = c.getString("number");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();
                        // adding each child node to HashMap key => value
                        contact.put("_id", id);
                        contact.put("name", name);
                        contact.put("number", number);
                        // adding contact to contact list
                        contactlist.add(contact);
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
                    MainActivity.this, contactlist,
                    R.layout.contact_items_listview, new String[]{"name", "number"}, new int[]{R.id.n,
                    R.id.number});
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String mNum, tel;
                    HashMap<String, String> map = new HashMap<>();
                    map = (HashMap<String, String>) contactlist.get(position);
                    mNum = map.get("number");
                    tel = "tel:" + mNum;

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(tel));
                    startActivity(intent);
                }
            });
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
            } else if (itemId == R.id.navigation_map) {
                startActivity(new Intent(this, MainActivity3.class));
                overridePendingTransition(R.anim.hold, R.anim.fade_in);
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

//    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }
//    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_contact;
    }

}
