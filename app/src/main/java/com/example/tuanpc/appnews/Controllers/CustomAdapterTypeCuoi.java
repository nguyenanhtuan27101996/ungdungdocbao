package com.example.tuanpc.appnews.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanpc.appnews.Models.TypeCuoi;
import com.example.tuanpc.appnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tuanpc on 9/24/2017.
 */

public class CustomAdapterTypeCuoi extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<TypeCuoi>arrayListCuoi;

    public CustomAdapterTypeCuoi(Context context, int layout, ArrayList<TypeCuoi> arrayListCuoi) {
        this.context = context;
        this.layout = layout;
        this.arrayListCuoi = arrayListCuoi;
    }

    @Override
    public int getCount() {
        return arrayListCuoi.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        ImageView imgNews;
        TextView txtTieuDeNews;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater= (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            viewHolder=new ViewHolder();

            //anh xa
            viewHolder.imgNews=view.findViewById(R.id.imgNews);
            viewHolder.txtTieuDeNews=view.findViewById(R.id.txtTieuDeNews);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
            TypeCuoi objCuoi=arrayListCuoi.get(i);

            viewHolder.txtTieuDeNews.setText(objCuoi.getTitle());
            Picasso.with(context).load(objCuoi.getImage()).into(viewHolder.imgNews);
        }

        return view;
    }
}
