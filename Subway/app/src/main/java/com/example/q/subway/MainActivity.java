package com.example.q.subway;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    int price;
    int calorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*Intent intent = getIntent();
        final String uid = intent.getExtras().getString("userID");*/
        Toast.makeText(getApplicationContext(), LoadingActivity.global_uid, Toast.LENGTH_SHORT).show();

        //All ingredients
        final String[] breadItems = new String[]{"Honey Oat", "Hearty Italian", "Wheat", "Parmesan Oregano", "White", "Flat Bread"};
        final String[] mainItems = new String[]{"Spicy Italian Avocado", "Turkey Bacon Avocado", "Veggie Avocado", "Ham", "Roasted Beef", "B.L.T.", "Turkey", "Turkey Bacon", "Italian B.M.T."};
        final String[] cheeseItems = new String[]{"No Cheese","American", "Shredded"};
        final String[] vegeItems = new String[]{"Lettuce", "Tomatoes", "Cucumbers", "Red Onions", "Peppers", "Pickles", "Olives", "Jalapeno"};
        final String[] sauceItems = new String[]{"Ranch", "Mayonnaise", "Sweet Onion", "Sweet Chili", "Hot Chili",
                "Chipotle", "Yellow Mustard", "Honey Mustard", "Horseradish", "Thousand Island", "Italian Dressing", "Olive Oil",
                "Red Wine Vinegar", "Smoke BBQ", "Salt", "Black Pepper" };
        final String[] extraItems = new String[]{"Double Up", "EggMayo", "Omelet", "Avocado", "Bacon", "Pepperoni", "Double Cheese"};
        final String[] isPublicItems = new String[]{"나만 안다", "공개한다",};

        //Initialization
        final List<String> selectedBread = new ArrayList<>();
        selectedBread.add(breadItems[0]);
        final List<String> selectedMain = new ArrayList<>();
        selectedMain.add(mainItems[0]);
        final List<String> selectedCheese = new ArrayList<>();
        selectedCheese.add(cheeseItems[0]);
        final List<String> selectedVeges = new ArrayList<>();
        final List<String> selectedSauce = new ArrayList<>();
        final List<String> selectedExtra = new ArrayList<>();
        final List<String> isPublic = new ArrayList<>();
        isPublic.add(isPublicItems[0]);

        final ImageView lettuce = findViewById(R.id.lettuce);
        final ImageView tomato = findViewById(R.id.tomato);
        final ImageView pepper = findViewById(R.id.pepper);
        final ImageView olive = findViewById(R.id.olive);
        final ImageView pickle = findViewById(R.id.pickle);
        final ImageView jalapeno = findViewById(R.id.jalapeno);
        final ImageView onion = findViewById(R.id.onion);
        final ImageView cucumber = findViewById(R.id.cucumber);
        final ImageView main = findViewById(R.id.main);
        final ImageView main2 = findViewById(R.id.main2);
        final ImageView top = findViewById(R.id.top);
        final ImageView bottom = findViewById(R.id.bottom);
        final ImageView cheese = findViewById(R.id.cheese);

        Button btnBread = (Button) findViewById(R.id.btnBread);
        btnBread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog  .setTitle("Bread 고르세요")
                        .setSingleChoiceItems(breadItems,0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedBread.clear();
                                selectedBread.add(breadItems[which]);
                            }
                        })
                        .setPositiveButton("ㅇㅋ", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), selectedBread.get(0), Toast.LENGTH_SHORT).show();
                                top.setVisibility(View.VISIBLE);
                                bottom.setVisibility(View.VISIBLE);
                                switch (selectedBread.get(0)){
                                    case "Honey Oat":
                                        top.setImageResource(R.drawable.honey_oat_top);
                                        bottom.setImageResource(R.drawable.honey_oat_bottom);
                                        break;
                                    case"Hearty Italian":
                                        top.setImageResource(R.drawable.hearty_top);
                                        bottom.setImageResource(R.drawable.hearty_bottom);
                                        break;
                                    case "Wheat":
                                        top.setImageResource(R.drawable.wheat_top);
                                        bottom.setImageResource(R.drawable.wheat_bottom);
                                        break;
                                    case "Parmesan Oregano":
                                        top.setImageResource(R.drawable.parmasean_oregano_top);
                                        bottom.setImageResource(R.drawable.parmaesan_oregano_bottom);
                                        break;
                                    case "White":
                                        top.setImageResource(R.drawable.white_top);
                                        bottom.setImageResource(R.drawable.white_bottom);
                                        break;
                                    case "Flat Bread":
                                        top.setImageResource(R.drawable.flat_bread_top);
                                        bottom.setImageResource(R.drawable.flat_bread_bottom);
                                        break;
                                    default:
                                        break;
                                }
                            } }).create().show();

            }
        });


        Button btnMain = findViewById(R.id.btnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog  .setTitle("Main Ingredient 정하세요")
                        .setSingleChoiceItems(mainItems,0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedMain.clear();
                                selectedMain.add(mainItems[which]);
                            }
                        })
                        .setPositiveButton("ㅇㅋ", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                Resources res = getResources();
                                switch(selectedMain.get(0)) {
                                    case"Italian B.M.T.":
                                    case "Spicy Italian Avocado":
                                        main.setImageResource(R.drawable.pepperoni);
                                        main.setVisibility(View.VISIBLE);
                                        main2.setImageResource(R.drawable.ham);
                                        main2.setVisibility(View.VISIBLE);
                                        break;
                                    case "Turkey Bacon":
                                    case "Turkey Bacon Avocado":
                                        main.setImageResource(R.drawable.turkey);
                                        main.setVisibility(View.VISIBLE);
                                        main2.setImageResource(R.drawable.bacon);
                                        main2.setVisibility(View.VISIBLE);
                                        break;

                                    case "Ham":
                                        main.setImageResource(R.drawable.ham);
                                        main.setVisibility(View.VISIBLE);
                                        break;
                                    case "Roasted Beef":
                                        main.setImageResource(R.drawable.roasted_beef);
                                        main.setVisibility(View.VISIBLE);
                                        break;
                                    case"B.L.T.":
                                        main.setImageResource(R.drawable.bacon);
                                        main.setVisibility(View.VISIBLE);
                                        break;
                                    case "Turkey":
                                        main.setImageResource(R.drawable.turkey);
                                        main.setVisibility(View.VISIBLE);
                                        break;
                                    default:
                                        break;
                                }
                            } }).create().show();

            }
        });


        Button btnCheese = findViewById(R.id.btnCheese);
        btnCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog  .setTitle("Cheese를 고르세요")
                        .setSingleChoiceItems(cheeseItems,0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedCheese.clear();
                                selectedCheese.add(cheeseItems[which]);
                            }
                        })
                        .setPositiveButton("ㅇㅋ", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                cheese.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), selectedCheese.get(0), Toast.LENGTH_SHORT).show(); } }).create().show();

            }
        });

        Button btnVege =  findViewById(R.id.btnVege);
        btnVege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                selectedVeges.clear();
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog  .setTitle("Vegetable 고르세요")
                        .setMultiChoiceItems(vegeItems,
                                new boolean[]{false, false, false, false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(isChecked) {
                                            Toast.makeText(getApplicationContext(), vegeItems[which], Toast.LENGTH_SHORT).show();
                                            selectedVeges.add(vegeItems[which]);
                                        } else {
                                            selectedVeges.remove(vegeItems[which]);
                                        }
                                    }
                                })
                        .setPositiveButton("ㅇㅋ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if( selectedVeges.size() == 0) {
                                    Toast.makeText(getApplicationContext(), "Vegetable 먹어야 건강하다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String items = "";
                                    for (String selectedItem : selectedVeges) {
                                        items += (selectedItem + ", ");
                                    }


                                    items = items.substring(0, items.length() - 2);
                                    Toast.makeText(getApplicationContext(), items, Toast.LENGTH_SHORT).show();
                                }
                                for (String selectedItem: selectedVeges){
                                    Log.d("sauce", selectedItem);
                                    switch(selectedItem) {
                                        case "Lettuce":
                                            lettuce.setVisibility(View.VISIBLE);
                                            break;
                                        case "Tomatoes":
                                            tomato.setVisibility(View.VISIBLE);
                                            break;
                                        case "Cucumbers":
                                            cucumber.setVisibility(View.VISIBLE);
                                            break;
                                        case "Red Onions":
                                            onion.setVisibility(View.VISIBLE);
                                            break;
                                        case "Peppers":
                                            pepper.setVisibility(View.VISIBLE);
                                            break;
                                        case "Pickles":
                                            pickle.setVisibility(View.VISIBLE);
                                            break;
                                        case "Olives":
                                            olive.setVisibility(View.VISIBLE);
                                            break;
                                        case "Jalapeno":
                                            jalapeno.setVisibility(View.VISIBLE);
                                            break;
                                        default:
                                            break;
                                    }
                                }

                            }
                        }).create().show();
            }
        });


        Button btnSauce =  findViewById(R.id.btnSauce);
        btnSauce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                selectedSauce.clear();
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog  .setTitle("Sauce 고르세요")
                        .setMultiChoiceItems(sauceItems,
                                new boolean[]{false, false, false, false, false, false, false, false,
                                        false, false, false, false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(isChecked) {
                                            Toast.makeText(getApplicationContext(), sauceItems[which], Toast.LENGTH_SHORT).show();
                                            selectedSauce.add(sauceItems[which]);
                                        } else {
                                            selectedSauce.remove(sauceItems[which]);
                                        }
                                    }
                                })
                        .setPositiveButton("ㅇㅋ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if( selectedSauce.size() == 0) {
                                    Toast.makeText(getApplicationContext(), "Sauce 없이 먹나?", Toast.LENGTH_SHORT).show();
                                } else {
                                    String items = "";
                                    for (String selectedItem : selectedSauce) {
                                        items += (selectedItem + ", ");
                                    }
                                    items = items.substring(0, items.length() - 2);
                                    Toast.makeText(getApplicationContext(), items, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).create().show();
            }
        });


        Button btnExtra = findViewById(R.id.btnExtra);
        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectedExtra.clear();
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog  .setTitle("Extra topping 고르세요")
                        .setMultiChoiceItems(extraItems,
                                new boolean[]{false, false, false, false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(isChecked) {
                                            Toast.makeText(getApplicationContext(), extraItems[which], Toast.LENGTH_SHORT).show();
                                            selectedExtra.add(extraItems[which]);
                                        } else {
                                            selectedExtra.remove(extraItems[which]);
                                        }
                                    }
                                })
                        .setPositiveButton("ㅇㅋ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if( selectedExtra.size() == 0) {
                                    Toast.makeText(getApplicationContext(), "추가 항목 없다", Toast.LENGTH_SHORT).show();
                                } else {
                                    String items = "";
                                    for (String selectedItem : selectedExtra) {
                                        items += (selectedItem + ", ");
                                    }

                                    items = items.substring(0, items.length() - 2);
                                    Toast.makeText(getApplicationContext(), items, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).create().show();
            }
        });

        Button btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price = 0;
                calorie = 0;
                switch(selectedMain.get(0)) {
                    case "Spicy Italian Avocado":
                        price += 6700;
                        calorie +=540;
                        break;
                    case "Turkey Bacon Avocado":
                        price += 6300;
                        calorie +=420;
                        break;
                    case "Veggie Avocado":
                        price += 5000;
                        calorie += 290;
                        break;
                    case "Ham":
                        price += 4700;
                        calorie += 290;
                        break;
                    case"Roasted Beef":
                        price += 5800;
                        calorie += 320;
                        break;
                    case "B.L.T.":
                        price += 4900;
                        calorie += 380;
                        break;
                    case "Turkey":
                        price += 4900;
                        calorie += 280;
                        break;
                    case "Turkey Bacon":
                        price += 5300;
                        calorie += 360;
                        break;
                    case "Italian B.M.T.":
                        price += 4900;
                        calorie += 410;
                        break;
                    default:
                        break;
                }

                for (String selectedItem: selectedExtra){
                    Log.d("extra item", selectedItem);
                    switch(selectedItem) {
                        case "Double Up":
                            price += 1500;
                            calorie += calorie;
                            break;
                        case "EggMayo":
                            price += 1500;
                            calorie += 0;
                            break;
                        case "Omelet":
                            price += 1100;
                            calorie += 0;
                            break;
                        case "Avocado":
                            price += 1100;
                            calorie += 0;
                            break;
                        case "Bacon":
                            price += 900;
                            calorie += 0;
                            break;
                        case "Pepperoni":
                            price += 800;
                            calorie += 0;
                            break;
                        case "Double Cheese":
                            price += 800;
                            calorie += 0;
                            break;
                        default:
                            break;
                    }
                }

                switch(selectedBread.get(0)) {
                    case "Honey Oat" :
                    case "Flat Bread":
                        calorie += 20 ;
                        break;
                    default:
                        break;
                }

                calorie -= 40 - 5* selectedVeges.size();

                if (!selectedCheese.isEmpty()) {
                    switch(selectedCheese.get(0)) {
                        case "American" :
                            calorie += 40;
                            break;
                        case "Shredded":
                            calorie += 50 ;
                        default:
                            break;
                    }
                }

                for (String selectedItem: selectedSauce){
                    Log.d("sauce", selectedItem);
                    switch(selectedItem) {
                        case "Ranch":
                            calorie += 110;
                            break;
                        case "Mayonnaise":
                            calorie += 110;
                            break;
                        case "Sweet Onion":
                            calorie += 40;
                            break;
                        case "Sweet Chili":
                            calorie += 30;
                            break;
                        case "Hot Chili":
                            calorie += 40;
                            break;
                        case "Chipotle":
                            calorie += 100;
                            break;
                        case "Yellow Mustard":
                            calorie += 5;
                            break;
                        case "Thousand Island":
                            calorie += 80;
                            break;
                        case "Italian Dressing":
                            calorie += 80;
                            break;
                        case "Olive Oil":
                            calorie += 45;
                            break;
                        case "Red Wine Vinegar":
                            calorie += 40;
                            break;
                        case "Smoke BBQ":
                            calorie += 30;
                            break;
                        case "Salt":
                            calorie += 0;
                            break;
                        case "Black Pepper":
                            calorie += 0;
                            break;
                        default:
                            break;
                    }
                }

                TextView summary = findViewById(R.id.summary);
                summary.setText( price +"원, "+ calorie+"kcal");
            }
        });

        EditText userInput = findViewById(R.id.editText);
        userInput.setText(null);
        userInput.setHint("Enter");

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog  .setTitle("조합법을 공개할건가")
                        .setSingleChoiceItems(isPublicItems,0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isPublic.clear();
                                isPublic.add(isPublicItems[which]);
                            }
                        })
                        .setPositiveButton("결정", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), isPublic.get(0), Toast.LENGTH_SHORT).show();


                                //make a json array and a network request to upload this recipe to the server.
                                String bread = selectedBread.get(0);
                                String main = selectedMain.get(0);
                                String cheese = selectedCheese.get(0);
                                String vege = "";
                                String sauce = "";
                                String extra = "";
                                EditText userInput = findViewById(R.id.editText);
                                String title = userInput.getText().toString();

                                for (String selectedItem: selectedVeges){
                                    vege = vege +","+ selectedItem;
                                }
                                for (String selectedItem: selectedSauce){
                                    sauce = sauce +","+ selectedItem;
                                }
                                for (String selectedItem: selectedExtra){
                                    extra = extra +","+ selectedItem;
                                }

                                JSONArray jsonArray = new JSONArray();
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.accumulate("title", title);
                                    jsonObject.accumulate("bread", bread);
                                    jsonObject.accumulate("main", main);
                                    jsonObject.accumulate("cheese", cheese);
                                    jsonObject.accumulate("vege", vege);
                                    jsonObject.accumulate("sauce", sauce );
                                    jsonObject.accumulate("extra", extra);
                                    jsonObject.accumulate("price", ""+price );
                                    jsonObject.accumulate("calorie", ""+calorie );
                                    jsonObject.accumulate("isPublic", isPublic.get(0) );
                                    jsonObject.accumulate("like", ""+0);
                                    jsonObject.accumulate("creator", LoadingActivity.global_uid);
                                    jsonArray.put(jsonObject);
                                } catch (Exception e) {
                                    Log.d("exception", e.getMessage());
                                }
                                String message;
                                try{ message = jsonArray.getString(0); }catch (Exception e){ message =  "empty";}
                                Log.d("jsonarray(0)", message);
                                NetworkTask networkTask = new NetworkTask("api/recipes", "post", null, jsonArray);
                                networkTask.execute();


                            } }).create().show();

            }
        });

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_simulator:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.hold, R.anim.fade_in);
                    return true;
                case R.id.navigation_mySubway:
                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                    overridePendingTransition(R.anim.hold, R.anim.fade_in);
                    return true;
                case R.id.navigation_top100:
                    startActivity(new Intent(getApplicationContext(), MainActivity3.class));
                    overridePendingTransition(R.anim.hold, R.anim.fade_in);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }
    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }
    int getNavigationMenuItemId() {
        return R.id.navigation_simulator;
    }
}
