package com.example.q.contactpractice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{
    private ArrayList<ListViewObject> listVO = new ArrayList<ListViewObject>() ;
    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listVO.size() ;
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //postion = ListView의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.custom_img) ;
        TextView title = (TextView) convertView.findViewById(R.id.custom_title) ;
        TextView Context = (TextView) convertView.findViewById(R.id.custom_context) ;


        ListViewObject listViewItem = listVO.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        image.setImageBitmap(listViewItem.getImg());
        title.setText(listViewItem.getTitle());
        Context.setText(listViewItem.getContext());


        //리스트뷰 클릭 이벤트
        convertView.setOnClickListener(view -> {
            Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            String tel = "tel:" + listViewItem.getContext();
            intent.setData(Uri.parse(tel));
            context.startActivity(intent);

        });


        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public Object getItem(int position) {
        return listVO.get(position) ;
    }

    // 데이터값 넣어줌
    public void addVO(Bitmap icon, String title, String desc) {
        ListViewObject item = new ListViewObject();

        item.setImg(icon);
        item.setTitle(title);
        item.setContext(desc);

        listVO.add(item);
    }
}