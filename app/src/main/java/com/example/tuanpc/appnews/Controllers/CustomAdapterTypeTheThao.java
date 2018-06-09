package com.example.tuanpc.appnews.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanpc.appnews.Models.TypeStartUp;
import com.example.tuanpc.appnews.Models.TypeTheThao;
import com.example.tuanpc.appnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tuanpc on 9/24/2017.
 */

public class CustomAdapterTypeTheThao extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<TypeTheThao>arrayListTypeTheThao;

    public CustomAdapterTypeTheThao(Context context, int layout, ArrayList<TypeTheThao> arrayListTypeTheThao) {
        this.context = context;
        this.layout = layout;
        this.arrayListTypeTheThao = arrayListTypeTheThao;
    }

    @Override
    public int getCount() {
        return arrayListTypeTheThao.size();
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
            LayoutInflater inflater=
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            viewHolder=new ViewHolder();

            //anh xa
            viewHolder.imgNews=view.findViewById(R.id.imgNews);
            viewHolder.txtTieuDeNews=view.findViewById(R.id.txtTieuDeNews);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();

            TypeTheThao objTheThao=arrayListTypeTheThao.get(i);

            viewHolder.txtTieuDeNews.setText(objTheThao.getTitle());
            Picasso.with(context).load(objTheThao.getImage()).into(viewHolder.imgNews);
        }
        return view;
    }
}
