package com.example.tuanpc.appnews.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuanpc.appnews.Models.NewsKinhDoanh;
import com.example.tuanpc.appnews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tuanpc on 9/23/2017.
 */

public class CustomAdapterNewsKinhDoanh extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<NewsKinhDoanh> arrayListNewsKinhDoanh;

    public CustomAdapterNewsKinhDoanh(Context context, int layout, ArrayList<NewsKinhDoanh> arrayListNewsKinhDoanh) {
        this.context = context;
        this.layout = layout;
        this.arrayListNewsKinhDoanh = arrayListNewsKinhDoanh;
    }

    @Override
    public int getCount() {
        return arrayListNewsKinhDoanh.size();
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
        ImageView imgNewsKinhDoanh;
        TextView txtNewsKinhDoanh;
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
            viewHolder.imgNewsKinhDoanh=view.findViewById(R.id.imgNews);
            viewHolder.txtNewsKinhDoanh=view.findViewById(R.id.txtTieuDeNews);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        NewsKinhDoanh objNewsKinhDoanh = arrayListNewsKinhDoanh.get(i);
        viewHolder.txtNewsKinhDoanh.setText(objNewsKinhDoanh.getTitle());
        Picasso.with(context).load(objNewsKinhDoanh.getImage()).into(viewHolder.imgNewsKinhDoanh);
        return view;
    }
}
