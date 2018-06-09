package com.example.tuanpc.appnews.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanpc.appnews.Models.TypeXe;
import com.example.tuanpc.appnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tuanpc on 9/24/2017.
 */

public class CustomAdapterTypeXe extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<TypeXe>arrayListXe;

    public CustomAdapterTypeXe(Context context, int layout, ArrayList<TypeXe> arrayListXe) {
        this.context = context;
        this.layout = layout;
        this.arrayListXe = arrayListXe;
    }

    @Override
    public int getCount() {
        return arrayListXe.size();
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
            TypeXe objXe=arrayListXe.get(i);
            viewHolder.txtTieuDeNews.setText(objXe.getTitle());
            Picasso.with(context).load(objXe.getImage()).into(viewHolder.imgNews);
        }
        return view;
    }
}
