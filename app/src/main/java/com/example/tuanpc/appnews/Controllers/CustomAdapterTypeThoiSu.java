package com.example.tuanpc.appnews.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanpc.appnews.Models.TypeThoiSu;
import com.example.tuanpc.appnews.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by tuanpc on 9/23/2017.
 */

public class CustomAdapterTypeThoiSu extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<TypeThoiSu>arrayListTypeThoiSu;

    public CustomAdapterTypeThoiSu(Context context, int layout, ArrayList<TypeThoiSu> arrayListTypeThoiSu) {
        this.context = context;
        this.layout = layout;
        this.arrayListTypeThoiSu = arrayListTypeThoiSu;
    }

    @Override
    public int getCount() {
        return arrayListTypeThoiSu.size();
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
        ImageView imgThoiSu;
        TextView txtThoiSu;

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
            viewHolder.imgThoiSu=view.findViewById(R.id.imgNews);
            viewHolder.txtThoiSu=view.findViewById(R.id.txtTieuDeNews);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();

        }
        TypeThoiSu objThoiSu=arrayListTypeThoiSu.get(i);
        viewHolder.txtThoiSu.setText(objThoiSu.getTitle());
        Picasso.with(context).load(objThoiSu.getImage()).into(viewHolder.imgThoiSu);
        return view;

    }
}
