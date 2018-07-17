package com.example.q.subway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeListAdapter extends BaseAdapter {
    int groupid;
    JSONArray recipeIdList;
    Context context;
    String recipe;

    public RecipeListAdapter(Context context, int vg, String recipe){
        this.context=context;
        groupid=vg;
        this.recipe = recipe;
        //this.recipeIdList = recipeIdListList;
    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView titleView;
        public TextView priceView;
        public TextView likeCountView;
        public TextView calorieView;
        public String recipe;
        public int position;
    }
    public Object getItem(int position) {
        try {
            return  recipeIdList.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }
    public int getCount() {
        try {
            JSONArray jsonArray = new JSONArray(this.recipe);
            return jsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // Inflate the list_item.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleView = rowView.findViewById(R.id.title);
            viewHolder.calorieView = rowView.findViewById(R.id.calorie);
            viewHolder.likeCountView =  rowView.findViewById(R.id.likeCount);
            viewHolder.priceView =  rowView.findViewById(R.id.price);
            viewHolder.recipe = this.recipe;
            viewHolder.position = position;
            rowView.setTag(viewHolder);
        }
        // Set text to each TextView of ListView item
        try {
            JSONArray jsonArray = new JSONArray(this.recipe);
            JSONObject jsonObject = (JSONObject) jsonArray.get(position);
            ViewHolder viewHolder = (ViewHolder) rowView.getTag();

            viewHolder.titleView.setText(jsonObject.getString("title"));
            viewHolder.calorieView.setText(jsonObject.getString("calorie"));
            viewHolder.likeCountView.setText(jsonObject.getString("like"));
            viewHolder.priceView.setText(jsonObject.getString("price"));
            viewHolder.position = position;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rowView;
    }
}
