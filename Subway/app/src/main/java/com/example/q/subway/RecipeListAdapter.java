package com.example.q.subway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

    public RecipeListAdapter(Context context, int vg){
        this.context=context;
        groupid=vg;
        //this.recipeIdList = recipeIdListList;
    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView titleView;
        public TextView priceView;
        public TextView likeCountView;
        public TextView calorieView;
        public String recipe_id;
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
        return 10;
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
            rowView.setTag(viewHolder);
        }
        // Set text to each TextView of ListView item

        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        /*viewHolder.titleView.setText(viewHolder.name);
        viewHolder.calorieView.setText(viewHolder.phoneNum);
        viewHolder.likeView.setText(viewHolder.name);
        viewHolder.priceView.setText(viewHolder.phoneNum);*/
        return rowView;
    }
}
